package com.goodguys.bodyfit.api;

import com.goodguys.bodyfit.common.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

public interface UserApi {

    //TODO add all info

    @GET(Constants.PREF_USER + "/statistics")
    Observable getUserStatistics();

    @GET(Constants.PREF_USER + "/profile")
    Observable getUserProfile();

    @POST(Constants.PREF_USER + "/profile/update")
    Observable updateUserProfile();

    @Multipart
    @POST(Constants.PREF_USER + "/profile/avatar")
    Observable updateUserAvatar();
}
