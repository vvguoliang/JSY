package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.HomeLoanBannerList;
import com.jsy.jsydemo.EntityClass.ProductSuList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.BannerLoopAdapter;
import com.jsy.jsydemo.adapter.BannerLoopAdapter2;
import com.jsy.jsydemo.adapter.LoanSupAdaperListview;
import com.jsy.jsydemo.adapter.MyViewPagerAdapter;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        this.mActivity = (Activity) context;
    }

    private LinearLayout loan_frame;

    private ViewPager loan_viewpage;

    //图片地址集合( 项目中一般是对于的HTTP地址 )
    List<Map<String, String>> mImageUrl = null;
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
        getHeader();
        getHttp();
    }

    private void getHttp() {
        OkHttpManager.postAsync(HttpURL.getInstance().BANNER, "banner", null, this);
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT, "product", null, this);
    }

    private void getHeader() {
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
            final int finalI = i;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, LoanWebViewActivity.class);
                    intent.putExtra("url", mImageUrl.get(finalI).get("url"));
                    mActivity.startActivity(intent);
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
        loan_viewpage.setAdapter(new BannerLoopAdapter2(mActivity, mBannerImageViews, mImageUrl));
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
                    bannerHandler.obtainMessage().sendToTarget();
                }

            };
            bannerTimer.schedule(bannerTask, AppUtil.getInstance().TIME);// 3秒钟自动翻页一次
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler bannerHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            loan_viewpage.setCurrentItem(currentItem + 1);
        }
    };


    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "banner":
                ToatUtils.showShort1(mActivity, this.getString(R.string.network_timed));
                break;
            case "product":
                ToatUtils.showShort1(mActivity, this.getString(R.string.network_timed));
                break;
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "banner":
                homeLoanBannerList = new HomeLoanBannerList();
                homeLoanBannerList = JsonData.getInstance().getJsonLaonHome(result);
                mImageUrl = new ArrayList<>();
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
                        bannerHandler.obtainMessage().sendToTarget();
                    }
                };
                bannerTimer.schedule(bannerTask, AppUtil.getInstance().TIME);
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
        pollBanner();
        MobclickAgent.onPageStart("LoanSupermarketFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LoanSupermarketFragment");
    }
}

