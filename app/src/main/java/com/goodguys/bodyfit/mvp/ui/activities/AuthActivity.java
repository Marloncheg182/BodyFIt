package com.goodguys.bodyfit.mvp.ui.activities;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.goodguys.bodyfit.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Oleg Romanenchuk on 17.09.2017.
 */

@EActivity(R.layout.auth_activity)
public class AuthActivity extends MvpAppCompatActivity {
    @ViewById(R.id.auth_layout)
    LinearLayout mAuthLayout;

    @AfterViews
    void loadSignInScreen() {
        getSupportFragmentManager().beginTransaction().add(R.id.auth_fragment_container, new SignInFragment_()).commit();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.auth_fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(mAuthLayout,
                message,
                Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.show();
    }
}
