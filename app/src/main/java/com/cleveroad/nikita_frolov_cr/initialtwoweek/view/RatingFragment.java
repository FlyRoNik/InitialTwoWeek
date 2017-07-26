package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;

public class RatingFragment extends Fragment {
    public static RatingFragment newInstance() {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }
}
