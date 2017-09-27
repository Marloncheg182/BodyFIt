package com.goodguys.bodyfit.di.modules.auth;

import com.goodguys.bodyfit.api.AuthApi;
import com.goodguys.bodyfit.mvp.BodyFitRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

@Module(includes = {AuthModule.class})
public class ApiAuthModule {
    @Provides
    @Singleton
    public BodyFitRepository provideAuthRepository(AuthApi authApi){
        return new BodyFitRepository(authApi);
    }
}
