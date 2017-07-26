package com.cleveroad.nikita_frolov_cr.initialtwoweek;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.StudentFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFrgManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase("universitydb");
        mFrgManager = getSupportFragmentManager();

        mFrgManager.beginTransaction()
                .add(R.id.flWrap, StudentFragment.newInstance())
                .commit();
    }

}
