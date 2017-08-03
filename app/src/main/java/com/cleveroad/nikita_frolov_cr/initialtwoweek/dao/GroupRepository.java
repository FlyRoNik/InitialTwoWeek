package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentResolver;
import android.net.Uri;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.DaoSession;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.GroupDao;

import java.util.List;

import static com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.GroupEntry;

public class GroupRepository {
    private static final Uri GROUP_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            GroupEntry.TABLE_GROUPS);

    private ContentResolver mContentResolver;
    private GroupDao mGroupDao;

    public GroupRepository(ContentResolver mContentResolver, DaoSession daoSession) {
        this.mContentResolver = mContentResolver;
        mGroupDao = daoSession.getGroupDao();
    }

    public List<Group> getAllGroups(){
        return mGroupDao.loadAll();
    }

    public Group getGroup(long id){
        return mGroupDao.load(id);
    }

    public void editGroup(long id, String name){
        Group group = mGroupDao.load(id);
        group.setName(name);
        mGroupDao.save(group);
        mContentResolver.notifyChange(GROUP_UPDATE_URI, null);
    }

    public void addGroup(String name){
        Group group = new Group();
        group.setName(name);
        mGroupDao.save(group);
        mContentResolver.notifyChange(GROUP_UPDATE_URI, null);
    }

    public void deleteGroup(long id){
        Group group = mGroupDao.load(id);
        mGroupDao.delete(group);
        mContentResolver.notifyChange(GROUP_UPDATE_URI, null);
    }

}
