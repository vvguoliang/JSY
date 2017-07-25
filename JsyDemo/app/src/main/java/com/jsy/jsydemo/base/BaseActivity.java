package com.jsy.jsydemo.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
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
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.NetWorkUtils;
import com.jsy.jsydemo.utils.ToatUtils;

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

    protected void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (ImmersiveUtils.BuildVERSION()) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

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

    @Override
    protected void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String _pkgName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        if (!_pkgName.equals(getPackageName())) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
