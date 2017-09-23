package com.goodguys.bodyfit.di;

import android.content.Context;

import com.goodguys.bodyfit.app.BodyFitApplication;
import com.goodguys.bodyfit.di.modules.AuthModule;
import com.goodguys.bodyfit.di.modules.ContextModule;
import com.goodguys.bodyfit.di.modules.EventBusModule;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.presenters.HomePresenter;
import com.goodguys.bodyfit.mvp.presenters.ResetPasswordPresenter;
import com.goodguys.bodyfit.mvp.presenters.SignInPresenter;
import com.goodguys.bodyfit.mvp.presenters.SignUpPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Oleg Romanenchuk on 26.08.2017.
 */

@Singleton
@Component(modules = {ContextModule.class, EventBusModule.class, AuthModule.class})
public interface AppComponent {

    Context getContext();

    BodyFitRepository getAuthRepository();

    Bus getBus();

    void inject(SignInPresenter presenter);

    void inject(SignUpPresenter presenter);

    void inject(ResetPasswordPresenter presenter);

    void inject(HomePresenter homePresenter);
}
