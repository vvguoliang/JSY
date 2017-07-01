package com.jsy.jsydemo.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.CommissioningActivity;
import com.jsy.jsydemo.activity.VectoringActivity;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.NetWorkUtils;
import com.jsy.jsydemo.utils.ToatUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 秦国良
 * @data: 2016年5月16日 下午6:46:18
 * @ModifiedPerson:
 * @ModifiedPersonData：2016年5月16日下午6:46:18 @ModifyRemarks：
 * @version: V1.1.0
 * @Copyright 沙小僧
 */
@SuppressLint("NewApi")
public abstract class BaseActivity extends FragmentActivity {//implements OnClickRefreshListener, OnPowerKeyListener {

    private Context mContext;

    private long time = -1;

    private Receiver receiver;

    private IntentFilter intentFilter;

    private final static int WAIT_EXIT_TIME = 60;

//    private PowerKeyObserver mPowerKeyObserver;

    private boolean isImmerStateBar = true;

    private String color = "#ffffff";

    private boolean flag = true;

    /**
     * 程序退出到后台的Key
     */
    public final static String APP_OUT = "sxs_app_out";

    public final static String APP_IS_OUT = "sxs_app_is_out";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        BaseActivityManager.getActivityManager().pushActivity(this);
        mContext = this;

    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 禁止到老的沉浸方式
     * 在setContentView之前调用
     */
    public void setImmerStateBar(boolean isImmerStateBar) {
        this.isImmerStateBar = isImmerStateBar;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getWindow().getDecorView().setBackgroundResource(R.color.white);
        stateBarTint(color, flag);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        stateBarTint(color, flag);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        stateBarTint(color, flag);
    }

//    @Override
//    public void onPowerKeyPressed() {
//        SharedPreferencesUtils.put(this, APP_OUT, System.currentTimeMillis());
//        SharedPreferencesUtils.put(this, APP_IS_OUT, true);
//    }
//
//    @Override
//    public void onPowerKeyPressedOn() {
//        if (isForeground(this, getClass().getName())) {
//            startGestureVerfy();
//        } else {
//        }
//    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 广播
     */
    private class Receiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
//                netWorkChange(intent.getIntExtra(NetWorkBroadcastReceiver.NET_CHANGE_STATE_INT,
//                        NetWorkBroadcastReceiver.NOT_NET_WORK));
            }
        }
    }

    protected void netWorkChange(int netWorkState) {
    }
//
//    /**
//     * 初始化title @Title: initHeadView @author: xusonghui @Description:
//     * View @throws
//     */
//    protected View initHeadView(int title) {
//        return initHeadView(getString(title));
//    }
//
//    private ImageView backView;
//
//    protected View initHeadView(String title) {
//        TextView mMyTabTextview = (TextView) findViewById(R.id.my_tab_textview);
//
//        if (mMyTabTextview == null)
//            mMyTabTextview = (TextView) findViewById(R.id.my_tab_textview);
//        mMyTabTextview.setText(title);
//        backView = (ImageView) findViewById(R.id.my_return_button);
//        if (backView == null)
//            backView = (ImageView) findViewById(R.id.my_return_button);
//        backView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                KeyBoardUtils.getInstance().closeKeybord(backView, BaseActivity.this);
//                finish();
//            }
//        });
//        backView.setVisibility(View.VISIBLE);
//        return backView;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mRemindDialog != null) {
//            mRemindDialog.dismiss();
//        }
//        if (mPowerKeyObserver != null) {
//            mPowerKeyObserver.stopListen();
//            mPowerKeyObserver = null;
//        }
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

//    Class[] classes = new Class[]{New_ProductdetailsActivity.class, Investmentdetailstwo.class,
//            WithdRawalsActivityTwo.class, RechargeMoneyActivity.class, MyredenvelopeActivity.class, RecordExitActivity.class, ExitListActivity.class};

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (System.currentTimeMillis() - time < 1000) {
            return;
        }
        time = System.currentTimeMillis();
        IsClass(intent);
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

//    private NotWorkDialog notWorkDialog;

//    protected void showNotWorkDialog() {
//        if (notWorkDialog == null) {
//            notWorkDialog = new NotWorkDialog(this);
//            notWorkDialog.setOnClickRefreshListener(this);
//        }
//        if (!isFinishing())
//            notWorkDialog.show();
//    }
//
//    protected void dissNotWorkDialog() {
//        if (notWorkDialog != null) {
//            notWorkDialog.dismiss();
//        }
//    }

//    /**
//     * 如果弹出无网络dialog，然后点击刷新按钮后回调该方法
//     */
//    @Override
//    public void onRefresh() {
//    }

    @Override
    protected void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String _pkgName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        if (!_pkgName.equals(getPackageName())) {
//            SharedPreferencesUtils.put(this, APP_OUT, System.currentTimeMillis());
//            SharedPreferencesUtils.put(this, "isRemind", "0");
//            SharedPreferencesUtils.put(this, APP_IS_OUT, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (intentFilter == null) {
//            intentFilter = new IntentFilter();
//            intentFilter.addAction(NetWorkBroadcastReceiver.NET_CHANGE_STATE_ACTION);
//            intentFilter.addAction("exitMsg");
//        }
//        if (receiver == null) {
//            receiver = new Receiver();
//            registerReceiver(receiver, intentFilter);
//        }
//        if (mPowerKeyObserver == null) {
//            mPowerKeyObserver = new PowerKeyObserver(this);
//            mPowerKeyObserver.setHomeKeyListener(this);
//            mPowerKeyObserver.startListen();
//        }
//        if (this.getClass().getName().equals(HomeLoginActivity.class.getName())) {
//            return;
//        }
        startGestureVerfy();
    }

    /**
     * 弹出手势密码 @Title: startGestureVerfy @Description:
     */
    private void startGestureVerfy() {
//        long lastTime = (Long) SharedPreferencesUtils.get(this, APP_OUT, 0l);
//        boolean isOut = (Boolean) SharedPreferencesUtils.get(this, APP_IS_OUT, false);
//        if (isOut) {// 一键退出到后台
//            SharedPreferencesUtils.put(this, APP_IS_OUT, false);
//            if ((System.currentTimeMillis() - lastTime) < WAIT_EXIT_TIME * 1000) {// 且退出时间小于1分钟，不弹出手势密码，清除退出后台的
//                return;
//            } else {
//                //不做处理
//            }
//        } else {
//            return;
//        }
//        UserInfo.getInstance().theNExtStep(this, handler);
//        if (GestureVerifyActivity.getPasd(this).equals("")// 如果没有手势密码，不跳转页面
//                || !GestureVerifyActivity.isOpen(this)// 手势密码关闭状态，不跳转页面
//                || SharedPreferencesUtils.get(this, "id", "").toString().equals("")// 没有登录，不跳转页面
//                || getClass().getName().equals(GestureVerifyActivity.class.getName())) {
//            // 符合if判断，直接返回false，不进行页面跳转
//            return;
//        }
//        if (!BaseActivityManager.getActivityManager().currentActivity().toString().contains("GestureVerifyActivity")) {
//            Intent intent1 = new Intent(this, GestureVerifyActivity.class);
//            intent1.putExtra(GestureVerifyActivity.USER_OPERATING, GestureVerifyActivity.USER_CHECK);
//            startActivityForResult(intent1, 10000);
//        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (10000 == arg0) {
            if (arg1 == RESULT_OK) {// 清除退出记录
//                SharedPreferencesUtils.put(this, APP_IS_OUT, false);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
//            SharedPreferencesUtils.put(this, APP_OUT, System.currentTimeMillis());
//            SharedPreferencesUtils.put(this, APP_IS_OUT, true);
        }
    }

    //退出运行到后台
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 绑定控件id
     */
    protected abstract void findViewById();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 通过类名启动Activity
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    private void remind(String msg, boolean isExit) {
        waitDismiss();
//        Getintent.getInstance().getIntent(this, StartpageActivity.class, 0, new String[]{"sxsmian"}, "11");
//        if ("0".equals(msg))
//            ToatUtils.showLong(mContext, "距离上次登录时间过长,请重新登录");
//        else
//            ToatUtils.showLong(mContext, msg);
    }

    //是否强制退出
    private int isShow = 0;

//    public boolean baseSuccess(HttpEntity httpEntity, boolean isExit) {
//        if (httpEntity != null) {
//            if ("104".equals(httpEntity.getSxsCode())) {
//                SharedPreferencesUtils.put(this, "isRemind", "1");
//                SharedPreferencesUtils.logoutSuccess(getBaseContext());
//                remind(httpEntity.getSxsMsg(), isExit);
//                return true;
//            } else if ("999".equals(httpEntity.getSxsCode())) {
//                if (isShow == 0) {
//                    String title = "";
//                    String content = "";
//                    if (!httpEntity.getSxsMsg().contains("title") && !httpEntity.getSxsMsg().contains("content")) {
//                        exitRoutine("hide", "", httpEntity.getSxsMsg());
//                        isShow++;
//                    } else {
//                        try {
//                            JSONObject objMsg = new JSONObject(httpEntity.getSxsMsg());
//                            if (objMsg.has("title")) {
//                                title = objMsg.optString("title");
//                            }
//                            if (objMsg.has("content")) {
//                                content = objMsg.optString("content");
//                            }
//                            exitRoutine("hide", title, content);
//                            isShow++;
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                return true;
//            }
//        }
//        return false;
//    }
//    private void exitRoutine(String flag, String title, String msg) {
//        if (StringUtil.isNullOrEmpty(msg))
//            msg = getString(R.string.sys_maintenance);
//        CustomSysSureDialog  builder = new CustomSysSureDialog.Builder(this);
//        builder.setClose(flag, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                dialog.dismiss();
//                BaseActivityManager.getActivityManager().finishAllActivity();
//                System.exit(0);
//            }
//        });
//        if (!StringUtil.isNullOrEmpty(title)) {
//            builder.setTitle(title);
//        } else {
//            builder.setTitle(getString(R.string.sys_tips));
//        }
//        builder.setMessage(msg);
//        builder.setPositiveButton(getString(R.string.sys_myknow),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        BaseActivityManager.getActivityManager().finishAllActivity();
//                        System.exit(0);
//                    }
//                });
//        Dialog dialog = builder.create();
//        dialog.setCancelable(false);
//        dialog.show();
//    }

    /**
     * 网络请求耗时操作，弹出等等框 @Title: loadDataWait @author: xusonghui @Description:
     */
    protected void loadDataWait() {
//        if (mSxsProgressBar == null)
//            mSxsProgressBar = new SXSProgressBar(this);
//        if (!mSxsProgressBar.isShowing())
//            mSxsProgressBar.show();
    }

    /**
     * 隐藏等等框 @Title: waitDismiss @author: xusonghui @Description:
     */
    protected void waitDismiss() {
//        if (mSxsProgressBar != null)
//            mSxsProgressBar.dismiss();
    }

    /**
     * 通过Action启动Activity
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    public boolean NetWorkshow(Intent intent) {
        if (intent.getComponent() != null) {
//            for (int i = 0; i < classes.length; i++) {
//                if (intent.getComponent().getClassName().contains(classes[i].getName())) {
//
//                    return true;
//                } else {
//
//                }
//            }
        }
        return false;
    }

    public void IsClass(Intent intent) {
        if (NetWorkshow(intent)) {
        } else {
            if (!NetWorkUtils.isNetworkConnected(this) && intent.getComponent() != null
                    && !intent.getComponent().getClassName().equals(VectoringActivity.class.getName())
                    && !intent.getComponent().getClassName().equals(CommissioningActivity.class.getName())) {
                ToatUtils.showShort1(this, getString(R.string.no_network_timed));
                return;
            }
        }
    }

    public void stateBarTint(String color, boolean flag) {
        ImmersiveUtils.stateBarTint(this, color, flag, this.isImmerStateBar);
    }

    //清除状态栏黑色字体,即状态栏显示白色字体,Activity使用
    public void statusBarDarkMode() {
        //沉浸式状态栏
        ImmersiveUtils.stateBarTint(this, "#00000000", true, false);
        //设置状态栏白色字体
        ImmersiveUtils.StatusBarDarkMode(this);
    }

    //清除状态栏黑色字体,即状态栏显示白色字体，Fragment使用
    public void statusFragmentBarDarkMode() {
        //沉浸式状态栏
        ImmersiveUtils.stateBarTint(this, "#00000000", true, false);
        //设置状态栏白色字体
        ImmersiveUtils.StatusFragmentBarDarkMode(this);
    }

    //设置状态栏黑色字体
    public void statusBarLightMode() {
        ImmersiveUtils.StatusBarLightMode(this);
    }

}
