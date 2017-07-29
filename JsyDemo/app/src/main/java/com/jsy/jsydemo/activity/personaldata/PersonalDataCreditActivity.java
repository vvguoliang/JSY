package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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

    private String[] purpose = new String[]{"购车贷款", "购房贷款", "网购贷款", "过桥短期资金", "装修贷款", "教育培训贷款", "旅游贷款",
            "三农贷款", "其他"};

    private String[] cards_record = new String[]{"无信用记录", "应用记录良好", "少量逾期", "征信较差"};

    private String[] degree_education = new String[]{"大专以下", "大专", "本科", "研究生及以上"};

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_credit);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B(this);
        }
    }

    @Override
    protected void findViewById() {
        personal_credit_degree_education = findViewById(R.id.personal_credit_degree_education);
        personal_no_cards = findViewById(R.id.personal_no_cards);
        personal_no_cards_record = findViewById(R.id.personal_no_cards_record);
        personal_credit_liabilities = findViewById(R.id.personal_credit_liabilities);
        personal_credit_no_loan = findViewById(R.id.personal_credit_no_loan);
        personal_credit_no_taobao = findViewById(R.id.personal_credit_no_taobao);
        personal_credit_purpose = findViewById(R.id.personal_credit_purpose);

        personal_credit_degree_education.setOnClickListener(this);
        personal_no_cards.setOnClickListener(this);
        personal_no_cards_record.setOnClickListener(this);
        personal_credit_liabilities.setOnClickListener(this);
        personal_credit_no_loan.setOnClickListener(this);
        personal_credit_no_taobao.setOnClickListener(this);
        personal_credit_purpose.setOnClickListener(this);

        findViewById(R.id.personal_credit_degree_education_linea).setOnClickListener(this);
        findViewById(R.id.personal_no_cards_linear).setOnClickListener(this);
        findViewById(R.id.personal_no_cards_record_linear).setOnClickListener(this);
        findViewById(R.id.personal_credit_liabilities_linear).setOnClickListener(this);
        findViewById(R.id.personal_credit_no_loan_linear).setOnClickListener(this);
        findViewById(R.id.personal_credit_no_taobao_linear).setOnClickListener(this);
        findViewById(R.id.personal_credit_purpose_linear).setOnClickListener(this);

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_credit));
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);

        getHttp();

    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().PERSONALDATACREDIT, "user_credit", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("edu", personal_credit_degree_education.getText().toString());
        map.put("creditcard", personal_no_cards.getText().toString());
        map.put("credit_record", personal_no_cards_record.getText().toString());
        map.put("liabilities_status", personal_credit_liabilities.getText().toString());
        map.put("loan_record", personal_credit_no_loan.getText().toString());
        map.put("taobao_id", personal_credit_no_taobao.getText().toString());
        map.put("loan_use", personal_credit_purpose.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().PERSONALDATACREDITADD, "user_credit_add", map, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_credit_degree_education_linea:
            case R.id.personal_credit_degree_education://文化程度
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_degree_education(),
                            "degree_education", mHandler, 1002);
                }
                break;
            case R.id.personal_no_cards_linear:
            case R.id.personal_no_cards://有无信用卡
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this,
                            "no_cards", this.getString(R.string.name_loan_wu),
                            this.getString(R.string.name_loan_you), mHandler, 1006);
                }
                break;
            case R.id.personal_no_cards_record_linear:
            case R.id.personal_no_cards_record://两年内应用记录
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().getDialog(this, getList_cards_record(),
                            "cards_record", mHandler, 1001);
                }
                break;
            case R.id.personal_credit_liabilities_linear:
            case R.id.personal_credit_liabilities://负债情况
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this, "credit_liabilities",
                            this.getString(R.string.name_loan_wu), this.getString(R.string.name_loan_you), mHandler, 1003);
                }
                break;
            case R.id.personal_credit_no_loan_linear:
            case R.id.personal_credit_no_loan://有无成功贷款情况
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this, "credit_no_loa",
                            this.getString(R.string.name_loan_wu), this.getString(R.string.name_loan_you), mHandler, 1004);
                }
                break;
            case R.id.personal_credit_no_taobao_linear:
            case R.id.personal_credit_no_taobao://是否实名淘宝
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(PersonalDataCreditActivity.this, "credit_no_taoba",
                            this.getString(R.string.name_loan_wu), this.getString(R.string.name_loan_you), mHandler, 1005);
                }
                break;
            case R.id.personal_credit_purpose_linear:
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
                intent = new Intent();
                intent.putExtra("complete", "2");
                setResult(100, intent);
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
                case 1003:
                    personal_credit_liabilities.setText(msg.obj.toString());
                    break;
                case 1004:
                    personal_credit_no_loan.setText(msg.obj.toString());
                    break;
                case 1005:
                    personal_credit_no_taobao.setText(msg.obj.toString());
                    break;
                case 1006:
                    personal_no_cards.setText(msg.obj.toString());
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
        switch (name) {
            case "user_credit":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "user_credit_add":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "user_credit":
                List<Map<String, String>> list = JsonData.getInstance().getJsonPersonalDataCredit(result);
                if (list != null && list.size() > 0) {
                    personal_credit_degree_education.setText(list.get(0).get("edu"));
                    personal_no_cards.setText(list.get(0).get("creditcard"));
                    personal_no_cards_record.setText(list.get(0).get("credit_record"));
                    personal_credit_liabilities.setText(list.get(0).get("liabilities_status"));
                    personal_credit_no_loan.setText(list.get(0).get("loan_record"));
                    personal_credit_no_taobao.setText(list.get(0).get("taobao_id"));
                    personal_credit_purpose.setText(list.get(0).get("loan_use"));
                }
                break;
            case "user_credit_add":
                JSONObject object = new JSONObject(result);
                if (object.optString("code").equals("0000")) {
                    intent = new Intent();
                    intent.putExtra("complete", "1");
                    setResult(100, intent);
                    finish();
                } else {
                    ToatUtils.showShort1(this, object.optString("msg"));
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra("complete", "2");
            setResult(100, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
