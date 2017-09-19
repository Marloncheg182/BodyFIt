package com.goodguys.bodyfit.mvp.presenters;

import android.text.TextUtils;

import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.app.BodyFitApplication;
import com.goodguys.bodyfit.common.Utils;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.common.AuthUtils;
import com.goodguys.bodyfit.mvp.models.auth.signup.SignUpRegularRequest;
import com.goodguys.bodyfit.mvp.views.SignUpView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Oleg Romanenchuk on 19.09.2017.
 */

public class SignUpPresenter extends BasePresenter<SignUpView>{

    @Inject
    BodyFitRepository mBodyFitRepository;

    public SignUpPresenter(){
        BodyFitApplication.getAppComponent().inject(this);}

    public void signUpRegular(String email, String password){
        Integer emailError = null;
        Integer passwordError = null;

        getViewState().hideFormError();

        if (TextUtils.isEmpty(email)){
            emailError = R.string.invalid_mail;
        }

        if (TextUtils.isEmpty(password)){
            passwordError = R.string.invalid_password;
        }

        if (emailError != null || passwordError != null){
            getViewState().showFormError(emailError, passwordError);
            return;
        }

        getViewState().startSignUp();

        Disposable disposable = mBodyFitRepository.signUpRegular(new SignUpRegularRequest(email, password))
                .doOnNext(authResponse -> AuthUtils.setToken(authResponse.getToken()))
                .compose(Utils.applySchedulers())
                .subscribe(authResponse -> {
                    getViewState().finishSignUp();
                    getViewState().successSignUp();
                }, throwable -> {
                    getViewState().finishSignUp();
                    getViewState().failedSignUp(throwable.getMessage());
                });

        unsubscribeOnDestroy(disposable);
    }

    public void onErrorCancel(){
        getViewState().hideError();
    }
}
