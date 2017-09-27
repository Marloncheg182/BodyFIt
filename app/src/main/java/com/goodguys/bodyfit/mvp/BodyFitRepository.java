package com.goodguys.bodyfit.mvp;

import com.goodguys.bodyfit.api.AuthApi;
import com.goodguys.bodyfit.api.UserApi;
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
    private AuthApi mAuthApi;
    private UserApi mUserApi;

    public BodyFitRepository(AuthApi authApi) {
        mAuthApi = authApi;
    }

    public BodyFitRepository(UserApi userApi){
        mUserApi = userApi;

    }

    public Observable<AuthResponse> signInRegular(SignInRegularRequest signInRegularRequest){
        return mAuthApi.signInRegular(signInRegularRequest);
    }

    public Observable<AuthResponse> signInSocial(SignInSocialRequest signInSocialRequest){
        return mAuthApi.signInSocial(signInSocialRequest);
    }

    public Observable<AuthResponse> signUpRegular(SignUpRegularRequest signUpRegularRequest){
        return mAuthApi.signUpRegular(signUpRegularRequest);
    }

    public Observable<AuthResponse> signUpSocial(SignUpSocialRequest signUpSocialRequest){
        return mAuthApi.signUpSocial(signUpSocialRequest);
    }

    public Observable<ResponseBody> resetPassword(String email){
        return mAuthApi.resetPassword(email);
    }

    //TODO add all info
    public Observable getUserStatistics(){
        return mUserApi.getUserStatistics();
    }

    public Observable getUserProfile(){
        return mUserApi.getUserProfile();
    }

    public Observable updateUserProfile(){
        return mUserApi.updateUserProfile();
    }

    public Observable updateUserAvatar(){
        return mUserApi.updateUserAvatar();
    }

}
