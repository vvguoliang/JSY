package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.moblie.zmxy.antgroup.creditsdk.app.CreditApp;
import com.android.moblie.zmxy.antgroup.creditsdk.app.ICreditListener;
import com.jsy.jsydemo.EntityClass.LoanDatailsData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.personaldata.PersonalDataCertificatesActivity;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/4.
 * 贷款详情
 */
@SuppressLint("SetTextI18n")
public class LoanDetailsActivity extends BaseActivity implements View.OnClickListener, DataCallBack, ICreditListener {

    private EditText loan_details_editText_range;
    private TextView loan_details_textView_rang;

    private EditText loan_details_editText_day;
    private TextView loan_details_textView_day;

    private TextView loan_details_resource_rat;
    private TextView loan_details_repayment;
    private TextView loan_details_loan_time;

    private Button loan_details_basic_information;
    private Button loan_details_phone_operator;
    private Button loan_details_id;
    private Button loan_details_other;

    private Button oan_details_esame_credit;

    private Button loan_details_button;

    private TextView details_repayment_text;

    private TextView details_editText_day_text;

    private String id = "";

    private double loanMax = 0;

    private double loanMin = 0;

    private double day_monthMax = 0;

    private double day_monthMin = 0;

    private double profit = 0;

    private int anInt = 0;

    private LoanDatailsData loanDatailsData;

    private LinearLayout basic_information_linear;
    private LinearLayout phone_operator_linear;
    private LinearLayout esame_credit_linear;
    private LinearLayout etails_id_linear;
    private LinearLayout details_other_linear;

    private Intent intent = null;

    private CreditApp creditApp;

    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loandetails);
        creditApp = CreditApp.getOrCreateInstance(this.getApplicationContext());
        username = SharedPreferencesUtils.get(this, "username", "").toString();
        id = getIntent().getExtras().getString("id");
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.loan_details_phone_operator://运营商
                break;
            case R.id.loan_details_basic_information://基础信息认证
                startActivity(new Intent(LoanDetailsActivity.this, BasicAuthenticationActivity.class));
                break;
            case R.id.loan_details_id://身份证
                startActivity(new Intent(LoanDetailsActivity.this, PersonalDataCertificatesActivity.class));
                break;
            case R.id.loan_details_other://其他
                startActivity(new Intent(LoanDetailsActivity.this, OtherInformationActivity.class));
                break;
            case R.id.loan_details_button://补齐

                break;
            case R.id.oan_details_esame_credit://芝麻信用
                getSesameCredit();
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_details));

        loan_details_editText_range = (EditText) findViewById(R.id.loan_details_editText_range);
        loan_details_textView_rang = (TextView) findViewById(R.id.loan_details_textView_rang);
        loan_details_editText_day = (EditText) findViewById(R.id.loan_details_editText_day);
        loan_details_textView_day = (TextView) findViewById(R.id.loan_details_textView_day);

        loan_details_resource_rat = (TextView) findViewById(R.id.loan_details_resource_rat);
        loan_details_repayment = (TextView) findViewById(R.id.loan_details_repayment);
        loan_details_loan_time = (TextView) findViewById(R.id.loan_details_loan_time);

        loan_details_basic_information = (Button) findViewById(R.id.loan_details_basic_information);
        loan_details_basic_information.setOnClickListener(this);
        loan_details_phone_operator = (Button) findViewById(R.id.loan_details_phone_operator);
        loan_details_phone_operator.setOnClickListener(this);
        loan_details_id = (Button) findViewById(R.id.loan_details_id);
        loan_details_id.setOnClickListener(this);
        loan_details_other = (Button) findViewById(R.id.loan_details_other);
        loan_details_other.setOnClickListener(this);
        oan_details_esame_credit = (Button) findViewById(R.id.oan_details_esame_credit);
        oan_details_esame_credit.setOnClickListener(this);
        loan_details_button = (Button) findViewById(R.id.loan_details_button);
        loan_details_button.setOnClickListener(this);

        details_repayment_text = (TextView) findViewById(R.id.details_repayment_text);
        details_editText_day_text = (TextView) findViewById(R.id.details_editText_day_text);


        loan_details_editText_range.addTextChangedListener(textWatcher(1));
        loan_details_editText_day.addTextChangedListener(textWatcher(2));

        basic_information_linear = (LinearLayout) findViewById(R.id.basic_information_linear);
        phone_operator_linear = (LinearLayout) findViewById(R.id.phone_operator_linear);
        esame_credit_linear = (LinearLayout) findViewById(R.id.esame_credit_linear);
        etails_id_linear = (LinearLayout) findViewById(R.id.etails_id_linear);
        details_other_linear = (LinearLayout) findViewById(R.id.details_other_linear);
    }

    @Override
    protected void initView() {
        getHttp();
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedPreferencesUtils.get(this, "uid", "").toString());
        map.put("id", Long.parseLong(id));
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT_DETAIL, "product_detail", map, this);
    }

    private void getSesameCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("identity_type", "1");
        JSONObject id = new JSONObject();
        try {
            id.put("mobileNo", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("identity_param", id.toString());
        OkHttpManager.postAsync(HttpURL.getInstance().SESAMECREDIT, "SesameCredit", map, this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "product_detail":
                Log.e("", "====" + request + "====" + e);
                break;
            case "SesameCredit":
                break;
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "product_detail":
                loanDatailsData = JsonData.getInstance().getJsonLoanDailsData(result);
                if (loanDatailsData.getFv_unit().equals("1")) {//天
                    details_repayment_text.setText(this.getString(R.string.name_loan_details_repayment_yue));
                    details_editText_day_text.setText(this.getString(R.string.name_loan_details_month));
                } else {
                    details_repayment_text.setText(this.getString(R.string.name_loan_details_repayment));
                    details_editText_day_text.setText(this.getString(R.string.name_loan_details_day));

                }
                loan_details_resource_rat.setText(loanDatailsData.getFeilv());
                loan_details_textView_rang.setText("额度范围" + loanDatailsData.getEdufanwei());

                String[] edufan = loanDatailsData.getEdufanwei().split("-");
                loanMax = Double.parseDouble(edufan[1]);
                loanMin = Double.parseDouble(edufan[0]);
                loan_details_editText_range.setText(edufan[1]);

                loan_details_textView_day.setText("期限范围" + loanDatailsData.getQixianfanwei());
                String[] edufan1 = loanDatailsData.getQixianfanwei().split("-");
                day_monthMax = Double.parseDouble(edufan1[1]);
                day_monthMin = Double.parseDouble(edufan1[0]);
                loan_details_editText_day.setText(edufan1[1]);
                loan_details_loan_time.setText("24小时");

                String edufan2 = loanDatailsData.getFeilv().substring(0, loanDatailsData.getFeilv().length() - 1);
                profit = Double.parseDouble(edufan2);
                loan_details_repayment.setText(getAlgorithm(loanMax, day_monthMax, profit));

                anInt++;
                if (StringUtil.isNullOrEmpty(loanDatailsData.getUser_auth()) || loanDatailsData.getUser_auth().equals("null")) {
                    loan_details_basic_information.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    loan_details_phone_operator.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    loan_details_id.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    loan_details_other.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    oan_details_esame_credit.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                } else {

                }
                String data_id = loanDatailsData.getData_id();
                Pattern pattern = Pattern.compile("\\[(.*)\\]");
                Matcher matcher = pattern.matcher(data_id);
                String ResponseDates = "";
                while (matcher.find()) {
                    ResponseDates = matcher.group(1);
                }
                String[] data_ids = ResponseDates.split(",");
//                for (String data_id1 : data_ids) {
//                    if (data_id1.contains("1")) {
                basic_information_linear.setVisibility(View.VISIBLE);
//                    } else if (data_id1.contains("2")) {
                phone_operator_linear.setVisibility(View.VISIBLE);
//                    } else if (data_id1.contains("3")) {
                esame_credit_linear.setVisibility(View.VISIBLE);
//                    } else if (data_id1.contains("4")) {
                etails_id_linear.setVisibility(View.VISIBLE);
//                    } else if (data_id1.contains("5")) {
                details_other_linear.setVisibility(View.VISIBLE);
//                    }
//        }
        break;
        case "SesameCredit":
        JSONObject object = new JSONObject(result);
        JSONObject jsonObject = new JSONObject(object.optString("data"));
        String params = "";
        String sign = "";
        if (jsonObject.has("params")) {
            params = jsonObject.optString("params");
        }
        if (jsonObject.has("sign")) {
            sign = jsonObject.optString("sign");
        }
        creditApp.authenticate(this, "1002755", "text", params, sign, null, this);
        break;
    }

}

    private String getAlgorithm(double loan, double day_month, double profit) {
        int run = (int) (loan / day_month + loan * (profit / 1000));
        return run + "";
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

    /**
     * 动态金额显示
     */
    private TextWatcher textWatcher(final int i) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (i) {
                    case 1:
                        if (!StringUtil.isNullOrEmpty(s.toString())) {
                            if (Double.parseDouble(s.toString()) > loanMax || Double.parseDouble(s.toString()) < loanMin) {
                                ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的金额已经超出范围");
                                loan_details_editText_range.setText("" + (int) loanMax);
                            } else {
                                if (anInt != 0) {
                                    double dou = Double.parseDouble(loan_details_editText_day.getText().toString());
                                    loan_details_repayment.setText(getAlgorithm(Double.parseDouble(s.toString()), dou, profit));
                                }
                            }
                        } else {
                            loan_details_editText_range.setText("0");
                        }
                        break;
                    case 2:
                        if (!StringUtil.isNullOrEmpty(s.toString())) {
                            if (Double.parseDouble(s.toString()) > day_monthMax || Double.parseDouble(s.toString()) < day_monthMin) {
                                if (loanDatailsData != null && !StringUtil.isNullOrEmpty(loanDatailsData.getFv_unit()) &&
                                        loanDatailsData.getFv_unit().equals("1")) {
                                    ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的月份已经超出范围");
                                    loan_details_editText_day.setText("" + (int) day_monthMax);
                                } else {
                                    ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的天数已经超出范围");
                                    loan_details_editText_day.setText("" + (int) day_monthMax);
                                }
                            } else {
                                if (anInt != 0) {
                                    double dou = Double.parseDouble(loan_details_editText_range.getText().toString());
                                    loan_details_repayment.setText(getAlgorithm(dou, Double.parseDouble(s.toString()), profit));
                                }
                            }
                        } else {
                            loan_details_editText_day.setText("0");
                        }
                        break;
                }
            }
        };
        return textWatcher;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("", "====" + requestCode + "======" + resultCode + "===" + data);
    }

    @Override
    public void onComplete(Bundle bundle) {
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            Log.d("", key + " = " + bundle.getString(key));
        }
    }

    @Override
    public void onError(Bundle bundle) {
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            Log.d("", key + " = " + bundle.getString(key));
        }

    }

    @Override
    public void onCancel() {
        Log.e("", "");
    }
}