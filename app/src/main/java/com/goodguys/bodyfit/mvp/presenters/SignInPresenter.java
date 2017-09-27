package com.goodguys.bodyfit.mvp.presenters;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.app.BodyFitApplication;
import com.goodguys.bodyfit.common.Constants;
import com.goodguys.bodyfit.common.Utils;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.common.AuthUtils;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInRegularRequest;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInSocialRequest;
import com.goodguys.bodyfit.mvp.models.auth.signup.SignUpSocialRequest;
import com.goodguys.bodyfit.mvp.views.SignInView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Oleg Romanenchuk on 18.09.2017.
 */

@InjectViewState
public class SignInPresenter extends BasePresenter<SignInView> {

    @Inject
    BodyFitRepository mBodyFitRepository;

    public SignInPresenter() {
        BodyFitApplication.getAuthComponent().inject(this);
    }

    public void signInRegular(String email, String password) {
        Integer emailError = null;
        Integer passwordError = null;

        getViewState().hideFormError();

        if (TextUtils.isEmpty(email)) {
            emailError = R.string.invalid_mail;
        }
        if (TextUtils.isEmpty(password)) {
            passwordError = R.string.invalid_password;
        }

        if (emailError != null || passwordError != null) {
            getViewState().showRegularFormError(emailError, passwordError);
            return;
        }

        getViewState().startSignIn();
        Disposable disposable = mBodyFitRepository.signInRegular(new SignInRegularRequest(email, password))
                .doOnNext(authResponse -> AuthUtils.setToken(authResponse.getToken()))
                .compose(Utils.applySchedulers())
                .subscribe(authResponse -> {
                    getViewState().finishSignIn();
                    getViewState().successSignIn(AuthUtils.getTutorial());
                }, throwable -> {
                    getViewState().finishSignIn();
                    getViewState().failedSignIn(throwable.getMessage());
                });

        unsubscribeOnDestroy(disposable);
    }

    //TODO remove test
    public void signInTEST(String email, String password) {
        Integer emailError = null;
        Integer passwordError = null;

        getViewState().hideFormError();

        if (TextUtils.isEmpty(email)) {
            emailError = R.string.invalid_mail;
        }
        if (TextUtils.isEmpty(password)) {
            passwordError = R.string.invalid_password;
        }

        if (emailError != null || passwordError != null) {
            getViewState().showRegularFormError(emailError, passwordError);
            return;
        }

        if (email.equals("testmail@gmail.com") && password.equals("123")) {
            getViewState().startSignIn();
            getViewState().finishSignIn();
            getViewState().successSignIn(AuthUtils.getTutorial());
        }else {
            signInRegular(email, password);
        }
    }

    public void signInSocial(String network, String networkKey) {
        Integer networkKeyError = null;

        if (!TextUtils.isEmpty(networkKey)) {
            networkKeyError = R.string.invalid_social_user;
        }

        if (networkKeyError != null) {
            getViewState().showNetworkFormError(networkKeyError);
            return;
        }

        getViewState().startSignIn();

        Disposable disposable = mBodyFitRepository.signInSocial(new SignInSocialRequest(network, networkKey))
                .doOnNext(authResponse -> AuthUtils.setToken(authResponse.getToken()))
                .compose(Utils.applySchedulers())
                .subscribe(authResponse -> {
                    getViewState().finishSignIn();
                    getViewState().successSignIn(AuthUtils.getTutorial());
                }, throwable -> {
                    getViewState().finishSignIn();
                    if (throwable.getMessage().contains(Constants.SOCIAL_USER)){
                        signUpSocial(network, networkKey);
                    }else {
                        getViewState().failedSignIn(throwable.getMessage());
                    }
                });

        unsubscribeOnDestroy(disposable);
    }

    public void signUpSocial(String network, String networkKey) {
        Integer networkKeyError = null;

        getViewState().hideFormError();

        if (TextUtils.isEmpty(networkKey)) {
            networkKeyError = R.string.invalid_social_user;
        }

        if (networkKeyError != null) {
            getViewState().showNetworkFormError(networkKeyError);
            return;
        }

        getViewState().startSignUp();

        Disposable disposable = mBodyFitRepository.signUpSocial(new SignUpSocialRequest(network, networkKey))
                .doOnNext(authResponse -> {AuthUtils.setToken(authResponse.getToken()); AuthUtils.setTutorial(true);})
                .compose(Utils.applySchedulers())
                .subscribe(authResponse -> {
                    getViewState().finishSignUp();
                    getViewState().successSignUp(AuthUtils.getTutorial());
                }, throwable -> {
                    getViewState().finishSignUp();
                    getViewState().failedSignUp(throwable.getMessage());
                });

        unsubscribeOnDestroy(disposable);
    }

    public void onErrorCancel() {
        getViewState().hideError();
    }
}
