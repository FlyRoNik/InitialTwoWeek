package com.cleveroad.nikita_frolov_cr.greendao;

import android.app.Application;

import com.cleveroad.nikita_frolov_cr.greendao.model.DaoMaster;
import com.cleveroad.nikita_frolov_cr.greendao.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mycats-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
