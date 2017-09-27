package com.goodguys.bodyfit.di.modules.auth;

import com.goodguys.bodyfit.api.AuthApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oleg Romanenchuk on 26.08.2017.
 */

@Module
public class EventAuthBusModule {
    @Provides
    @Singleton
    public Bus provideBus(AuthApi authApi){
        return new Bus();
    }
}
