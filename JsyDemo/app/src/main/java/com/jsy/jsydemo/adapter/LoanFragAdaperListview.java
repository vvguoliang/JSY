package com.jsy.jsydemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.LoanDatailsData;
import com.jsy.jsydemo.EntityClass.ProductSuList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LoanDetailsActivity;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.MyGridView;
import com.jsy.jsydemo.webview.LoanWebViewActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/26.
 * <p>
 * 设置listview
 */

public class LoanFragAdaperListview extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;

    private Intent intent = null;

    private List<Map<String, String>> list;

    public LoanFragAdaperListview(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from( context );
    }

    public LoanFragAdaperListview(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get( position );
        } else {
            return position;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate( R.layout.loan_frag_adapter_item, null );
            viewHolder.loan_fra_adapter_item_imag = convertView.findViewById( R.id.loan_fra_adapter_item_imag );
            viewHolder.loan_fra_adapter_item_text = convertView.findViewById( R.id.loan_fra_adapter_item_text );
            convertView.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list != null && list.size() > 0) {
            Glide.with( context )
                    .load( list.get( position ).get( "cat_icon" ) )
                    .listener( new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            viewHolder.loan_fra_adapter_item_imag.setImageResource( R.mipmap.ic_path_in_load );
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    } )
                    .into( viewHolder.loan_fra_adapter_item_imag );
            String cat_name = list.get(position).get("cat_name");
            if (cat_name.equals("学生贷")){
                cat_name = "分期贷";
            }
            viewHolder.loan_fra_adapter_item_text.setText(cat_name);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView loan_fra_adapter_item_imag;
        TextView loan_fra_adapter_item_text;
    }
}
