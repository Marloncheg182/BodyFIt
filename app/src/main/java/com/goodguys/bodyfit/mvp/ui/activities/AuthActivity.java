package com.goodguys.bodyfit.mvp.ui.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.ui.fragments.SignInFragment;
import com.goodguys.bodyfit.mvp.ui.fragments.SignInFragment_;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

@EActivity(R.layout.auth_activity)
public class AuthActivity extends MvpAppCompatActivity {
    private static final String LOG_TAG = "AuthActivity";
    @ViewById(R.id.auth_layout)
    LinearLayout mAuthLayout;
    private GoogleApiClient mGoogleApiClient;

    @AfterViews
    void loadSignInScreen() {
        Log.d(LOG_TAG, "loadSignInScreen");
        getSupportFragmentManager().beginTransaction().add(R.id.auth_fragment_container, new SignInFragment_()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
        GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        if (mGoogleApiClient != null) {
            Log.d(LOG_TAG, "GoogleApiClient connecting");
            mGoogleApiClient.connect();
        } else {
            Log.d(LOG_TAG, "GoogleApiClient building");
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            List<Fragment> fragments = getSupportFragmentManager().getFragments();
                            if (fragments != null) {
                                for (final Fragment fragment : fragments) {
                                    if (fragment instanceof SignInFragment) {
                                        ((SignInFragment) fragment).failedSignIn(connectionResult.getErrorMessage());
                                    }
                                }
                            }
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                    .build();
        }
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void replaceFragment(Fragment fragment) {
        Log.d(LOG_TAG, "replaceFragment");
        getSupportFragmentManager().beginTransaction().replace(R.id.auth_fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "onBackPressed - " + String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void showSnackBar(String message) {
        Log.d(LOG_TAG, "showSnackBar - " + message);
        Snackbar snackbar = Snackbar.make(mAuthLayout,
                message,
                Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.show();
    }
}
