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

import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.controller.ExamRVAdapter;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.ExamRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Exam;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.util.InterfaceNotImplement;

import java.util.List;

public class ExamFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Exam>>{

    private static final int LOADER_MANAGER_ID = 2;

    private static final int CM_DELETE = 5;
    private static final int CM_EDIT = 6;

    private static final Uri EXAM_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            UniversityContract.ExamEntry.TABLE_EXAMS);

    private RecyclerView rvExams;
    private ExamRVAdapter mExamRVAdapter;
    private OnFragmentExamListener mListener;
    private ExamRepository mExamRepository;
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

    public static ExamFragment newInstance() {
        ExamFragment fragment = new ExamFragment();
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
        if (context instanceof OnFragmentExamListener) {
            mListener = (OnFragmentExamListener) context;
        } else {
            throw new InterfaceNotImplement(context.toString()
                    + " must implement " + OnFragmentExamListener.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        rvExams = view.findViewById(R.id.rvExams);

        mExamRVAdapter = new ExamRVAdapter();
        rvExams.setLayoutManager(new LinearLayoutManager(getContext()));
        rvExams.setAdapter(mExamRVAdapter);

        registerForContextMenu(rvExams);
        rvExams.setOnCreateContextMenuListener(this);

        view.findViewById(R.id.bAddExam).setOnClickListener(view1 -> mListener.addExam());

        mExamRepository = new ExamRepository(getContext().getContentResolver());
        getLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);
        getActivity().getContentResolver()
                .registerContentObserver(EXAM_UPDATE_URI, true, mContentObserver);
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
                mExamRepository.deleteExam(mExamRVAdapter.getItemSelected(item).getId());
                getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
                break;
            case CM_EDIT:
                mListener.editExam(mExamRVAdapter.getItemSelected(item).getId());
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public Loader<List<Exam>> onCreateLoader(int id, Bundle args) {
        return new ExamsATLoader(getContext(), mExamRepository);
    }

    @Override
    public void onLoadFinished(Loader<List<Exam>> loader, List<Exam> data) {
        mExamRVAdapter.setExams(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Exam>> loader) {

    }

    private static class ExamsATLoader extends AsyncTaskLoader<List<Exam>> {
        private ExamRepository mExamRepository;

        ExamsATLoader(Context context, ExamRepository examRepository) {
            super(context);
            mExamRepository = examRepository;
        }

        @Override
        public List<Exam> loadInBackground() {
            return mExamRepository.getAllExams();
        }
    }

    public interface OnFragmentExamListener {
        void addExam();

        void editExam(long id);
    }
}
