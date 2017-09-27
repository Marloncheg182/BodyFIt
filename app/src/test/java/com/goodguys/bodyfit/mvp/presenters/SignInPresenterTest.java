package com.goodguys.bodyfit.mvp.presenters;

import android.content.Context;
import android.util.Base64;

import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.di.AuthComponent;
import com.goodguys.bodyfit.mvp.BodyFitRepository;
import com.goodguys.bodyfit.mvp.models.auth.AuthResponse;
import com.goodguys.bodyfit.mvp.models.auth.signin.SignInRegularRequest;
import com.goodguys.bodyfit.mvp.views.SignInView$$State;
import com.goodguys.bodyfit.test.BodyFitAuthTestRunner;
import com.goodguys.bodyfit.test.TestAuthComponent;
import com.goodguys.bodyfit.test.TestAuthComponentRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

@RunWith(BodyFitAuthTestRunner.class)
public class SignInPresenterTest {

    @Mock
    BodyFitRepository bodyFitRepository;

    @Rule
    public TestAuthComponentRule testAuthComponentRule = new TestAuthComponentRule(testAuthComponent());

    @Mock
    SignInView$$State signInViewState;

    private SignInPresenter signInPresenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        signInPresenter = new SignInPresenter();
        signInPresenter.setViewState(signInViewState);

        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(scheduler ->
                Schedulers.trampoline());
        RxAndroidPlugins.reset();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Test
    public void signin_shouldSignSuccessfull(){
        String email = email();
        String password = password();
        when(bodyFitRepository.signInRegular(new SignInRegularRequest(email, password))).thenReturn(Observable.just(new AuthResponse()));

        signInPresenter.signInRegular(email(), password());

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(email, email());
        Assert.assertEquals(password, password());
        isSignInAndHideShowProgressCalled();
        verify(signInViewState).successSignIn(false);
    }

    @Test
    public void signin_shouldShowError() {
        when(bodyFitRepository.signInRegular(new SignInRegularRequest(email(), password()))).thenReturn(Observable.error(new Throwable()));

        signInPresenter.signInRegular(email(), password());

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("", "");

        isSignInAndHideShowProgressCalled();
        verify(signInViewState).failedSignIn(anyString());
    }

    @Test
    public void signin_shouldShowPasswordAndEmailEmptyErros() {
        signInPresenter.signInRegular(null, null);
        verify(signInViewState).showRegularFormError(R.string.invalid_mail, R.string.invalid_password);
    }

    @Test
    public void signin_shouldOnErrorCancel() {
        signInPresenter.onErrorCancel();
        verify(signInViewState).hideError();
    }

    private void isSignInAndHideShowProgressCalled() {
        verify(signInViewState).hideFormError();
        verify(signInViewState).startSignIn();
        verify(signInViewState).finishSignIn();
        verify(bodyFitRepository).signInRegular(new SignInRegularRequest(email(), password()));
    }

    private AuthComponent testAuthComponent() {
        return new TestAuthComponent() {
            @Override
            public Context getContext() {
                return RuntimeEnvironment.application;
            }

            @Override
            public void inject(SignInPresenter presenter) {
                presenter.mBodyFitRepository = bodyFitRepository;
            }
        };
    }

    private String email() {
        return "testmail@gmail.com";
    }

    private String password() {
        return "123";
    }
}
