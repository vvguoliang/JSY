package com.jsy.jsydemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/7/6.
 * 其他信息
 */

public class OtherInformationActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout information_mailbox_linear;
    private EditText information_mailbox_editText;
    private LinearLayout information_spouse_linear;
    private EditText information_spouse_editText;
    private LinearLayout information_live_linear;
    private EditText information_live_editText;
    private LinearLayout information_mode_linear;
    private TextView information_mode_text;
    private LinearLayout information_corporate_linear;
    private EditText information_corporate_editText;
    private LinearLayout information_corporate_address_linear;
    private EditText information_corporate_address_editText;
    private LinearLayout information_corporate_phone_linear;
    private EditText information_corporate_phone_editText;
    private Button information_please_button;
    private LinearLayout loan_personal_linear;
    private TextView other_relatives_name;
    private Button other_relatives_wathet;
    private TextView other_relatives_phone;
    private LinearLayout other_contacts_linear;
    private TextView other_contacts_name;
    private Button other_contacts_wathet;
    private TextView other_contacts_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_other_information);
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.information_mode_text:
                finish();
                break;
            case R.id.information_please_button:
                finish();
                break;
            case R.id.other_relatives_wathet:
                finish();
                break;
            case R.id.other_contacts_wathet:
                finish();
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_details_other));

        information_mailbox_linear = (LinearLayout) findViewById(R.id.information_mailbox_linear);
        information_spouse_linear = (LinearLayout) findViewById(R.id.information_spouse_linear);
        information_live_linear = (LinearLayout) findViewById(R.id.information_live_linear);
        information_mode_linear = (LinearLayout) findViewById(R.id.information_mode_linear);
        information_corporate_linear = (LinearLayout) findViewById(R.id.information_corporate_linear);
        information_corporate_address_linear = (LinearLayout) findViewById(R.id.information_corporate_address_linear);
        information_corporate_phone_linear = (LinearLayout) findViewById(R.id.information_corporate_phone_linear);
        loan_personal_linear = (LinearLayout) findViewById(R.id.loan_personal_linear);
        other_contacts_linear = (LinearLayout) findViewById(R.id.other_contacts_linear);

        information_mailbox_editText = (EditText) findViewById(R.id.information_mailbox_editText);
        information_spouse_editText = (EditText) findViewById(R.id.information_spouse_editText);
        information_live_editText = (EditText) findViewById(R.id.information_live_editText);
        information_corporate_editText = (EditText) findViewById(R.id.information_corporate_editText);
        information_corporate_address_editText = (EditText) findViewById(R.id.information_corporate_address_editText);
        information_corporate_phone_editText = (EditText) findViewById(R.id.information_corporate_phone_editText);

        information_mode_text = (TextView) findViewById(R.id.information_mode_text);
        other_relatives_name = (TextView) findViewById(R.id.other_relatives_name);
        other_relatives_phone = (TextView) findViewById(R.id.other_relatives_phone);
        other_contacts_name = (TextView) findViewById(R.id.other_contacts_name);
        other_contacts_phone = (TextView) findViewById(R.id.other_contacts_phone);

        information_please_button = (Button) findViewById(R.id.information_please_button);
        other_relatives_wathet = (Button) findViewById(R.id.other_relatives_wathet);
        other_contacts_wathet = (Button) findViewById(R.id.other_contacts_wathet);


    }

    @Override
    protected void initView() {
        information_mode_text.setOnClickListener(this);
        information_please_button.setOnClickListener(this);
        other_relatives_wathet.setOnClickListener(this);
        other_contacts_wathet.setOnClickListener(this);

    }
}
