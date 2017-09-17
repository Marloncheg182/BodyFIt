package com.goodguys.bodyfit.di.modules;

import com.goodguys.bodyfit.app.BodyFitApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

@Module(includes = {RetrofiModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public BodyFitApi provideBodyFitApi(Retrofit retrofit){
        return retrofit.create(BodyFitApi.class);
    }
}
