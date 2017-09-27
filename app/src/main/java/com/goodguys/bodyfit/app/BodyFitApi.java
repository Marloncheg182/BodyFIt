package com.goodguys.bodyfit.app;

import com.goodguys.bodyfit.common.Constants;
import com.goodguys.bodyfit.mvp.models.auth.AuthResponse;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInRegularRequest;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInSocialRequest;
import com.goodguys.bodyfit.mvp.models.auth.signup.SignUpRegularRequest;
import com.goodguys.bodyfit.mvp.models.auth.signup.SignUpSocialRequest;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

public interface BodyFitApi {

    @POST(Constants.PREF_AUTH + "/signin/app")
    Observable<AuthResponse> signInRegular(@Body SignInRegularRequest signInRegularRequest);

    @POST(Constants.PREF_AUTH + "/signin/network")
    Observable<AuthResponse> signInSocial(@Body SignInSocialRequest signInSocialRequest);

    @POST(Constants.PREF_AUTH + "/signup/app")
    Observable<AuthResponse> signUpRegular(@Body SignUpRegularRequest signUpRegularRequest);

    @POST(Constants.PREF_AUTH + "/signup/network")
    Observable<AuthResponse> signUpSocial(@Body SignUpSocialRequest signUpRequest);

    @GET(Constants.PREF_AUTH + "/resetpassword")
    Observable<ResponseBody> resetPassword(@Query("email") String email);

}
