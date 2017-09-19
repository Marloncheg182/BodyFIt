package com.goodguys.bodyfit.mvp.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.goodguys.bodyfit.R;

/**
 * Created by Oleg Romanenchuk on 18.09.2017.
 */

public class BodyFitProgressDialog extends Dialog {
    private Context mContext;

    public BodyFitProgressDialog(Context mContext, int themeResId) {
        super(mContext, themeResId);
        this.mContext = mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_fit_progress_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}