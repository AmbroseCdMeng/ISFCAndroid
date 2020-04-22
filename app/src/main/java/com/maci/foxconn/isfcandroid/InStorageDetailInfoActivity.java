package com.maci.foxconn.isfcandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.InvalidScannerNameException;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.maci.foxconn.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maci.foxconn.utils.Utils.showMsg;


/***
 * 入库信息详情查询
 *
 * @author AmbroseCdMeng

 * @time 2020/4/7 下午 04:46
 ***/
public class InStorageDetailInfoActivity extends HoneyWellScannerActivity {

    private int index = 1;

    public static final String RETURN_INFO_WORKORDER = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_WORKORDER";
    public static final String RETURN_INFO_PAYDEPARTMENT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_PAYDEPARTMENT";
    public static final String RETURN_INFO_STORAGESTATE = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_STORAGESTATE";
    public static final String RETURN_INFO_MATERIALNUM = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNUM";
    public static final String RETURN_INFO_MATERIALNAME = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNAME";
    public static final String RETURN_INFO_INSTORAGECOUNT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_INSTORAGECOUNT";

    private TableLayout mTlInStorage;
    private TextView mTvIndex;
    private TextView mTvBarCode;
    private TextView mTvPkgCount;
    private TextView mMtlCount;

    private TextView mWorkOrder;
    private TextView mPayDepartment;
    private TextView mMaterialNum;
    private TextView mMaterialName;
    private TextView mInStorageCount;
    private TextView mPalletCount;
    private TextView mPackageCount;
    private TextView mMaterialCount;

    private TableRow mTrInStorage;

    private ConstraintLayout dialog_confirm;
    private EditText mLocationCode;
    private Button mCommit;


    private static BarcodeReader barcodeReader;
    private AidcManager aidcManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_storage_detail_info);

        initView();
        initEvent();
        insertTableRows("111");
    }

    private void initEvent() {
        mCommit.setOnClickListener(v -> commitInstorage());
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

        dialogView.findViewById(R.id.btn_commit).setOnClickListener(v -> commit());
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> alertDialog.dismiss());

    }

    private void commit() {
//        Toast.makeText(this, "Commit", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), InStorageActivity.class));
    }

    private void initView() {
        super.initTitleView();
        showTitle(false);
        showLeft(true, "<入库信息");
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));

        mTrInStorage = findViewById(R.id.tr_inStorage);
        mTvIndex = findViewById(R.id.tv_index);
        mTvBarCode = findViewById(R.id.tv_barCode);
        mTvPkgCount = findViewById(R.id.tv_pkgCount);
        mMtlCount = findViewById(R.id.tv_mtlCount);

        mTlInStorage = findViewById(R.id.tl_inStorage);

        dialog_confirm = findViewById(R.id.cl_dialog_confirm);
        mCommit = findViewById(R.id.btn_commit_inStorage);
        mLocationCode = findViewById(R.id.et_location_code);

        mWorkOrder = findViewById(R.id.tv_workOrder);
        mPayDepartment = findViewById(R.id.tv_payDepartment);
        mMaterialNum = findViewById(R.id.tv_materialNum);
        mMaterialName = findViewById(R.id.tv_materialName);
        mInStorageCount = findViewById(R.id.tv_inStorageCount);
        mPalletCount = findViewById(R.id.tv_palletCount);
        mPackageCount = findViewById(R.id.tv_packageCount);
        mMaterialCount = findViewById(R.id.tv_materialCount);


        mWorkOrder.setText(getParamsInfo(RETURN_INFO_WORKORDER));
        mPayDepartment.setText(getParamsInfo(RETURN_INFO_PAYDEPARTMENT));
        mMaterialNum.setText(getParamsInfo(RETURN_INFO_MATERIALNUM));
        mMaterialName.setText(getParamsInfo(RETURN_INFO_MATERIALNAME));
        mInStorageCount.setText(getParamsInfo(RETURN_INFO_INSTORAGECOUNT));
        mPalletCount.setText("0");
        mPackageCount.setText("0");
        mMaterialCount.setText("0");

    }

    /**
     * 获取上级页面传递的参数
     *
     * @return
     */
    private String getParamsInfo(String name) {
        return getIntent().getStringExtra(name);
    }


    private boolean insertTableRows(String barCode) {

        LinearLayout.LayoutParams lp = new TableRow.LayoutParams(-1, -1);
        lp.setMargins(1, 1, 1, 1);
//        TableRow或者TextView.setLayoutParams(lp);

        TableRow row = new TableRow(this);
        row.setBackgroundResource(R.color.colorBlack);

        for (int i = 0; i < mTrInStorage.getChildCount(); i++) {
            TextView tvTitle = (TextView) mTrInStorage.getChildAt(i);

            TextView tv = new TextView(this);

            tv.setWidth(tvTitle.getWidth());
            tv.setLayoutParams(tvTitle.getLayoutParams());
            tv.setBackgroundResource(R.drawable.shape_border_tbody);
            tv.setTextColor(getColor(R.color.colorBlack));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) tvTitle.getTextSize() + 1);
            tv.setGravity(tvTitle.getGravity());

            row.addView(tv);

            switch (tvTitle.getId()) {
                case R.id.tv_index:
                    tv.setText(index++ + "");
                    break;
                case R.id.tv_barCode:
                    tv.setText(barCode);
                    break;
                case R.id.tv_pkgCount:
                    tv.setText(index++ + "");
                    break;
                case R.id.tv_mtlCount:
                    tv.setText(index++ + "");
                    break;
            }
        }

        mTlInStorage.addView(row);
        return true;
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent event) {
//        showMsg(this, "data = " + event.getBarcodeData());
        runOnUiThread(() -> insertTableRows(event.getBarcodeData()));
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent event) {
        //
    }
}
