package com.goodguys.bodyfit.mvp.ui.fragments;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.ui.activities.TutorialActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Oleg Romanenchuk on 23.09.2017.
 */

@EFragment(R.layout.second_tutorial_fragment)
public class SecondTutorialFragment extends MvpAppCompatFragment {
    private static final String LOG_TAG = "SecondTutorialFragment";
    private TutorialActivity mActivity;
    @ViewById(R.id.tutorial_second_next_button)
    TextView mNextButton;

    @Override
    public void onAttach(Context context) {
        mActivity = (TutorialActivity) getActivity();
        super.onAttach(context);
    }

    @Click(R.id.tutorial_second_next_button)
    public void nextTutorial(){
        Log.d(LOG_TAG, "nextTutorial");
        mActivity.getTutorialPager().setCurrentItem(2);
    }
}
