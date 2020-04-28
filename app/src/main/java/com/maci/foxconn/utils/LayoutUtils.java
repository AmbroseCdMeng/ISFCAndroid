package com.maci.foxconn.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.maci.foxconn.isfcandroid.R;

import java.lang.reflect.Field;

public class LayoutUtils {
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v == null) return;
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static int getMipmapIdByName(String name){
        return getIdByName(name, R.mipmap.class);
    }


    private static int getIdByName(String name, Class clazz) {
//        Class mipmap = R.mipmap.class;
        try {
            Field field = clazz.getField(name);
            int resId = field.getInt(name);
            return resId;
        } catch (NoSuchFieldException e) {
            return 0;
        } catch (IllegalAccessException e) {
            return 0;
        }
    }
}