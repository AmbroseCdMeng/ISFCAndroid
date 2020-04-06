package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.maci.foxconn.utils.Utils;

/***
 * 主页（菜单）界面
 * 
 * @author AmbroseCdMeng

 * @time 2020/4/6 上午 10:40
 ***/
public class HomeActivity extends TitleBarActivity {

    private Button minStorage;
    private Button moutStorage;
    private Button masnRelated;
    private Button mmore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initView();
        initEvent();
    }

    private void initView() {
        super.initTitleView();
        showLeft(false, null, null);
        showRight(true, "用户名", (v) -> Utils.toast(HomeActivity.this, "User Details"));

        minStorage = findViewById(R.id.btn_InStorage);
        moutStorage = findViewById(R.id.btn_OutStorage);
        masnRelated = findViewById(R.id.btn_AsnRelated);
        mmore = findViewById(R.id.btn_More);
    }

    private void initEvent() {
        minStorage.setOnClickListener(v -> jumpToInStorageWorkOrderView());
    }

    private void jumpToInStorageWorkOrderView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                minStorage.setClickable(false);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(HomeActivity.this, InStorageWorkOrderActivity.class));
                minStorage.setClickable(true);
            }
        }.start();
    }
}
