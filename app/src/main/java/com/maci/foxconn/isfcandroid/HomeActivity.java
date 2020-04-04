package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.maci.foxconn.utils.Utils;

public class HomeActivity extends TitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initView();
    }

    private void initView() {
        super.initTitleView();
        showLeft(true, "上一步", (v)->Utils.toast(HomeActivity.this, "Go Back"));
        showRight(true, "下一步", null);
    }
}
