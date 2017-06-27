package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.BannerLoopAdapter;
import com.jsy.jsydemo.adapter.CardRecordAdapter;
import com.jsy.jsydemo.view.Base1.Consumption;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.interfaces.Action;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.view.RefreshRecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 借款
 */
@SuppressWarnings("deprecation")
@SuppressLint({"ValidFragment", "InflateParams"})
public class LoanFragment extends BaseFragment {

    private Activity mActivity;

    public LoanFragment() {
        super();
    }

    public LoanFragment(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    private RefreshRecyclerView mRecyclerView;

    private CardRecordAdapter mAdapter;

    private Handler mHandler;
    private int page = 1;

    private LinearLayout loan_frame;

    private ViewPager loan_viewpage;

    //图片地址集合( 项目中一般是对于的HTTP地址 )
    List<Integer> mImageUrl = new ArrayList<>();
    //banner中图片的集合
    List<ImageView> mBannerImageViews = new ArrayList<>();
    //banner上点点的集合
    List<ImageView> mBannerDots = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fra_loan;
    }

    @Override
    protected void initView() {

        mHandler = new Handler();
        mAdapter = new CardRecordAdapter(mActivity);

        //添加Header
        View mHeader = LayoutInflater.from(mActivity).inflate(R.layout.fra_loan_top, null);
        mHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtil.Dispay(mActivity)[1] / 2 + 48));
        getHeader(mHeader);
        mAdapter.setHeader(mHeader);
        //添加footer
        final TextView footer = new TextView(mActivity);
        footer.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(mActivity, 48)));
        footer.setTextSize(16);
        footer.setGravity(Gravity.CENTER);
        footer.setText("-- Footer --");
        mAdapter.setFooter(footer);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.loan_recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
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
                getData(false);
                page++;
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
                    mAdapter.addAll(getVirtualData());
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                } else {
                    mAdapter.addAll(getVirtualData());
                    if (page >= 3) {
                        mRecyclerView.showNoMore();
                    }
                }
            }
        }, 1500);
    }

    public Consumption[] getVirtualData() {
        return new Consumption[]{
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼", "", "",
                        "", "", ""),
        };
    }

    private void getHeader(View mHeader) {
        loan_viewpage = (ViewPager) mHeader.findViewById(R.id.loan_viewpage);
        final ViewGroup.LayoutParams lp = loan_viewpage.getLayoutParams();
        lp.height = DisplayUtils.dip2px(mActivity, 150);
        loan_viewpage.setLayoutParams(lp);
        loan_viewpage.setOnPageChangeListener(new NavigationPageChangeListener());
        loan_frame = (LinearLayout) mHeader.findViewById(R.id.loan_frame);

        //随便加几个图片地址进入集合
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                mImageUrl.add(R.mipmap.ic_loan_brightness);
            } else {
                mImageUrl.add(R.mipmap.ic_loan_dark);
            }
        }


        //设置ViewPage的页面内容
        refreshBanner();
    }

    // -------------------------------------------------------------------------
    // Banner相关
    // -------------------------------------------------------------------------
    private void refreshBanner() {

        mBannerImageViews.clear();
        mBannerDots.clear();
        loan_frame.removeAllViews();
        for (final int url : mImageUrl) {
            ImageView iv = new ImageView(mActivity);

            iv.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            iv.setBackgroundResource(url);

            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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

        loan_viewpage.setAdapter(new BannerLoopAdapter(mBannerImageViews));


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

        //

        currentItem = Integer.MAX_VALUE / 2;
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
