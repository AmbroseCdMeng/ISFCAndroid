package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/***
 * 入库信息详情查询
 * 
 * @author AmbroseCdMeng

 * @time 2020/4/7 下午 04:46
 ***/
public class InStorageDetailInfoActivity extends TitleBarActivity {


    public static final String RETURN_INFO = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_storage_detail_info);

        TextView textView = findViewById(R.id.test);
        textView.setText(getParamsInfo(RETURN_INFO));
    }

    /**
     * 获取上级页面传递的参数
     *
     * @return
     */
    private String getParamsInfo(String name){
        return getIntent().getStringExtra(name);
    }
}
