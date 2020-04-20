package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maci.foxconn.utils.DrawableUtils;
import com.maci.foxconn.utils.HttpUtils;
import com.maci.foxconn.utils.Utils;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 入库界面
 *
 * @author AmbroseCdMeng

 * @time 2020/3/28 下午 03:39
 ***/
public class InStorageWorkOrderActivity extends TitleBarActivity {

    private static final int COMPLETED = 0;
    //final String[] mapKeys1 = {"FORMNO", "DPTNAME", "FORMSTATUSNAME", "MTLNO", "MTLNO", "PLANQTY", "UNIT", "ACTUALQTY", "DPTNO", "PRODNAME", "FORMSTATUS"};
    private final String[] mapKeys = {"FORMNO", "DPTNAME", "FORMSTATUSNAME", "MTLNO", "MTLNO", "PLANQTY"};
    private final int[] ids = {R.id.workOrder, R.id.payDepartment, R.id.storageState, R.id.materialNum, R.id.materialName, R.id.inStorageCount};

    private ListView mInStorageWorkOrder;
    private EditText mEtSearch;
    private Button mBtnSearch;


    private List<Map<String, Object>> data = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Bundle data = msg.getData();
//            String val = data.getString("value");
            if (msg.what == COMPLETED) {
                mInStorageWorkOrder.setAdapter(new SimpleAdapter(InStorageWorkOrderActivity.this, data,
                        R.layout.list_view_1,
                        mapKeys,
                        ids
                ));
            }
        }
    };

//    Runnable work = new Thread(){
//        @Override
//        public void run() {
//            super.run();
//        }
//    };

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            data = getDataFromHttp("");
            Message msg = new Message();
            msg.what = COMPLETED;
            handler.sendMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.in_storage_work_order);

        initView();

        showLeft(true, "<入库工令单", v -> finish());
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));


        /**
         * 问题 1 ：
         * Google 针对 Android P 应用程序要求默认使用加密链接，否则将在 httpURL Connection 时出现以下异常：
         *
         *  Cleartext HTTP traffic to 10.161.139.45 not permitted
         *
         * 解决方法：
         *  1. 降低 targetSdkVersion 版本到 27 以下
         *  2. APP 改用 HTTPS 加密请求
         *  3. 更改网络配置
         *
         * 问题 2 ：
         * android.os.NetworkOnMainThreadException
         *
         *  安卓 `4.0` 及以上版本禁止在主线程中访问 HTTP 请求。
         *
         * 问题 3 ：
         * Only the original thread that created a view hierarchy can touch its views
         *
         *  Android系统中的视图组件并不是线程安全的，如果要更新视图，必须在主线程中更新，不可以在子线程中执行更新的操作
         *  此处需要采用 Handler对象消息分发机制，在子线程中完成获取数据，然后通知主线程更新视图
         *
         */
//        new Thread(work).start();
        new WorkThread().start();


        initEvent();
    }

    private void initEvent() {

        mBtnSearch.setOnClickListener(v -> Utils.toast(this, "查询成功"));

        new DrawableUtils(mEtSearch, new DrawableUtils.OnDrawableListener() {
            @Override
            public void onLeft(View view, Drawable left) {

            }

            @Override
            public void onRight(View view, Drawable right) {
//                Toast.makeText(InStorageWorkOrderActivity.this, "RIGHT ICON TOUCHED", Toast.LENGTH_LONG).show();
                mEtSearch.setText("");
            }

            @Override
            public void onTop(View view, Drawable top) {

            }

            @Override
            public void onBottom(View view, Drawable bottom) {

            }
        });

        mInStorageWorkOrder.setOnItemClickListener((parent, view, position, id) ->
                {
                    Intent intent = new Intent(getApplicationContext(), InStorageDetailInfoActivity.class);

                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_WORKORDER, data.get(position).get("FORMNO").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_PAYDEPARTMENT, data.get(position).get("DPTNAME").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_STORAGESTATE, data.get(position).get("FORMSTATUSNAME").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_MATERIALNUM, data.get(position).get("MTLNO").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_MATERIALNAME, data.get(position).get("MTLNO").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_INSTORAGECOUNT, data.get(position).get("PLANQTY").toString().trim());

                    startActivity(intent);
                }
        );
    }

    private List<Map<String, Object>> getDataFromHttp(String url) {
        url = "http://10.161.139.45:5088/api/App/QueryInStockForms?formno=20200328";
        try {
//            result = HttpUtils.doGet(url);
            /* 方便测试，将返回结果直接复制，正式使用时屏蔽该行代码 */
            String result = "{\"status\":true,\"message\":\"獲取信息成功\",\"result\":[{\"FORMNO\":\"W2K20041101\",\"DPTNO\":\"TOU508\",\"DPTNAME\":\"生產一課\",\"FORMSTATUS\":\"0\",\"FORMSTATUSNAME\":\"未入庫\",\"MTLNO\":\"1A52TU000-600-T\",\"PRODNAME\":\"HOUSING\",\"PLANQTY\":\"500\",\"ACTUALQTY\":\"0\",\"UNIT\":\"PC\"},{\"FORMNO\":\"W2K20041202\",\"DPTNO\":\"TOU509\",\"DPTNAME\":\"生產二課\",\"FORMSTATUS\":\"1\",\"FORMSTATUSNAME\":\"部份入庫\",\"MTLNO\":\"1A52TU000-600-T\",\"PRODNAME\":\"MIPAD\",\"PLANQTY\":\"300\",\"ACTUALQTY\":\"100\",\"UNIT\":\"PC\"},{\"FORMNO\":\"W2K20041202\",\"DPTNO\":\"TOU509\",\"DPTNAME\":\"生產二課\",\"FORMSTATUS\":\"1\",\"FORMSTATUSNAME\":\"部份入庫\",\"MTLNO\":\"1A52TU000-600-T\",\"PRODNAME\":\"MIPAD_TWO\",\"PLANQTY\":\"360\",\"ACTUALQTY\":\"120\",\"UNIT\":\"PC\"},{\"FORMNO\":\"W2K20041302\",\"DPTNO\":\"TOU509\",\"DPTNAME\":\"生產三課\",\"FORMSTATUS\":\"0\",\"FORMSTATUSNAME\":\"未入庫\",\"MTLNO\":\"1A52TU000-600-T\",\"PRODNAME\":\"CASE\",\"PLANQTY\":\"600\",\"ACTUALQTY\":\"0\",\"UNIT\":\"PC\"},{\"FORMNO\":\"W2K20041302\",\"DPTNO\":\"TOU509\",\"DPTNAME\":\"生產三課\",\"FORMSTATUS\":\"0\",\"FORMSTATUSNAME\":\"未入庫\",\"MTLNO\":\"1A52TU000-600-T\",\"PRODNAME\":\"CASE_TWO\",\"PLANQTY\":\"1900\",\"ACTUALQTY\":\"0\",\"UNIT\":\"PC\"}]}";
            JSONObject jsonObject = JSONObject.parseObject(result);
            if ((!(boolean) jsonObject.get("status"))) {
                Utils.toast(this, jsonObject.get("message").toString());
                return null;
            }
            data = ((List<Map<String, Object>>) jsonObject.get("result"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 示例数据 已弃用
     */
    private void initData() {

        /* list item id */
        String[] mapKeys = {"workOrder", "payDepartment", "storageState", "materialNum", "materialName", "inStorageCount"};
        int[] ids = {R.id.workOrder, R.id.payDepartment, R.id.storageState, R.id.materialNum, R.id.materialName, R.id.inStorageCount};

        /* 示例数据 start */
        String[] workOrder = {"93109073362", "490250992", "1096868926", "4399xyx"};
        String[] payDepartment = {"关务物流部", "机电总务部", "产品开发部", "工程技术部"};
        String[] storageState = {"已入库", "部分入库", "未入库", "未入库", "未入库"};
        String[] materialNum = {"MTN032111423", "ORD124528996", "XYY48847811", "GRF14523697"};
        String[] materialName = {"破铜", "烂铁", "塑料", "收破烂~"};
        String[] inStorageCount = {"0/200 PCS", "50/2000 PCS", "0/0 PCS", "1000/1000 PCS"};

        /* 示例数据 end */

        for (int i = 0; i < workOrder.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("workOrder", workOrder[i]);
            item.put("payDepartment", payDepartment[i]);
            item.put("storageState", storageState[i]);
            item.put("materialNum", materialNum[i]);
            item.put("materialName", materialName[i]);
            item.put("inStorageCount", inStorageCount[i]);

            String temp = storageState[i];
            item.put("layoutId", "已入库".equals(temp) ? R.layout.list_view_0 : ("部分入库".equals(temp) ? R.layout.list_view_1 : R.layout.list_view_2));

            data.add(item);
        }

        /**
         * 后续修改为加载自定义布局
         */
        mInStorageWorkOrder.setAdapter(new SimpleAdapter(InStorageWorkOrderActivity.this, data,
                R.layout.list_view_1,
                mapKeys,
                ids
        ));
    }

    private void initView() {
        super.initTitleView();
        showTitle(false);
        showLeft(true, "<入库工令单");
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));
        mInStorageWorkOrder = findViewById(R.id.lv_in_storage_work_order);
        mEtSearch = findViewById(R.id.et_search);
        mBtnSearch = findViewById(R.id.btn_search);
    }
}

