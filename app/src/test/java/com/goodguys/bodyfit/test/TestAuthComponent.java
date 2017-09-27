package com.goodguys.bodyfit.test;

import android.content.Context;

import com.goodguys.bodyfit.di.AuthComponent;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.presenters.ResetPasswordPresenter;
import com.goodguys.bodyfit.mvp.presenters.SignInPresenter;
import com.goodguys.bodyfit.mvp.presenters.SignUpPresenter;
import com.squareup.otto.Bus;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

public class TestAuthComponent implements AuthComponent {
    @Override
    public Context getContext() {
        return RuntimeEnvironment.application;
    }

    @Override
    public BodyFitRepository getRepository() {
        return null;
    }

    @Override
    public Bus getBus() {
        return null;
    }

    @Override
    public void inject(SignInPresenter presenter) {

    }

    @Override
    public void inject(SignUpPresenter presenter) {

    }

    @Override
    public void inject(ResetPasswordPresenter presenter) {

    }
}
