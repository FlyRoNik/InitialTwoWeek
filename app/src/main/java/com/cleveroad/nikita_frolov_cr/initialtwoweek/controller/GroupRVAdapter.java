package com.cleveroad.nikita_frolov_cr.initialtwoweek.controller;


import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupRVAdapter extends RecyclerView.Adapter<GroupRVAdapter.GroupHolder>{
    private List<Group> mGroups;

    public GroupRVAdapter() {
        this.mGroups = new ArrayList<>();
    }

    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new GroupHolder(view);
    }

    public List<Group> getGroups() {
        return new ArrayList<>(mGroups);
    }

    public void setGroups(List<Group> groups) {
        mGroups.clear();
        mGroups.addAll(groups);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(GroupHolder holder, int position) {
        Group group = mGroups.get(position);
        holder.bindGroup(group);
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public Group getItemSelected(MenuItem item) {
        return mGroups.get(item.getOrder());
    }

    class GroupHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener {

        private static final int CM_DELETE = 3;
        private static final int CM_EDIT = 4;

        private TextView tvNameGroup;

        GroupHolder(View itemView) {
            super(itemView);
            tvNameGroup = itemView.findViewById(R.id.tvNameGroup);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bindGroup(Group group) {
            tvNameGroup.setText(group.getName());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, CM_DELETE, getAdapterPosition(), R.string.delete_record);
            contextMenu.add(0, CM_EDIT, getAdapterPosition(), R.string.edit_record);
        }
    }
}
