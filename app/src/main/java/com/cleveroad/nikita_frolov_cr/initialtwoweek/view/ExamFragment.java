package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.controller.ExamRVAdapter;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamFragment extends Fragment {

    private FloatingActionButton bAddExam;
    private RecyclerView rvExams;

    private ExamRVAdapter mExamRVAdapter;
    private List<Exam> mExams;
    private OnFragmentExamListener mListener;

    private static final int CM_DELETE = 5;
    private static final int CM_EDIT = 6;

    public static ExamFragment newInstance() {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        rvExams = view.findViewById(R.id.rvExams);
        bAddExam = view.findViewById(R.id.bAddExam);

        mExams = new ArrayList<>();
        mExamRVAdapter = new ExamRVAdapter(mExams);
        rvExams.setLayoutManager(new LinearLayoutManager(getContext()));
        rvExams.setAdapter(mExamRVAdapter);


        return view;
    }


    public interface OnFragmentExamListener {
        void addExam();

        void editExam(long id);
    }
}
