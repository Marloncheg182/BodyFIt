package com.goodguys.bodyfit.mvp.ui.fragments;

import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.presenters.SignUpPresenter;
import com.goodguys.bodyfit.mvp.views.SignUpView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Oleg Romanenchuk on 19.09.2017.
 */

@EFragment(R.layout.sign_up_fragment)
public class SignUpFragment extends MvpFragment implements SignUpView{
    public static final String LOG_TAG = "SignInFragment";

    @InjectPresenter
    SignUpPresenter mSignUpPresenter;

    @ViewById(R.id.sign_up_email_et)
    EditText mEmailEt;
    @ViewById(R.id.sign_up_password)
    EditText mPasswordEt;
    @ViewById(R.id.sign_up_register)
    Button mSignUpBtn;
    @ViewById(R.id.sign_up_back_btn)
    ImageButton mBackBtn;
    private DietPreloader mPreloader;
    private AlertDialog mErrorDialog;
    private AuthActivity mActivity;

}
