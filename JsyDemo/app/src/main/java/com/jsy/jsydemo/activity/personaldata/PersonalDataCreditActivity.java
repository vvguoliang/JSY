package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.TimeUtils;
import com.jsy.jsydemo.view.PublicDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/27.
 * 个人资信
 */

public class PersonalDataCreditActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView personal_credit_degree_education;

    private TextView personal_no_cards;

    private TextView personal_no_cards_record;

    private TextView personal_credit_liabilities;

    private TextView personal_credit_no_loan;

    private TextView personal_credit_no_taobao;

    private TextView personal_credit_purpose;

    private String[] purpose = new String[]{"购车贷款", "购房贷款", "网购贷款", "过桥短期资金", "装修贷款", "教育培训贷款", "旅游贷款", "三农贷款", "其他"};

    private String[] cards_record = new String[]{"无信用记录", "应用记录良好", "少量逾期", "征信较差"};

    private String[] degree_education = new String[]{"大专以下", "大专", "本科", "研究生及以上"};

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

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().PERSONALDATACREDIT, "user_credit", map, this);
    }

    private void getHttpCredit(){
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("edu", "");
        map.put("creditcard", "");
        map.put("credit_record", "");
        map.put("liabilities_status", "");
        map.put("loan_record","");
        map.put("taobao_id", "");
        map.put("loan_use", "");
        OkHttpManager.postAsync(HttpURL.getInstance().PERSONALDATACREDITADD, "user_credit_add", map, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_credit_degree_education://文化程度
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_degree_education(),
                            "degree_education", mHandler, 1002);
                }
                break;
            case R.id.personal_no_cards://有无信用卡
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this,
                            "no_cards", this.getString(R.string.name_loan_wu),
                            this.getString(R.string.name_loan_you), 1);
                }
                break;
            case R.id.personal_no_cards_record://两年内应用记录
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_cards_record(),
                            "credit_purpose", mHandler, 1001);
                }
                break;
            case R.id.personal_credit_liabilities://负债情况
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this, "credit_liabilities",
                            this.getString(R.string.name_loan_wu), this.getString(R.string.name_loan_you), 1);
                }
                break;
            case R.id.personal_credit_no_loan://有无成功贷款情况
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this, "credit_no_loa",
                            this.getString(R.string.name_loan_wu), this.getString(R.string.name_loan_you), 1);
                }
                break;
            case R.id.personal_credit_no_taobao://是否实名淘宝
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this, "credit_no_taoba",
                            this.getString(R.string.name_loan_wu), this.getString(R.string.name_loan_you), 1);
                }
                break;
            case R.id.personal_credit_purpose://贷款用途
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_purpose(),
                            "credit_purpose", mHandler, 1000);
                }
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete://完成
                getHttpCredit();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    personal_credit_purpose.setText(msg.obj.toString());
                    break;
                case 1001:
                    personal_no_cards_record.setText(msg.obj.toString());
                    break;
                case 1002:
                    personal_credit_degree_education.setText(msg.obj.toString());
                    break;
            }
        }
    };

    /**
     * 贷款用途
     *
     * @return
     */
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

    /**
     * 两年内信用程度
     *
     * @return
     */
    private List<Map<String, Object>> getList_cards_record() {
        List<Map<String, Object>> list_cards_record = new ArrayList<>();
        for (String aPurpose : cards_record) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_cards_record.add(map);
        }
        return list_cards_record;
    }

    /**
     * 文化程度
     *
     * @return
     */
    private List<Map<String, Object>> getList_degree_education() {
        List<Map<String, Object>> list_degree_education = new ArrayList<>();
        for (String aPurpose : degree_education) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_degree_education.add(map);
        }
        return list_degree_education;
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
        switch (name){
            case "user_credit":
                break;
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {

        switch (name){
            case "user_credit":
                break;
        }
    }
}
