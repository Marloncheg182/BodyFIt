package com.goodguys.bodyfit.di;

import android.content.Context;

import com.goodguys.bodyfit.di.modules.user.ApiUserModule;
import com.goodguys.bodyfit.di.modules.ContextModule;
import com.goodguys.bodyfit.di.modules.user.EventUserBusModule;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.presenters.HomeActivityPresenter;
import com.goodguys.bodyfit.mvp.presenters.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

@Singleton
@Component(modules = {ContextModule.class, EventUserBusModule.class, ApiUserModule.class})
public interface UserComponent {

    Context getContext();

    BodyFitRepository getRepository();

    Bus getBus();

    void inject(HomePresenter homePresenter);

    void inject(HomeActivityPresenter homeActivityPresenter);
}
