package com.goodguys.bodyfit.di.modules.user;

import com.goodguys.bodyfit.api.UserApi;
import com.goodguys.bodyfit.mvp.BodyFitRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

@Module(includes = {UserModule.class})
public class ApiUserModule {

    @Provides
    @Singleton
    public BodyFitRepository provideUserRepository(UserApi userApi){
        return new BodyFitRepository(userApi);
    }
}
