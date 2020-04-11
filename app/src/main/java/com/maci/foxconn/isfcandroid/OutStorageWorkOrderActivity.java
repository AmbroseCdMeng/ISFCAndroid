package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 出库工龄单界面
 * 
 * @author AmbroseCdMeng

 * @time 2020/4/10 上午 09:05
 ***/
public class OutStorageWorkOrderActivity extends TitleBarActivity {

    private ListView mOutStorageWorkOrder;
    private ListView mOutStorageWorkOrderSub;

    private List<Map<String, Object>> data = new ArrayList<>();
    private List<Map<String, Object>> subData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_storage_work_order);
        initView();
        
        initData();
        initEvent();
    }

    private void initData() {
        /* list item id */
        String[] mapKeys = {"workOrder", "payDepartment", "storageState"};
        String [] mapKeysSub = {"materialNum", "materialName", "inStorageCount"};
        int[] ids = {R.id.workOrder, R.id.payDepartment, R.id.storageState};
        int[] idsSub = {R.id.materialNum, R.id.materialName, R.id.inStorageCount};


        String[] workOrder = {"93109073362", "490250992", "1096868926", "4399xyx"};
        String[] payDepartment = {"关务物流部", "机电总务部", "产品开发部", "工程技术部"};
        String[] storageState = {"已入库", "部分入库", "未入库", "未入库", "未入库"};


        for (int i = 0; i < workOrder.length; i ++){
            Map<String, Object> item = new HashMap<>();
            item.put("workOrder", workOrder[i]);
            item.put("payDepartment", payDepartment[i]);
            item.put("storageState", storageState[i]);

//            for ( int j = 0; j < (int) (Math.random() * 5 + 1); j ++){
//                Map<String, Object> subItem = new HashMap<>();
//                subItem.put("materialNum", Math.random() * 1000000000);
//                subItem.put("materialName", Math.random() * 1000000000);
//                subItem.put("inStorageCount", Math.random() * 1000 + "/" + Math.random() * 10000 + " PCS");
//                subData.add(subItem);
//                mOutStorageWorkOrderSub.setAdapter(new SimpleAdapter(OutStorageListViewActivity.this, subData, R.layout.list_view_10_01,
//                        mapKeysSub,
//                        idsSub ));
//
//            }

            data.add(item);
        }

        mOutStorageWorkOrder.setAdapter(new SimpleAdapter(OutStorageWorkOrderActivity.this, data, R.layout.list_view_10, mapKeys, ids));

    }

    private void initEvent() {
    }

    private void initView() {
        super.initTitleView();
        showTitle(false);
        showLeft(true, "<出库工令单");
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));

        mOutStorageWorkOrder = findViewById(R.id.lv_out_storage_work_order);
        mOutStorageWorkOrderSub = findViewById(R.id.lv_out_work_storage_sub);
    }
}
