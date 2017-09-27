package com.goodguys.bodyfit.test;

import android.support.annotation.NonNull;

import com.goodguys.bodyfit.app.BodyFitApplication;
import com.goodguys.bodyfit.di.AuthComponent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

public class TestAuthComponentRule implements TestRule {

    private AuthComponent authComponent;

    public TestAuthComponentRule() {authComponent = new TestAuthComponent();}

    public TestAuthComponentRule(@NonNull AuthComponent component){
        this.authComponent = component;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                BodyFitApplication.setAuthComponent(authComponent);
                base.evaluate();
            }
        };
    }
}
