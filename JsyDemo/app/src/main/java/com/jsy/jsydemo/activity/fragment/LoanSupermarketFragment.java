package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.HomeLoanBannerList;
import com.jsy.jsydemo.EntityClass.ProductSuList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.BannerLoopAdapter;
import com.jsy.jsydemo.adapter.LoanSupAdaperListview;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.umeng.analytics.MobclickAgent;

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
 * <p>
 * 贷款超市
 */
@SuppressWarnings("deprecation")
@SuppressLint({"ValidFragment", "InflateParams"})
public class LoanSupermarketFragment extends BaseFragment implements DataCallBack {

    private Activity mActivity;

    public LoanSupermarketFragment() {
        super();
    }

    public LoanSupermarketFragment(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    private LinearLayout loan_frame;

    private ViewPager loan_viewpage;

    //图片地址集合( 项目中一般是对于的HTTP地址 )
    List<Map<String, String>> mImageUrl = new ArrayList<>();
    //banner中图片的集合
    List<ImageView> mBannerImageViews = new ArrayList<>();
    //banner上点点的集合
    List<ImageView> mBannerDots = new ArrayList<>();

    private ListView loan_supermarket_listview;

    private HomeLoanBannerList homeLoanBannerList;

    private ProductSuList productSuList;

    @Override
    protected int getLayout() {
        return R.layout.fra_loansupermarketfragment;
    }

    @Override
    protected void initView() {
        getHttp();
        getHeader();
    }

    private void getHttp() {
        OkHttpManager.postAsync(HttpURL.getInstance().BANNER, "banner", null, this);
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT, "product", null, this);
    }

    private void getHeader() {
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            LinearLayout tab_activity_lin = (LinearLayout) findViewById(R.id.tab_activity_lin);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_activity_lin.getLayoutParams();
            lp.height = DisplayUtils.px2dip(mActivity, 48 * 11);
        }
        loan_supermarket_listview = (ListView) findViewById(R.id.loan_supermarket_listview);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(mActivity.getString(R.string.name_loan_supermarket));
        loan_viewpage = (ViewPager) findViewById(R.id.loan_viewpage);
        loan_viewpage.setOnPageChangeListener(new NavigationPageChangeListener());
        loan_frame = (LinearLayout) findViewById(R.id.loan_frame);
    }

    // -------------------------------------------------------------------------
    // Banner相关
    // -------------------------------------------------------------------------
    private void refreshBanner() {
        mBannerImageViews.clear();
        mBannerDots.clear();
        loan_frame.removeAllViews();
        for (int i = 0; i < mImageUrl.size(); i++) {
            ImageView iv = new ImageView(mActivity);

            iv.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

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
                break;
            case "product":
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
            case "product":
                productSuList = new ProductSuList();
                productSuList = JsonData.getInstance().getJsonProduct(result);
                loan_supermarket_listview.setAdapter(new LoanSupAdaperListview(mActivity, productSuList));
                break;
        }
    }

    class NavigationPageChangeListener implements ViewPager.OnPageChangeListener {

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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LoanSupermarketFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LoanSupermarketFragment");
    }
}

