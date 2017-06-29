package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.SetUp.SetUPActivity;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.FeedbackActivity;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.FriendsActivity;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.HelpCenterActivity;
import com.jsy.jsydemo.activity.personaldata.PersonalDataActivity;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.view.BottomDialog;
import com.jsy.jsydemo.view.PublicDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TextView personal_logo;

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
                showDialog();
//                mActivity.startActivity(new Intent(mActivity, LogoActivity.class));
                break;
            case R.id.personal_camera://照片
                getDialog();
                break;
        }
    }

    private void getDialog() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "adadad");
        map.put("boolean", "1");
        list.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "adadad");
        map1.put("boolean", "1");
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "adadad");
        map2.put("boolean", "1");
        list.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "adadad");
        map3.put("boolean", "1");
        list.add(map3);


        PublicDialog.Builder builder = new PublicDialog.Builder(mActivity);
        String stringa = SharedPreferencesUtils.get(mActivity, "111111", "").toString();
        List<Map<String, Object>> listViewEntities = SharedPreferencesUtils.getInfo(mActivity, stringa);
        if (null != listViewEntities && listViewEntities.size() > 0) {
            list.clear();
            list = listViewEntities;
        }
        builder.setItems(list, "111111", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    // 提示对话框方法
    private void showDialog() {
        final BottomDialog sxsDialog = new BottomDialog(mActivity, R.layout.buttom_dialog);
        sxsDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        sxsDialog.setWidthHeight(AppUtil.Dispay(mActivity)[0], 0);
        sxsDialog.getWindow().setGravity(Gravity.BOTTOM);
        sxsDialog.setOnClick(R.id.btn_take_photo, new View.OnClickListener() {//拍照
            @Override
            public void onClick(View v) {
                sxsDialog.dismiss();
            }
        });
        Button button = (Button) sxsDialog.findViewById(R.id.btn_pick_photo);//从相册中选择
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
