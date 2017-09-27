package com.goodguys.bodyfit.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goodguys.bodyfit.app.BodyFitApplication;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.views.HomeView;

import javax.inject.Inject;

/**
 * Created by Oleg Romanenchuk on 23.09.2017.
 */

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    @Inject
    BodyFitRepository mBodyFitRepository;

    public HomePresenter() {
        BodyFitApplication.getsUserComponent().inject(this);
    }


}
