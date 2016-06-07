package com.silencedut.expandablelayoutsample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by SilenceDut on 16/6/7.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
