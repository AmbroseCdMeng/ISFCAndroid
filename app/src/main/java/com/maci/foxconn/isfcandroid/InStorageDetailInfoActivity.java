package com.maci.foxconn.isfcandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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


/***
 * 入库信息详情查询
 *
 * @author AmbroseCdMeng

 * @time 2020/4/7 下午 04:46
 ***/
public class InStorageDetailInfoActivity extends TitleBarActivity implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    private static int index = 1;

    public static final String RETURN_INFO_WORKORDER = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_WORKORDER";
    public static final String RETURN_INFO_PAYDEPARTMENT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_PAYDEPARTMENT";
    public static final String RETURN_INFO_STORAGESTATE = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_STORAGESTATE";
    public static final String RETURN_INFO_MATERIALNUM = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNUM";
    public static final String RETURN_INFO_MATERIALNAME = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_MATERIALNAME";
    public static final String RETURN_INFO_INSTORAGECOUNT = "COM.MACI.FOXCONN.ISFCANDROID.IN_STORAGE_DETAIL_INFO.RETURN_INFO_INSTORAGECOUNT";

    private TableLayout mTlInStorage;

    private TextView mWorkOrder;
    private TextView mPayDepartment;
    private TextView mMaterialNum;
    private TextView mMaterialName;
    private TextView mInStorageCount;
    private TextView mPalletCount;
    private TextView mPackageCount;
    private TextView mMaterialCount;

    private ConstraintLayout dialog_confirm;
    private EditText mLocationCode;
    private Button mCommit;


    private static BarcodeReader barcodeReader;
    private AidcManager aidcManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_storage_detail_info);

        initBarCodeListener();

        initView();
        initEvent();
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


    private void initBarCodeListener() {

        if (Build.MODEL.startsWith("VM1A"))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AidcManager.create(this, (aidcManager) -> {

            this.aidcManager = aidcManager;
            try {
                barcodeReader = aidcManager.createBarcodeReader();
            } catch (InvalidScannerNameException e) {
                Toast.makeText(this, "Invalid Scanner Name Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (barcodeReader != null) {
            barcodeReader.addBarcodeListener(this);

            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
            } catch (UnsupportedPropertyException e) {
                Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_LONG).show();
            }

            barcodeReader.addTriggerListener(this);

            Map<String, Object> properties = new HashMap<>();
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, false);
            barcodeReader.setProperties(properties);
        }
    }


    @Override
    public void onBarcodeEvent(final BarcodeReadEvent event) {

        runOnUiThread(() -> {
            List<String> list = new ArrayList<>();
            list.add("Barcode data: " + event.getBarcodeData());
            list.add("Character Set: " + event.getCharset());
            list.add("Code ID: " + event.getCodeId());
            list.add("AIM ID: " + event.getAimId());
            list.add("Timestamp: " + event.getTimestamp());
        });
        Toast.makeText(this, event.getBarcodeData(), Toast.LENGTH_LONG).show();
        insertTableRows(event.getBarcodeData());
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent event) {
        try {
            barcodeReader.aim(event.getState());
            barcodeReader.light(event.getState());
            barcodeReader.decode(event.getState());
        } catch (ScannerNotClaimedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Scanner is not claimed", Toast.LENGTH_SHORT).show();
        } catch (ScannerUnavailableException e) {
            e.printStackTrace();
            Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barcodeReader != null) {
            barcodeReader.removeBarcodeListener(this);
            barcodeReader.removeTriggerListener(this);
            barcodeReader.close();
            barcodeReader = null;
        }
        if (aidcManager != null)
            aidcManager.close();
    }

    static BarcodeReader getBarcodeReader() {
        return barcodeReader;
    }


    private boolean insertTableRows(String barCode) {

        TableRow row = new TableRow(this);
        TextView tvIndex = new TextView(this);
        TextView tvBarCode = new TextView(this);
        TextView tvPkgCount = new TextView(this);
        TextView tvMtlCount = new TextView(this);

        tvIndex.setText(index++);
        row.addView(tvIndex);
        tvBarCode.setText(barCode);
        row.addView(tvBarCode);
        tvPkgCount.setText("不知道");
        row.addView(tvPkgCount);
        tvMtlCount.setText("也不知道");
        row.addView(tvMtlCount);

        mTlInStorage.addView(row);
        return true;
    }
}
