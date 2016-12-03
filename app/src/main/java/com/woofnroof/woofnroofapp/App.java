package com.woofnroof.woofnroofapp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by jbarron on 11/13/16.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }


}
