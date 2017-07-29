package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;

public class MainFragment extends Fragment {

    private ViewPager pWrap;
    private PagerAdapter mPagerAdapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        pWrap = view.findViewById(R.id.vpWrap);

        FragmentManager fragmentManager = getChildFragmentManager();
        mPagerAdapter = new FragmentPagerAdapter(fragmentManager) {

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
        return view;
    }
}
