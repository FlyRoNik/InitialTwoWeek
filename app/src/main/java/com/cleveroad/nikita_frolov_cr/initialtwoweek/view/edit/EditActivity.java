package com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;

public class EditActivity extends AppCompatActivity implements
        EditStudentFragment.OnFragmentStudentListener {

    private static final String KEY_STUDENT_ID = "studentID";
    private static final String KEY_STUDENT_NAME = "studentName";
    private static final String KEY_STUDENT_GROUP = "studentGroup";

    private static final String KEY_CHOOSE = "choose";
    private static final int KEY_CHOOSE_EDIT = 1;
    private static final int KEY_CHOOSE_ADD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        FragmentManager mFrgManager = getSupportFragmentManager();

        Fragment fragment = null;
        switch (getIntent().getIntExtra(KEY_CHOOSE, 0)) {
            case KEY_CHOOSE_ADD:
                fragment = EditStudentFragment.newInstance();
                break;

            case KEY_CHOOSE_EDIT:
                long id = getIntent().getLongExtra(KEY_STUDENT_ID, 0);
                String name = getIntent().getStringExtra(KEY_STUDENT_NAME);
                String group = getIntent().getStringExtra(KEY_STUDENT_GROUP);
                fragment = EditStudentFragment.newInstance(id, name, group);
                break;
        }

        if (fragment != null) {
            mFrgManager
                    .beginTransaction()
                    .add(R.id.flWrap, fragment)
                    .commit();
        }else {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }

    }


    @Override
    public void addStudent(String name, String idGroup) {
        Intent intent = new Intent();
        intent.putExtra(KEY_STUDENT_NAME, name);
        intent.putExtra(KEY_STUDENT_GROUP, idGroup);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void editStudent(long id, String name, String idGroup) {
        Intent intent = new Intent();
        intent.putExtra(KEY_STUDENT_ID, id);
        intent.putExtra(KEY_STUDENT_NAME, name);
        intent.putExtra(KEY_STUDENT_GROUP, idGroup);
        setResult(RESULT_OK, intent);
        finish();
    }
}
