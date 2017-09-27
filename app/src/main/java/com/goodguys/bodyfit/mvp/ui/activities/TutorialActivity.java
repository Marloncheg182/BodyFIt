package com.goodguys.bodyfit.mvp.ui.activities;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.ui.adapters.TutorialPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Oleg Romanenchuk on 23.09.2017.
 */

@EActivity(R.layout.activity_tutorial)
public class TutorialActivity extends MvpAppCompatActivity{
    private static final String LOG_TAG = "TutorialActivity";

    @ViewById(R.id.tutorial_view_pager)
    ViewPager mTutorialPager;
    @ViewById(R.id.tutorial_first_indicator)
    ImageView mFirstIndicator;
    @ViewById(R.id.tutorial_second_indicator)
    ImageView mSecondIndicator;
    @ViewById(R.id.tutorial_third_indicator)
    ImageView mThirdIndicator;
    private TutorialPagerAdapter adapter;

    @AfterViews
    public void initPager() {
        Log.d(LOG_TAG, "initPager");
        getMvpDelegate().onAttach();
        adapter = new TutorialPagerAdapter(getSupportFragmentManager());
        mTutorialPager.setAdapter(adapter);
        mTutorialPager.setCurrentItem(0);
        mTutorialPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicatorSelect(position);
            }

            @Override
            public void onPageSelected(int position) {
                indicatorSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public ViewPager getTutorialPager() {
        return mTutorialPager;
    }

    private void indicatorSelect(int position) {
        Log.d(LOG_TAG, "indicatorSelected position: " + String.valueOf(position));
        switch (position) {
            case 0:
                mFirstIndicator.setImageResource(R.drawable.tutorial_indicator_selected);
                mSecondIndicator.setImageResource(R.drawable.tutorial_indicator_unselected);
                mThirdIndicator.setImageResource(R.drawable.tutorial_indicator_unselected);
                break;
            case 1:
                mFirstIndicator.setImageResource(R.drawable.tutorial_indicator_unselected);
                mSecondIndicator.setImageResource(R.drawable.tutorial_indicator_selected);
                mThirdIndicator.setImageResource(R.drawable.tutorial_indicator_unselected);
                break;
            case 2:
                mFirstIndicator.setImageResource(R.drawable.tutorial_indicator_unselected);
                mSecondIndicator.setImageResource(R.drawable.tutorial_indicator_unselected);
                mThirdIndicator.setImageResource(R.drawable.tutorial_indicator_selected);
                break;
        }
    }
}
