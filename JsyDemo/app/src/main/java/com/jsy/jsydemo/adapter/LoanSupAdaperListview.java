package com.jsy.jsydemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.jsy.jsydemo.view.MyGridView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/26.
 * <p>
 * 设置listview
 */

public class LoanSupAdaperListview extends BaseAdapter implements DataCallBack {

    private ProductSuList productSuList;
    private LayoutInflater mInflater;

    private Context context;

    private Intent intent = null;

    private LoanDatailsData loanDatailsData;

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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.view_loan_bvh, null);
            viewHolder.loan_praise = convertView.findViewById(R.id.loan_praise);
            viewHolder.loan_recommend = convertView.findViewById(R.id.loan_recommend);
            viewHolder.loan_gridView = convertView.findViewById(R.id.loan_gridView);
            viewHolder.loan_recommend.setTag(position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.loan_recommend.setText(productSuList.getProduct().get(position));
            viewHolder.loan_gridView.setTag("0");
            viewHolder.loan_gridView.setAdapter(new LoansupemarketGridviewAdapter(context, productSuList.getProductSuList()));
            notifyDataSetChanged();
        } else if (position == 1) {
            viewHolder.loan_recommend.setText(productSuList.getProduct().get(position));
            viewHolder.loan_gridView.setTag("1");
            viewHolder.loan_gridView.setAdapter(new LoansupemarketGridviewAdapter(context, productSuList.getProductSus()));
        }
        notifyDataSetChanged();
        viewHolder.loan_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.isEmpty(SharedPreferencesUtils.get(context, "uid", "").toString())) {
                    context.startActivity(new Intent(context, LogoActivity.class));
                } else {
                    getHITSPRODUCT(productSuList.getProductSuList().get(position).getId());
                    switch (viewHolder.loan_gridView.getTag().toString()) {
                        case "0":
                            if (productSuList.getProductSuList().get(position).getApi_type().equals("3")) {
                                intent = new Intent(parent.getContext(), LoanDetailsActivity.class);
                                intent.putExtra("id", productSuList.getProductSuList().get(position).getId());
                                context.startActivity(intent);
                            } else if (productSuList.getProductSuList().get(position).getApi_type().equals("1")) {
                                getHttp(productSuList.getProductSuList().get(position).getId());
                            } else {
                                if (!TextUtils.isEmpty(productSuList.getProductSuList().get(position).getPro_link())) {
                                    intent = new Intent(parent.getContext(), LoanWebViewActivity.class);
                                    intent.putExtra("url", productSuList.getProductSuList().get(position).getPro_link());
                                    context.startActivity(intent);
                                }
                            }
                            break;
                        case "1":
                            if (productSuList.getProductSuList().get(position).getApi_type().equals("3")) {
                                intent = new Intent(parent.getContext(), LoanDetailsActivity.class);
                                intent.putExtra("id", productSuList.getProductSus().get(position).getId());
                                context.startActivity(intent);
                            } else if (productSuList.getProductSus().get(position).getApi_type().equals("1")) {
                                getHttp(productSuList.getProductSus().get(position).getId());
                            } else {
                                if (!TextUtils.isEmpty(productSuList.getProductSus().get(position).getPro_link())) {
                                    intent = new Intent(parent.getContext(), LoanWebViewActivity.class);
                                    intent.putExtra("url", productSuList.getProductSus().get(position).getPro_link());
                                    context.startActivity(intent);
                                }
                            }
                            break;
                    }
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView loan_praise;
        TextView loan_recommend;
        MyGridView loan_gridView;

    }

    private void getHITSPRODUCT(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(context, "uid", "").toString()));
        map.put("id", Long.parseLong(id));
        map.put("channel", AppUtil.getInstance().getChannel(context, 2));
        OkHttpManager.postAsync(HttpURL.getInstance().HITSPRODUCT, "", map, this);

    }

    private void getHttp(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedPreferencesUtils.get(context, "uid", "").toString());
        map.put("id", Long.parseLong(id));
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT_DETAIL, "product_detail", map, this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        ToatUtils.showShort1(context, context.getString(R.string.network_timed));
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        if (name.equals("product_detail")) {
            loanDatailsData = JsonData.getInstance().getJsonLoanDailsData(result);
            if (!TextUtils.isEmpty(loanDatailsData.getPro_link())) {
                intent = new Intent(context, LoanWebViewActivity.class);
                intent.putExtra("url", loanDatailsData.getPro_link());
                context.startActivity(intent);
            }
        }
    }
}
