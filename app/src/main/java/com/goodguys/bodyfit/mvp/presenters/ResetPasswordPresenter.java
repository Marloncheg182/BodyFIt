package com.goodguys.bodyfit.mvp.presenters;

import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.app.BodyFitApplication;
import com.goodguys.bodyfit.common.Utils;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.views.ResetPasswordView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Oleg Romanenchuk on 22.09.2017.
 */

@InjectViewState
public class ResetPasswordPresenter extends BasePresenter<ResetPasswordView>{

    @Inject
    BodyFitRepository mBodyFitRepository;

    public ResetPasswordPresenter(){
        BodyFitApplication.getAppComponent().inject(this);
    }

    public void resetPassword(String email){
        Integer emailError = null;

        getViewState().hideFormPasswordError();

        if (TextUtils.isEmpty(email)){
            emailError = R.string.invalid_mail;
        }

        if (emailError != null){
            getViewState().showResetPasswordFormError(emailError);
            return;
        }

        getViewState().startResetPassword();

        Disposable disposable = mBodyFitRepository.resetPassword(email)
                .compose(Utils.applySchedulers())
                .subscribe(responseBody -> {
                    getViewState().finishResetPassword();
                    getViewState().successResetPassword();
                }, throwable -> {
                    getViewState().finishResetPassword();
                    getViewState().failedResetPassword(throwable.getMessage());
                });
        unsubscribeOnDestroy(disposable);
    }

}
