package ru.index_art.indexcrm;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
