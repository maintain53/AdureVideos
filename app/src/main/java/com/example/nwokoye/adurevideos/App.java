package com.example.nwokoye.adurevideos;

import android.app.Application;
import android.content.Context;

/**
 * Created by NWOKOYE on 8/31/2017.
 */
public class App extends Application {

    private static Context sContext;

    private static App sInstance;

    public static Context getAppContext() {
//        if(sContext == null){
//            return getApplica;
//        }

        return sContext;
    }

    private void setAppContext(Context context) {
        sContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        sContext = getApplicationContext();

        setAppContext(sContext);
    }
}
