package com.goodguys.bodyfit.app;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDexApplication;

import com.facebook.appevents.AppEventsLogger;
import com.goodguys.bodyfit.di.AppComponent;
import com.goodguys.bodyfit.di.modules.ContextModule;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

public class BodyFitApplication extends MultiDexApplication {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent){
        sAppComponent = appComponent;
    }
}
