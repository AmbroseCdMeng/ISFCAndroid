package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/***
 * 自定义标题栏
 * 
 * @author AmbroseCdMeng

 * @time 2020/4/6 下午 01:44
 ***/
public class TitleBarActivity extends AppCompatActivity {

    private TextView mleft;
    private TextView mtitle;
    private TextView mright;

    private ConstraintLayout mleftLayout;
    private ConstraintLayout mrightLayou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_bar);
//        initTitleView();
    }

    protected void initTitleView() {
        mleftLayout = findViewById(R.id.cl_left);
        mrightLayou = findViewById(R.id.cl_right);

        mleft = findViewById(R.id.tv_left);
        mtitle = findViewById(R.id.tv_title);
        mright = findViewById(R.id.tv_right);
    }

    protected void showLeft(boolean isShow, String text, View.OnClickListener onClickListener) {
        if (mleftLayout != null) {
            if (isShow) {
                mleftLayout.setVisibility(View.VISIBLE);
                mleft.setVisibility(View.VISIBLE);
                mleft.setText(text);
                if (onClickListener != null)
                    mleftLayout.setOnClickListener(onClickListener);
                else
                    mleftLayout.setOnClickListener((v) -> finish());
            } else {
                mleftLayout.setVisibility(View.INVISIBLE);
                mleft.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected void showRight(boolean isShow, String text, View.OnClickListener onClickListener) {
        if (mrightLayou != null) {
            if (isShow) {
                mrightLayou.setVisibility(View.VISIBLE);
                mright.setVisibility(View.VISIBLE);
                mright.setText(text);
                if (onClickListener != null)
                    mrightLayou.setOnClickListener(onClickListener);
                else
                    mrightLayou.setOnClickListener((v -> finish()));
            } else {
                mrightLayou.setVisibility(View.INVISIBLE);
                mright.setVisibility(View.INVISIBLE);
            }
        }
    }
}
