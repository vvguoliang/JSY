package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.BannerLoopAdapter;
import com.jsy.jsydemo.adapter.LoanSupAdaperListview;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.DisplayUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 贷款超市
 */
@SuppressWarnings("deprecation")
@SuppressLint({"ValidFragment", "InflateParams"})
public class LoanSupermarketFragment extends BaseFragment {

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
    List<Integer> mImageUrl = new ArrayList<>();
    //banner中图片的集合
    List<ImageView> mBannerImageViews = new ArrayList<>();
    //banner上点点的集合
    List<ImageView> mBannerDots = new ArrayList<>();

    private ListView loan_supermarket_listview;

    private Map<String, String> map;
    private Map<String, String> map1;
    private Map<String, String> map2;
    private Map<String, String> map3;

    private List<Map<String, String>> list;
    private List<Map<String, String>> list1;

    @Override
    protected int getLayout() {
        return R.layout.fra_loansupermarketfragment;
    }

    @Override
    protected void initView() {
        loan_supermarket_listview = (ListView) findViewById(R.id.loan_supermarket_listview);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(mActivity.getString(R.string.name_loan_supermarket));
        getHeader();
        list = new ArrayList<>();
        map = new HashMap<>();
        map.put("url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498482390224&di=ec18d1b711a39f6f422c8ee9cc68dcf0&imgtype=0&src=http%3A%2F%2Fpic36.photophoto.cn%2F20150723%2F0032017378658140_b.jpg");
        map.put("title", "心凉");
        map1 = new HashMap<>();
        map1.put("url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498482431469&di=2e27c8f55b5cf279e6db828998478e32&imgtype=0&src=http%3A%2F%2Fpic74.nipic.com%2Ffile%2F20150809%2F14041019_231544651000_2.jpg");
        map1.put("title", "肚兜");
        list.add(map);
        list.add(map1);

        list1 = new ArrayList<>();
        map = new HashMap<>();
        map.put("url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498482794682&di=606d91bd8d0ee32045d1abc4dad3e962&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F91%2F34%2F56U58PICTxh_1024.jpg");
        map.put("title", "一二三");
        map.put("pinyin", "yiersan");
        map.put("string", "有身份证可借5000元\n最快3分钟下款");

        map1 = new HashMap<>();
        map1.put("url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498482794682&di=606d91bd8d0ee32045d1abc4dad3e962&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F91%2F34%2F56U58PICTxh_1024.jpg");
        map1.put("title", "一二三");
        map1.put("pinyin", "yiersan");
        map1.put("string", "有身份证可借5000元\n最快3分钟下款");

        map2 = new HashMap<>();
        map2.put("url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498482794682&di=606d91bd8d0ee32045d1abc4dad3e962&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F91%2F34%2F56U58PICTxh_1024.jpg");
        map2.put("title", "一二三");
        map2.put("pinyin", "yiersan");
        map2.put("string", "有身份证可借5000元\n最快3分钟下款");

        map3 = new HashMap<>();
        map3.put("url", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498482794682&di=606d91bd8d0ee32045d1abc4dad3e962&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F91%2F34%2F56U58PICTxh_1024.jpg");
        map3.put("title", "一二三");
        map3.put("pinyin", "yiersan");
        map3.put("string", "有身份证可借5000元\n最快3分钟下款");


        list1.add(map);
        list1.add(map1);
        list1.add(map2);
        list1.add(map3);

        loan_supermarket_listview.setAdapter(new LoanSupAdaperListview(mActivity, list, list1));
    }


    private void getHeader() {
        loan_viewpage = (ViewPager) findViewById(R.id.loan_viewpage);
        loan_viewpage.setOnPageChangeListener(new NavigationPageChangeListener());
        loan_frame = (LinearLayout) findViewById(R.id.loan_frame);

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
}

