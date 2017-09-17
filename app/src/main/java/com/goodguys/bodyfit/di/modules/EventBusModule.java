package com.goodguys.bodyfit.di.modules;

import com.goodguys.bodyfit.app.BodyFitApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oleg Romanenchuk on 26.08.2017.
 */

@Module
public class EventBusModule {
    @Provides
    @Singleton
    public Bus provideBus(BodyFitApi authApi){
        return new Bus();
    }
}
