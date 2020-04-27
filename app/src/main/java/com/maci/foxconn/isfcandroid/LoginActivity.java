package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.maci.foxconn.utils.HttpUtils;
import com.maci.foxconn.utils.MachineUtils;
import com.maci.foxconn.utils.PropertiesUtils;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.maci.foxconn.isfcandroid.CommonActivity.getAPIURL;
import static com.maci.foxconn.isfcandroid.CommonActivity.getCurrentUser;
import static com.maci.foxconn.isfcandroid.CommonActivity.getIP;
import static com.maci.foxconn.isfcandroid.CommonActivity.getSYSID;
import static com.maci.foxconn.isfcandroid.CommonActivity.isReleaseMode;
import static com.maci.foxconn.utils.Utils.showMsg;


/***
 * 登录界面
 *
 * @author AmbroseCdMeng

 * @time 2020/3/27 下午 04:00
 ***/
public class LoginActivity extends TitleBarActivity {

    @BindView(R.id.ed_userno)
    EditText mUserNo;
    @BindView(R.id.ed_pwd)
    EditText mPwd;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.cb_s_pwd)
    CheckBox mShowPwd;
    @BindView(R.id.tv_f_pwd)
    TextView mFgtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        mUserNo.setText("F1680502");
        mPwd.setText("Administrator");
        checkReleaseMode();

        initEvent();
    }

    public void checkReleaseMode() {
        if ("F1680502".equals(getUserNo()))
            CommonActivity.setReleaseMode(false);
        else
            CommonActivity.setReleaseMode(true);
    }

    private void initEvent() {

        mLogin.setOnClickListener(v -> login());
        mShowPwd.setOnCheckedChangeListener((v, isChecked) -> {
            changePwdStatus(isChecked);
        });
        mFgtPwd.setOnClickListener(v -> forgetPwd());
    }

    private void forgetPwd() {
        showMsg(this, "忘记密码");
    }

    private void changePwdStatus(boolean isChecked) {
        if (isChecked)
            mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else
            mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mPwd.setSelection(mPwd.getText().length());
    }

    private void login() {
        if (getUserNo().isEmpty() || getPwd().isEmpty()) {
            showMsg(LoginActivity.this, "账号密码不能为空");
            return;
        }

        new Thread(() -> {
            mLogin.setClickable(false);
            if (isReleaseMode()) {
                Beans response = getUserResponse();
                if (response.getStatus()) {
                    CommonActivity.setCurrentUser(getUserNo());
                    showMsg(LoginActivity.this, response.getMessage());
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else
                    showMsg(this, response.getMessage());
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CommonActivity.setCurrentUser(getUserNo());
                showMsg(LoginActivity.this, "登录成功(测试环境)");
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
            mLogin.setClickable(true);
        }).start();
    }

    private String getUserNo() {
        return mUserNo.getText().toString().trim();
    }

    private String getPwd() {
        //省略加密/解密过程
        return mPwd.getText().toString().trim();
    }

    private boolean getSPwdStatus() {
        return mShowPwd.isChecked();
    }

    private Beans getUserResponse() {
        Beans beans = new Beans();
        String url = String.format("%sSys/LoginValidate?sysname=%s&userid=%s&pwd=%s&ip=%s"
                , getAPIURL()
                , getSYSID()
                , getCurrentUser()
                , getPwd()
                , getIP());

        try {
            beans = HttpUtils.doGet(url, Beans.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }
}
