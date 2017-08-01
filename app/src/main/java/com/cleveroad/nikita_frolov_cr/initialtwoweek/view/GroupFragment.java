package com.cleveroad.nikita_frolov_cr.initialtwoweek.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.App;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.controller.GroupRVAdapter;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.DaoSession;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.GroupDao;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.util.InterfaceNotImplement;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends Fragment {

    private FloatingActionButton bAddGroup;
    private RecyclerView rvGroups;

    private GroupRVAdapter mGroupRVAdapter;
    private List<Group> mGroups;
    private OnFragmentGroupListener mListener;

    private static final int CM_DELETE = 3;
    private static final int CM_EDIT = 4;

    private GroupDao mGroupDao;
    private Query<Group> groupsQuery;

    public GroupDao getGroupDao() {
        return mGroupDao;
    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        rvGroups = view.findViewById(R.id.rvGroups);
        bAddGroup = view.findViewById(R.id.bAddGroup);

        mGroups = new ArrayList<>();
        mGroupRVAdapter = new GroupRVAdapter(mGroups);
        rvGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGroups.setAdapter(mGroupRVAdapter);

        registerForContextMenu(rvGroups);
        rvGroups.setOnCreateContextMenuListener(this);

        DaoSession daoSession = ((App)getActivity().getApplication()).getDaoSession();
        mGroupDao = daoSession.getGroupDao();

        groupsQuery = mGroupDao.queryBuilder().build();
        updateGroups();

        bAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroup();
            }
        });
        return view;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CM_DELETE:
                mGroupDao.deleteByKey(mGroupRVAdapter.getItemSelected(item).getId());
                updateGroups();
                break;
            case CM_EDIT:
                mListener.editGroup(mGroupRVAdapter.getItemSelected(item).getId());
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public void updateGroups() {
        mGroups = groupsQuery.list();
        mGroupRVAdapter.setGroups(mGroups);
    }

    private void addGroup() {
        mListener.addGroup();
    }

    public interface OnFragmentGroupListener {
        void addGroup();

        void editGroup(long id);
    }
}
