package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.MyGridView;

import java.util.List;
import java.util.Map;

/**
 * Created by vvguoliang on 2017/6/26.
 * <p>
 * 设置listview
 */

public class LoanSupAdaperListview extends BaseAdapter {


    private List<Map<String, String>> list;
    private List<Map<String, String>> listdata;
    private LayoutInflater mInflater;

    private Context context;

    public LoanSupAdaperListview(Context context, List<Map<String, String>> list, List<Map<String, String>> listdata) {
        this.list = list;
        this.context = context;
        this.listdata = listdata;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.view_loan_bvh, null);

            viewHolder.loan_praise = (ImageView) convertView.findViewById(R.id.loan_praise);
            viewHolder.loan_recommend = (TextView) convertView.findViewById(R.id.loan_recommend);
            viewHolder.loan_gridView = (MyGridView) convertView.findViewById(R.id.loan_gridView);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).get("url"))
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.loan_praise);
        viewHolder.loan_praise.setTag(R.id.loan_praise, position);
        viewHolder.loan_recommend.setText(list.get(position).get("title"));

        viewHolder.loan_gridView.setAdapter(new LoansupemarketGridviewAdapter(context, listdata));
        viewHolder.loan_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToatUtils.showShort1(context, "当年度" + position);
            }
        });


        return convertView;
    }

    class ViewHolder {
        ImageView loan_praise;
        TextView loan_recommend;
        MyGridView loan_gridView;

    }
}
