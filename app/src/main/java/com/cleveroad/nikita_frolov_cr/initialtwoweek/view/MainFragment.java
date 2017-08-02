package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;

public class MainFragment extends Fragment {
    private final static String[] TITLES = new String[]{"Students", "Groups", "Exams", "Ratings"};

    private ViewPager vpConteiner;
    private PagerAdapter mPagerAdapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return TITLES.length;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return StudentFragment.newInstance();
                    case 1:
                        return GroupFragment.newInstance();
                    case 2:
                        return ExamFragment.newInstance();
                    case 3:
                        return RatingFragment.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TITLES[position];
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        vpConteiner = view.findViewById(R.id.vpConteiner);
        vpConteiner.setAdapter(mPagerAdapter);
        return view;
    }
}
