package com.goodguys.bodyfit.di.modules.auth;

import com.goodguys.bodyfit.api.AuthApi;
import com.goodguys.bodyfit.di.modules.RetrofitModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

@Module(includes = {RetrofitModule.class})
public class AuthModule {
    @Provides
    @Singleton
    public AuthApi provideBodyFitApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}
