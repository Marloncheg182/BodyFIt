package com.goodguys.bodyfit.di;

import android.content.Context;

import com.goodguys.bodyfit.di.modules.auth.ApiAuthModule;
import com.goodguys.bodyfit.di.modules.ContextModule;
import com.goodguys.bodyfit.di.modules.auth.EventAuthBusModule;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
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
@Component(modules = {ContextModule.class, EventAuthBusModule.class, ApiAuthModule.class})
public interface AuthComponent {

    Context getContext();

    BodyFitRepository getRepository();

    Bus getBus();

    void inject(SignInPresenter presenter);

    void inject(SignUpPresenter presenter);

    void inject(ResetPasswordPresenter presenter);

}
