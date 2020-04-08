package com.maci.foxconn.utils;

import android.app.Activity;
import android.widget.Toast;

import java.util.UUID;

/***
 * 自定义工具类
 * 
 * @author AmbroseCdMeng

 * @time 2020/3/28 上午 11:46
 ***/
public class Utils {

    /**
     * Show a toast
     * @param activity
     * @param msg
     */
    public static void toast(final Activity activity, final String msg){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * return a new guid
     *
     * @return
     */

    public static String GUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }
}
