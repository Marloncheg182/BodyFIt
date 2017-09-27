package com.goodguys.bodyfit.mvp.presenters;

import com.goodguys.bodyfit.mvp.common.AuthUtils;
import com.goodguys.bodyfit.mvp.views.SplashView;
import com.goodguys.bodyfit.test.BodyFitAuthTestRunner;
import com.goodguys.bodyfit.test.TestAuthComponentRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

@RunWith(BodyFitAuthTestRunner.class)
public class SplashPresenterTest {

    @Rule
    public TestAuthComponentRule testAuthComponentRule = new TestAuthComponentRule();

    @Mock
    SplashView splashView;

    private SplashPresenter splashPresenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        splashPresenter = new SplashPresenter();
        splashPresenter.getAttachedViews().add(splashView);
    }

    @Test
    public void splash_shouldAuthorizedStateFalse(){
        AuthUtils.setToken(null);
        splashPresenter.attachView(splashView);
        verify(splashView).setAuthorized(false);
    }

    @Test
    public void splash_shouldAuthorizedStateTrue(){
        AuthUtils.setToken("token");
        splashPresenter.attachView(splashView);
        verify(splashView).setAuthorized(true);
    }
}
