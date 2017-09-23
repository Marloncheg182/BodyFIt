package com.goodguys.bodyfit.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Oleg Romanenchuk on 23.09.2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HomeView extends MvpView{

    void startLoadUserStatistics();

    void finishLoadUserStatistics();

    void failedLoadUserStatistics(String message);

    void hideError();

    @StateStrategyType(SkipStrategy.class)
    void successLoadUserStatistics();

}

