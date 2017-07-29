package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.RepositoryObserver;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.controller.StudentRVAdapter;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.StudentRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment implements View.OnClickListener,
        RepositoryObserver,
        LoaderManager.LoaderCallbacks<List<Student>>{

    private static final int LOADER_MANAGER_ID = 1;

    private static final int CM_DELETE = 1;
    private static final int CM_EDIT = 2;

    private StudentRepository mStudentRepository;

    private OnFragmentStudentListener mListener;

    private RecyclerView rv;
    private StudentRVAdapter mStudentRVAdapter;

    private FloatingActionButton bAddStudent;

    private StudentsATLoader mStudentsATLoader;

    public static StudentFragment newInstance() {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        bAddStudent = view.findViewById(R.id.bAddStudent);

        bAddStudent.setOnClickListener(this);

        rv = view.findViewById(R.id.rvStudents);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        mStudentRVAdapter = new StudentRVAdapter(new ArrayList<Student>());
        rv.setAdapter(mStudentRVAdapter);

        registerForContextMenu(rv);

        mStudentRepository = StudentRepository.getInstance(getContext());
        mStudentRepository.registerObserver(this);
        mStudentsATLoader = new StudentsATLoader(getContext(), mStudentRepository);

        getLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);

        mStudentRepository.open();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentStudentListener) {
            mListener = (OnFragmentStudentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentStudentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStudentRepository.removeObserver(this);
        mStudentRepository.close();
        mStudentRepository = null;
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bAddStudent:
                mListener.addStudent();
                break;
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CM_DELETE:
                mStudentRepository.removeStudent(mStudentRVAdapter.getItemSelected(item).getId());
                break;
            case CM_EDIT:
                mListener.editStudent(mStudentRVAdapter.getItemSelected(item).getId());
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public void onStudentDataChanged() {
        getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
    }

    @Override
    public Loader<List<Student>> onCreateLoader(int id, Bundle args) {
        return new StudentsATLoader(getContext(), mStudentRepository);
    }

    @Override
    public void onLoadFinished(Loader<List<Student>> loader, List<Student> data) {
        mStudentRVAdapter.setStudents(data);
        mStudentRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Student>> loader) {

    }

    private static class StudentsATLoader extends AsyncTaskLoader<List<Student>> {
        private StudentRepository mStudentRepository;

        public StudentsATLoader(Context context, StudentRepository studentRepository) {
            super(context);
            mStudentRepository = studentRepository;
        }

        @Override
        public List<Student> loadInBackground() {
            return mStudentRepository.getAllStudents();
        }
    }

    public interface OnFragmentStudentListener {
        void addStudent();

        void editStudent(int id);
    }
}
