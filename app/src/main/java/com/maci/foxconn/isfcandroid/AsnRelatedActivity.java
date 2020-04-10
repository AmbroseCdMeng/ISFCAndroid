package com.maci.foxconn.isfcandroid;

import android.os.Bundle;

public class AsnRelatedActivity extends TitleBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asn_related);
        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
        super.initTitleView();
    }
}
