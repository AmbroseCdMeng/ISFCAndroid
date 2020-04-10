package com.maci.foxconn.isfcandroid;

import android.os.Bundle;

/***
 * 入库操作界面
 * 
 * @author AmbroseCdMeng

 * @time 2020/4/10 上午 08:50
 ***/
public class InStorageActivity extends TitleBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_storage);

        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
        super.initTitleView();
    }
}
