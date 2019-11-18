package com.sjy.personalassistance.iflyAIUI;

import android.app.Activity;
import android.app.Application;

import com.sjy.personalassistance.iflyAIUI.di.DaggerAppComponent;
import com.sjy.personalassistance.iflyAIUI.di.AppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class ChatApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        mAppComponent
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
