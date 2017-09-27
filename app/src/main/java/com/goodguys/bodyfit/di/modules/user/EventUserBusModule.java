package com.goodguys.bodyfit.di.modules.user;

import com.goodguys.bodyfit.api.UserApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

@Module
public class EventUserBusModule {
    @Provides
    @Singleton
    public Bus provideBus(UserApi userApi){
        return new Bus();
    }
}
