package com.maci.foxconn.utils;

import android.view.View;
import android.view.ViewGroup;

public class LayoutUtils {
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v == null) return ;
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
