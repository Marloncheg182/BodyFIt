package com.goodguys.bodyfit.mvp.ui.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.presenters.SignUpPresenter;
import com.goodguys.bodyfit.mvp.ui.activities.AuthActivity;
import com.goodguys.bodyfit.mvp.ui.activities.HomeActivity_;
import com.goodguys.bodyfit.mvp.ui.activities.TutorialActivity_;
import com.goodguys.bodyfit.mvp.ui.dialogs.BodyFitProgressDialog;
import com.goodguys.bodyfit.mvp.views.SignUpView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

/**
 * Created by Oleg Romanenchuk on 19.09.2017.
 */

@EFragment(R.layout.sign_up_fragment)
public class SignUpFragment extends MvpAppCompatFragment implements SignUpView{
    public static final String LOG_TAG = "SignUpFragment";

    @InjectPresenter
    SignUpPresenter mSignUpPresenter;

    @ViewById(R.id.sign_up_email_et)
    EditText mEmailEt;
    @ViewById(R.id.sign_up_email_input_layout)
    TextInputLayout mEmailInputLayout;
    @ViewById(R.id.sign_up_password)
    EditText mPasswordEt;
    @ViewById(R.id.sign_up_password_input_layout)
    TextInputLayout mPasswordInputLayout;
    @ViewById(R.id.sign_up_button)
    Button mSignUpBtn;
    @ViewById(R.id.sign_up_back_btn)
    ImageButton mBackBtn;
    private BodyFitProgressDialog mProgressDialog;
    private AlertDialog mErrorDialog;
    private AuthActivity mActivity;

    @Override
    public void onAttach(Context context) {
        Log.d(LOG_TAG, "onAttach");
        mActivity = (AuthActivity) getActivity();
        super.onAttach(context);
    }

    @AfterViews
    public void initProgressDialog(){
        Log.d(LOG_TAG, "initProgressDialog");
        mProgressDialog = new BodyFitProgressDialog(mActivity, R.style.BodyFitDialog);
    }

    private void toggleProgress(final boolean isShown) {
        Log.d(LOG_TAG, "toggleProgress - " + String.valueOf(isShown));
        if (isShown) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Click(R.id.sign_up_back_btn)
    public void backPressed(){
        Log.d(LOG_TAG, "backPressed");
        mActivity.onBackPressed();
    }

    @Click(R.id.sign_up_button)
    public void registerUser() {
        Log.d(LOG_TAG, "registerUser");
        //TODO replace
//        mSignUpPresenter.signUpRegular(mEmailEt.getText().toString(), mPasswordEt.getText().toString());

        mSignUpPresenter.signUpRegularTEST(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
    }

    @Override
    public void startSignUp() {
        Log.d(LOG_TAG, "startSignUp");
        toggleProgress(true);
    }

    @Override
    public void finishSignUp() {
        Log.d(LOG_TAG, "finishSignUp");
        toggleProgress(false);
    }

    @Override
    public void failedSignUp(String message) {
        Log.d(LOG_TAG, "failedSignUp - " + message);
        mErrorDialog = new AlertDialog.Builder(mActivity, R.style.BodyFitErrorDialog)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(getString(R.string.okay), (dialog, which) -> dialog.dismiss())
                .setOnCancelListener(dialog -> mSignUpPresenter.onErrorCancel())
                .show();
    }

    @Override
    public void hideError() {
        Log.d(LOG_TAG, "hideError");
        if (mErrorDialog != null && mErrorDialog.isShowing()) {
            mErrorDialog.cancel();
        }
    }

    @Override
    public void hideFormError() {
        Log.d(LOG_TAG, "hideFormError");
        mEmailInputLayout.setError(null);
        mPasswordInputLayout.setError(null);
    }

    @Override
    public void showFormError(Integer emailError, Integer passwordError) {
        Log.d(LOG_TAG, "showFormError");
        mEmailInputLayout.setError(emailError == null ? null : getString(emailError));
        mPasswordInputLayout.setError(passwordError == null ? null : getString(passwordError));
    }

    @Override
    public void successSignUp() {
        Log.d(LOG_TAG, "successSignUp");
        //TODO rework method to check tutorial seen
//        final Intent intent = new Intent(mActivity, HomeActivity_.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

        final Intent intent = new Intent(mActivity, TutorialActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void sendSignUpSuccessNotification(){
        Log.d(LOG_TAG, "sendSignUpSuccessNotification");
        Random random = new Random();
        int messageId = random.nextInt(9999 - 1000);
        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity, messageId, new Intent(),
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mActivity)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(getString(R.string.notification_sign_up_title))
        .setContentText(getString(R.string.notification_sign_up_message))
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager)
                        mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId, notificationBuilder.build());

    }
}
