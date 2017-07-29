package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 车产
 */

public class PersonalDataCarActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView car_estate;
    private EditText car_new_car;
    private TextView car_life;
    private TextView car_mortgage;
    private TextView car_no_mortgage;

    private String[] car_life_String = new String[]{"0-6个月", "1年", "2年", "3年", "3-5年", "5年以上"};
    private String[] list_car_estate_String = new String[]{"无车产", "有车产有抵押", "有车产无抵押"};

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_car);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_estate_linear:
            case R.id.car_estate:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    ShowDialog.getInstance().getDialog(this, getcar_estate(), "car_estate", mHandler, 1001);
                }
                break;
            case R.id.car_life_linear:
            case R.id.car_life:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    ShowDialog.getInstance().getDialog(this, getcar_life(), "car_life", mHandler, 1000);
                }
                break;
            case R.id.car_mortgage_linear:
            case R.id.car_mortgage:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "car_mortgage", this.getString(R.string.name_loan_wu),
                            this.getString(R.string.name_loan_you), mHandler, 1002);
                }
                break;
            case R.id.car_no_mortgage_linear:
            case R.id.car_no_mortgage:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "car_no_mortgage", this.getString(R.string.name_loan_wu),
                            this.getString(R.string.name_loan_you), mHandler, 1003);
                }
                break;
            case R.id.title_image:
                intent = new Intent();
                intent.putExtra("complete", "2");
                setResult(105, intent);
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
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_car_production));

        car_estate = findViewById(R.id.car_estate);
        car_new_car = findViewById(R.id.car_new_car);
        car_life = findViewById(R.id.car_life);
        car_mortgage = findViewById(R.id.car_mortgage);
        car_no_mortgage = findViewById(R.id.car_no_mortgage);

        getHttp();
        car_estate.setOnClickListener(this);
        car_life.setOnClickListener(this);
        car_mortgage.setOnClickListener(this);
        car_no_mortgage.setOnClickListener(this);

        findViewById(R.id.car_estate_linear).setOnClickListener(this);
        findViewById(R.id.car_life_linear).setOnClickListener(this);
        findViewById(R.id.car_mortgage_linear).setOnClickListener(this);
        findViewById(R.id.car_no_mortgage_linear).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().CARLIST, "car_list", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("car", car_life.getText().toString());
        map.put("car_price", car_new_car.getText().toString());
        map.put("use_time", car_estate.getText().toString());
        map.put("installment", car_mortgage.getText().toString());
        map.put("mortgage", car_no_mortgage.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().CARADD, "car_add", map, this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    car_life.setText(msg.obj.toString());
                    break;
                case 1001:
                    car_estate.setText(msg.obj.toString());
//                    if (msg.obj.toString().equals("无车产")) {
//                        findViewById(R.id.car_new_car_linear).setVisibility(View.GONE);
//                        findViewById(R.id.car_life_linear).setVisibility(View.GONE);
//                        findViewById(R.id.car_mortgage_linear).setVisibility(View.GONE);
//                        findViewById(R.id.car_no_mortgage_linear).setVisibility(View.GONE);
//                    } else {
//                        findViewById(R.id.car_new_car_linear).setVisibility(View.VISIBLE);
//                        findViewById(R.id.car_life_linear).setVisibility(View.VISIBLE);
//                        findViewById(R.id.car_mortgage_linear).setVisibility(View.VISIBLE);
//                        findViewById(R.id.car_no_mortgage_linear).setVisibility(View.VISIBLE);
//                    }
                    break;
                case 1002:
                    car_mortgage.setText(msg.obj.toString());
                    break;
                case 1003:
                    car_no_mortgage.setText(msg.obj.toString());
                    break;
            }
        }
    };

    /**
     * 使用车年限
     *
     * @return
     */
    private List<Map<String, Object>> getcar_life() {
        List<Map<String, Object>> list_car_life = new ArrayList<>();
        for (String aPurpose : car_life_String) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_car_life.add(map);
        }
        return list_car_life;
    }

    /**
     * 名下车产
     *
     * @return
     */
    private List<Map<String, Object>> getcar_estate() {
        List<Map<String, Object>> list_car_estate = new ArrayList<>();
        for (String aPurpose : list_car_estate_String) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_car_estate.add(map);
        }
        return list_car_estate;
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
            case "car_add":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "car_list":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "car_add":
                JSONObject object = new JSONObject(result);
                if (object.optString("code").equals("0000")) {
                    intent = new Intent();
                    intent.putExtra("complete", "1");
                    setResult(105, intent);
                    finish();
                } else {
                    ToatUtils.showShort1(this, object.optString("msg"));
                }
                break;
            case "car_list":
                List<Map<String, String>> maps = JsonData.getInstance().getJsonPersonalDataCar(result);
                if (maps != null && maps.size() > 0) {
                    car_life.setText(maps.get(0).get("car"));
                    car_new_car.setText(maps.get(0).get("car_price"));
                    car_estate.setText(maps.get(0).get("use_time"));
                    car_mortgage.setText(maps.get(0).get("installment"));
                    car_no_mortgage.setText(maps.get(0).get("mortgage"));
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra("complete", "2");
            setResult(105, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
