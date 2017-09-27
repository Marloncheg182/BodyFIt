package com.goodguys.bodyfit.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.di.AuthComponent;
import com.goodguys.bodyfit.di.DaggerAuthComponent;
import com.goodguys.bodyfit.di.DaggerUserComponent;
import com.goodguys.bodyfit.di.UserComponent;
import com.goodguys.bodyfit.di.modules.ContextModule;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */


public class BodyFitApplication extends Application {
    private static AuthComponent sAuthComponent;
    private static UserComponent sUserComponent;
    private TwitterAuthConfig authConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        sAuthComponent = DaggerAuthComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        sUserComponent = DaggerUserComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        Twitter.initialize(this);
        authConfig = new TwitterAuthConfig(getResources()
                .getString(R.string.twitter_key),
                getResources().getString(R.string.twitter_secret));

    }

    public static AuthComponent getAuthComponent() {
        return sAuthComponent;
    }

    public static UserComponent getsUserComponent() {
        return sUserComponent;
    }

    @VisibleForTesting
    public static void setAuthComponent(@NonNull AuthComponent authComponent){
        sAuthComponent = authComponent;
    }

    @VisibleForTesting
    public static void setsUserComponent(@NonNull UserComponent userComponent){
        sUserComponent = userComponent;
    }

}
