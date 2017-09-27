package com.goodguys.bodyfit.mvp.presenters;

import android.text.TextUtils;

import com.arellomobile.mvp.MvpPresenter;
import com.goodguys.bodyfit.mvp.common.AuthUtils;
import com.goodguys.bodyfit.mvp.views.SplashView;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

public class SplashPresenter extends MvpPresenter<SplashView> {
    @Override
    public void attachView(SplashView view) {
        super.attachView(view);
        view.setAuthorized(!TextUtils.isEmpty(AuthUtils.getToken()));
    }
}
