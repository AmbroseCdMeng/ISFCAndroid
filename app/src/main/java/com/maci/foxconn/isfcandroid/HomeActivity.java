package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class HomeActivity extends TitleBarActivity {

//    private ImageButton m

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        initView();
    }

    private void initView() {

        mprev.setText("");
        muser.setText("用户");
    }
}
