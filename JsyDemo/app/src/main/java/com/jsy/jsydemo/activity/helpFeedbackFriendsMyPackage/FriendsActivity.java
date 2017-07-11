package com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.Share.ShareWxapTencent;
import com.jsy.jsydemo.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/28.
 * 邀请好友
 */

public class FriendsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView friends_image;
    private Button friends_button;
    private ShareWxapTencent shareWxapTencent;

    private String mQqTitle = "[沙小僧理财]送你600元启动资金,跟小沙一起取金吧!";

    private String mQqSummary = "预期15%年化收益，会理财更赚钱！沙小僧，靠谱儿！";

    private String mWxwebtitle = "【沙小僧理财】送你600元启动资金,跟小沙一起取金吧!";

    private String mWxwebdescription = "我在沙小僧理财，安全可靠福利又多，新手注册即送600元，最高15%年化收益，快来一起赚~";

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_friends);
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.friends_button:
                shareWxapTencent = new ShareWxapTencent(this,url,mQqTitle,mQqSummary,mWxwebtitle,mWxwebdescription);
                break;

        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_inviting_friends));

        friends_image = (ImageView) findViewById(R.id.friends_image);
        findViewById(R.id.friends_button).setOnClickListener(this);
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
