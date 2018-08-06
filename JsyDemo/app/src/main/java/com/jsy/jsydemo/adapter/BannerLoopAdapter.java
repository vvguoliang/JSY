package com.jsy.jsydemo.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.webview.LoanWebViewActivity;

import java.util.List;
import java.util.Map;

/**
 * vvguolaing 2017-6-24
 * Viewpage 适配器
 */
@SuppressWarnings("deprecation")
public class BannerLoopAdapter extends PagerAdapter implements View.OnClickListener {

    private List<ImageView> list = null;

    private Context context;

    private List<Map<String, String>> mapList;

    private int position1 = 0;

    public BannerLoopAdapter(Context context, List<ImageView> _list, List<Map<String, String>> mapList) {
        this.list = _list;
        this.context = context;
        this.mapList = mapList;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
//            ((ViewPager) container).removeView((View)object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position = position % list.size();
        this.position1 = position;
        if (position1 < 0) {
            position1 = list.size() + position;
        }
        final ImageView view = list.get(position1);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }

        Glide.with(context)
                .load(mapList.get(position1).get("path"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        view.setImageResource(R.mipmap.ic_path_in_load);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        return false;
                    }
                })
                .into(view);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
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

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(context, LoanWebViewActivity.class);
//        intent.putExtra("url", mapList.get(position1).get("url"));
//        context.startActivity(intent);
    }
}


