package com.maci.foxconn.isfcandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.android.flexbox.FlexboxLayout;
import com.maci.foxconn.utils.HttpUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.maci.foxconn.isfcandroid.CommonActivity.getAPIURL;
import static com.maci.foxconn.isfcandroid.CommonActivity.getCurrentUser;
import static com.maci.foxconn.isfcandroid.CommonActivity.getSYSID;
import static com.maci.foxconn.utils.LayoutUtils.getMipmapIdByName;
import static com.maci.foxconn.utils.UnitConvertUtils.dp2px;
import static com.maci.foxconn.utils.Utils.showMsg;

/***
 * 主页（菜单）界面
 *
 * @author AmbroseCdMeng

 * @time 2020/4/6 上午 10:40
 ***/
public class HomeActivity extends TitleBarActivity {

    @BindView(R.id.btn_InStorage)
    Button mInStorage;
    @BindView(R.id.btn_OutStorage)
    Button mOutStorage;
    @BindView(R.id.btn_AsnRelated)
    Button mAsnRelated;
    @BindView(R.id.btn_More)
    Button mMore;

    @BindView(R.id.fl_homeMenu)
    FlexboxLayout mFlHomeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        showTitleBtn();
        initMenuList();
        //initEvent();
    }

    private void showTitleBtn() {
        super.initTitleView();
        showLeft(false);
        showRight(true, "用户名", v -> startActivity(new Intent(getApplicationContext(), UserActivity.class)));

    }

    private void initEvent() {
        mInStorage.setOnClickListener(v -> jumpToInStorageWorkOrderView());
        mOutStorage.setOnClickListener(v -> jumpToOutStorageWorkOrderView());
        mAsnRelated.setOnClickListener(v -> jumpToAsnRelatedView());
    }

    private void jumpToInStorageWorkOrderView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mInStorage.setClickable(false);
                startActivity(new Intent(HomeActivity.this, InStorageWorkOrderActivity.class));
                mInStorage.setClickable(true);
            }
        }.start();
    }

    private void jumpToOutStorageWorkOrderView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mOutStorage.setClickable(false);
                startActivity(new Intent(HomeActivity.this, OutStorageWorkOrderActivity.class));
                mOutStorage.setClickable(true);
            }
        }.start();
    }

    private void jumpToAsnRelatedView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mAsnRelated.setClickable(false);
                startActivity(new Intent(HomeActivity.this, AsnRelatedActivity.class));
                mAsnRelated.setClickable(true);
            }
        }.start();
    }

    private Beans getMenuResponse() {
        Beans beans = new Beans();
        String url = String.format("%sSys/GetModuleInfo?sysname=%s&userId=%s"
                , getAPIURL()
                , getSYSID()
                , getCurrentUser());
        try {
            beans = HttpUtils.doGet(url, Beans.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }

    private void initMenuList() {
        new Thread(() -> {
            Beans response = getMenuResponse();
            if (!response.getStatus()) {
                showMsg(this, "菜单列表获取失败");
                return;
            }
            try {
                List<Map<String, Object>> result = JSONObject.parseObject(String.valueOf(response.getResult()), new TypeReference<List<Map<String, Object>>>() {
                });
                runOnUiThread(() -> {
                    for (Map item : result) {
                        String mName = String.valueOf(item.get("MODULENAME"));

                        Button button = new Button(this);
                        button.setTag(mName);

                        button.setText(String.valueOf(item.get("MODULCNAME")));
                        button.setBackgroundResource(R.drawable.shape_corner_shadow_1);

                        //Drawable top = ContextCompat.getDrawable(this, R.mipmap.instorage);
                        Drawable top = ContextCompat.getDrawable(this, getMipmapIdByName("instorage"));
                        button.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                        button.setCompoundDrawablePadding(-10);

                        button.setPadding(0, 12, 0, 0);
                        mFlHomeMenu.addView(button);

                        FlexboxLayout.LayoutParams llp = new FlexboxLayout.LayoutParams(dp2px(this, 121), dp2px(this, 77));
                        llp.setMargins(0, 0, 0, 40);
                        button.setLayoutParams(llp);

                        switch (String.valueOf(button.getTag()).trim().toUpperCase()) {
                            case "INSTOCK":
                                button.setOnClickListener((v) -> jumpToInStorageWorkOrderView());
                                break;
                            case "BINDASN":
                                button.setOnClickListener((v) -> jumpToAsnRelatedView());
                                break;
                            case "SHIP":
                                button.setOnClickListener((v) -> jumpToAsnRelatedView());
                                break;
                            case "OUTSTOCK":
                                button.setOnClickListener((v) -> jumpToOutStorageWorkOrderView());
                                break;
                            default:
                                break;
                        }



                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }).start();
    }
}
