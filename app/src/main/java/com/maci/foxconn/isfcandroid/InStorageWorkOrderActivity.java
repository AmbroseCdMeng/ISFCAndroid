package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.maci.foxconn.utils.DrawableUtils;
import com.maci.foxconn.utils.Utils;

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
    private EditText mEtSearch;
    private Button mBtnSearch;

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

    List<Map<String, Object>> data = new ArrayList<>();
    /* 示例数据 end */


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.in_storage_work_order);

        initView();

        showLeft(true, "<入库工令单", v -> finish());
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));


        initData();

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

//        mInStorageWorkOrder.setOnItemClickListener( new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Utils.toast(InStorageWorkOrderActivity.this, position + " - - - " + id);
//            }
//        });


        mInStorageWorkOrder.setOnItemClickListener((parent, view, position, id) ->
                {
                    Utils.toast(this, position + " - - - " + id);
                    Intent intent = new Intent(getApplicationContext(), InStorageDetailInfoActivity.class);

                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_WORKORDER, data.get(position).get("workOrder").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_PAYDEPARTMENT, data.get(position).get("payDepartment").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_STORAGESTATE, data.get(position).get("storageState").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_MATERIALNUM, data.get(position).get("materialNum").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_MATERIALNAME, data.get(position).get("materialName").toString().trim());
                    intent.putExtra(InStorageDetailInfoActivity.RETURN_INFO_INSTORAGECOUNT, data.get(position).get("inStorageCount").toString().trim());

                    startActivity(intent);
                }
        );
    }

    private void initData() {

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
        mInStorageWorkOrder = findViewById(R.id.lv_in_storage_work_order);
        mEtSearch = findViewById(R.id.et_search);
        mBtnSearch = findViewById(R.id.btn_search);
    }

}
