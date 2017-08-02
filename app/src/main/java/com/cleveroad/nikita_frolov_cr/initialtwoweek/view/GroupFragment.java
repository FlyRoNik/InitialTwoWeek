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
import com.cleveroad.nikita_frolov_cr.initialtwoweek.controller.GroupRVAdapter;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.GroupRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.util.InterfaceNotImplement;

import java.util.List;

import static com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.GroupEntry;

public class GroupFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Group>>{

    private static final int LOADER_MANAGER_ID = 3;

    private static final int CM_DELETE = 3;
    private static final int CM_EDIT = 4;

    private static final Uri GROUP_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            GroupEntry.TABLE_GROUPS);

    private RecyclerView rvGroups;
    private GroupRVAdapter mGroupRVAdapter;
    private OnFragmentGroupListener mListener;
    private GroupRepository mGroupRepository;
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

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
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
        if (context instanceof OnFragmentGroupListener) {
            mListener = (OnFragmentGroupListener) context;
        } else {
            throw new InterfaceNotImplement(context.toString()
                    + " must implement " + OnFragmentGroupListener.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        view.findViewById(R.id.bAddGroup).setOnClickListener(view1 -> mListener.addGroup());

        rvGroups = view.findViewById(R.id.rvGroups);
        mGroupRVAdapter = new GroupRVAdapter();
        rvGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGroups.setAdapter(mGroupRVAdapter);

        registerForContextMenu(rvGroups);
        rvGroups.setOnCreateContextMenuListener(this);

        mGroupRepository = new GroupRepository(getContext().getContentResolver());
        getLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);
        getActivity().getContentResolver()
                .registerContentObserver(GROUP_UPDATE_URI, true, mContentObserver);
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
                mGroupRepository.deleteGroup(mGroupRVAdapter.getItemSelected(item).getId());
                getLoaderManager().getLoader(LOADER_MANAGER_ID).forceLoad();
                break;
            case CM_EDIT:
                mListener.editGroup(mGroupRVAdapter.getItemSelected(item).getId());
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public Loader<List<Group>> onCreateLoader(int id, Bundle args) {
        return new GroupsATLoader(getContext(), mGroupRepository);
    }

    @Override
    public void onLoadFinished(Loader<List<Group>> loader, List<Group> data) {
        mGroupRVAdapter.setGroups(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Group>> loader) {

    }

    private static class GroupsATLoader extends AsyncTaskLoader<List<Group>> {
        private GroupRepository mGroupRepository;

        GroupsATLoader(Context context, GroupRepository groupRepository) {
            super(context);
            mGroupRepository = groupRepository;
        }

        @Override
        public List<Group> loadInBackground() {
            return mGroupRepository.getAllGroups();
        }
    }

    public interface OnFragmentGroupListener {
        void addGroup();

        void editGroup(long id);
    }
}
