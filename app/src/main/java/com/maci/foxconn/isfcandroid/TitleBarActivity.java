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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_bar);
//        initTitleView();
    }

    protected void initTitleView() {
        mleft = findViewById(R.id.tv_left);
        mtitle = findViewById(R.id.tv_title);
        mright = findViewById(R.id.tv_right);
    }

    protected void showTitle(boolean isShow, String text, View.OnClickListener onClickListener) {
        if (isShow) {
            mtitle.setVisibility(View.VISIBLE);
            mtitle.setText(text);
            if (onClickListener != null)
                mtitle.setOnClickListener(onClickListener);
            else
                mtitle.setOnClickListener(v -> finish());
        } else {
            mtitle.setVisibility(View.INVISIBLE);
        }
    }

    protected void showLeft(boolean isShow, String text, View.OnClickListener onClickListener) {
        if (isShow) {
            mleft.setVisibility(View.VISIBLE);
            mleft.setText(text);
            if (onClickListener != null)
                mleft.setOnClickListener(onClickListener);
            else
                mleft.setOnClickListener((v) -> finish());
        } else {
            mleft.setVisibility(View.INVISIBLE);
            mleft.setVisibility(View.INVISIBLE);
        }
    }

    protected void showRight(boolean isShow, String text, View.OnClickListener onClickListener) {
        if (isShow) {
            mright.setVisibility(View.VISIBLE);
            mright.setText(text);
            if (onClickListener != null)
                mright.setOnClickListener(onClickListener);
            else
                mright.setOnClickListener((v -> finish()));
        } else {
            mright.setVisibility(View.INVISIBLE);
            mright.setVisibility(View.INVISIBLE);
        }
    }
}
