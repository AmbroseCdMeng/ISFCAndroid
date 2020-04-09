package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends TitleBarActivity {

    private TextView muserno;
    private TextView musername;
    private EditText moldPwd;
    private EditText mnewPwd;
    private EditText mrePwd;

    private Button mchangePwd;
    private Button mlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        initView();
        intiEvent();
    }

    private void intiEvent() {
         mchangePwd.setOnClickListener( v -> mchangePwd());
         mlogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        Toast.makeText(UserActivity.this, "注销", Toast.LENGTH_LONG).show();
    }

    private void mchangePwd() {
        Toast.makeText(UserActivity.this, "修改密码", Toast.LENGTH_LONG).show();
    }

    private void initView() {
        super.initTitleView();
        showTitle(false, null, null);

        showLeft(true, "<首页", v -> finish());
        showRight(true, "用户名", v -> {return;} );

        muserno = findViewById(R.id.tv_userno);
        musername = findViewById(R.id.tv_username);
        moldPwd = findViewById(R.id.et_oldPwd);
        mnewPwd = findViewById(R.id.et_newPwd);
        mrePwd = findViewById(R.id.et_rePwd);

        mchangePwd = findViewById(R.id.btn_cPwd);
        mlogout = findViewById(R.id.btn_logout);
    }

    private String getUserNo(){
        return muserno.getText().toString().trim();
    }

    private String getUserName(){
        return musername.getText().toString().trim();
    }

    private String getOldPwd(){
        return moldPwd.getText().toString().trim();
    }

    private String getNewPwd(){
        return mnewPwd.getText().toString().trim();
    }

    private String getRePwd(){
        return mrePwd.getText().toString().trim();
    }
}
