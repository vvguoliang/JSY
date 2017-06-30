package com.jsy.jsydemo.utils.PublicClass;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.view.BottomDialog;
import com.jsy.jsydemo.view.PublicDialog;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by vvguoliang on 2017/6/30.
 * 公共类 Diaog
 */

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ShowDialog implements Serializable {

    /**
     * 单例对象实例
     */
    private static class ShowDialogHolder {
        static final ShowDialog INSTANCE = new ShowDialog();
    }

    public static ShowDialog getInstance() {
        return ShowDialogHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private ShowDialog() {
    }

    /**
     * readResolve方法应对单例对象被序列化时候
     */
    private Object readResolve() {
        return getInstance();
    }

    // 提示对话框方法
    public void showDialog(final Activity context, final String name, String btn_take, String btn_pick, int species) {
        final BottomDialog sxsDialog = new BottomDialog(context, R.layout.buttom_dialog);
        sxsDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        sxsDialog.setWidthHeight(AppUtil.Dispay(context)[0], 0);
        sxsDialog.getWindow().setGravity(Gravity.BOTTOM);
        Button button1 = (Button) sxsDialog.findViewById(R.id.btn_take_photo);
        button1.setText(btn_take);
        Button button = (Button) sxsDialog.findViewById(R.id.btn_pick_photo);
        button.setText(btn_pick);
        switch (species) {
            case 1:
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtils.put(context, name, "1");
                        sxsDialog.dismiss();
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {//有
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtils.put(context, name, "2");
                        sxsDialog.dismiss();
                    }
                });
                break;
        }
        sxsDialog.setOnClick(R.id.btn_cancel, new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                sxsDialog.dismiss();
            }
        });
        if (!context.isFinishing()) {
            sxsDialog.show();
        }
    }


    public void getDialog(Activity mActivity, List<Map<String, Object>> list, String name, final Handler mHandler, final int what) {
        PublicDialog.Builder builder = new PublicDialog.Builder(mActivity);
        String stringa = SharedPreferencesUtils.get(mActivity, name, "").toString();
        if (!StringUtil.isNullOrEmpty(stringa)) {
            List<Map<String, Object>> listViewEntities = SharedPreferencesUtils.getInfo(mActivity, stringa);
            if (null != listViewEntities && listViewEntities.size() > 0) {
                list.clear();
                list = listViewEntities;
            }
        }
        final List<Map<String, Object>> finalList = list;
        builder.setItems(list, name, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; finalList.size() > i; i++) {
                    if (finalList.get(i).get("boolean").equals("2")) {
                        Message message = new Message();
                        message.what = what;
                        message.obj = finalList.get(i).get("name").toString();
                        mHandler.dispatchMessage(message);
                    }
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
