package com.jsy.jsydemo.activity.personaldata;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.TimeUtils;
import com.jsy.jsydemo.view.PublicDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vvguoliang on 2017/6/27.
 * 个人资信
 */

public class PersonalDataCreditActivity extends BaseActivity implements View.OnClickListener {

    private TextView personal_credit_degree_education;

    private TextView personal_no_cards;

    private TextView personal_no_cards_record;

    private TextView personal_credit_liabilities;

    private TextView personal_credit_no_loan;

    private TextView personal_credit_no_taobao;

    private TextView personal_credit_purpose;

    private String[] purpose = new String[]{"购车贷款", "购房贷款", "网购贷款", "过桥短期资金", "装修贷款", "教育培训贷款", "旅游贷款", "三农贷款", "其他"};

    private int purpose_int = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_credit);
        findViewById();
    }

    @Override
    protected void findViewById() {
        personal_credit_degree_education = (TextView) findViewById(R.id.personal_credit_degree_education);
        personal_no_cards = (TextView) findViewById(R.id.personal_no_cards);
        personal_no_cards_record = (TextView) findViewById(R.id.personal_no_cards_record);
        personal_credit_liabilities = (TextView) findViewById(R.id.personal_credit_liabilities);
        personal_credit_no_loan = (TextView) findViewById(R.id.personal_credit_no_loan);
        personal_credit_no_taobao = (TextView) findViewById(R.id.personal_credit_no_taobao);
        personal_credit_purpose = (TextView) findViewById(R.id.personal_credit_purpose);

        personal_credit_degree_education.setOnClickListener(this);
        personal_no_cards.setOnClickListener(this);
        personal_no_cards_record.setOnClickListener(this);
        personal_credit_liabilities.setOnClickListener(this);
        personal_credit_no_loan.setOnClickListener(this);
        personal_credit_no_taobao.setOnClickListener(this);
        personal_credit_purpose.setOnClickListener(this);

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_credit));
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_credit_degree_education://文化程度
                break;
            case R.id.personal_no_cards://有无信用卡
                break;
            case R.id.personal_no_cards_record://两年内应用记录
                break;
            case R.id.personal_credit_liabilities://负债情况
                break;
            case R.id.personal_credit_no_loan://有无成功贷款情况
                break;
            case R.id.personal_credit_no_taobao://是否实名淘宝
                break;
            case R.id.personal_credit_purpose://贷款用途
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    getDialog(getList_purpose(), "credit_purpose");
                }
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete://完成
                break;
        }
    }

    private void getDialog(List<Map<String, Object>> list, String name) {
        PublicDialog.Builder builder = new PublicDialog.Builder(this);
        String stringa = SharedPreferencesUtils.get(this, name, "").toString();
        if (!StringUtil.isNullOrEmpty(stringa)) {
            List<Map<String, Object>> listViewEntities = SharedPreferencesUtils.getInfo(this, stringa);
            if (null != listViewEntities && listViewEntities.size() > 0) {
                list.clear();
                list = listViewEntities;
            }
        }
        final List<Map<String, Object>> finalList = list;
        builder.setItems(list, name, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; finalList.size() > i; i++) {
                    if (finalList.get(i).get("boolean").equals("2")) {
                        personal_credit_purpose.setText(finalList.get(i).get("name").toString());
                    }
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private List<Map<String, Object>> getList_purpose() {
        List<Map<String, Object>> list_purpose = new ArrayList<>();
        for (String aPurpose : purpose) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_purpose.add(map);
        }
        return list_purpose;
    }

}
