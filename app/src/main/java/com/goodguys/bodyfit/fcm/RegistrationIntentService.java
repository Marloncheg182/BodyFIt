package com.goodguys.bodyfit.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.goodguys.bodyfit.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class RegistrationIntentService extends IntentService {
    private static final String LOG_TAG = "RegIntentService";
    private String token;

    public RegistrationIntentService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleIntent");
        token = FirebaseInstanceId.getInstance().getToken();
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
        Log.d(LOG_TAG, "FCM Registration Token: " + token);
    }


    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }
}

