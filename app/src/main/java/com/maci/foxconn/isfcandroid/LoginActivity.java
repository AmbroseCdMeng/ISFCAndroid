package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maci.foxconn.utils.SharedPreferencesUtils;
import com.maci.foxconn.utils.Utils;

/***
 * 登录界面
 *
 * @author AmbroseCdMeng

 * @time 2020/3/27 下午 04:00
 ***/
public class LoginActivity extends AppCompatActivity {

    private EditText musername;
    private EditText mpassword;
    private CheckBox msPwd;
    private TextView mfPwd;
    private Button mlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initView();
        initEvent();

        musername.setText("Admin");
        mpassword.setText("Administrator");
    }


    private void initView() {
        musername = findViewById(R.id.ed_username);
        mpassword = findViewById(R.id.ed_pwd);
        msPwd = findViewById(R.id.cb_s_pwd);
        mfPwd = findViewById(R.id.tv_f_pwd);
        mlogin = findViewById(R.id.btn_login);

    }

    private void initEvent() {
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login()  {
        if(musername.getText().toString().trim().isEmpty() || mpassword.getText().toString().trim().isEmpty())
        {
            Utils.toast(LoginActivity.this,"用户名密码不能为空" );
            return ;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                mlogin.setClickable(false);
                if ("Admin".equals(musername.getText().toString().trim())) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.toast(LoginActivity.this, "登录成功");
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else
                    Utils.toast(LoginActivity.this, "登录失败");
                mlogin.setClickable(true);
            }
        }.start();
    }
}
