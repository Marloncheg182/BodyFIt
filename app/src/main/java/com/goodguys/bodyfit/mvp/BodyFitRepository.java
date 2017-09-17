package com.goodguys.bodyfit.mvp;

import com.goodguys.bodyfit.app.BodyFitApi;
import com.goodguys.bodyfit.mvp.models.auth.AuthResponse;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInRegularRequest;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInSocialRequest;
import com.goodguys.bodyfit.mvp.models.auth.signup.SignUpRegularRequest;
import com.goodguys.bodyfit.mvp.models.auth.signup.SignUpSocialRequest;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

public class BodyFitRepository {
    private BodyFitApi mBodyFitApi;

    public BodyFitRepository(BodyFitApi bodyFitApi) {
        mBodyFitApi = bodyFitApi;
    }

    public Observable<AuthResponse> signInRegular(SignInRegularRequest signInRegularRequest){
        return mBodyFitApi.signInRegular(signInRegularRequest);
    }

    public Observable<AuthResponse> signInSocial(SignInSocialRequest signInSocialRequest){
        return mBodyFitApi.signInSocial(signInSocialRequest);
    }

    public Observable<AuthResponse> signUpRegular(SignUpRegularRequest signUpRegularRequest){
        return mBodyFitApi.signUpRegular(signUpRegularRequest);
    }

    public Observable<AuthResponse> signUpSocial(SignUpSocialRequest signUpSocialRequest){
        return mBodyFitApi.signUpSocial(signUpSocialRequest);
    }

    public Observable<ResponseBody> resetPassword(String email){
        return mBodyFitApi.resetPassword(email);
    }
}
