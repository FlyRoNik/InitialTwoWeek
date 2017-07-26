package com.cleveroad.nikita_frolov_cr.initialtwoweek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.ExamFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.GroupFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.RatingFragment;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.StudentFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager pWrap;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase("universitydb");

        pWrap = (ViewPager) findViewById(R.id.pWrap);
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = StudentFragment.newInstance();
                        break;
                    case 1:
                        fragment = GroupFragment.newInstance();
                        break;
                    case 2:
                        fragment = ExamFragment.newInstance();
                        break;
                    case 3:
                        fragment = RatingFragment.newInstance();
                        break;
                }
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String title = "";
                switch (position) {
                    case 0:
                        title = "Students";
                        break;
                    case 1:
                        title = "Groups";
                        break;
                    case 2:
                        title = "Exams";
                        break;
                    case 3:
                        title = "Ratings";
                        break;
                }
                return title;
            }
        };
        pWrap.setAdapter(mPagerAdapter);
    }

}
