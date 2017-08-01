package com.cleveroad.nikita_frolov_cr.initialtwoweek;

import android.app.Application;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.DaoMaster;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.DaoSession;

import org.greenrobot.greendao.database.Database;


public class App extends com.activeandroid.app.Application {

    private static App sInstance;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, UniversityDBHelper.DB_NAME);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static Application get() {
        return sInstance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
