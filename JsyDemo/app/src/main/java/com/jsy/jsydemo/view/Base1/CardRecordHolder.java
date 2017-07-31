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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.HomeProduct;
import com.jsy.jsydemo.EntityClass.LoanDatailsData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LoanDetailsActivity;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.webview.LoanWebViewActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Request;

@SuppressWarnings({"LoopStatementThatDoesntLoop", "UnusedAssignment"})
public class CardRecordHolder extends BaseViewHolder<HomeProduct> implements DataCallBack {

    private TextView home_loan_product_title;
    private TextView home_loan_product_quota;
    private TextView home_loan_product_number;
    private TextView home_loan_product_interest_rate;

    private ImageView home_loan_product_image;

    private ViewGroup parent;

    private Context context;

    private Intent intent;

    private LoanDatailsData loanDatailsData;

    public CardRecordHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.holder_consume);
        this.parent = parent;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(final HomeProduct object) {
        super.setData(object);
        if (object != null) {
            Glide.with(context)
                    .load(object.getImg())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            home_loan_product_image.setImageResource(R.mipmap.ic_path_in_load);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(home_loan_product_image);
            home_loan_product_title.setText(object.getPro_name());
            home_loan_product_quota.setText(object.getPro_describe());
            home_loan_product_interest_rate.setText(parent.getContext().getString(R.string.name_loan_product_interest_rat) + object.getFeilv());
            SpannableStringBuilder builder = new SpannableStringBuilder(home_loan_product_interest_rate.getText().toString());
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 3, home_loan_product_interest_rate.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            home_loan_product_interest_rate.setText(builder);

            if (object.getPro_hits().length() < 5) {
                home_loan_product_number.setText(context.getString(R.string.loan_fragment_applicants) + object.getPro_hits() + testRandom() + "人");
            } else {
                home_loan_product_number.setText(context.getString(R.string.loan_fragment_applicants) + object.getPro_hits() + "人");
            }
            builder = new SpannableStringBuilder(home_loan_product_number.getText().toString());
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 4, home_loan_product_number.getText().length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            home_loan_product_number.setText(builder);

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
        home_loan_product_image = findViewById(R.id.home_loan_product_image);
        home_loan_product_title = findViewById(R.id.home_loan_product_title);
        home_loan_product_quota = findViewById(R.id.home_loan_product_quota);
        home_loan_product_number = findViewById(R.id.home_loan_product_number);
        home_loan_product_interest_rate = findViewById(R.id.home_loan_product_interest_rate);
    }

    @Override
    public void onItemViewClick(HomeProduct object) {
        super.onItemViewClick(object);
        if (StringUtil.isNullOrEmpty(SharedPreferencesUtils.get(parent.getContext(), "uid", "").toString())) {
            parent.getContext().startActivity(new Intent(parent.getContext(), LogoActivity.class));
        } else {
            getHITSPRODUCT(object);
            switch (object.getApi_type()) {
                case "1":
                    if (!TextUtils.isEmpty(object.getPro_link())) {
                        intent = new Intent(parent.getContext(), LoanWebViewActivity.class);
                        intent.putExtra("url", object.getPro_link());
                        context.startActivity(intent);
                    }
                    break;
                case "2":
                    getHttp(object.getId());
                    break;
                case "3":
                    intent = new Intent(parent.getContext(), LoanDetailsActivity.class);
                    intent.putExtra("id", object.getId());
                    parent.getContext().startActivity(intent);
                    break;
            }
        }
    }

    private void getHITSPRODUCT(HomeProduct homeProduct) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(context, "uid", "").toString()));
        map.put("id", Long.parseLong(homeProduct.getId()));
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
                intent = new Intent(parent.getContext(), LoanWebViewActivity.class);
                intent.putExtra("url", loanDatailsData.getPro_link());
                context.startActivity(intent);
            }
        }
    }
}