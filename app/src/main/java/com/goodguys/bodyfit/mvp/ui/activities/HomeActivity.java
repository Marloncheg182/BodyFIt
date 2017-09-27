package com.goodguys.bodyfit.mvp.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.goodguys.bodyfit.R;
import com.goodguys.bodyfit.mvp.ui.fragments.HomeFragment_;
import com.goodguys.bodyfit.mvp.ui.fragments.SignInFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = "HomeActivity";

    @ViewById(R.id.main_layout_menu)
    RelativeLayout mLayout;
    @ViewById(R.id.sign_up_menu_btn)
    ImageButton mMenuBtn;
    @ViewById(R.id.home_activity_drawer)
    DrawerLayout mDrawerLayout;
    @ViewById(R.id.signout_layout_menu)
    RelativeLayout mSignOutLayout;
    private DrawerLayout.DrawerListener mDrawerListener;

    @AfterViews
    public void initActivity() {
        loadHomeScreen();
        initDrawer();
    }

    private void initDrawer() {
        Log.d(LOG_TAG, "initDrawer");
        mDrawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.d(LOG_TAG, "onDrawerSlide");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d(LOG_TAG, "onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(LOG_TAG, "onDrawerClosed");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d(LOG_TAG, "onDrawerStateChanged");
            }
        };
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        mDrawerLayout.addDrawerListener(mDrawerListener);
    }

    @Click(R.id.sign_up_menu_btn)
    public void openMenu() {
        Log.d(LOG_TAG, "openMenu");
        openDrawer();
    }

    @Click(R.id.main_layout_menu)
    public void openMain() {
        mLayout.setSelected(true);
    }

    @Click(R.id.signout_layout_menu)
    public void signOut() {
        Log.d(LOG_TAG, "signOut");
        //TODO set token ""
        Intent logoutIntent = new Intent(this, AuthActivity_.class);
        startActivity(logoutIntent);
        finish();
    }

    private void openDrawer() {
        Log.d(LOG_TAG, "openDrawer");
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        Log.d(LOG_TAG, "closeDrawer");
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onDestroy() {
        mDrawerLayout.removeDrawerListener(mDrawerListener);
        super.onDestroy();
    }

    public void loadHomeScreen() {
        Log.d(LOG_TAG, "loadSignInScreen");
        getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_container, new HomeFragment_()).commit();
    }


    public void replaceFragment(Fragment fragment) {
        Log.d(LOG_TAG, "replaceFragment");
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "onBackPressed");
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
