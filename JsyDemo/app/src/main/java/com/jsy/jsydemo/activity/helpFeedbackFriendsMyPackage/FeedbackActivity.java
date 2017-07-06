package com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/28.
 * 反馈建议
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    private EditText information_corporate_editText;
    private ImageView feedback_image;
    private TextView feedback_path;
    private EditText feedback_path_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_feedback);
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.feedback_path_button:
                finish();
                break;
            case R.id.feedback_image:
                finish();
                break;
        }
    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_feedback));

        information_corporate_editText = (EditText) findViewById(R.id.information_corporate_editText);
        feedback_image = (ImageView) findViewById(R.id.feedback_image);
        feedback_path = (TextView) findViewById(R.id.feedback_path);
        feedback_path_editText = (EditText) findViewById(R.id.feedback_path_editText);
        findViewById(R.id.feedback_path_button).setOnClickListener(this);
        feedback_image.setOnClickListener(this);
    }

    @Override
    protected void initView() {

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
