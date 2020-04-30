package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends TitleBarActivity {

    @BindView(R.id.tv_userno)
    TextView muserno;

    @BindView(R.id.tv_username)
    TextView musername;
    @BindView(R.id.et_oldPwd)
    EditText moldPwd;
    @BindView(R.id.et_newPwd)
    EditText mnewPwd;
    @BindView(R.id.et_rePwd)
    EditText mrePwd;

    @BindView(R.id.btn_cPwd)
    Button mchangePwd;
    @BindView(R.id.btn_logout)
    Button mlogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        ButterKnife.bind(this);
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

    private void showTitleBtn() {
        super.initTitleView();
        showTitle(false);
        showLeft(true, "<首页", v -> finish());
        showRight(false );

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
