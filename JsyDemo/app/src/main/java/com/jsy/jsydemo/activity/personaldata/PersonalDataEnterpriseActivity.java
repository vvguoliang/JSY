package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 企业经营情况
 */

public class PersonalDataEnterpriseActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView personal_enterprise_identity;
    private TextView personal_enterprise_shares;
    private TextView personal_enterprise_location;
    private TextView personal_enterprise_type;
    private TextView personal_enterprise_time;
    private TextView personal_enterprise_industry;
    private TextView personal_enterprise_life;
    private TextView personal_enterprise_private_water;
    private TextView personal_enterprise_public_water;

    private String[] enterprise_industry = new String[]{"批发/零售", "制造业", "金融/保险/证券", "住宿/餐饮/旅游",
            "商业服务/娱乐/艺术/体育", "计算机/互联网", "通讯电子", "建筑/房地产", "法律/咨询", "卫生/教育/社会服务", "公共事业/社会团体",
            "生物/制药", "广告/媒体", "能源", "贸易", "交通运输/仓储/物流", "农/林/牧/渔", "其他"};

    private String[] enterprise_type = new String[]{"政府或企事业单位", "国企央企", "外资企业", "上市公司",
            "普通民营企业", "个体工商户"};

    private String[] nterprise_shares = new String[]{"少于10%", "10%-20%", "20%-30%", "30%-40%", "40%-50%", "50%及以上"};

    private String[] enterprise_identity = new String[]{"法人", "股东", "其他"};

    private String[] enterprise_life = new String[]{"不足半年", "半年～1年", "1年～2年", "2年～3年", "3年以上"};

    private String[] enterprise_time = new String[]{"未办理", "已办理且注册不满6个月", "已办理且注册不满一年",
            "已办理且注册超过一年", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_enterprise);
        findViewById();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.personal_enterprise_identity:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_identity(),
                            "enterprise_identity", mHandler, 1003);
                }
                break;
            case R.id.personal_enterprise_shares:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_share(),
                            "enterprise_shares", mHandler, 1002);
                }
                break;
            case R.id.personal_enterprise_location:
                break;
            case R.id.personal_enterprise_type:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_type(),
                            "enterprise_type", mHandler, 1001);
                }
                break;
            case R.id.personal_enterprise_time:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_time(),
                            "enterprise_time", mHandler, 1005);
                }
                break;
            case R.id.personal_enterprise_industry:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_industry(),
                            "enterprise_industry", mHandler, 1000);
                }
                break;
            case R.id.personal_enterprise_life:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_life(),
                            "enterprise_life", mHandler, 1004);
                }
                break;
            case R.id.personal_enterprise_private_water:
                break;
            case R.id.personal_enterprise_public_water:
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                finish();
                break;
        }

    }

    @Override
    protected void findViewById() {
        personal_enterprise_identity = (TextView) findViewById(R.id.personal_enterprise_identity);
        personal_enterprise_shares = (TextView) findViewById(R.id.personal_enterprise_shares);
        personal_enterprise_location = (TextView) findViewById(R.id.personal_enterprise_location);
        personal_enterprise_type = (TextView) findViewById(R.id.personal_enterprise_type);
        personal_enterprise_time = (TextView) findViewById(R.id.personal_enterprise_time);
        personal_enterprise_industry = (TextView) findViewById(R.id.personal_enterprise_industry);
        personal_enterprise_life = (TextView) findViewById(R.id.personal_enterprise_life);
        personal_enterprise_private_water = (TextView) findViewById(R.id.personal_enterprise_private_water);
        personal_enterprise_public_water = (TextView) findViewById(R.id.personal_enterprise_public_water);

        personal_enterprise_identity.setOnClickListener(this);
        personal_enterprise_shares.setOnClickListener(this);
        personal_enterprise_location.setOnClickListener(this);
        personal_enterprise_type.setOnClickListener(this);
        personal_enterprise_time.setOnClickListener(this);
        personal_enterprise_industry.setOnClickListener(this);
        personal_enterprise_life.setOnClickListener(this);
        personal_enterprise_private_water.setOnClickListener(this);
        personal_enterprise_public_water.setOnClickListener(this);

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_enterprise));

        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().PERSONALDATACREDIT, "user_enterprise", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("edu", "");
        map.put("creditcard", "");
        map.put("credit_record", "");
        map.put("liabilities_status", "");
        map.put("loan_record", "");
        map.put("taobao_id", "");
        map.put("loan_use", "");
        OkHttpManager.postAsync(HttpURL.getInstance().PERSONALDATACREDITADD, "user_enterprise_add", map, this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    personal_enterprise_industry.setText(msg.obj.toString());
                    break;
                case 1001:
                    personal_enterprise_type.setText(msg.obj.toString());
                    break;
                case 1002:
                    personal_enterprise_shares.setText(msg.obj.toString());
                    break;
                case 1003:
                    personal_enterprise_identity.setText(msg.obj.toString());
                    break;
                case 1004:
                    personal_enterprise_life.setText(msg.obj.toString());
                    break;
                case 1005:
                    personal_enterprise_time.setText(msg.obj.toString());
                    break;
            }
        }
    };

    /**
     * 经营公司所属行业
     *
     * @return
     */
    private List<Map<String, Object>> getList_enterprise_industry() {
        List<Map<String, Object>> list_enterprise_industry = new ArrayList<>();
        for (String aPurpose : enterprise_industry) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_enterprise_industry.add(map);
        }
        return list_enterprise_industry;
    }

    /**
     * 经营企业类型
     *
     * @return
     */
    private List<Map<String, Object>> getList_enterprise_type() {
        List<Map<String, Object>> list_enterprise_type = new ArrayList<>();
        for (String aPurpose : enterprise_type) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_enterprise_type.add(map);
        }
        return list_enterprise_type;
    }

    /**
     * 经营企业类型
     *
     * @return
     */
    private List<Map<String, Object>> getList_enterprise_share() {
        List<Map<String, Object>> list_enterprise_sharee = new ArrayList<>();
        for (String aPurpose : nterprise_shares) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_enterprise_sharee.add(map);
        }
        return list_enterprise_sharee;
    }

    /**
     * 企业的身份
     *
     * @return
     */
    private List<Map<String, Object>> getList_enterprise_identity() {
        List<Map<String, Object>> list_enterprise_identity = new ArrayList<>();
        for (String aPurpose : enterprise_identity) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_enterprise_identity.add(map);
        }
        return list_enterprise_identity;
    }

    /**
     * 企业经营年限
     *
     * @return
     */
    private List<Map<String, Object>> getList_enterprise_life() {
        List<Map<String, Object>> list_enterprise_life = new ArrayList<>();
        for (String aPurpose : enterprise_life) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_enterprise_life.add(map);
        }
        return list_enterprise_life;
    }

    /**
     * 营业执照事件
     *
     * @return
     */
    private List<Map<String, Object>> getList_enterprise_time() {
        List<Map<String, Object>> list_enterprise_time = new ArrayList<>();
        for (String aPurpose : enterprise_time) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_enterprise_time.add(map);
        }
        return list_enterprise_time;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "user_enterprise":
                break;
            case "user_enterprise_add":
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "user_enterprise":
                break;
            case "user_enterprise_add":
                break;
        }
    }
}
