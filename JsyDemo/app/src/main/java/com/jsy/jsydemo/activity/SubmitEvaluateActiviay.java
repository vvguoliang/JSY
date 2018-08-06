package com.jsy.jsydemo.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.control.MyEdittextView;
import com.jsy.jsydemo.control.MyTextView;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.Base1.RatingBar;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by jsy_zj on 2017/10/19.
 */

public class SubmitEvaluateActiviay extends BaseActivity implements DataCallBack{

    private EditText evaluate_edittextView;

    private RatingBar user_ratingBar;

    private RadioGroup radioGroup;

    private int ratingCounts =5;

    private int loan_type = 1;

    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_valuate);
        pid = Integer.parseInt(getIntent().getStringExtra("pid"));
        evaluate_edittextView = findViewById(R.id.submit_valuate_edittext1);
        user_ratingBar = findViewById(R.id.submit_valuate_ratingbar);
        radioGroup = findViewById(R.id.radiogroup);
        findViewById( R.id.title_image ).setVisibility( View.VISIBLE );

        TextView title_view = findViewById( R.id.title_view );
        title_view.setText( this.getString( R.string.commen_evaluate ) );
        viewListener();

    }

    private void viewListener() {
        user_ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                ratingCounts = (int) ratingCount;
//                ToatUtils.showShort1(SubmitEvaluateActiviay.this,ratingCount+"=====");
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case 0://已贷款
                        loan_type = 1;
                        break;
                    case 1://未贷款
                        loan_type = 0;
                        break;
                }
            }
        });

        findViewById( R.id.title_image ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void findViewById() {



    }

    @Override
    protected void initView() {

    }



//    @Override
    public void submitClick(View v) {
            if (v.getId() == R.id.submit_valuate_submit) {//提交
//                ToatUtils.showShort1(this, "点击了提交");
                submitData();
            }
    }

    private void submitData() {
        //拉取评论
        Map<String, Object> map = new HashMap<>();

        String userUID = SharedPreferencesUtils.get(this,"uid","")+"";
        map.put( "uid", userUID);
        map.put( "pid", pid);
        map.put( "score", ratingCounts);
        map.put( "type", loan_type);
        if (!evaluate_edittextView.getText().equals(null)) {
            map.put( "comment", evaluate_edittextView.getText()+"");
        }else {
            map.put( "comment", "");
        }
        OkHttpManager.postAsync( HttpURL.getInstance().SUBMITEVALUATE, "submit_evaluate", map, this );

    }


    @Override
    public void requestFailure(Request request, String name, IOException e) {
        ToatUtils.showShort1( this, this.getString( R.string.network_timed ) );
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
            if (name.equals("submit_evaluate")){
                JSONObject jsonobject = new JSONObject(result);
                String resultData = jsonobject.getString("msg");
                ToatUtils.showShort1( this, resultData );
                finish();
            }
    }

}
