package com.cleveroad.nikita_frolov_cr.initialtwoweek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.ExamFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.GroupFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.MainFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.StudentFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit.EditExamFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit.EditGroupFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit.EditStudentFragment;

public class MainActivity extends AppCompatActivity implements
        StudentFragment.OnFragmentStudentListener, GroupFragment.OnFragmentGroupListener, ExamFragment.OnFragmentExamListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        deleteDatabase(UniversityDBHelper.DB_NAME);
        UniversityDBHelper universityDBHelper = UniversityDBHelper.getInstance(this);
        universityDBHelper.onCreate(universityDBHelper.getWritableDatabase());
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.llWrap, MainFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void addStudent() {
        goToFragment(EditStudentFragment.newInstance(),
                EditStudentFragment.class.getSimpleName());
    }

    @Override
    public void editStudent(Long id) {
        goToFragment(EditStudentFragment.newInstance(id),
                EditStudentFragment.class.getSimpleName());
    }

    @Override
    public void addGroup() {
        goToFragment(EditGroupFragment.newInstance(),
                EditGroupFragment.class.getSimpleName());
    }

    @Override
    public void editGroup(long id) {
        goToFragment(EditGroupFragment.newInstance(id),
                EditGroupFragment.class.getSimpleName());
    }

    @Override
    public void addExam() {
        goToFragment(EditExamFragment.newInstance(),
                EditExamFragment.class.getSimpleName());
    }


    @Override
    public void editExam(long id) {
        goToFragment(EditExamFragment.newInstance(id),
                EditExamFragment.class.getSimpleName());
    }

    private void goToFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.llWrap, fragment)
                .addToBackStack(tag)
                .commit();
    }
}
