package com.goodguys.bodyfit.mvp.common;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

public class AuthUtils {
    private static final String TOKEN = "token";
    private static final String TUTORIAL = "tutorial";

    public static String getToken(){
        return PrefUtils.getPrefs().getString(TOKEN, "");
    }

    public static void setToken(String token){
        PrefUtils.getEditor().putString(TOKEN, token).commit();
    }

    public static Boolean getTutorial(){
        return PrefUtils.getPrefs().getBoolean(TUTORIAL, false);
    }

    public static void setTutorial(Boolean isSeen){
        PrefUtils.getEditor().putBoolean(TUTORIAL, isSeen);
    }
}
