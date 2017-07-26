package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.StudentDAO;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit.EditActivity;

import static android.app.Activity.RESULT_OK;
import static com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.*;

public class StudentFragment extends Fragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_MANAGER_ID = 1;

    private static final int CM_DELETE = 1;
    private static final int CM_EDIT = 2;

    private static final int REQUEST_CODE_STUDENT_ADD = 1;
    private static final int REQUEST_CODE_STUDENT_EDIT = 2;

    private static final String KEY_STUDENT_ID = "studentID";
    private static final String KEY_STUDENT_NAME = "studentName";
    private static final String KEY_STUDENT_GROUP = "studentGroup";

    private static final String KEY_CHOOSE = "choose";
    private static final int KEY_CHOOSE_EDIT = 1;
    private static final int KEY_CHOOSE_ADD = 2;

    private StudentDAO mStudentDAO;
    private SimpleCursorAdapter mSCAdapter;

    private ListView lvStudents;

    private FloatingActionButton bAddStudent;

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
        registerForContextMenu(lvStudents);

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
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra(KEY_CHOOSE, KEY_CHOOSE_ADD);
                startActivityForResult(intent, REQUEST_CODE_STUDENT_ADD);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_STUDENT_ADD:
                    mStudentDAO.addStudent(data.getStringExtra(KEY_STUDENT_NAME),
                            data.getStringExtra(KEY_STUDENT_GROUP));
                    getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
                    break;
                case REQUEST_CODE_STUDENT_EDIT:
                    mStudentDAO.editStudent(
                            data.getLongExtra(KEY_STUDENT_ID, 0),
                            data.getStringExtra(KEY_STUDENT_NAME),
                            data.getStringExtra(KEY_STUDENT_GROUP));
                    getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
                    break;
            }
        } else {
            Toast.makeText(getContext(), "Wrong result", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE, 0, R.string.delete_record);
        menu.add(0, CM_EDIT, 0, R.string.edit_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case CM_DELETE:
                //TODO how it knows this id
                mStudentDAO.removeStudent(acmi.id);
                getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
                break;
            case CM_EDIT:
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra(KEY_STUDENT_ID, acmi.id);

                Cursor cursor = mSCAdapter.getCursor();
                cursor.moveToPosition(acmi.position);

                intent.putExtra(KEY_STUDENT_NAME,
                        cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_NAME)));
                //TODO idGroup
                intent.putExtra(KEY_STUDENT_GROUP,
                        cursor.getString(cursor.getColumnIndex(GroupEntry.COLUMN_NAME)));
                intent.putExtra(KEY_CHOOSE, KEY_CHOOSE_EDIT);
                startActivityForResult(intent, REQUEST_CODE_STUDENT_EDIT);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
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
