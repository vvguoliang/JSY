package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.activity.SetUp.SetUPActivity;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.FeedbackActivity;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.FriendsActivity;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.HelpCenterActivity;
import com.jsy.jsydemo.activity.personaldata.PersonalDataActivity;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.view.BottomDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 个人中心
 */
@SuppressWarnings({"deprecation", "ConstantConditions", "ResultOfMethodCallIgnored"})
@SuppressLint({"ValidFragment", "InflateParams"})
public class PersonalCenterFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;

    public PersonalCenterFragment() {
        super();
    }

    public PersonalCenterFragment(Activity activity) {
        super(activity);
        if (activity == null) {
            this.mActivity = getActivity();
        } else {
            this.mActivity = activity;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fra_personalcenterfragment;
    }

    private ImageView personal_camera;
    private TextView personal_logo;

    private UserCenterRealize userCenterRealize = new UserCenterRealize();

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

//        findViewById(R.id.personal_loan_my).setOnClickListener(this);
//        findViewById(R.id.personal_loan_my_package).setOnClickListener(this);
//        personal_numder = (TextView) findViewById(R.id.personal_numder);

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
                mActivity.startActivity(new Intent(mActivity, FriendsActivity.class));
                break;
            case R.id.personal_loan_help_center://帮助中心
                mActivity.startActivity(new Intent(mActivity, HelpCenterActivity.class));
                break;
            case R.id.personal_loan_feedback://意见反馈
                mActivity.startActivity(new Intent(mActivity, FeedbackActivity.class));
                break;
            case R.id.personal_loan_setup://设置
                mActivity.startActivity(new Intent(mActivity, SetUPActivity.class));
                break;
//            case R.id.personal_loan_my://我的借款
//                break;
//            case R.id.personal_loan_my_package://我的卡包
//                break;
            case R.id.personal_logo://登录
                if (StringUtil.isNullOrEmpty(SharedPreferencesUtils.get(mActivity, "uid", "").toString())) {
                    mActivity.startActivity(new Intent(mActivity, LogoActivity.class));
                } else {
                    personal_logo.setClickable(false);
                }
                break;
            case R.id.personal_camera://照片
                showDialog(mActivity.getString(R.string.name_loan_personal_camera), mActivity.getString(R.string.name_loan_personal_album));
                break;
        }
    }

    // 提示对话框方法
    public void showDialog(String btn_take, String btn_pick) {
        final BottomDialog sxsDialog = new BottomDialog(mActivity, R.layout.buttom_dialog);
        sxsDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        sxsDialog.setWidthHeight(AppUtil.getInstance().Dispay(mActivity)[0], 0);
        sxsDialog.getWindow().setGravity(Gravity.BOTTOM);
        Button button1 = (Button) sxsDialog.findViewById(R.id.btn_pick_photo1);
        button1.setText(btn_take);
        Button button = (Button) sxsDialog.findViewById(R.id.btn_pick_photo2);
        button.setText(btn_pick);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCenterRealize.getFileByPhotograph(mActivity);//拍照外部调用
                sxsDialog.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {//有
            @Override
            public void onClick(View v) {
                userCenterRealize.getFileByPhotoAlbum(mActivity);//相册外部调用
                sxsDialog.dismiss();
            }
        });
        sxsDialog.setOnClick(R.id.btn_cancel, new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                sxsDialog.dismiss();
            }
        });
        if (!mActivity.isFinishing()) {
            sxsDialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtil.isNullOrEmpty(SharedPreferencesUtils.get(mActivity, "uid", "").toString())) {
            if (!StringUtil.isNullOrEmpty(SharedPreferencesUtils.get(mActivity, "username", "").toString())) {
                personal_logo.setText(SharedPreferencesUtils.get(mActivity, "username", "").toString());
            } else {
                personal_logo.setText(SharedPreferencesUtils.get(mActivity, "uid", "").toString());
            }
        } else {
            personal_logo.setText(mActivity.getString(R.string.name_loan_personal_logn));
        }
        MobclickAgent.onPageStart("PersonalCenterFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PersonalCenterFragment");
    }
}
