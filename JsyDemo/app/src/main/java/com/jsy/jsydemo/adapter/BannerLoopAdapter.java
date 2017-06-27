package com.jsy.jsydemo.adapter;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * vvguolaing 2017-6-24
 *  Viewpage 适配器
 */
@SuppressWarnings("deprecation")
public class BannerLoopAdapter extends PagerAdapter {

    List<ImageView> list = null;

    public BannerLoopAdapter(List<ImageView> _list) {
        list = _list;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        //    ((ViewPager) container).removeView((View)object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= list.size();
        if (position < 0) {
            position = list.size() + position;
        }
        ImageView view = list.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}


