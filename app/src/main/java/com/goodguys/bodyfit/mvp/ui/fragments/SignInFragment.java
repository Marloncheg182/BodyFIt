package com.goodguys.bodyfit.mvp.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpFragment;
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
import com.goodguys.bodyfit.mvp.presenters.SignInPresenter;
import com.goodguys.bodyfit.mvp.ui.activities.AuthActivity;
import com.goodguys.bodyfit.mvp.ui.dialogs.BodyFitProgressDialog;
import com.goodguys.bodyfit.mvp.views.SignInView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Romanenchuk on 18.09.2017.
 */

@EFragment(R.layout.sign_in_fragment)
public class SignInFragment extends MvpFragment implements SignInView {
    private static final String LOG_TAG = "SignInFragment";
    private static final int GOOGLE_NETWORK = 201;
    private static final int FACEBOOK_NETWORK = 202;
    private static final int TWITTER_NETWORK = 203;
    @ViewById(R.id.sign_in_email_et)
    EditText mEmailEt;
    @ViewById(R.id.sign_in_password)
    EditText mPasswordEt;
    @ViewById(R.id.sign_in_button)
    Button mSignIngBtn;
    @ViewById(R.id.sign_in_register)
    TextView mSignUpBtn;
    @ViewById(R.id.sign_in_facebook)
    Button mFacebookBtn;
    @ViewById(R.id.sign_in_google)
    Button mGoogleBtn;
    @ViewById(R.id.sign_in_twitter)
    Button mTwitterBtn;
    @StringRes(R.string.network_auth_error)
    String networkError;
    @StringRes(R.string.permission_error_title)
    String permissionGrantedTitle;
    @StringRes(R.string.permission_app_error)
    String appPermissionError;
    private BodyFitProgressDialog mProgressDialog;
    private AlertDialog mErrorDialog;
    private AlertDialog mSocialErrorDialog;
    private AuthActivity mActivity;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private TwitterAuthClient mTwitterAuthClient;
    private AlertDialog mPermissionDialog;
    private int mLoginCode;
    @InjectPresenter
    SignInPresenter mSignInPresenter;

    @Override
    public void onAttach(Context context) {
        Log.d(LOG_TAG, "onAttach");
        mActivity = (AuthActivity) getActivity();
        super.onAttach(context);
    }

    @AfterViews
    public void requestPermissions(){
        Log.d(LOG_TAG, "requestPermissions");
        initPermissionsDialog();
        RxPermissions permissions = new RxPermissions(mActivity);
        permissions
                .request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (!granted) {
                        mPermissionDialog.show();
                    }else {
                        initCallbacks();
                    }
                });
    }

    //TODO reset password dialog integration (email)

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

    public void initCallbacks() {
        Log.d(LOG_TAG, "initCallbacks");
        initFacebook();
        initGoogle();
        mProgressDialog = new BodyFitProgressDialog(mActivity, R.style.BodyFitDialog);
        mPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
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

    private void initGoogle() {
        Log.d(LOG_TAG, "initGoogle");
        GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(LOG_TAG, "initGoogle - onConnectionFailed");
                        failedSignIn(connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
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
        mSignInPresenter.signInRegular(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
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
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
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
        Log.d(LOG_TAG, "registration");
        mActivity.replaceFragment(new SignUpFragment_());
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
        mErrorDialog = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setOnCancelListener(dialog -> mSignInPresenter.onErrorCancel())
                .show();
    }

    @Override
    public void failedSignUp(String message) {
        Log.d(LOG_TAG, "failedSignUp - " + message);
        mErrorDialog = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.app_name)
                .setMessage(message)
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
        mEmailEt.setError(null);
        mPasswordEt.setError(null);
    }

    @Override
    public void showRegularFormError(Integer emailError, Integer passwordError) {
        Log.d(LOG_TAG, "hideFormError");
        mEmailEt.setError(emailError == null ? null : getString(emailError));
        mPasswordEt.setError(passwordError == null ? null : getString(passwordError));
    }

    @Override
    public void showNetworkFormError(Integer networkKeyError) {
        Log.d(LOG_TAG, "showNetworkFormError");
        mSocialErrorDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.app_name)
                .setMessage(R.string.invalid_social_user)
                .setOnCancelListener(DialogInterface::dismiss)
                .show();
    }

    @Override
    public void successSignIn() {
        Log.d(LOG_TAG, "successSignIn");
        final Intent intent = new Intent(this, HomeActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void successSignUp() {
        Log.d(LOG_TAG, "successSignUp");
        final Intent intent = new Intent(this, HomeActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
