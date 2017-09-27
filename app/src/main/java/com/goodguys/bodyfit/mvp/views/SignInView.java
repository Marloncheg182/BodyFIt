package com.goodguys.bodyfit.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Oleg Romanenchuk on 18.09.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SignInView extends MvpView{

    void startSignIn();

    void startSignUp();

    void finishSignIn();

    void finishSignUp();

    void failedSignIn(String message);

    void failedSignUp(String message);

    void hideError();

    void hideFormError();

    void showRegularFormError(Integer emailError, Integer passwordError);

    void showNetworkFormError(Integer networkKeyError);

    @StateStrategyType(SkipStrategy.class)
    void successSignIn();

    @StateStrategyType(SkipStrategy.class)
    void successSignUp();
}
