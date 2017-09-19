package com.goodguys.bodyfit.mvp.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.presenters.SplashPresenter;
import com.goodguys.bodyfit.mvp.views.SplashView;

import org.androidannotations.annotations.EActivity;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends MvpAppCompatActivity implements SplashView{
    public static final String LOG_TAG = "SplashActivity";

    @InjectPresenter
    SplashPresenter mSplashPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");
        getMvpDelegate().onAttach();
    }

    @Override
    public void setAuthorized(boolean isAuthorized) {
        Log.d(LOG_TAG, "setAuthorized status - " + String.valueOf(isAuthorized));
        startActivity(new Intent(this, isAuthorized ? HomeActivity_.class : AuthActivity_class));
    }
}
