package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.StudentDAO;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract;

import static com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.*;

public class StudentFragment extends Fragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_MANAGER_ID = 1;

    private StudentDAO mStudentDAO;
    private SimpleCursorAdapter mSCAdapter;

    private ListView lvStudents;

    private Button bAddStudent;

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
        mStudentDAO = new StudentDAO(getContext());

        String[] from = new String[] {StudentEntry.COLUMN_NAME, GroupEntry.COLUMN_NAME};
        int[] to = new int[] { R.id.tvStudent, R.id.tvGroup};

        mSCAdapter = new SimpleCursorAdapter(getContext(), R.layout.item, null, from, to, 0);
        lvStudents = view.findViewById(R.id.lvStudents);
        lvStudents.setAdapter(mSCAdapter);

        bAddStudent = view.findViewById(R.id.bAddStudent);

        bAddStudent.setOnClickListener(this);

        getLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStudentDAO.close();
        mStudentDAO = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bAddStudent:
                    mStudentDAO.addStudent("default", "1");
                getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentsCursorLoader(getContext(), mStudentDAO);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSCAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private static class StudentsCursorLoader extends CursorLoader {
        private StudentDAO mStudentDAO;

        public StudentsCursorLoader(Context context, StudentDAO studentDAO) {
            super(context);
            this.mStudentDAO = studentDAO;
        }

        @Override
        public Cursor loadInBackground() {
            return mStudentDAO.getAllStudents();
        }

    }
}
