package com.jsy.jsydemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by jsy_zj on 2017/10/26.
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViewList;

    public MyViewPagerAdapter(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    //表示viewpager共存放了多少个页面
    @Override
    public int getCount() {//表示viewpager共存放了多少个页面
        return Integer.MAX_VALUE;//我们设置viewpager中有Integer.MAX_VALUE个页面
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * position % imageList.size() 而不是position，是为了防止角标越界异常
     * 因为我们设置了viewpager子页面的数量有Integer.MAX_VALUE，而imageList的数量只是5。
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViewList.get(position % imageViewList.size()));
        return imageViewList.get(position % imageViewList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
