package com.husen.sqlite.helper;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;

/**
 * Created by gulamhusen on 20-11-2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
    }
}
