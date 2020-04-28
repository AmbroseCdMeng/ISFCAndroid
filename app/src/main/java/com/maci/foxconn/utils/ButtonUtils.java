package com.maci.foxconn.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.maci.foxconn.isfcandroid.HomeActivity;
import com.maci.foxconn.isfcandroid.InStorageWorkOrderActivity;

public class ButtonUtils {
    public static void jumpToActivity(Button button, Context context, Class clazz){
        new Thread(()->{
            button.setClickable(false);
            context.startActivity(new Intent(context, clazz));
            button.setClickable(true);
        }).start();
    }
}
