package com.goodguys.bodyfit.test;

import com.goodguys.bodyfit.BuildConfig;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

/**
 * Created by Oleg Romanenchuk on 24.09.2017.
 */

public class BodyFitAuthTestRunner extends RobolectricTestRunner {

    private static final int SDK_EMULATE_LEVEL = 23;

    public BodyFitAuthTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public Config getConfig(Method method) {
        final Config defaultConfig = super.getConfig(method);
        return new Config.Implementation(
                new int[]{SDK_EMULATE_LEVEL},
                defaultConfig.manifest(),
                defaultConfig.qualifiers(),
                defaultConfig.packageName(),
                defaultConfig.resourceDir(),
                defaultConfig.assetDir(),
                defaultConfig.shadows(),
                defaultConfig.instrumentedPackages(),
                defaultConfig.application(),
                defaultConfig.libraries(),
                defaultConfig.constants() == Void.class ? BuildConfig.class : defaultConfig.constants()
        );
    }
}
