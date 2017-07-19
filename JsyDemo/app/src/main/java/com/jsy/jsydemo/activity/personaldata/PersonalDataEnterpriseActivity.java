package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.jsy.jsydemo.EntityClass.model.JsonBean;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;
import com.jsy.jsydemo.view.BottomDialog;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;

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
@SuppressLint("SetTextI18n")
public class PersonalDataEnterpriseActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView personal_enterprise_identity;
    private TextView personal_enterprise_shares;
    private TextView personal_enterprise_location;
    private TextView personal_enterprise_type;
    private TextView personal_enterprise_time;
    private TextView personal_enterprise_industry;
    private TextView personal_enterprise_life;
    private EditText personal_enterprise_private_water;
    private EditText personal_enterprise_public_water;

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


    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_enterprise);
        findViewById();
        initJsonData();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.personal_enterprise_identity_linear:
            case R.id.personal_enterprise_identity:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_identity(),
                            "enterprise_identity", mHandler, 1003);
                }
                break;
            case R.id.personal_enterprise_shares_linear:
            case R.id.personal_enterprise_shares:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_share(),
                            "enterprise_shares", mHandler, 1002);
                }
                break;
            case R.id.personal_enterprise_location_linear:
            case R.id.personal_enterprise_location:
                showPickerView();
                break;
            case R.id.personal_enterprise_type_linear:
            case R.id.personal_enterprise_type:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_type(),
                            "enterprise_type", mHandler, 1001);
                }
                break;
            case R.id.personal_enterprise_industry_linear:
            case R.id.personal_enterprise_time:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_time(),
                            "enterprise_time", mHandler, 1005);
                }
                break;
            case R.id.personal_enterprise_time_linear:
            case R.id.personal_enterprise_industry:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_industry(),
                            "enterprise_industry", mHandler, 1000);
                }
                break;
            case R.id.personal_enterprise_life_linear:
            case R.id.personal_enterprise_life:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_enterprise_life(),
                            "enterprise_life", mHandler, 1004);
                }
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                getHttpCredit();
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
        personal_enterprise_private_water = (EditText) findViewById(R.id.personal_enterprise_private_water);
        personal_enterprise_public_water = (EditText) findViewById(R.id.personal_enterprise_public_water);

        getHttp();

        personal_enterprise_identity.setOnClickListener(this);
        personal_enterprise_shares.setOnClickListener(this);
        personal_enterprise_location.setOnClickListener(this);
        personal_enterprise_type.setOnClickListener(this);
        personal_enterprise_time.setOnClickListener(this);
        personal_enterprise_industry.setOnClickListener(this);
        personal_enterprise_life.setOnClickListener(this);

        findViewById(R.id.personal_enterprise_identity_linear).setOnClickListener(this);
        findViewById(R.id.personal_enterprise_shares_linear).setOnClickListener(this);
        findViewById(R.id.personal_enterprise_location_linear).setOnClickListener(this);
        findViewById(R.id.personal_enterprise_type_linear).setOnClickListener(this);
        findViewById(R.id.personal_enterprise_industry_linear).setOnClickListener(this);
        findViewById(R.id.personal_enterprise_time_linear).setOnClickListener(this);
        findViewById(R.id.personal_enterprise_life_linear).setOnClickListener(this);

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
        OkHttpManager.postAsync(HttpURL.getInstance().COMPANYSTATUSLLIST, "company_status_list", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("company_identity", personal_enterprise_identity.getText().toString());
        map.put("company_share", personal_enterprise_shares.getText().toString());
        map.put("address", personal_enterprise_location.getText().toString());
        map.put("type", personal_enterprise_type.getText().toString());
        map.put("industry", personal_enterprise_industry.getText().toString());
        map.put("charter_date", personal_enterprise_time.getText().toString());
        map.put("operation_year", personal_enterprise_life.getText().toString());
        map.put("private", personal_enterprise_private_water.getText().toString());
        map.put("public", personal_enterprise_public_water.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().COMPANYSTATUSADD, "company_status_add", map, this);
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
            case "company_status_list":
                break;
            case "company_status_add":
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "company_status_list":
                List<Map<String, String>> maps = JsonData.getInstance().getJsonPersonalDataenterprise(result);
                personal_enterprise_identity.setText(maps.get(0).get("company_identity"));
                personal_enterprise_shares.setText(maps.get(0).get("company_share"));
                personal_enterprise_location.setText(maps.get(0).get("address"));
                personal_enterprise_type.setText(maps.get(0).get("type"));
                personal_enterprise_industry.setText(maps.get(0).get("industry"));
                personal_enterprise_time.setText(maps.get(0).get("charter_date"));
                personal_enterprise_life.setText(maps.get(0).get("operation_year"));
                personal_enterprise_private_water.setText(maps.get(0).get("private"));
                personal_enterprise_public_water.setText(maps.get(0).get("public"));
                break;
            case "company_status_add":
                finish();
                break;
        }
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                personal_enterprise_location.setText(text);
            }
        }).setTitleText("")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(13)
                .setOutSideCancelable(false)
                .build();
          /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private void initJsonData() {   //解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
        //  获取json数据
        String JsonData = AppUtil.getJson(this, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
