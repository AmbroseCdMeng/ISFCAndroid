package com.maci.foxconn.utils;

import android.content.Context;

public class UnitConvertUtils {

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
