package com.javayhu.kiss.views.dialog;

import android.content.Context;

import com.javayhu.kiss.views.R;

/**
 * Created by yarolegovich on 16.04.2016.
 */
public class ProgressDialog extends AbsDialog<ProgressDialog> {

    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    {
        setCancelable(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_progress;
    }
}
