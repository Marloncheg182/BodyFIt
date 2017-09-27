package com.goodguys.bodyfit.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Oleg Romanenchuk on 19.09.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SignUpView extends MvpView{

    void startSignUp();

    void finishSignUp();

    void failedSignUp(String message);

    void hideError();

    void hideFormError();

    void showFormError(Integer emailError, Integer passwordError);

    void sendSignUpSuccessNotification();

    @StateStrategyType(SkipStrategy.class)
    void successSignUp();
}
