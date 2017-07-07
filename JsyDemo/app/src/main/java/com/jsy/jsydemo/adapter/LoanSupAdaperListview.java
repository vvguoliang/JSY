package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.ProductSuList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.jsy.jsydemo.view.MyGridView;

/**
 * Created by vvguoliang on 2017/6/26.
 * <p>
 * 设置listview
 */

public class LoanSupAdaperListview extends BaseAdapter {

    private ProductSuList productSuList;
    private LayoutInflater mInflater;

    private Context context;

    public LoanSupAdaperListview(Context context, ProductSuList productSuList) {
        this.context = context;
        this.productSuList = productSuList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productSuList.getProduct().size();
    }

    @Override
    public Object getItem(int position) {
        return productSuList.getProduct().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.view_loan_bvh, null);

            viewHolder.loan_praise = (ImageView) convertView.findViewById(R.id.loan_praise);
            viewHolder.loan_recommend = (TextView) convertView.findViewById(R.id.loan_recommend);
            viewHolder.loan_gridView = (MyGridView) convertView.findViewById(R.id.loan_gridView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Glide.with(context)
//                .load(list.get(position).get("url"))
//                .fitCenter()
//                .placeholder(R.mipmap.ic_launcher)
//                .crossFade()
//                .into(viewHolder.loan_praise);
//        viewHolder.loan_praise.setTag(R.id.loan_praise, position);
        viewHolder.loan_recommend.setText(productSuList.getProduct().get(position));
        if (position == 0) {
            viewHolder.loan_gridView.setAdapter(new LoansupemarketGridviewAdapter(context, productSuList.getProductSuList()));
        } else if (position == 1) {
            viewHolder.loan_gridView.setAdapter(new LoansupemarketGridviewAdapter(context, productSuList.getProductSus()));
        }
        viewHolder.loan_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, LoanWebViewActivity.class);
                intent.putExtra("url", productSuList.getProductSuList().get(position).getPro_link());
                context.startActivity(intent);
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
