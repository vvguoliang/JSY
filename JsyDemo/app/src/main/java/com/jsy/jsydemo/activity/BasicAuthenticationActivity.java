package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.TimeUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/7/4.
 * 基本信息认证
 */

public class BasicAuthenticationActivity extends BaseActivity implements View.OnClickListener {

    private TextView loan_basic_please_in_text;
    private TextView loan_basic_number_text;
    private TextView loan_basic_application_text;
    private TextView loan_loan_application_text;
    private TextView loan_loan_highest_text;
    private TextView loan_education_level_text;
    private TextView loan_basic_security_text;
    private TextView loan_basic_cat_text;
    private TextView loan_basic_typ_text;

    private EditText loan__basic_please_in_editText;
    private EditText loan_details_basic_id_editText;
    private EditText loan_loan_highest_editText;
    private EditText loan_basic_content_editText;

    private ImageView loan_basic_image_image;

    private TextView loan_details_basic_loan_text;

    private Button loan_basic_button;

    private TextView loan_basic_application;
    private TextView loan_loan_application;
    private TextView loan_education_level;
    private TextView loan_basic_security;
    private TextView loan_basic_cat;
    private TextView loan_basic_typ;
    private TextView loan_basic_life;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_basic_authentication);
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_basic_application_linear:
            case R.id.loan_basic_application:
                break;
            case R.id.loan_loan_application_linear:
            case R.id.loan_loan_application:
                break;
            case R.id.loan_education_level_linear:
            case R.id.loan_education_level:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"硕士以上", "本科", "大专", "中专/高中及一下"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "education_level", strings, mHandler,
                            1);
                }
                break;
            case R.id.loan_basic_security_linear:
            case R.id.loan_basic_security:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"缴纳本地社保", "未缴纳社保"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_security", strings, mHandler,
                            2);
                }
                break;
            case R.id.loan_basic_cat_linear:
            case R.id.loan_basic_cat:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"无车", "本人名下有车,无贷款", "本人名下有车,有按揭贷款", "本人名下有车,但已被抵押", "其他(请备注)"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_cat", strings, mHandler,
                            3);
                }
                break;
            case R.id.loan_basic_typ_linear:
            case R.id.loan_basic_typ:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"企业主", "个体工商户", "上班人群", "学生", "无固定职业"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_typ", strings, mHandler,
                            4);
                }
                break;
            case R.id.loan_basic_life_linear:
            case R.id.loan_basic_life:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"0-5个月", "6-12个月", "1-3年", "3-7年", "7年以上"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_life", strings, mHandler,
                            5);
                }
                break;
            case R.id.loan_basic_image_image:
                break;
            case R.id.loan_basic_button:
                break;
            case R.id.loan_details_basic_loan_text:
                break;
            case R.id.title_complete:
                finish();
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
        title_view.setText(this.getString(R.string.name_loan_basic_information));

        loan_basic_please_in_text = (TextView) findViewById(R.id.loan_basic_please_in_text);
        loan_basic_number_text = (TextView) findViewById(R.id.loan_basic_number_text);
        loan_basic_application_text = (TextView) findViewById(R.id.loan_basic_application_text);
        loan_loan_application_text = (TextView) findViewById(R.id.loan_loan_application_text);
        loan_loan_highest_text = (TextView) findViewById(R.id.loan_loan_highest_text);
        loan_education_level_text = (TextView) findViewById(R.id.loan_education_level_text);
        loan_basic_security_text = (TextView) findViewById(R.id.loan_basic_security_text);
        loan_basic_cat_text = (TextView) findViewById(R.id.loan_basic_cat_text);
        loan_basic_typ_text = (TextView) findViewById(R.id.loan_basic_typ_text);

        findViewById(R.id.loan_basic_application_linear).setOnClickListener(this);
        findViewById(R.id.loan_loan_application_linear).setOnClickListener(this);
        findViewById(R.id.loan_education_level_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_security_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_cat_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_typ_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_life_linear).setOnClickListener(this);

        loan__basic_please_in_editText = (EditText) findViewById(R.id.loan__basic_please_in_editText);
        loan_details_basic_id_editText = (EditText) findViewById(R.id.loan_details_basic_id_editText);
        loan_loan_highest_editText = (EditText) findViewById(R.id.loan_loan_highest_editText);
        loan_basic_content_editText = (EditText) findViewById(R.id.loan_basic_content_editText);

        loan_basic_application = (TextView) findViewById(R.id.loan_basic_application);
        loan_loan_application = (TextView) findViewById(R.id.loan_loan_application);
        loan_education_level = (TextView) findViewById(R.id.loan_education_level);
        loan_basic_security = (TextView) findViewById(R.id.loan_basic_security);
        loan_basic_typ = (TextView) findViewById(R.id.loan_basic_typ);
        loan_basic_cat = (TextView) findViewById(R.id.loan_basic_cat);
        loan_basic_life = (TextView) findViewById(R.id.loan_basic_life);

        loan_details_basic_loan_text = (TextView) findViewById(R.id.loan_details_basic_loan_text);
        loan_basic_image_image = (ImageView) findViewById(R.id.loan_basic_image_image);
        loan_basic_button = (Button) findViewById(R.id.loan_basic_button);


    }

    @Override
    protected void initView() {
        loan_basic_button.setOnClickListener(this);
        loan_basic_image_image.setOnClickListener(this);
        loan_basic_application.setOnClickListener(this);
        loan_loan_application.setOnClickListener(this);
        loan_education_level.setOnClickListener(this);
        loan_basic_security.setOnClickListener(this);
        loan_basic_typ.setOnClickListener(this);
        loan_basic_cat.setOnClickListener(this);
        loan_basic_life.setOnClickListener(this);
        loan_details_basic_loan_text.setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    loan_education_level.setText(msg.obj.toString());
                    break;
                case 2:
                    loan_basic_security.setText(msg.obj.toString());
                    break;
                case 3:
                    loan_basic_cat.setText(msg.obj.toString());
                    break;
                case 4:
                    loan_basic_typ.setText(msg.obj.toString());
                    break;
                case 5:
                    loan_basic_life.setText(msg.obj.toString());
                    break;
            }
        }
    };

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
