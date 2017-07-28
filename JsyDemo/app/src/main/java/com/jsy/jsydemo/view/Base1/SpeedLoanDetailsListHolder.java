package com.jsy.jsydemo.view.Base1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.HomeProduct;
import com.jsy.jsydemo.EntityClass.SpeedLoanDetailsListData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LoanDetailsActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
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

@SuppressWarnings("UnusedAssignment")
public class SpeedLoanDetailsListHolder extends BaseViewHolder<SpeedLoanDetailsListData> implements DataCallBack {

    private ImageView speed_loan_detailsList_image;

    private TextView speed_loan_detailsList_name;

    private TextView speed_loan_detailsList_text;

    private TextView speed_loan_detailsList_rate;

    private Context context;

    private Intent intent = null;


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
            speed_loan_detailsList_name.setText(data.getPro_name());
            if (data.getPro_hits().length() > 5) {
                speed_loan_detailsList_text.setText("申请人数:" + data.getPro_hits());
            } else {
                speed_loan_detailsList_text.setText("申请人数:" + data.getPro_hits() + testRandom());
            }
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

    private String testRandom() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            return random.nextInt(20) + "";
        }
        return "";
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        speed_loan_detailsList_image = findViewById(R.id.speed_loan_detailsList_image);
        speed_loan_detailsList_name = findViewById(R.id.speed_loan_detailsList_name);
        speed_loan_detailsList_text = findViewById(R.id.speed_loan_detailsList_text);
        speed_loan_detailsList_rate = findViewById(R.id.speed_loan_detailsList_rate);
    }

    @Override
    public void onItemViewClick(SpeedLoanDetailsListData data) {
        super.onItemViewClick(data);
        getHITSPRODUCT(data);
        switch (data.getApi_type()) {
            case "1":
            case "2":
                if (!TextUtils.isEmpty(data.getPro_link())) {
                    intent = new Intent(context, LoanWebViewActivity.class);
                    intent.putExtra("url", data.getPro_link());
                    context.startActivity(intent);
                }
                break;
            case "3":
                intent = new Intent(context, LoanDetailsActivity.class);
                intent.putExtra("id", data.getId());
                context.startActivity(intent);
                break;
        }
    }

    private void getHITSPRODUCT(SpeedLoanDetailsListData speedLoanDetailsListData) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(context, "uid", "").toString()));
        map.put("id", Long.parseLong(speedLoanDetailsListData.getId()));
        map.put("channel", AppUtil.getInstance().getChannel(context, 2));
        OkHttpManager.postAsync(HttpURL.getInstance().HITSPRODUCT, "", map, this);

    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {

    }
}
