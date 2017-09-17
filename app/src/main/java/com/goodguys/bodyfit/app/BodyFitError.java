package com.goodguys.bodyfit.app;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

public class BodyFitError extends Throwable {
    public BodyFitError(ResponseBody responseBody){
        super(getMessage(responseBody));
    }

    private static String getMessage(ResponseBody responseBody){
        try {
            return new JSONObject(responseBody.string()).optString("message");
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }
        return "Request failure";
    }
}
