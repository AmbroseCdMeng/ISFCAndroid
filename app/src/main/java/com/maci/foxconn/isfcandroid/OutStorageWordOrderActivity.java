package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;

/***
 * 出库工龄单界面
 * 
 * @author AmbroseCdMeng

 * @time 2020/4/10 上午 09:05
 ***/
public class OutStorageWordOrderActivity extends TitleBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_storage_work_order);
        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
        super.initTitleView();
        showLeft(true, "<出库工令单", null);
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));
    }
}
