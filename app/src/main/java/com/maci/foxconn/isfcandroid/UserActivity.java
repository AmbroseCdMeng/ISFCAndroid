package com.maci.foxconn.isfcandroid;

import android.os.Bundle;

public class UserActivity extends TitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        initView();
        intiEvent();
    }

    private void intiEvent() {

        showLeft(true, "<首页", v -> finish());
        showRight(true, "用户名", null );

    }

    private void initView() {

    }
}
