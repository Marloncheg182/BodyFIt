package com.goodguys.bodyfit.mvp.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.common.CheckNull;
import com.goodguys.bodyfit.common.Constants;
import com.goodguys.bodyfit.mvp.presenters.ResetPasswordPresenter;
import com.goodguys.bodyfit.mvp.presenters.SignInPresenter;
import com.goodguys.bodyfit.mvp.ui.activities.AuthActivity;
import com.goodguys.bodyfit.mvp.ui.activities.HomeActivity_;
import com.goodguys.bodyfit.mvp.ui.activities.TutorialActivity_;
import com.goodguys.bodyfit.mvp.ui.dialogs.BodyFitProgressDialog;
import com.goodguys.bodyfit.mvp.views.ResetPasswordView;
import com.goodguys.bodyfit.mvp.views.SignInView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Romanenchuk on 18.09.2017.
 */

@EFragment(R.layout.sign_in_fragment)
public class SignInFragment extends MvpAppCompatFragment implements SignInView, ResetPasswordView {
    private static final String LOG_TAG = "SignInFragment";
    @InjectPresenter
    SignInPresenter mSignInPresenter;

    @InjectPresenter
    ResetPasswordPresenter mResetPasswordPresenter;

    private static final int GOOGLE_NETWORK = 201;
    private static final int FACEBOOK_NETWORK = 202;
    private static final int TWITTER_NETWORK = 203;
    @ViewById(R.id.sign_in_email_et)
    EditText mEmailEt;
    @ViewById(R.id.sign_in_email_input_layout)
    TextInputLayout mEmailInputLayout;
    @ViewById(R.id.sign_in_password)
    EditText mPasswordEt;
    @ViewById(R.id.sign_in_password_input_layout)
    TextInputLayout mPasswordInputLayout;
    @ViewById(R.id.sign_in_button)
    Button mSignIngBtn;
    @ViewById(R.id.sign_in_register)
    TextView mSignUpBtn;
    @ViewById(R.id.sign_in_facebook)
    LinearLayout mFacebookBtn;
    @ViewById(R.id.sign_in_google)
    LinearLayout mGoogleBtn;
    @ViewById(R.id.sign_in_twitter)
    LinearLayout mTwitterBtn;
    @ViewById(R.id.reset_pass_tv)
    TextView mForgotPassword;
    @StringRes(R.string.network_auth_error)
    String networkError;
    @StringRes(R.string.permission_error_title)
    String permissionGrantedTitle;
    @StringRes(R.string.permission_app_error)
    String appPermissionError;
    @ColorRes(R.color.colorAccent)
    int textColorRes;
    private BodyFitProgressDialog mProgressDialog;
    private AlertDialog mErrorDialog;
    private AlertDialog mSocialErrorDialog;
    private AuthActivity mActivity;
    private CallbackManager mCallbackManager;
    private TwitterAuthClient mTwitterAuthClient;
    private AlertDialog mPermissionDialog;
    private int mLoginCode;
    private ResetPasswordDialog_ mResetPasswordDialog;

    @Override
    public void onAttach(Context context) {
        Log.d(LOG_TAG, "onAttach");
        mActivity = (AuthActivity) getActivity();
        super.onAttach(context);
    }

    @AfterViews
    public void requestPermissions() {
        Log.d(LOG_TAG, "requestPermissions");
        initPermissionsDialog();
        mProgressDialog = new BodyFitProgressDialog(mActivity, R.style.BodyFitDialog);
        RxPermissions permissions = new RxPermissions(mActivity);
        permissions
                .request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (!granted) {
                        mPermissionDialog.show();
                    } else {
                        initCallbacks();
                    }
                });
    }

    private void initPermissionsDialog() {
        Log.d(LOG_TAG, "initPermissionsDialog");
        mPermissionDialog = new AlertDialog.Builder(mActivity)
                .setCancelable(false)
                .setTitle(permissionGrantedTitle)
                .setMessage(appPermissionError)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(LOG_TAG, "permission dialog - onOkClick");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 102);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(LOG_TAG, "permission dialog - onCancelClick");
                        dialog.dismiss();
                    }
                })
                .create();
    }

    private void initCallbacks() {
        Log.d(LOG_TAG, "initCallbacks");
        initFacebook();
        mPasswordEt.setOnEditorActionListener((textView, id, event) -> {
            if (id == R.id.sign_in_password || id == EditorInfo.IME_NULL) {
                signIn();
                return true;
            }
            return false;
        });

    }

    private void initFacebook() {
        Log.d(LOG_TAG, "initFacebook");
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(LOG_TAG, "initFacebook - registerCallback - onSuccess");
                        AccessToken accessToken = loginResult.getAccessToken();
                        String mNetwork = Constants.FACEBOOK;
                        String mNetworkKey = accessToken.getToken();
                        mSignInPresenter.signInSocial(mNetwork, mNetworkKey);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(LOG_TAG, "initFacebook - registerCallback - onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(LOG_TAG, "initFacebook - registerCallback - onError");
                        failedSignIn(exception.getMessage());
                    }
                });
    }


    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Log.d(LOG_TAG, "handleGoogleSignInResult");
        if (result.isSuccess()) {
            GoogleSignInAccount googleAccount = result.getSignInAccount();
            CheckNull.check(googleAccount, argument -> {
                String mNetwork = Constants.GOOGLE;
                String mNetworkKey = argument.getIdToken();
                mSignInPresenter.signInSocial(mNetwork, mNetworkKey);
            }, () -> mActivity.showSnackBar(networkError));
        }
    }

    @Click(R.id.sign_in_button)
    public void signIn() {
        Log.d(LOG_TAG, "signIn");
        //TODO replace
//        mSignInPresenter.signInRegular(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
        mSignInPresenter.signInTEST(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
    }

    @Click(R.id.sign_in_facebook)
    public void signInFacebook() {
        Log.d(LOG_TAG, "signInFacebook");
        mLoginCode = FACEBOOK_NETWORK;
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);
    }

    @Click(R.id.sign_in_google)
    public void signInGoogle() {
        Log.d(LOG_TAG, "signInGoogle");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mActivity.getGoogleApiClient());
        startActivityForResult(signInIntent, GOOGLE_NETWORK);
    }

    @Click(R.id.sign_in_twitter)
    public void signInTwitter() {
        Log.d(LOG_TAG, "signInTwitter");
        mLoginCode = TWITTER_NETWORK;
        mTwitterAuthClient = new TwitterAuthClient();
        mTwitterAuthClient.authorize(mActivity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(LOG_TAG, "signInTwitter - success");
                TwitterSession session = result.data;
                long id = session.getId();
                Log.d(LOG_TAG, "twitter id = " + id);
                String mNetwork = Constants.TWITTER;
                String mNetworkKey = String.valueOf(id);
                mSignInPresenter.signInSocial(mNetwork, mNetworkKey);
            }

            @Override
            public void failure(TwitterException e) {
                Log.d(LOG_TAG, "signInTwitter - failure - " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Click(R.id.sign_in_register)
    public void registerUser() {
        Log.d(LOG_TAG, "registerUser");
        mActivity.replaceFragment(new SignUpFragment_());
    }

    @Click(R.id.reset_pass_tv)
    public void resetPassword() {
        Log.d(LOG_TAG, "resetPassword");
        mResetPasswordDialog = new ResetPasswordDialog_();
        mResetPasswordDialog.show(getFragmentManager(), "reset_pass_dialog");
    }

    private void toggleProgress(final boolean isShown) {
        Log.d(LOG_TAG, "toggleProgress - " + String.valueOf(isShown));
        if (isShown) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void startSignIn() {
        Log.d(LOG_TAG, "startSignIn");
        toggleProgress(true);
    }

    @Override
    public void startSignUp() {
        Log.d(LOG_TAG, "startSignUp");
        toggleProgress(true);
    }

    @Override
    public void finishSignIn() {
        Log.d(LOG_TAG, "finishSignIn");
        toggleProgress(false);
    }

    @Override
    public void finishSignUp() {
        Log.d(LOG_TAG, "finishSignUp");
        toggleProgress(false);
    }

    @Override
    public void failedSignIn(String message) {
        Log.d(LOG_TAG, "failedSignIn - " + message);
        mErrorDialog = new AlertDialog.Builder(mActivity, R.style.BodyFitErrorDialog)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(getString(R.string.okay), (dialog, which) -> dialog.dismiss())
                .setOnCancelListener(dialog -> mSignInPresenter.onErrorCancel())
                .show();
    }

    @Override
    public void failedSignUp(String message) {
        Log.d(LOG_TAG, "failedSignUp - " + message);
        mErrorDialog = new AlertDialog.Builder(mActivity, R.style.BodyFitErrorDialog)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(getString(R.string.okay), (dialog, which) -> dialog.dismiss())
                .setOnCancelListener(dialog -> mSignInPresenter.onErrorCancel())
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
    public void showRegularFormError(Integer emailError, Integer passwordError) {
        Log.d(LOG_TAG, "showRegularFormError");
        mEmailInputLayout.setError(emailError == null ? null : getString(emailError));
        mPasswordInputLayout.setError(passwordError == null ? null : getString(passwordError));
    }

    @Override
    public void showNetworkFormError(Integer networkKeyError) {
        Log.d(LOG_TAG, "showNetworkFormError");
        mSocialErrorDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_name)
                .setMessage(R.string.invalid_social_user)
                .setPositiveButton(getString(R.string.okay), (dialog, which) -> dialog.dismiss())
                .setOnCancelListener(DialogInterface::dismiss)
                .show();
    }

    @Override
    public void successSignIn() {
        Log.d(LOG_TAG, "successSignIn");
        //TODO rework method to check tutorial seen
//        final Intent intent = new Intent(mActivity, HomeActivity_.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

        final Intent intent = new Intent(mActivity, TutorialActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
    public void startResetPassword() {
        Log.d(LOG_TAG, "startResetPassword");
        mResetPasswordDialog.isShownProgress(true);
    }

    @Override
    public void finishResetPassword() {
        Log.d(LOG_TAG, "finishResetPassword");
        mResetPasswordDialog.isShownProgress(false);
    }

    @Override
    public void failedResetPassword(String message) {
        Log.d(LOG_TAG, "failedResetPassword - " + message);
        mActivity.showSnackBar(message);
    }

    @Override
    public void hideFormPasswordError() {
        Log.d(LOG_TAG, "hideFormPasswordError");
        mResetPasswordDialog.getInputField().setError(null);
    }

    @Override
    public void showResetPasswordFormError(Integer emailError) {
        Log.d(LOG_TAG, "showRegularFormError");
        mResetPasswordDialog.getInputField().setError(emailError == null ? null : getString(emailError));
    }

    @Override
    public void successResetPassword() {
        Log.d(LOG_TAG, "successResetPassword");
        mResetPasswordDialog.dismiss();
        mActivity.showSnackBar(getString(R.string.reset_password_success));
    }

    public ResetPasswordPresenter delegateResetPassPresenter(){
        return mResetPasswordPresenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult");
        if (requestCode == GOOGLE_NETWORK) {
            Log.d(LOG_TAG, "onActivityResult - GOOGLE");
            if (data != null) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleGoogleSignInResult(result);
            }
        }
        switch (mLoginCode) {
            case FACEBOOK_NETWORK:
                Log.d(LOG_TAG, "onActivityResult - FACEBOOK");
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case TWITTER_NETWORK:
                Log.d(LOG_TAG, "onActivityResult - TWITTER");
                mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
