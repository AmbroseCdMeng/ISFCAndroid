package com.maci.foxconn.isfcandroid;

import android.content.Intent;
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
        showLeft(true, "<ASN 关联", null);
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));
    }
}
