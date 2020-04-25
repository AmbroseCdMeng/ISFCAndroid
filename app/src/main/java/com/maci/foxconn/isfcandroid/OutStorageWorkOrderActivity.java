package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.maci.foxconn.utils.HttpUtils;
import com.maci.foxconn.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.maci.foxconn.isfcandroid.LoginActivity.isReleaseMode;
import static com.maci.foxconn.utils.Utils.showMsg;

/***
 * 出库工龄单界面
 *
 * @author AmbroseCdMeng

 * @time 2020/4/10 上午 09:05
 ***/
public class OutStorageWorkOrderActivity extends TitleBarActivity {

    @BindView(R.id.center_reclycleview)
    RecyclerView c_RecyclerView;

    private RvAdapter mRvAdapter;
    private Beans mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_storage_work_order);
        ButterKnife.bind(this);

        initData();
        initView();
        initEvent();
    }

    private void initView() {
        super.initTitleView();
        showTitle(false);
        showLeft(true, "<出库信息");
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));
    }

    private void initEvent() {
    }

    //模拟数据
    private void initData() {

        String url = "http://10.161.139.45:5088/api/App/QueryOutStockForms?formno=20200328";

        Beans response = new Beans();
        try {
            response = HttpUtils.doGet(url, Beans.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO HttpTestResponseText
        if (!isReleaseMode()){
            response = initTestResponseText();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        c_RecyclerView.setLayoutManager(layoutManager);
        c_RecyclerView.setFocusableInTouchMode(false);

        mBean = new Beans();
        List<Map<String, Object>> datas = new ArrayList<>();

        if ((!response.getStatus())) {
            showMsg(this, response.getMessage());
        }

        datas = JSONObject.parseObject(response.getResult().toString(), new TypeReference<List<Map<String, Object>>>(){});

        mBean.setResult(datas);
        mRvAdapter = new RvAdapter(this, (List<Map<String, Object>>) mBean.getResult());
        c_RecyclerView.setAdapter(mRvAdapter);
        mRvAdapter.notifyDataSetChanged();
    }

    /**
     * 设置一个public方法,供adapter点击事件调用
     *
     * @param position 为第一层recycleview位置
     * @param tag      为第二层recycleview位置
     */
    public void OnClickListener(int position, int tag) {
        final List<Map<String, Object>> datasBeans = (List<Map<String, Object>>) mBean.getResult();
        for (int i = 0; i < datasBeans.size(); i++) {
            if (i == position) {
                List<Map<String, Object>> option = (List<Map<String, Object>>) datasBeans.get(i).get("CHILDREN");
                for (int j = 0; j < option.size(); j++) {
                    if (j == tag) {
                        option.get(j).put("isSelect", true);
                    } else {
                        option.get(j).put("isSelect", false);
                    }
                }
                Toast.makeText(getApplicationContext(),
                        datasBeans.get(position).get("FORMNO") + "-" + option.get(tag).get("MTLNO"),
                        Toast.LENGTH_SHORT).show();

            } else {
                //这里让之前选中的效果还原成未选中
                List<Map<String, Object>> option = (List<Map<String, Object>>) datasBeans.get(i).get("CHILDREN");
                for (int j = 0; j < option.size(); j++) {
                    option.get(j).put("isSelect", false);
                }
            }
        }
        mRvAdapter.notifyDataSetChanged();
    }

    private Beans initTestResponseText(){
        String result = "{\n" +
                "  \"status\": true,\n" +
                "  \"message\": \"獲取信息成功\",\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"FORMNO\": \"W2K20041101\",\n" +
                "      \"DPTNO\": \"TOU508\",\n" +
                "      \"DPTNAME\": \"生產一課\",\n" +
                "      \"FORMSTATUS\": \"0\",\n" +
                "      \"FORMSTATUSNAME\": \"未入庫\",\n" +
                "      \"CHILDREN\": [\n" +
                "        {\n" +
                "          \"MTLNO\": \"1A52TU000-600-T\",\n" +
                "          \"PRODNAME\": \"HOUSING\",\n" +
                "          \"PLANQTY\": \"500\",\n" +
                "          \"ACTUALQTY\": \"0\",\n" +
                "          \"UNIT\": \"PC\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"FORMNO\": \"W2K20041202\",\n" +
                "      \"DPTNO\": \"TOU509\",\n" +
                "      \"DPTNAME\": \"生產二課\",\n" +
                "      \"FORMSTATUS\": \"1\",\n" +
                "      \"FORMSTATUSNAME\": \"部份入庫\",\n" +
                "      \"CHILDREN\": [\n" +
                "        {\n" +
                "          \"MTLNO\": \"1A52TU000-600-T\",\n" +
                "          \"PRODNAME\": \"MIPAD\",\n" +
                "          \"PLANQTY\": \"300\",\n" +
                "          \"ACTUALQTY\": \"100\",\n" +
                "          \"UNIT\": \"PC\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"MTLNO\": \"1A52TU000-600-T\",\n" +
                "          \"PRODNAME\": \"MIPAD_TWO\",\n" +
                "          \"PLANQTY\": \"360\",\n" +
                "          \"ACTUALQTY\": \"120\",\n" +
                "          \"UNIT\": \"PC\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"FORMNO\": \"W2K20041302\",\n" +
                "      \"DPTNO\": \"TOU509\",\n" +
                "      \"DPTNAME\": \"生產三課\",\n" +
                "      \"FORMSTATUS\": \"0\",\n" +
                "      \"FORMSTATUSNAME\": \"未入庫\",\n" +
                "      \"CHILDREN\": [\n" +
                "        {\n" +
                "          \"MTLNO\": \"1A52TU000-600-T\",\n" +
                "          \"PRODNAME\": \"CASE\",\n" +
                "          \"PLANQTY\": \"600\",\n" +
                "          \"ACTUALQTY\": \"0\",\n" +
                "          \"UNIT\": \"PC\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"MTLNO\": \"1A52TU000-600-T\",\n" +
                "          \"PRODNAME\": \"CASE_TWO\",\n" +
                "          \"PLANQTY\": \"1900\",\n" +
                "          \"ACTUALQTY\": \"0\",\n" +
                "          \"UNIT\": \"PC\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        return JSONObject.parseObject(result.replace("\\","" ), Beans.class);
    }
}
