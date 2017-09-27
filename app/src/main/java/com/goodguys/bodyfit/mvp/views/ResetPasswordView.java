package com.goodguys.bodyfit.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Oleg Romanenchuk on 22.09.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ResetPasswordView extends MvpView{

    void startResetPassword();

    void finishResetPassword();

    void failedResetPassword(String message);

    void hideFormPasswordError();

    void showResetPasswordFormError(Integer emailError);

    @StateStrategyType(SkipStrategy.class)
    void successResetPassword();
}
