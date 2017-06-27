package com.jsy.jsydemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.fragment.LoanFragment;
import com.jsy.jsydemo.activity.fragment.LoanSupermarketFragment;
import com.jsy.jsydemo.activity.fragment.PersonalCenterFragment;
import com.jsy.jsydemo.activity.fragment.QuickCardFragment;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.base.BaseActivityManager;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.MainActivityView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 2017-6-23
 * 基础主类
 */
public class MainActivity extends BaseActivity implements MainActivityView.OnItemClickListener {

    private Activity activity;

    private MainActivityView mainActivityView;

    private int[] titles = {R.string.name_loan, R.string.name_loan_supermarket, R.string.name_quick_card, R.string.name_personal_center};
    private int[] selectedImage = {R.mipmap.ic_loan_dark, R.mipmap.ic_loansupermarket_dark, R.mipmap.ic_quickcrd_dark, R.mipmap.ic_personalcenter_dark};
    private int[] unSelectedImage = {R.mipmap.ic_loan_brightness, R.mipmap.ic_loansupermarket_brightness, R.mipmap.ic_quickcard_brightness,
            R.mipmap.ic_personalcenter_brightness};

    private int mHeight;
    private boolean isGetHeight = true;

    private int mSelectPosition = 0;

    private List<Fragment> listfragment = new ArrayList<>();

    private List<Fragment> listnewftagment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setImmerStateBar(false);
        this.activity = this;
        setContentView(R.layout.activity_main);
        findViewById();
    }

    @Override
    protected void findViewById() {
        int position = getIntent().getIntExtra("selectPosition", -1);
        if (position == -1) {
            mSelectPosition = 0;
        } else {
            mSelectPosition = position;
        }
        initView();

        if (position == -1) {
        } else {
//            if (position == 2) {//修改密码后重新登录
//                judgeIsNotLogin();// 先判断用户是否登录
//            } else if (position == 1) {
            showFragment(position);
//            }
        }
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            setTranslucentStatus(true);
        }

        if (mSelectPosition % 2 == 0) {//初始化设置状态栏颜色
            stateBarTint("#ffffff", false);
            SharedPreferencesUtils.put(activity, "STATUS_FONT_COLOR", "WHITE");
        } else {
            //fragment产品页和发现页状态栏字体颜色为黑色
            SharedPreferencesUtils.put(activity, "STATUS_FONT_COLOR", "BLACK");
            stateBarTint("#000000", true);
        }
    }

    @Override
    protected void initView() {
        for (int i = 0; i <= 3; i++) {
            listfragment.add(null);
        }
        listnewftagment.add(new LoanFragment(MainActivity.this));
        listnewftagment.add(new LoanSupermarketFragment(MainActivity.this));
        listnewftagment.add(new QuickCardFragment(MainActivity.this));
        listnewftagment.add(new PersonalCenterFragment(MainActivity.this));

        // 获取屏幕宽度
        Display dm = getWindowManager().getDefaultDisplay();
        final int screenWith = dm.getWidth();
        mainActivityView = (MainActivityView) findViewById(R.id.act_main_tab);
        // 初始化获取底部导航自身高度
        final ViewTreeObserver vt = mainActivityView.getViewTreeObserver();
        vt.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isGetHeight) {
                    mHeight = mainActivityView.getHeight();
                    mainActivityView.setLayout(titles, selectedImage, unSelectedImage, screenWith, mHeight, MainActivity.this);
                    mainActivityView.setColorLing(mSelectPosition);
                    mainActivityView.setOnItemClickListener(MainActivity.this);
                    isGetHeight = false;
                }
                return true;
            }
        });
        showFragment(mSelectPosition);
    }

    @Override
    public void onItemClick(int position) {
        showFragment(position);
    }

    /**
     * 动态添加和显示fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        setImmerStateBar(false);
        for (int i = 0; i < listfragment.size(); i++) {
            if (position == i) {
                if (listfragment.get(i) == null) {
                    listfragment.remove(i);
                    listfragment.add(i, listnewftagment.get(i));
                    if (!listnewftagment.get(i).isAdded()) {
                        transaction.add(R.id.cat_main_fragment_content, listfragment.get(i));
                    } else {
                        transaction.show(listfragment.get(i));
                    }
                } else {
                    transaction.show(listfragment.get(i));
                }
                if (position % 2 == 0) {
                    //fragment首页和我的页状态栏字体颜色为白色
                    SharedPreferencesUtils.put(activity, "STATUS_FONT_COLOR", "WHITE");
                    //stateBarTint("#ffffff", false);
                    //清除状态栏黑色字体
                    statusFragmentBarDarkMode();
                } else {
                    //fragment产品页和发现页状态栏字体颜色为黑色
                    SharedPreferencesUtils.put(activity, "STATUS_FONT_COLOR", "BLACK");
                    stateBarTint("#000000", true);
                }
            }
        }
        transaction.commitAllowingStateLoss();
    }


    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        for (int i = 0; i < listfragment.size(); i++) {
            if (listfragment.get(i) != null) {
                transaction.hide(listfragment.get(i));
            }
        }
    }

    /**
     * 连续按两次返回键就退出
     */
    private int keyBackClickCount = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    ToatUtils.showShort1(this, "再按一次退出");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    BaseActivityManager.getActivityManager().finishAllActivity();
                    finish();
                    break;
                default:
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
