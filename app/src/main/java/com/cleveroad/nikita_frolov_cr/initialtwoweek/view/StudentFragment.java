package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.cleveroad.nikita_frolov_cr.initialtwoweek.App;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.controller.StudentRVAdapter;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.StudentRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Student;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.util.InterfaceNotImplement;

import java.util.List;

public class StudentFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Student>> {
    private static final Uri STUDENT_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            StudentEntry.TABLE_STUDENTS);

    private static final int CM_DELETE = 1;
    private static final int CM_EDIT = 2;

    private static final int LOADER_MANAGER_ID = 1;

    private RecyclerView rvStudents;
    private StudentRVAdapter mStudentRVAdapter;
    private OnFragmentStudentListener mListener;
    private StudentRepository mStudentRepository;
    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
        }
    };

    public static StudentFragment newInstance() {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentStudentListener) {
            mListener = (OnFragmentStudentListener) context;
        } else {
            throw  new InterfaceNotImplement(context.toString()
                    + " must implement " + OnFragmentStudentListener.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        view.findViewById(R.id.bAddStudent).setOnClickListener(view1 -> mListener.addStudent());

        rvStudents = view.findViewById(R.id.rvStudents);
        mStudentRVAdapter = new StudentRVAdapter();
        rvStudents.setLayoutManager(new LinearLayoutManager(getContext()));
        rvStudents.setAdapter(mStudentRVAdapter);

        registerForContextMenu(rvStudents);
        rvStudents.setOnCreateContextMenuListener(this);

        mStudentRepository = new StudentRepository(getContext().getContentResolver(),
                ((App)getActivity().getApplication()).getDaoSession());
        getLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);
        getActivity().getContentResolver()
                .registerContentObserver(STUDENT_UPDATE_URI, true, mContentObserver);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getContentResolver()
                .unregisterContentObserver(mContentObserver);
        mListener = null;
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CM_DELETE:
                mStudentRepository.deleteStudent(mStudentRVAdapter.getItemSelected(item).getId());
                getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
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
    public Loader<List<Student>> onCreateLoader(int id, Bundle args) {
        return new StudentsATLoader(getContext(), mStudentRepository);
    }

    @Override
    public void onLoadFinished(Loader<List<Student>> loader, List<Student> data) {
        mStudentRVAdapter.setStudents(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Student>> loader) {

    }

    private static class StudentsATLoader extends AsyncTaskLoader<List<Student>> {
        private StudentRepository mStudentRepository;

        StudentsATLoader(Context context, StudentRepository studentRepository) {
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

        void editStudent(long id);
    }
}
