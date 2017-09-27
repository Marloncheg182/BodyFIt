package com.goodguys.bodyfit.mvp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.ui.activities.HomeActivity_;
import com.goodguys.bodyfit.mvp.ui.activities.TutorialActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Oleg Romanenchuk on 23.09.2017.
 */

@EFragment(R.layout.third_tutorial_fragment)
public class ThirdTutorialFragment extends MvpAppCompatFragment {
    private static final String LOG_TAG = "ThirdTutorialFragment";
    private TutorialActivity mActivity;
    @ViewById(R.id.tutorial_third_close_button)
    TextView mCloseButton;

    @Override
    public void onAttach(Context context) {
        mActivity = (TutorialActivity) getActivity();
        super.onAttach(context);
    }

    @Click(R.id.tutorial_third_close_button)
    public void closeTutorial(){
        Log.d(LOG_TAG, "closeTutorial");
        HomeActivity_.intent(getContext()).flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).start();
    }
}
