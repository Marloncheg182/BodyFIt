package com.goodguys.bodyfit.mvp.ui.activities;


import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInSocialRequest;
import com.goodguys.bodyfit.mvp.presenters.SplashPresenter;
import com.goodguys.bodyfit.mvp.views.SplashView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends MvpAppCompatActivity implements SplashView {
    public static final String LOG_TAG = "SplashActivity";

    @InjectPresenter
    SplashPresenter mSplashPresenter;

    @AfterViews
    public void checkAuthorization() {
        Log.d(LOG_TAG, "checkAuthorization");
        getMvpDelegate().onAttach();
    }

    @Override
    public void setAuthorized(boolean isAuthorized) {
        Log.d(LOG_TAG, "setAuthorized status - " + String.valueOf(isAuthorized));
        Timer timer = new Timer();
        TimerTask delayedThreadStartTask = new TimerTask() {
            @Override
            public void run() {
                new Thread(() -> {
                    if (isAuthorized) {
                        HomeActivity_.intent(getApplicationContext())
                                .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .start();
                    } else {
                        AuthActivity_.intent(getApplicationContext())
                                .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .start();
                    }
                }).start();
            }
        };
        timer.schedule(delayedThreadStartTask, 2 * 1000);
    }
}
