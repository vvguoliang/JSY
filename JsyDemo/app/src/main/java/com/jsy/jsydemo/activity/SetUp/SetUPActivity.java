package com.jsy.jsydemo.activity.SetUp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.view.PublicPhoneDialog;

/**
 * Created by vvguoliang on 2017/6/28.
 * <p>
 * 设置
 */

public class SetUPActivity extends BaseActivity implements View.OnClickListener {
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_up);
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.set_up_about:
                startActivity(new Intent(SetUPActivity.this, SetUpAboutActivity.class));
                break;
            case R.id.set_up_password:
                intent = new Intent(SetUPActivity.this, SetUpPasswordActivity.class);
                intent.putExtra("name", "2");
                startActivity(intent);
                break;
            case R.id.set_up_sign_out:
                getPhone("提示", "您是否确认安全退出?");
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_setup));

        findViewById(R.id.set_up_about).setOnClickListener(this);
        findViewById(R.id.set_up_password).setOnClickListener(this);
        findViewById(R.id.set_up_sign_out).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }

    private void getPhone(String title, String msg) {
        PublicPhoneDialog.Builder builder = new PublicPhoneDialog.Builder(this);
        builder.setTitle(title);
        builder.setTiltleMsg(msg);
        builder.setContentViewCancel("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setContentViewDetermine("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferencesUtils.logoutSuccess(SetUPActivity.this);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
