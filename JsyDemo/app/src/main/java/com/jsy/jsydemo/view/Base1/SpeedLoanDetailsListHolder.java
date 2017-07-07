package com.jsy.jsydemo.view.Base1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.SpeedLoanDetailsListData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.view.BaseViewHolder;

/**
 * Created by vvguoliang on 2017/7/8.
 * <p>
 * 数据展示
 */

public class SpeedLoanDetailsListHolder extends BaseViewHolder<SpeedLoanDetailsListData> {

    private ImageView speed_loan_detailsList_image;

    private TextView speed_loan_detailsList_name;

    private TextView speed_loan_detailsList_text;

    private TextView speed_loan_detailsList_rate;


    public SpeedLoanDetailsListHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.act_speedloan_details_list);
    }

    @Override
    public void setData(SpeedLoanDetailsListData data) {
        super.setData(data);
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
    }
}
