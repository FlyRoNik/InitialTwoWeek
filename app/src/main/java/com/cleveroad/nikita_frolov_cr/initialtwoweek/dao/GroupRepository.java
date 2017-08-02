package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentResolver;
import android.net.Uri;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;

import java.util.List;

import static com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.GroupEntry;

public class GroupRepository {
    private static final Uri GROUP_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            GroupEntry.TABLE_GROUPS);

    private ContentResolver mContentResolver;

    public GroupRepository(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }

    public List<Group> getAllGroups(){
        List<Group> groups = Group.all(Group.class);
        return groups;
    }

    public Group getGroup(long id){
        return Group.load(Group.class, id);
    }

    public void editGroup(long id, String name){
        Group group = Group.load(Group.class, id);
        group.setName(name);
        group.save();
        mContentResolver.notifyChange(GROUP_UPDATE_URI, null);
    }

    public void addGroup(String name){
        Group group = new Group();
        group.setName(name);
        group.save();
        mContentResolver.notifyChange(GROUP_UPDATE_URI, null);
    }

    public void deleteGroup(long id){
        Group group = Group.load(Group.class, id);
        group.delete();
        mContentResolver.notifyChange(GROUP_UPDATE_URI, null);
    }
}
