package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.HomeLoanBannerList;
import com.jsy.jsydemo.EntityClass.QuickBank;
import com.jsy.jsydemo.EntityClass.QuickBankList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.BannerLoopAdapter;
import com.jsy.jsydemo.adapter.QuickCardAdapter;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.interfaces.Action;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.view.RefreshRecyclerView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/23.
 * 快速办卡
 */
@SuppressWarnings("deprecation")
@SuppressLint({"ValidFragment", "InflateParams"})
public class QuickCardFragment extends BaseFragment implements DataCallBack {
    private Activity mActivity;

    public QuickCardFragment() {
        super();
    }

    public QuickCardFragment(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    @Override
    protected int getLayout() {
        return R.layout.fra_loan;
    }

    private RefreshRecyclerView mRecyclerView;

    private QuickCardAdapter mAdapter;

    private Handler mHandler;
    private int page = 1;

    private LinearLayout loan_frame;

    private ViewPager loan_viewpage;

    private HomeLoanBannerList homeLoanBannerList;

    private QuickBankList quickBankList;

    private QuickBank[] VirtualData;

    //图片地址集合( 项目中一般是对于的HTTP地址 )
    List<Map<String, String>> mImageUrl = new ArrayList<>();
    //banner中图片的集合
    List<ImageView> mBannerImageViews = new ArrayList<>();
    //banner上点点的集合
    List<ImageView> mBannerDots = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        OkHttpManager.postAsync(HttpURL.getInstance().BANNER, "banner", null, this);
        getBank();
        mHandler = new Handler();
        mAdapter = new QuickCardAdapter(mActivity);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(mActivity.getString(R.string.name_quick_card));

        //添加Header
        View mHeader = LayoutInflater.from(mActivity).inflate(R.layout.fra_quickcardfragment, null);
        mHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                AppUtil.getInstance().Dispay(mActivity)[1] / 3 + DisplayUtils.dip2px(mActivity, 6)));
        getHeader(mHeader);
        mAdapter.setHeader(mHeader);
        //添加footer
        final TextView footer = new TextView(mActivity);
        footer.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(mActivity, 24)));
        footer.setTextSize(16);
        footer.setGravity(Gravity.CENTER);
        footer.setText("");
        mAdapter.setFooter(footer);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.loan_recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                mRecyclerView.showNoMore();
//                getData(false);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });
    }

    public void getData(final boolean isRefresh) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    page = 1;
                    mAdapter.clear();
                    mAdapter.addAll(VirtualData);
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1500);
    }

    private void getBank() {
        OkHttpManager.postAsync(HttpURL.getInstance().BANK, "bank", null, this);
    }

    private void getHeader(View mHeader) {
        loan_viewpage = (ViewPager) mHeader.findViewById(R.id.loan_viewpage);
        final ViewGroup.LayoutParams lp = loan_viewpage.getLayoutParams();
        lp.height = DisplayUtils.dip2px(mActivity, 150);
        loan_viewpage.setLayoutParams(lp);
        loan_viewpage.setOnPageChangeListener(new NavigationPageChangeListener());
        loan_frame = (LinearLayout) mHeader.findViewById(R.id.loan_frame);
    }

    // -------------------------------------------------------------------------
    // Banner相关
    // -------------------------------------------------------------------------
    private void refreshBanner() {
        mBannerImageViews.clear();
        mBannerDots.clear();
        loan_frame.removeAllViews();
        for (int i = 0; mImageUrl.size() > i; i++) {
            ImageView iv = new ImageView(mActivity);

            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mBannerImageViews.add(iv);
        }

        //循环添加 点
        for (int i = 0; i < mBannerImageViews.size(); i++) {
            ImageView view = new ImageView(mActivity);
            int w = DisplayUtils.dip2px(mActivity, 10);
            int margin = DisplayUtils.dip2px(mActivity, 8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, w);
            layoutParams.setMargins(margin, 0, 0, 0);
            view.setLayoutParams(layoutParams);
            view.setImageResource(R.mipmap.dot_grey);
            if (i == getCurrentItem()) {
                view.setImageResource(R.mipmap.dot_white);
            }
            view.setScaleType(ImageView.ScaleType.MATRIX);
            mBannerDots.add(view);
            loan_frame.addView(view);
        }
        // 如果这样设置会一页一页的滑动过去 直接就ANR了!!!
        //banner.setCurrentItem(Integer.MAX_VALUE/2);

        //通过反射修改内部索引位置
        Class bannerClass = loan_viewpage.getClass();
        try {
            Field field = bannerClass.getDeclaredField("mCurItem");
            try {
                field.setAccessible(true);
                field.setInt(loan_viewpage, Integer.MAX_VALUE / 2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        currentItem = Integer.MAX_VALUE / 2;
        loan_viewpage.setAdapter(new BannerLoopAdapter(mActivity, mBannerImageViews, mImageUrl));
        pollBanner();
    }

    private int getCurrentItem() {
        return currentItem % mBannerImageViews.size();
    }

    //-------------------------------------
    // banner 自动翻页的相关代码, 不需要删除即可
    //-------------------------------------
    private Timer bannerTimer;
    private TimerTask bannerTask;
    private int currentItem;

    private void pollBanner() {
        if (bannerTimer == null || bannerTask == null) {
            bannerTimer = new Timer();
            bannerTask = new TimerTask() {
                @Override
                public void run() {
                    currentItem = currentItem + 1;
                    bannerHandler.obtainMessage().sendToTarget();
                }

            };
            bannerTimer.schedule(bannerTask, 3000);// 3秒钟自动翻页一次
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler bannerHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            loan_viewpage.setCurrentItem(currentItem);
        }
    };

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "banner":
                Log.e("", "=====" + request);
                break;
            case "bank":
                Log.e("", "=====" + request);
                break;
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "banner":
                homeLoanBannerList = new HomeLoanBannerList();
                homeLoanBannerList = JsonData.getInstance().getJsonLaonHome(result);
                for (int i = 0; homeLoanBannerList.getLoanBanners().size() > i; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("path", HttpURL.getInstance().HTTP_URL_PATH + homeLoanBannerList.getLoanBanners().get(i).getImg().
                            replace("\\", ""));
                    map.put("url", homeLoanBannerList.getLoanBanners().get(i).getImg_url());
                    mImageUrl.add(map);
                }
                //设置ViewPage的页面内容
                refreshBanner();
                break;
            case "bank":
                quickBankList = JsonData.getInstance().getJsonQuickBank(result);
                if (quickBankList.getQuickBankList().size() == 0) {
                    mRecyclerView.showNoMore();
                } else {
                    VirtualData = new QuickBank[quickBankList.getQuickBankList().size()];
                    for (int i = 0; quickBankList.getQuickBankList().size() > i; i++) {
                        VirtualData[i] = new QuickBank(quickBankList.getQuickBankList().get(i).getId(),
                                quickBankList.getQuickBankList().get(i).getName(), quickBankList.getQuickBankList().get(i).getDescribe(),
                                quickBankList.getQuickBankList().get(i).getLink(), HttpURL.getInstance().HTTP_URL_PATH +
                                quickBankList.getQuickBankList().get(i).getIcon().replace("\\", ""),
                                quickBankList.getQuickBankList().get(i).getCreated_at(), quickBankList.getQuickBankList().get(i).getUpdated_at());

                    }
                    if (page != 1) {
                        mAdapter.addAll(VirtualData);
                    }
                }
                break;
        }
    }

    class NavigationPageChangeListener implements
            ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            position = position % mBannerImageViews.size();
            for (int i = 0; i < mBannerImageViews.size(); i++) {
                mBannerDots.get(i).setImageResource(R.mipmap.dot_grey);
                if (position == i) {
                    mBannerDots.get(position).setImageResource(R.mipmap.dot_white);
                }
            }
            //页面变动的时候重新设置定时器3秒钟后翻页  ( 防止手动滑动的时候定时器还在滑动 )
            if (bannerTimer != null && bannerTask != null) {
                bannerTask.cancel();
                bannerTask = new TimerTask() {
                    @Override
                    public void run() {
                        currentItem = currentItem + 1;
                        bannerHandler.obtainMessage().sendToTarget();
                    }
                };
                bannerTimer.schedule(bannerTask, 3000);
            }
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        if (bannerTimer != null) {
            bannerTimer.cancel();
            bannerTimer = null;
        }
        if (bannerTask != null) {
            bannerTask.cancel();
            bannerTask = null;
        }
    }
}
