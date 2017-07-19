package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * 家庭情况
 */

public class PersonalDataFamilyActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView personal_family_marriage;
    private TextView personal_family_city;
    private EditText personal_family_address;
    private TextView personal_family_household_register;

    private String[] family_marriage = new String[]{"已婚", "未婚", "离异"};

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_family);
        findViewById();
        initJsonData();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.personal_family_marriage_linear:
            case R.id.personal_family_marriage:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_family_marriage(),
                            "family_marriage", mHandler, 1001);
                }
                break;
            case R.id.personal_family_city_linear:
            case R.id.personal_family_city:
                showPickerView(1);
                break;
            case R.id.personal_family_household_register_linear:
            case R.id.personal_family_household_register:
                showPickerView(2);
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

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_family));

        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);

        personal_family_marriage = (TextView) findViewById(R.id.personal_family_marriage);
        personal_family_city = (TextView) findViewById(R.id.personal_family_city);
        personal_family_address = (EditText) findViewById(R.id.personal_family_address);
        personal_family_household_register = (TextView) findViewById(R.id.personal_family_household_register);

        findViewById(R.id.personal_family_marriage_linear).setOnClickListener(this);
        findViewById(R.id.personal_family_city_linear).setOnClickListener(this);
        findViewById(R.id.personal_family_household_register_linear).setOnClickListener(this);

        getHttp();

        personal_family_marriage.setOnClickListener(this);
        personal_family_city.setOnClickListener(this);
        personal_family_address.setOnClickListener(this);
        personal_family_household_register.setOnClickListener(this);
    }


    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().FAMILYLIST, "family_list", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("marriage_status", personal_family_marriage.getText().toString());
        map.put("city", personal_family_city.getText().toString());
        map.put("address", personal_family_address.getText().toString());
        map.put("hj_address", personal_family_household_register.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().FAMILYADD, "family_add", map, this);
    }

    @Override
    protected void initView() {

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
            case "family_list":
                break;
            case "family_add":
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "family_list":
                List<Map<String, String>> maps = JsonData.getInstance().getJsonPersonalDataFamily(result);
                personal_family_marriage.setText(maps.get(0).get("marriage_status"));
                personal_family_city.setText(maps.get(0).get("city"));
                personal_family_address.setText(maps.get(0).get("address"));
                personal_family_household_register.setText(maps.get(0).get("hj_address"));
                break;
            case "family_add":
                finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    personal_family_marriage.setText(msg.obj.toString());
                    break;
            }
        }
    };

    /**
     * 婚姻状态
     *
     * @return
     */
    private List<Map<String, Object>> getList_family_marriage() {
        List<Map<String, Object>> list_enterprise_industry = new ArrayList<>();
        for (String aPurpose : family_marriage) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_enterprise_industry.add(map);
        }
        return list_enterprise_industry;
    }

    private void showPickerView(final int city) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                switch (city) {
                    case 1:
                        personal_family_city.setText(text);
                        break;
                    case 2:
                        personal_family_household_register.setText(text);
                        break;
                }
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
