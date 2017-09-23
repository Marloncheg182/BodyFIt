package com.goodguys.bodyfit.mvp.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.goodguys.bodyfit.app.BodyFitApplication;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

public class PrefUtils {
    private static final String PREF_NAME = "bodyfit";

    public static SharedPreferences getPrefs() {
        return BodyFitApplication.getAppComponent().getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(){
        return getPrefs().edit();
    }
}
