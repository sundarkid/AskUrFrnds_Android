package com.trydevs.askyourfriends.askurfrnds.extras;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.trydevs.askyourfriends.askurfrnds.database.DBAskUrFrnd;


public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static DBAskUrFrnd dataBase;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public synchronized static DBAskUrFrnd getWritableDatabase() {
        if (dataBase == null) {
            Log.d("database", "creating object");
            dataBase = new DBAskUrFrnd(getAppContext());
        }
        Log.d("Database", "return");
        return dataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


}
