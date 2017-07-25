package com.jsy.jsydemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.ProductSu;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by vvguoliang on 2017/6/26.
 * <p>
 * 适配器  超市  和 贷款详情中的适配
 */
@SuppressLint("InflateParams")
public class LoansupemarketGridviewAdapter extends BaseAdapter {

    private Context context;

    private List<ProductSu> productSuList;

    private List<String> mapList;

    public LoansupemarketGridviewAdapter(Context mContext, List<ProductSu> productSuList) {
        this.context = mContext;
        this.productSuList = productSuList;
    }

    public LoansupemarketGridviewAdapter(Context context) {
        this.context = context;
    }

    public void GridviewAdapter(List<String> maps) {
        this.mapList = maps;
    }

    @Override
    public int getCount() {
        if (productSuList != null && productSuList.size() > 0) {
            return productSuList.size();
        } else if (mapList != null && mapList.size() > 0) {
            return mapList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (productSuList != null && productSuList.size() > 0) {
            return productSuList.get(position);
        } else if (mapList != null && mapList.size() > 0) {
            return mapList.get(position);
        } else {
            return position;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_loan_supemarket_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.supemarket_image = (ImageView) convertView.findViewById(R.id.adapter_gtidview_supemarket_image);
            viewHolder.supemarket_text = (TextView) convertView.findViewById(R.id.adapter_gtidview_supemarket_text);
            viewHolder.supemarket_text_qinyin = (TextView) convertView.findViewById(R.id.adapter_gtidview_supemarket_text_qinyin);
            viewHolder.supemarket_text_textview = (TextView) convertView.findViewById(R.id.adapter_gtidview_supemarket_text_textview);
            viewHolder.adapter_gtidview_supemarket_linear = (LinearLayout) convertView.findViewById(R.id.adapter_gtidview_supemarket_linear);
            viewHolder.adapter_gtidview_loan_linear = (LinearLayout) convertView.findViewById(R.id.adapter_gtidview_loan_linear);
            viewHolder.adapter_gtidview_loan_text = (TextView) convertView.findViewById(R.id.adapter_gtidview_loan_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (productSuList != null && productSuList.size() > 0) {
            viewHolder.adapter_gtidview_supemarket_linear.setVisibility(View.VISIBLE);
            viewHolder.adapter_gtidview_loan_linear.setVisibility(View.GONE);
            Glide.with(context)
                    .load(HttpURL.getInstance().HTTP_URL_PATH + productSuList.get(position).getImg())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            viewHolder.supemarket_image.setImageResource(R.mipmap.ic_launcher);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(viewHolder.supemarket_image);
            viewHolder.supemarket_text.setText(productSuList.get(position).getPro_name());
            viewHolder.supemarket_text_qinyin.setText(productSuList.get(position).getPro_name());
            viewHolder.supemarket_text_textview.setText(productSuList.get(position).getPro_describe());
        } else if (mapList != null && mapList.size() > 0) {
            viewHolder.adapter_gtidview_supemarket_linear.setVisibility(View.GONE);
            viewHolder.adapter_gtidview_loan_linear.setVisibility(View.VISIBLE);
            viewHolder.adapter_gtidview_loan_text.setText(mapList.get(position));
        }
        return convertView;
    }

    class ViewHolder {
        ImageView supemarket_image;
        TextView supemarket_text;
        TextView supemarket_text_qinyin;
        TextView supemarket_text_textview;
        LinearLayout adapter_gtidview_supemarket_linear;
        LinearLayout adapter_gtidview_loan_linear;
        TextView adapter_gtidview_loan_text;
    }
}
