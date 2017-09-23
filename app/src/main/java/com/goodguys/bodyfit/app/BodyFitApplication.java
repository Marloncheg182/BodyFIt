package com.goodguys.bodyfit.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.di.AppComponent;
import com.goodguys.bodyfit.di.DaggerAppComponent;
import com.goodguys.bodyfit.di.modules.ContextModule;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */


public class BodyFitApplication extends Application {
    private static AppComponent sAppComponent;
    private TwitterAuthConfig authConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        Twitter.initialize(this);
        authConfig = new TwitterAuthConfig(getResources()
                .getString(R.string.twitter_key),
                getResources().getString(R.string.twitter_secret));

    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent){
        sAppComponent = appComponent;
    }
}
