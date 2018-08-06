package com.jsy.jsydemo.view.Base1;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.LoanDatailsData;
import com.jsy.jsydemo.EntityClass.SpeedLoanDetailsListData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LoanDetailsActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.webview.LoanWebViewActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/8.
 * <p>
 * 数据展示
 */

@SuppressWarnings({"UnusedAssignment", "LoopStatementThatDoesntLoop"})
public class SpeedLoanDetailsListHolder extends BaseViewHolder<SpeedLoanDetailsListData> implements DataCallBack {

    private ImageView speed_loan_detailsList_image;

    private TextView speed_loan_detailsList_name;

    private TextView speed_loan_detailsList_text;

    private TextView speed_loan_detailsList_rate;

    private LinearLayout tagBackLL2,tagBackLL1;
    private ImageView imageView1,imageView2;


    private Context context;

    private Intent intent = null;

    private LoanDatailsData loanDatailsData;

    public SpeedLoanDetailsListHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.act_speedloan_details_list);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(SpeedLoanDetailsListData data) {
        super.setData(data);
        if (data != null) {
            Glide.with(context)
                    .load(data.getImg())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            speed_loan_detailsList_image.setImageResource(R.mipmap.ic_path_in_load);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(speed_loan_detailsList_image);
            Glide.with(context).load(data.getIs_new()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                    tagBackLL1.setVisibility(View.INVISIBLE);
                    return false;
                }

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                    tagBackLL1.setVisibility(View.VISIBLE);
                    tagBackLL1.setBackground(drawable);
                    return false;
                }
            }).into(imageView1);
             Glide.with(context).load(data.getIs_activity()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                    tagBackLL2.setVisibility(View.INVISIBLE);
                    return false;
                }

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                    tagBackLL2.setVisibility(View.VISIBLE);
                    tagBackLL2.setBackground(drawable);
                    return false;
                }
            }).into(imageView2);

            speed_loan_detailsList_name.setText(data.getPro_name());
            speed_loan_detailsList_text.setText("申请人数:" + data.getPro_hits());
            SpannableStringBuilder builder = new SpannableStringBuilder(speed_loan_detailsList_text.getText().toString());
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 5, speed_loan_detailsList_text.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            speed_loan_detailsList_text.setText(builder);
            speed_loan_detailsList_rate.setText(context.getString(R.string.loan_fragment_rate) + "  " + data.getFeilv());
            builder = new SpannableStringBuilder(speed_loan_detailsList_text.getText().toString());
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 2, speed_loan_detailsList_text.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            speed_loan_detailsList_text.setText(builder);
        }
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        speed_loan_detailsList_image = findViewById(R.id.speed_loan_detailsList_image);
        speed_loan_detailsList_name = findViewById(R.id.speed_loan_detailsList_name);
        speed_loan_detailsList_text = findViewById(R.id.speed_loan_detailsList_text);
        speed_loan_detailsList_rate = findViewById(R.id.speed_loan_detailsList_rate);
        tagBackLL1 = findViewById(R.id.speed_loan_list_item_tag_1);
        tagBackLL2 = findViewById(R.id.speed_loan_list_item_tag_2);
        imageView1 = findViewById(R.id.speed_loan_list_item_tag_image_1);
        imageView2 = findViewById(R.id.speed_loan_list_item_tag_image_2);
    }

    @Override
    public void onItemViewClick(SpeedLoanDetailsListData data) {
        super.onItemViewClick(data);
        getHITSPRODUCT(data);
//        switch (data.getApi_type()) {
////            case "1":
//            case "3":
//                if (!TextUtils.isEmpty(data.getPro_link())) {
//                    intent = new Intent(context, LoanWebViewActivity.class);
//                    intent.putExtra("url", data.getPro_link());
//                    context.startActivity(intent);
//                }
//                break;
//            case "2":
//                getHttp(data.getId());
////                intent = new Intent(context, LoanDetailsActivity.class);
////                intent.putExtra("id", data.getId());
////                context.startActivity(intent);
//                break;
////            case "3":
//            case "1":
//                intent = new Intent(context, LoanDetailsActivity.class);
//                intent.putExtra("id", data.getId());
//                context.startActivity(intent);
//                break;
//        }
        intent = new Intent(context, LoanDetailsActivity.class);
        intent.putExtra("Api_type",data.getApi_type());
//        intent.putExtra("Api_type","3");
        intent.putExtra("id", data.getId());
        context.startActivity(intent);
    }

    private void getHITSPRODUCT(SpeedLoanDetailsListData speedLoanDetailsListData) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(context, "uid", "").toString()));
        map.put("id", Long.parseLong(speedLoanDetailsListData.getId()));
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
