package com.goodguys.bodyfit.mvp.ui.fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.ui.activities.AuthActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

/**
 * Created by Oleg Romanenchuk on 21.09.2017.
 */

@EFragment(R.layout.body_fit_resetpass_dialog)
public class ResetPasswordDialog extends DialogFragment {
    private static final String LOG_TAG = "ResetPasswordDialog";
    private AuthActivity mActivity;

    @ViewById(R.id.reset_pass_email_et)
    EditText mEmailEt;
    @ViewById(R.id.reset_pass_input_layout)
    TextInputLayout mEmailLayout;
    @ViewById(R.id.reset_pass_tv)
    TextView mResetPassTv;
    @ViewById(R.id.reset_pass_progress)
    ProgressBar mProgressBar;
    @ViewById(R.id.reset_pass_title)
    TextView mTitleText;
    @StringRes(R.string.reset_pass_hint)
    String resetPassHint;
    @StringRes(R.string.progress_text)
    String loadingText;

    @Override
    public void onAttach(Context context) {
        Log.d(LOG_TAG, "onAttach");
        mActivity = (AuthActivity) getActivity();
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateDialog");
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                .TRANSPARENT));
        return dialog;
    }

    public void isShownProgress(final boolean isShown) {
        Log.d(LOG_TAG, "toggleProgress - " + String.valueOf(isShown));
        if (isShown) {
            mProgressBar.setVisibility(View.VISIBLE);
            mEmailLayout.setVisibility(View.GONE);
            mResetPassTv.setVisibility(View.GONE);
            mTitleText.setText(loadingText);
            mEmailEt.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmailLayout.setVisibility(View.VISIBLE);
            mResetPassTv.setVisibility(View.VISIBLE);
            mTitleText.setText(resetPassHint);
            mEmailEt.setVisibility(View.VISIBLE);
        }
    }

    @Click(R.id.reset_pass_tv)
    public void actionResetPassword() {
        Log.d(LOG_TAG, "actionResetPassword");
        List<Fragment> fragments = mActivity.getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (final Fragment fragment : fragments) {
                if (fragment instanceof SignInFragment) {
                    ((SignInFragment) fragment).delegateResetPassPresenter().resetPassword(mEmailEt.getText().toString());
                }
            }
        }
    }

    public TextInputLayout getInputField() {
        return mEmailLayout;
    }

}
