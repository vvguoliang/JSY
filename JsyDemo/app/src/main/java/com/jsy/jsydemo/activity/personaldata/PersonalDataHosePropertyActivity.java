package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 房产
 */

public class PersonalDataHosePropertyActivity extends BaseActivity implements View.OnClickListener {


    private TextView house_estate;
    private TextView house_location;
    private TextView house_type;
    private TextView house_market_price;
    private TextView house_mortgage;
    private TextView house_no_mortgage;

    private String[] House_type = new String[]{"商品房", "商住两用", "经济适用房", "宅基地", "军产房", "商铺", "写字楼", "厂房",
            "小产权房", "危改房", "其他"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_hose_peoerty);
        findViewById();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                break;
            case R.id.house_estate:
                ShowDialog.getInstance().getDialog(this, getHouse_type(), "house_type",
                        mHandler, 1001);
                break;
            case R.id.house_location:
                break;
            case R.id.house_type:
                ShowDialog.getInstance().getDialog(this, getHouse_type(), "house_type",
                        mHandler, 1000);
                break;
            case R.id.house_market_price:
                break;
            case R.id.house_mortgage:
                ShowDialog.getInstance().showDialog(this, "house_mortgage", this.getString(R.string.name_loan_wu),
                        this.getString(R.string.name_loan_you), 1);
                break;
            case R.id.house_no_mortgage:
                ShowDialog.getInstance().showDialog(this, "house_no_mortgage", this.getString(R.string.name_loan_wu),
                        this.getString(R.string.name_loan_you), 1);
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
        house_market_price = (TextView) findViewById(R.id.house_market_price);
        house_mortgage = (TextView) findViewById(R.id.house_mortgage);
        house_no_mortgage = (TextView) findViewById(R.id.house_no_mortgage);

        house_estate.setOnClickListener(this);
        house_location.setOnClickListener(this);
        house_type.setOnClickListener(this);
        house_market_price.setOnClickListener(this);
        house_mortgage.setOnClickListener(this);
        house_no_mortgage.setOnClickListener(this);
    }

    @Override
    protected void initView() {

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

}
