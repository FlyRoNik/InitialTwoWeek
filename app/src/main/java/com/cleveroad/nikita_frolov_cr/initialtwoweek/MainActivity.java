package com.cleveroad.nikita_frolov_cr.initialtwoweek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.MainFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.StudentFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit.EditStudentFragment;

public class MainActivity extends AppCompatActivity implements
        StudentFragment.OnFragmentStudentListener {

    private FragmentManager mFrgManager;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase(UniversityDBHelper.DB_NAME);

        mFrgManager = getSupportFragmentManager();

        fragment = MainFragment.newInstance();
        mFrgManager
                .beginTransaction()
                .add(R.id.llWrap, fragment)
                .commit();
    }

    @Override
    public void addStudent() {
        mFrgManager
                .beginTransaction()
                .replace(R.id.llWrap, EditStudentFragment.newInstance())
                .addToBackStack("2")
                .commit();
    }

    @Override
    public void editStudent(int id) {
        mFrgManager
                .beginTransaction()
                .replace(R.id.llWrap, EditStudentFragment.newInstance(id))
                .addToBackStack("2")
                .commit();
    }
}
