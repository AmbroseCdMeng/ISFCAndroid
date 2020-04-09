package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maci.foxconn.utils.Utils;


/***
 * 登录界面
 *
 * @author AmbroseCdMeng

 * @time 2020/3/27 下午 04:00
 ***/
public class LoginActivity extends TitleBarActivity {

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

        mlogin.setOnClickListener(v -> login());
        msPwd.setOnCheckedChangeListener((v, isChecked) -> {
            changePwdStatus(isChecked);
        });
        mfPwd.setOnClickListener(v -> forgetPwd());
    }

    private void forgetPwd() {
        Toast.makeText(LoginActivity.this, "忘记密码", Toast.LENGTH_LONG).show();
    }

    private void changePwdStatus(boolean isChecked){
        if (isChecked)
            mpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else
            mpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mpassword.setSelection(mpassword.getText().length());
    }


    private void login()  {
        if(musername.getText().toString().trim().isEmpty() || mpassword.getText().toString().trim().isEmpty())
        {
            Utils.toast(LoginActivity.this,"用户名密码不能为空" );
            return ;
        }

        new Thread(() -> {
            mlogin.setClickable(false);
            if ("Admin".equals(musername.getText().toString().trim())) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Utils.toast(LoginActivity.this, "登录成功");
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            } else
                Utils.toast(LoginActivity.this, "登录失败");
            mlogin.setClickable(true);
        }).start();
    }
}
