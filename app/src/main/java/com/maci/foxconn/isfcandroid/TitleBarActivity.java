package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TitleBarActivity extends AppCompatActivity {

    public TextView mprev;
    public TextView mtitle;
    public TextView muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_bar);

        initView();
    }

    private void initView() {
        mprev = findViewById(R.id.tv_prev);
        mtitle = findViewById(R.id.tv_title);
        muser = findViewById(R.id.tv_user);
    }
}
