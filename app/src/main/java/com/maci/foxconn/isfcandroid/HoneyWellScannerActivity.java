package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import static com.maci.foxconn.utils.Utils.showMsg;

/***
 * HoneyWell CN51 扫描接口测试
 *
 * @author AmbroseCdMeng

 * @time 2020/4/21 下午 02:22
 ***/
public class HoneyWellScannerActivity extends TitleBarActivity implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    //实现 BarcodeListener 和 TriggerListener 接口进行扫描键的触发监听和条码事件处理


    AidcManager manager;
    static BarcodeReader barcodeReader;//实现扫描属性的设置和扫描功能的使用

    static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }


    /**
     * 重写 onCreate 方法。在其中创建扫描对象，设置扫描属性，完成扫描工作的准备工作
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AidcManager.create(this, aidcManager -> {
            try {
                //创建 AidcManager 和 BarcodeReader 对象
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();

                //设置扫描属性
                //barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE, BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE, BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);

                barcodeReader.setProperty(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
                barcodeReader.setProperty(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
                barcodeReader.setProperty(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
                barcodeReader.setProperty(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
                barcodeReader.setProperty(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
                barcodeReader.setProperty(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);

                barcodeReader.setProperty(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
                barcodeReader.setProperty(BarcodeReader.PROPERTY_CENTER_DECODE, true);

                // Disable bad read response, handle in onFailureEvent when MODE_CLIENT_CONTROL
                // barcodeReader.setProperty(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, false);
                // Enable bad read response when MODE_AUTO_CONTROL
                barcodeReader.setProperty(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);

                //打开扫描功能
                barcodeReader.claim();

                //注册 Trigger 监听 和 Barcode 监听
                barcodeReader.addBarcodeListener(this);
                barcodeReader.addTriggerListener(this);

            } catch (InvalidScannerNameException e) {
                Toast.makeText(getApplicationContext(), "Invalid Scanner Name Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (UnsupportedPropertyException e) {
                Toast.makeText(getApplicationContext(), "Failed to apply properties", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }

    /**
     * 重写 onTriggerEvent 方法。实现按下扫描键开启补光和扫描功能，弹起按键关闭补光和扫描功能
     *
     * @param event
     */
    @Override
    public void onTriggerEvent(TriggerStateChangeEvent event) {
        if (barcodeReader != null)
            try {
                barcodeReader.light(event.getState());//开启/关闭补光
//            showMsg(this, event.getState() ? "开启 补光" : "关闭 补光");
                barcodeReader.aim(event.getState());//打开/关闭瞄准线
//            showMsg(this, event.getState() ? "开启 瞄准线" : "关闭 瞄准线");
                barcodeReader.decode(event.getState());//扫描信息解码
//            showMsg(this, event.getState() ? "开启 信息解码" : "关闭 信息解码");
            } catch (ScannerNotClaimedException e) {
                e.printStackTrace();
                Toast.makeText(this, "Scanner is not claimed", Toast.LENGTH_SHORT).show();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }

    /**
     * 重写 onBarcodeEvent 方法。实现条码数据接受处理
     *
     * @param event
     */
    @Override
    public void onBarcodeEvent(BarcodeReadEvent event) {
        showMsg(this, "data = " + event.getBarcodeData());

        runOnUiThread(() -> {
            Toast.makeText(HoneyWellScannerActivity.this, "Barcode data: " + event.getBarcodeData(), Toast.LENGTH_LONG);//获取扫描数据
            Toast.makeText(HoneyWellScannerActivity.this, "Character Set: " + event.getCharset(), Toast.LENGTH_LONG);
            Toast.makeText(HoneyWellScannerActivity.this, "Code ID: " + event.getCodeId(), Toast.LENGTH_LONG);
            Toast.makeText(HoneyWellScannerActivity.this, "AIM ID: " + event.getAimId(), Toast.LENGTH_LONG);
            Toast.makeText(HoneyWellScannerActivity.this, "Timestamp: " + event.getTimestamp(), Toast.LENGTH_LONG);
        });
    }

    /**
     * 重写 onFailureEvent 方法。实现扫描失败时的处理
     *
     * @param event
     */
    @Override
    public void onFailureEvent(BarcodeFailureEvent event) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show());
    }


    /**
     * 重写 onPause 方法。关闭扫描
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (barcodeReader != null)
            barcodeReader.release();
    }

    /**
     * 重写 onResume 方法。恢复扫描
     */
    @Override
    protected void onResume() {
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

    /**
     * 重写 onDestroy 方法。关闭所有扫描功能
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barcodeReader != null) {
            barcodeReader.removeBarcodeListener(this);
            barcodeReader.removeTriggerListener(this);
            barcodeReader.close();
        }
        if (manager != null)
            manager.close();
    }
}
