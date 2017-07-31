package com.cleveroad.nikita_frolov_cr.initialtwoweek;

import android.app.Application;


public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Application get() {
        return sInstance;
    }
}
