package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
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
 * 房产
 */

public class PersonalDataHosePropertyActivity extends BaseActivity implements View.OnClickListener, DataCallBack {


    private TextView house_estate;
    private TextView house_location;
    private TextView house_type;
    private EditText house_market_price;
    private TextView house_mortgage;
    private TextView house_no_mortgage;

    private String[] House_estate = new String[]{"无房产", "商品房", "商住两用", "经济适用房", "宅基地", "军产房", "商铺", "写字楼", "厂房",
            "小产权房", "危改房", "其他"};

    private String[] House_type = new String[]{"商品房", "商住两用", "经济适用房", "宅基地", "军产房", "商铺", "写字楼", "厂房",
            "小产权房", "危改房", "其他"};

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_hose_peoerty);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            LinearLayout tab_activity_lin = (LinearLayout) findViewById(R.id.tab_activity_lin);
            stateBarTint("#305591", true);
            statusFragmentBarDarkMode();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_activity_lin.getLayoutParams();
            lp.height = DisplayUtils.px2dip(this, 48 * 11);
        }
        initJsonData();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                getHttpCredit();
                break;
            case R.id.house_estate_linear:
            case R.id.house_estate:
                ShowDialog.getInstance().getDialog(this, getHouse_estate(), "house_estate",
                        mHandler, 1001);
                break;
            case R.id.house_location_linear:
            case R.id.house_location:
                showPickerView();
                break;
            case R.id.house_type_linear:
            case R.id.house_type:
                ShowDialog.getInstance().getDialog(this, getHouse_type(), "house_type",
                        mHandler, 1000);
                break;
            case R.id.house_mortgage_linear:
            case R.id.house_mortgage:
                ShowDialog.getInstance().showDialog(this, "house_mortgage", this.getString(R.string.name_loan_wu),
                        this.getString(R.string.name_loan_you), mHandler, 1002);
                break;
            case R.id.house_no_mortgage_linear:
            case R.id.house_no_mortgage:
                ShowDialog.getInstance().showDialog(this, "house_no_mortgage", this.getString(R.string.name_loan_wu),
                        this.getString(R.string.name_loan_you), mHandler, 1003);
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_house_property));

        house_estate = (TextView) findViewById(R.id.house_estate);
        house_location = (TextView) findViewById(R.id.house_location);
        house_type = (TextView) findViewById(R.id.house_type);
        house_market_price = (EditText) findViewById(R.id.house_market_price);
        house_mortgage = (TextView) findViewById(R.id.house_mortgage);
        house_no_mortgage = (TextView) findViewById(R.id.house_no_mortgage);

        getHttp();

        house_estate.setOnClickListener(this);
        house_location.setOnClickListener(this);
        house_type.setOnClickListener(this);
        house_market_price.setOnClickListener(this);
        house_mortgage.setOnClickListener(this);
        house_no_mortgage.setOnClickListener(this);

        findViewById(R.id.house_estate_linear).setOnClickListener(this);
        findViewById(R.id.house_location_linear).setOnClickListener(this);
        findViewById(R.id.house_type_linear).setOnClickListener(this);
        findViewById(R.id.house_mortgage_linear).setOnClickListener(this);
        findViewById(R.id.house_no_mortgage_linear).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().HOUSELIST, "hose_list", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("house", house_estate.getText().toString());
        map.put("house_address", house_location.getText().toString());
        map.put("house_type", house_type.getText().toString());
        map.put("house_price", house_market_price.getText().toString());
        map.put("installment", house_mortgage.getText().toString());
        map.put("mortgage", house_no_mortgage.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().HOUSEADD, "hose_add", map, this);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    house_type.setText(msg.obj.toString());
                    break;
                case 1001:
                    house_estate.setText(msg.obj.toString());
                    break;
                case 1002:
                    house_estate.setText(msg.obj.toString());
                    break;
                case 1003:
                    house_estate.setText(msg.obj.toString());
                    break;
            }
        }
    };

    /**
     * 房产类型
     *
     * @return
     */
    private List<Map<String, Object>> getHouse_type() {
        List<Map<String, Object>> list_cards_record = new ArrayList<>();
        for (String aPurpose : House_type) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_cards_record.add(map);
        }
        return list_cards_record;
    }

    /**
     * 名下房产
     *
     * @return
     */
    private List<Map<String, Object>> getHouse_estate() {
        List<Map<String, Object>> list_cards_record = new ArrayList<>();
        for (String aPurpose : House_estate) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_cards_record.add(map);
        }
        return list_cards_record;
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
            case "hose_add":
                break;
            case "hose_list":
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "hose_add":
                finish();
                break;
            case "hose_list":
                List<Map<String, String>> list = JsonData.getInstance().getJsonPersonalDataHose(result);
                house_estate.setText(list.get(0).get("house"));
                house_location.setText(list.get(0).get("house_address"));
                house_type.setText(list.get(0).get("house_type"));
                house_market_price.setText(list.get(0).get("house_price"));
                house_mortgage.setText(list.get(0).get("installment"));
                house_no_mortgage.setText(list.get(0).get("mortgage"));
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
                house_location.setText(text);
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
