package com.jsy.jsydemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsy.jsydemo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by vvguoliang on 2017/6/26.
 * <p>
 * 适配器  超市
 */
@SuppressLint("InflateParams")
public class LoansupemarketGridviewAdapter extends BaseAdapter {

    private List<Map<String, String>> list;

    private Context context;

    public LoansupemarketGridviewAdapter(Context mContext, List<Map<String, String>> list) {
        this.context = mContext;
        this.list = list;
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
            return list.get(position);
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
        if (list == null && list.size() <= 0) {
            return null;
        }
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_loan_supemarket_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.supemarket_image = (ImageView) convertView.findViewById(R.id.adapter_gtidview_supemarket_image);
            viewHolder.supemarket_text = (TextView) convertView.findViewById(R.id.adapter_gtidview_supemarket_text);
            viewHolder.supemarket_text_qinyin = (TextView) convertView.findViewById(R.id.adapter_gtidview_supemarket_text_qinyin);
            viewHolder.supemarket_text_textview = (TextView) convertView.findViewById(R.id.adapter_gtidview_supemarket_text_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).get("url"))
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.supemarket_image);
        viewHolder.supemarket_text.setText(list.get(position).get("title"));
        viewHolder.supemarket_text_qinyin.setText(list.get(position).get("pinyin"));
        viewHolder.supemarket_text_textview.setText(list.get(position).get("string"));
        return convertView;
    }

    class ViewHolder {
        ImageView supemarket_image;
        TextView supemarket_text;
        TextView supemarket_text_qinyin;
        TextView supemarket_text_textview;

    }
}
