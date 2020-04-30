package com.maci.foxconn.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

public class ActivityUtils {
    public static void jumpToActivity(Button button, Context context, Class clazz) {
        new Thread(() -> {
            if (button != null) button.setClickable(false);
            context.startActivity(new Intent(context, clazz));
            if (button != null) button.setClickable(true);
        }).start();
    }

    public static String getParamsInfo(Activity activity, String name) {
        return activity.getIntent().getStringExtra(name);
    }
}
