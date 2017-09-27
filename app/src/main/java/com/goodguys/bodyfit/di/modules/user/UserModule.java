package com.goodguys.bodyfit.di.modules.user;

import com.goodguys.bodyfit.api.UserApi;
import com.goodguys.bodyfit.di.modules.RetrofitModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

@Module(includes = {RetrofitModule.class})
public class UserModule {
    @Provides
    @Singleton
    public UserApi provideBodyFitApi(Retrofit retrofit){
        return retrofit.create(UserApi.class);
    }
}
