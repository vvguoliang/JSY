package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.SetUp.SetUPActivity;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.HelpCenterActivity;
import com.jsy.jsydemo.activity.personaldata.PersonalDataActivity;
import com.jsy.jsydemo.base.BaseFragment;

/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 个人中心
 */
@SuppressWarnings("deprecation")
@SuppressLint({"ValidFragment", "InflateParams"})
public class PersonalCenterFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;

    public PersonalCenterFragment() {
        super();
    }

    public PersonalCenterFragment(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    @Override
    protected int getLayout() {
        return R.layout.fra_personalcenterfragment;
    }

    private ImageView personal_camera;
    private TextView personal_logo, personal_numder;

    private Intent intent;

    @Override
    protected void initView() {
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(mActivity.getString(R.string.name_personal_center));

        personal_camera = (ImageView) findViewById(R.id.personal_camera);
        personal_camera.setOnClickListener(this);

        personal_logo = (TextView) findViewById(R.id.personal_logo);
        personal_logo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        personal_logo.getPaint().setAntiAlias(true);//抗锯齿
        personal_logo.setOnClickListener(this);

        findViewById(R.id.personal_loan_my).setOnClickListener(this);
        findViewById(R.id.personal_loan_my_package).setOnClickListener(this);
        personal_numder = (TextView) findViewById(R.id.personal_numder);

        findViewById(R.id.personal_loan_data).setOnClickListener(this);
        findViewById(R.id.personal_loan_inviting_friends).setOnClickListener(this);
        findViewById(R.id.personal_loan_help_center).setOnClickListener(this);
        findViewById(R.id.personal_loan_feedback).setOnClickListener(this);
        findViewById(R.id.personal_loan_setup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_loan_data:// 个人资料
                mActivity.startActivity(new Intent(mActivity, PersonalDataActivity.class));
                break;
            case R.id.personal_loan_inviting_friends://邀请好哟
                break;
            case R.id.personal_loan_help_center://帮助中心
                startActivity(new Intent(mActivity, HelpCenterActivity.class));
                break;
            case R.id.personal_loan_feedback://意见反馈
                break;
            case R.id.personal_loan_setup://设置
                startActivity(new Intent(mActivity, SetUPActivity.class));
                break;
            case R.id.personal_loan_my://我的借款
                break;
            case R.id.personal_loan_my_package://我的卡包
                break;
            case R.id.personal_logo://登录
                break;
            case R.id.personal_camera://照片
                break;
        }
    }
}
