package com.maci.foxconn.isfcandroid;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

    private ListView mInStorageWorkOrder;

    /* list item id */
    private String[] mapKeys = {"workOrder", "payDepartment", "storageState", "materialNum", "materialName", "inStorageCount"};
    private int[] ids = {R.id.workOrder, R.id.payDepartment, R.id.storageState, R.id.materialNum, R.id.materialName, R.id.inStorageCount};

    /* 示例数据 start */
    private String[] workOrder = {"93109073362", "490250992", "1096868926", "4399xyx"};
    private String[] payDepartment = {"关务物流部", "机电总务部", "产品开发部", "工程技术部"};
    private String[] storageState = {"已入库", "部分入库", "未入库", "未入库", "未入库"};
    private String[] materialNum = {"MTN032111423", "ORD124528996", "XYY48847811", "GRF14523697"};
    private String[] materialName = {"破铜", "烂铁", "塑料", "收破烂~"};
    private String[] inStorageCount = {"0/200 PCS", "50/2000 PCS", "0/0 PCS", "1000/1000 PCS"};
    /* 示例数据 end */


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.in_storage_work_order);

        initView();

        initData();


    }

    private void initData() {
        List<Map<String, Object>> data = new ArrayList<>();

        for (int i = 0; i < workOrder.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("workOrder", workOrder[i]);
            item.put("payDepartment", payDepartment[i]);
            item.put("storageState", storageState[i]);
            item.put("materialNum", materialNum[i]);
            item.put("materialName", materialName[i]);
            item.put("inStorageCount", inStorageCount[i]);
            data.add(item);
        }

        mInStorageWorkOrder.setAdapter(new SimpleAdapter(InStorageWorkOrderActivity.this, data, R.layout.list_view_0,
                mapKeys,
                ids
        ));
    }

    private void initView() {
        mInStorageWorkOrder = findViewById(R.id.lv_in_storage_work_order);
    }
}
