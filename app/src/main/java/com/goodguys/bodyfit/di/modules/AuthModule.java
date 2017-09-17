package com.goodguys.bodyfit.di.modules;

import com.goodguys.bodyfit.app.BodyFitApi;
import com.goodguys.bodyfit.mvp.BodyFitRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

@Module(includes = {ApiModule.class})
public class AuthModule {
    @Provides
    @Singleton
    public BodyFitRepository provideAuthRepository(BodyFitApi authApi){
        return new BodyFitRepository(authApi);
    }
}
