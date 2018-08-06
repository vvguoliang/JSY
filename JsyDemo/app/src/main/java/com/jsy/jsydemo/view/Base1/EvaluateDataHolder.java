package com.jsy.jsydemo.view.Base1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.EvaluateData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.view.BaseViewHolder;

/**
 * Created by jsy_zj on 2017/10/19.
 * 贷款详情界面评论holder
 */

public class EvaluateDataHolder extends BaseViewHolder<EvaluateData>{

    private Context context;
    private TextView usernevaluate_ame,evaluate_time,evaluate_text,value_isloan,value_noloan,evaluate_recycler_user_va;
    private RatingBar ratingBar;


    public EvaluateDataHolder(Context context, ViewGroup parent){
        super(context, parent, R.layout.view_evaluate_recycler_item);
        this.context = context;
    }

    @Override
    public void setData(EvaluateData data) {
        super.setData(data);
        if (data!=null){

            usernevaluate_ame.setText(data.getMobile()+"");
            String type = data.getType()+"";
            if (type.equals("1")){
                value_isloan.setVisibility(View.VISIBLE);
                value_noloan.setVisibility(View.GONE);
            }else {
                value_noloan.setVisibility(View.VISIBLE);
                value_isloan.setVisibility(View.GONE);
            }
            evaluate_recycler_user_va.setText(data.getComment()+"");
            evaluate_time.setText(data.getCreated_at()+"");
            ratingBar.setStar(Float.parseFloat(data.getScore()+""));
        }
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        usernevaluate_ame = findViewById(R.id.evaluate_recycler_user_name);
        evaluate_time = findViewById(R.id.evaluate_recycler_user_time);
        evaluate_text = findViewById(R.id.evaluate_recycler_user_isloan1);
        value_isloan = findViewById(R.id.evaluate_recycler_user_isloan1);
        value_noloan = findViewById(R.id.evaluate_recycler_user_isloan2);
        ratingBar = findViewById(R.id.evaluate_recycler_user_ratingbar);
        evaluate_recycler_user_va = findViewById(R.id.evaluate_recycler_user_va);
    }
}
