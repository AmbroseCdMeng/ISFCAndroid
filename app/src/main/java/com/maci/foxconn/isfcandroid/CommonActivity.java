package com.maci.foxconn.isfcandroid;

import android.support.v7.app.AppCompatActivity;

import com.maci.foxconn.utils.MachineUtils;
import com.maci.foxconn.utils.PropertiesUtils;

public class CommonActivity extends AppCompatActivity {

    private static boolean ReleaseMode;
    private static String CurrentUser;

    public static boolean isReleaseMode() {
        return ReleaseMode;
    }

    public static void setReleaseMode(boolean releaseMode) {
        ReleaseMode = releaseMode;
    }

    public static String getIP() {
        return MachineUtils.getLocalIPAddress();
    }

    public static String getSYSID() {
        return PropertiesUtils.getAppConfigProperties("SYSID");
    }

    public static String getAPIURL() {
        return isReleaseMode() ? PropertiesUtils.getNetConfigProperties("API_RELEASE") : PropertiesUtils.getNetConfigProperties("API_DEBUG");
    }

    public static String getCurrentUser() {
        return CurrentUser;
    }

    public static void setCurrentUser(String currentUser) {
        CommonActivity.CurrentUser = currentUser;
    }
}
