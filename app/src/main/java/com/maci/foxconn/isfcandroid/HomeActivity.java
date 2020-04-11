package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
        showLeft(false);
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));

        minStorage = findViewById(R.id.btn_InStorage);
        moutStorage = findViewById(R.id.btn_OutStorage);
        masnRelated = findViewById(R.id.btn_AsnRelated);
        mmore = findViewById(R.id.btn_More);
    }

    private void initEvent() {
        minStorage.setOnClickListener(v -> jumpToInStorageWorkOrderView());
        moutStorage.setOnClickListener(v -> jumpToOutStorageWorkOrderView());
        masnRelated.setOnClickListener(v -> jumpToAsnRelatedView());
    }

    private void jumpToInStorageWorkOrderView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                minStorage.setClickable(false);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(HomeActivity.this, InStorageWorkOrderActivity.class));
                minStorage.setClickable(true);
            }
        }.start();
    }

    private void jumpToOutStorageWorkOrderView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                moutStorage.setClickable(false);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(HomeActivity.this, OutStorageWorkOrderActivity.class));
                moutStorage.setClickable(true);
            }
        }.start();
    }

    private void jumpToAsnRelatedView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                masnRelated.setClickable(false);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(HomeActivity.this, AsnRelatedActivity.class));
                masnRelated.setClickable(true);
            }
        }.start();
    }
}
