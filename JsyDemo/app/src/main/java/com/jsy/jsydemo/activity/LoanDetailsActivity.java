package com.jsy.jsydemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/4.
 * 贷款详情
 */

public class LoanDetailsActivity extends BaseActivity implements View.OnClickListener ,DataCallBack{

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

    private Button loan_details_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loandetails);
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                break;
            case R.id.loan_details_phone_operator:
                break;
            case R.id.loan_details_basic_information:
                break;
            case R.id.loan_details_id:
                break;
            case R.id.loan_details_other:
                break;
            case R.id.loan_details_button:
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

        loan_details_button = (Button) findViewById(R.id.loan_details_button);
        loan_details_button.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", "");
        map.put("id", "");
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT_DETAIL,"product_detail", map, this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {

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
}
