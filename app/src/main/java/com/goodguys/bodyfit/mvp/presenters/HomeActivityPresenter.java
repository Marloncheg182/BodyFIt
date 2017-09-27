package com.goodguys.bodyfit.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.goodguys.bodyfit.app.BodyFitApplication;
import com.goodguys.bodyfit.mvp.views.HomeActivityView;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

@InjectViewState
public class HomeActivityPresenter extends BasePresenter<HomeActivityView> {

    public HomeActivityPresenter(){
        BodyFitApplication.getsUserComponent().inject(this);
    }

    public void signOut(){
        getViewState().signOut();
        getViewState().finishActivity();
    }
}
