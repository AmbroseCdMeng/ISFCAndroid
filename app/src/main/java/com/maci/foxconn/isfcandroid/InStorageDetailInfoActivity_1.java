package com.maci.foxconn.isfcandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.maci.foxconn.utils.StringUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.maci.foxconn.utils.ActivityUtils.getParamsInfo;
import static com.maci.foxconn.utils.StringUtils.isNullOrEmpty;


/***
 * 入库信息详情查询
 *
 * @author AmbroseCdMeng
 *
 * @time 2020/4/7 下午 04:46
 ***/
public class InStorageDetailInfoActivity_1 extends HoneyWellScannerActivity {

    private int index = 1;

    View dialogView;
    AlertDialog alertDialog;

    public static final String RETURN_INFO_WORKORDER = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_WORKORDER";
    public static final String RETURN_INFO_PAYDEPARTMENT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_PAYDEPARTMENT";
    public static final String RETURN_INFO_STORAGESTATE = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_STORAGESTATE";
    public static final String RETURN_INFO_MATERIALNUM = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNUM";
    public static final String RETURN_INFO_MATERIALNAME = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNAME";
    public static final String RETURN_INFO_INSTORAGECOUNT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_INSTORAGECOUNT";

    @BindView(R.id.btn_acceptCount)
    Button mAcceptCount;
    @BindView(R.id.tv_inStorageOrder)
    TextView mInStorageOrder;
    @BindView(R.id.tv_workOrder)
    TextView mWorkOrder;
    @BindView(R.id.tv_materialNum)
    TextView mMaterialNum;
    @BindView(R.id.tv_productName)
    TextView mProductName;
    @BindView(R.id.tv_specific)
    TextView mSpecific;
    @BindView(R.id.tv_payDepartment)
    TextView mPayDepartment;
    @BindView(R.id.tv_unit)
    TextView mUnit;
    @BindView(R.id.inStorageCount)
    TextView mInStorageCount;
    @BindView(R.id.tv_wareHouse)
    TextView mWareHouse;
    @BindView(R.id.tv_location)
    TextView mLocation;
    @BindView(R.id.tv_mark)
    TextView mMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.in_storage_detail_info_1_0);

        ButterKnife.bind(this);
        showTitleBtn();
        initData();
        initEvent();
        initMoreCodeMsgDialog();
        //insertTableRows("Test Code");

    }

    private void initMoreCodeMsgDialog() {
        dialogView = View.inflate(this, R.layout.in_storage_detail_info_1_1, null);
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertDialog = alertBuilder.setTitle(null).setIcon(null).setView(dialogView).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);

        alertDialog.hide();
    }

    private void initData() {
        mAcceptCount.setText(getParamsInfo(this, RETURN_INFO_INSTORAGECOUNT));
        mWorkOrder.setText(getParamsInfo(this, RETURN_INFO_WORKORDER));
        mPayDepartment.setText(getParamsInfo(this, RETURN_INFO_PAYDEPARTMENT));
        mMaterialNum.setText(getParamsInfo(this, RETURN_INFO_MATERIALNUM));
        mInStorageCount.setText(getParamsInfo(this, RETURN_INFO_INSTORAGECOUNT));
        mInStorageOrder.setText(getParamsInfo(this, ""));
        mSpecific.setText(getParamsInfo(this, ""));
        mUnit.setText(getParamsInfo(this, ""));
        mWareHouse.setText(getParamsInfo(this, ""));
        mLocation.setText(getParamsInfo(this, ""));
        mMark.setText(getParamsInfo(this, ""));
        mProductName.setText(getParamsInfo(this, ""));
    }

    private void initEvent() {
        mAcceptCount.setOnClickListener(v -> showMoreCodeMsgDialog());
    }

    private void showMoreCodeMsgDialog() {
        alertDialog.show();
    }


    private void showTitleBtn() {
        super.initTitleView();
        showTitle(false);
        showLeft(true, "<入库信息");
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));
    }

    private boolean insertTableRows(String barCode) {

        TableLayout mTlInStorage = dialogView.findViewById(R.id.tl_inStorage);
        TableRow mTrInStorage = dialogView.findViewById(R.id.tr_inStorage);

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
            tv.setLines(1);

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
        runOnUiThread(() ->
        {
            mAcceptCount.setText(new DecimalFormat(".00").format(Double.parseDouble(isNullOrEmpty(mAcceptCount.getText().toString()) ? "0" : mAcceptCount.getText().toString()) + (Math.random() * 1000)));
            insertTableRows(event.getBarcodeData());
        });

    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent event) {
        //
    }
}
