package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 登录界面
 * 
 * @author AmbroseCdMeng

 * @time 2020/3/27 下午 04:00
 ***/
public class Login extends AppCompatActivity {

    private Boolean loginState = false;
    private TextView tv_title;
    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_remembrePassword;
    private TextView tv_forgetPassword;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tv_title = findViewById(R.id.tv_login_title);
        et_username = findViewById(R.id.ed_login_username);
        et_password = findViewById(R.id.ed_login_password);
        cb_remembrePassword = findViewById(R.id.cb_login_rememberPassword);
        tv_forgetPassword = findViewById(R.id.tv_login_forgetPassword);
        btn_confirm = findViewById(R.id.btn_login_confirm);

        initLogin();

        btn_confirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLogin() {
    }
}
