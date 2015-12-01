package com.means.foods.view.dialog;

import com.means.foods.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;


public class BaseAlertDialog extends AlertDialog {
    public BaseAlertDialog(Context context, int theme) {
        super(context, theme);
        Window window = getWindow();
        window.setWindowAnimations(R.style.mystyle);
    }

    long animduring = 250;

    int direction = 1;

    public BaseAlertDialog(Context context) {
        super(context);
        Window window = getWindow();
        window.setWindowAnimations(R.style.mystyle);
    }

}
