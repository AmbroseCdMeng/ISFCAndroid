package com.maci.foxconn.isfcandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/***
 * 入库信息详情查询
 *
 * @author AmbroseCdMeng

 * @time 2020/4/7 下午 04:46
 ***/
public class InStorageDetailInfoActivity extends TitleBarActivity {


    public static final String RETURN_INFO_WORKORDER = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_WORKORDER";
    public static final String RETURN_INFO_PAYDEPARTMENT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_PAYDEPARTMENT";
    public static final String RETURN_INFO_STORAGESTATE = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_STORAGESTATE";
    public static final String RETURN_INFO_MATERIALNUM = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNUM";
    public static final String RETURN_INFO_MATERIALNAME = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNAME";
    public static final String RETURN_INFO_INSTORAGECOUNT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_INSTORAGECOUNT";


    private ConstraintLayout dialog_confirm;
    private EditText mlocationCode;
    private Button mcommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_storage_detail_info);

        initView();
        initEvent();
    }

    private void initEvent() {
        mcommit.setOnClickListener( v -> commitInstorage());
    }

    private void commitInstorage() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View dialogView = View.inflate(this, R.layout.in_storage_confirm_dialog, null);

        final AlertDialog alertDialog = alertBuilder.setTitle(null).setIcon(null).setView(dialogView).create();
        alertDialog.show();

        Window window = alertDialog.getWindow();

        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);

        window.setAttributes(layoutParams);       //window.getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);

        dialogView.findViewById(R.id.btn_commit).setOnClickListener( v -> commit());
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> alertDialog.dismiss());

    }

    private void commit() {
//        Toast.makeText(this, "Commit", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), InStorageActivity.class));
    }

    private void initView() {
        super.initTitleView();
        showTitle(false, null, null);
        showLeft(true, "<入库信息", v -> finish());
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));

        dialog_confirm = findViewById(R.id.cl_dialog_confirm);
        mcommit = findViewById(R.id.btn_commit_inStorage);
        mlocationCode = findViewById(R.id.et_location_code);
    }

    /**
     * 获取上级页面传递的参数
     *
     * @return
     */
    private String getParamsInfo(String name) {
        return getIntent().getStringExtra(name);
    }
}
