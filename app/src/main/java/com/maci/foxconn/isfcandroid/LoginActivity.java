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

    private Boolean mloginState = false;
    private TextView mtitle;
    private EditText musername;
    private EditText mpassword;
    private CheckBox mremembrePassword;
    private TextView mforgetPassword;
    private Button mconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initView();
        initEvent();
        initLogin();

        mconfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        mtitle = findViewById(R.id.tv_login_title);
        musername = findViewById(R.id.ed_login_username);
        mpassword = findViewById(R.id.ed_login_password);
        mremembrePassword = findViewById(R.id.cb_login_rememberPassword);
        mforgetPassword = findViewById(R.id.tv_login_forgetPassword);
        mconfirm = findViewById(R.id.btn_login_confirm);

    }

    private void initEvent() {

    }

    private void initLogin() {
        //首次登录
        if (false) {
            mremembrePassword.setChecked(false);
        }
        //记住密码
        if (mremembrePassword.isChecked()) {
            musername.setText(getLocalName());
            mpassword.setText(getLocalPassword());
        }

    }

    private void login() {
        if (getUsername().isEmpty()) {
            Utils.toast(LoginActivity.this, "用户名不能为空");
            return;
        }

        //显示加载 Loading

        new Thread() {
            @Override
            public void run() {
                super.run();
                setBtnConfirmClickable(false);

                // 登录判断

                if (true) {
                    Utils.toast(LoginActivity.this, "登录成功");
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else
                    Utils.toast(LoginActivity.this, "登录失败");
                Utils.toast(LoginActivity.this, "登录失败");
                setBtnConfirmClickable(true);

            }
        }.start();
    }

    /**
     * 设置登录按钮可点击状态
     *
     * @param b
     */
    private void setBtnConfirmClickable(boolean b) {
        mconfirm.setClickable(b);
    }

    private String getUsername() {
        return musername.getText().toString().trim();
    }

    private String getPassword() {
        return mpassword.getText().toString().trim();
    }

    private String getLocalName() {
        SharedPreferencesUtils utils = new SharedPreferencesUtils(this, "setting");
        return utils.getString("username");
    }

    private String getLocalPassword() {
        SharedPreferencesUtils utils = new SharedPreferencesUtils(this, "setting");
        //省略加密解密
        return utils.getString("password");
    }
}
