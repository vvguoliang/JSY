package com.jsy.jsydemo.activity.SetUp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/28.
 * <p>
 * 修改密码
 */

public class SetUpPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText password_phone;

    private EditText password_edittext_code;

    private Button password_button_code;

    private EditText password_edittext_pass;

    private EditText password_edittext_pass_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_up_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.password_button_code:
                break;
            case R.id.password_button_confirm:
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_set_up_password));

        password_phone = (EditText) findViewById(R.id.password_phone);
        password_edittext_code = (EditText) findViewById(R.id.password_edittext_code);
        password_edittext_pass = (EditText) findViewById(R.id.password_edittext_pass);
        password_edittext_pass_confirm = (EditText) findViewById(R.id.password_edittext_pass_confirm);

        password_button_code = (Button) findViewById(R.id.password_button_code);
        password_button_code.setOnClickListener(this);
        findViewById(R.id.password_button_confirm).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }
}
