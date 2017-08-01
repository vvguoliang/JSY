package com.jsy.jsydemo.activity.personaldata;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.BitmapUtils;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.BottomDialog;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/30.
 * 所有证件上传
 */

@SuppressWarnings("ConstantConditions")
public class PersonalDataUploadActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private ImageView upload_front_id;

    private ImageView upload_front_hold_id;

    private ImageView upload_hous;

    private ImageView upload_vehicle;

    private File file1 = null;
    private File file2 = null;
    private File file3 = null;
    private File file4 = null;

    private UserCenterRealize userCenterRealize = new UserCenterRealize();
    private String getpath = "0";

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_upload);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                intent = new Intent();
                intent.putExtra("complete", "2");
                setResult(107, intent);
                finish();
                break;
            case R.id.title_complete:
                getHttp();
                break;
            case R.id.upload_front_id_linear:
            case R.id.upload_front_id:
                getpath = "1";
                showDialog(PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_camera),
                        PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_album));
                break;
            case R.id.upload_front_hold_id_linear:
            case R.id.upload_front_hold_id:
                getpath = "2";
                showDialog(PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_camera),
                        PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_album));
                break;
            case R.id.upload_hous_linear:
            case R.id.upload_hous:
                getpath = "3";
                showDialog(PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_camera),
                        PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_album));
                break;
            case R.id.upload_vehicle_linear:
            case R.id.upload_vehicle:
                getpath = "4";
                showDialog(PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_camera),
                        PersonalDataUploadActivity.this.getString(R.string.name_loan_personal_album));
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_certificates));

        upload_front_id = findViewById(R.id.upload_front_id);
        upload_front_hold_id = findViewById(R.id.upload_front_hold_id);
        upload_hous = findViewById(R.id.upload_hous);
        upload_vehicle = findViewById(R.id.upload_vehicle);

        setGetpath();

        upload_front_id.setOnClickListener(this);
        upload_front_hold_id.setOnClickListener(this);
        upload_hous.setOnClickListener(this);
        upload_vehicle.setOnClickListener(this);

        findViewById(R.id.upload_front_id_linear).setOnClickListener(this);
        findViewById(R.id.upload_front_hold_id_linear).setOnClickListener(this);
        findViewById(R.id.upload_hous_linear).setOnClickListener(this);
        findViewById(R.id.upload_vehicle_linear).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        if (file1 != null && file2 != null && file3 != null && file4 != null) {
            ToatUtils.showShort1(this, "您还没有上传图片，不能点击完成");
            return;
        }
        OkHttpManager.uploadAsync(HttpURL.getInstance().PARPERSADD, "parees",
                SharedPreferencesUtils.get(this, "uid", "").toString(), file1, file2, file3, file4, this);
    }

    private void setGetpath() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().PARPERSLIST, "parees_list", map, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        if (name.equals("parees")) {
            ToatUtils.showShort1(this, this.getString(R.string.network_timed));
        } else if (name.equals("parees_list")) {
            ToatUtils.showShort1(this, this.getString(R.string.network_timed));
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        if (name.equals("parees")) {
            intent = new Intent();
            intent.putExtra("complete", "1");
            setResult(107, intent);
            finish();
        } else if (name.equals("parees_list")) {
            String idcard_front = "";
            String idcard = "";
            String house_card = "";
            String driving_card = "";
            JSONObject jsonObject = new JSONObject(result);
            jsonObject = new JSONObject(jsonObject.optString("data"));
            JSONArray array = new JSONArray(jsonObject.optString("data"));
            for (int i = 0; array.length() > i; i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);
                idcard_front = jsonObject1.optString("idcard_front");
                idcard = jsonObject1.optString("idcard");
                house_card = jsonObject1.optString("house_card");
                driving_card = jsonObject1.optString("driving_card");
            }
            if (StringUtil.isNullOrEmpty(idcard_front)) {
                upload_front_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_no_success);
            } else {
                upload_front_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
            }
            if (StringUtil.isNullOrEmpty(idcard)) {
                upload_front_hold_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_no_success);
            } else {
                upload_front_hold_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
            }
            if (StringUtil.isNullOrEmpty(house_card)) {
                upload_front_hold_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_no_success);
            } else {
                upload_hous.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
            }
            if (StringUtil.isNullOrEmpty(driving_card)) {
                upload_front_hold_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_no_success);
            } else {
                upload_vehicle.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
            }
        }

    }

    // 提示对话框方法
    public void showDialog(String btn_take, String btn_pick) {
        final BottomDialog sxsDialog = new BottomDialog(this, R.layout.buttom_dialog);
        sxsDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        sxsDialog.setWidthHeight(AppUtil.getInstance().Dispay(this)[0], 0);
        sxsDialog.getWindow().setGravity(Gravity.BOTTOM);
        Button button1 = (Button) sxsDialog.findViewById(R.id.btn_pick_photo1);
        button1.setText(btn_take);
        Button button = (Button) sxsDialog.findViewById(R.id.btn_pick_photo2);
        button.setText(btn_pick);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCenterRealize.getFileByPhotograph(PersonalDataUploadActivity.this);//拍照外部调用
                sxsDialog.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {//有
            @Override
            public void onClick(View v) {
                userCenterRealize.getFileByPhotoAlbum(PersonalDataUploadActivity.this);//相册外部调用
                sxsDialog.dismiss();
            }
        });
        sxsDialog.setOnClick(R.id.btn_cancel, new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                sxsDialog.dismiss();
            }
        });
        if (!isFinishing()) {
            sxsDialog.show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getFileByPhotograph(this);
            } else {
                Toast.makeText(this, "请授予相机权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_READ_SD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getFileByPhotograph(this);
            } else {
                Toast.makeText(this, "请授予读SD卡权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_WRITE_SK) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getFileByPhotograph(this);
            } else {
                Toast.makeText(this, "请授予写SD卡权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_READ_SD_PHOTOALBUM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.startPhotoAlbum(this);
            } else {
                Toast.makeText(this, "请授予读SD卡权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_WRITE_SK_PHOTOALBUM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.startPhotoAlbum(this);
            } else {
                Toast.makeText(this, "请授予写SD卡权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拍照
        if (AppUtil.getInstance().CAPTURE_IMAGE_REQUEST == requestCode) {
            if (RESULT_OK == resultCode) {
                Log.d("拍照得到图片", AppUtil.getInstance().mImageFile.toString());
                int mDegree = BitmapUtils.getBitmapDegree(AppUtil.getInstance().mImageFile.getAbsolutePath());
                Log.d("拍照得到图片的角度：", String.valueOf(mDegree));
                if (mDegree == 90 || mDegree == 180 || mDegree == 270) {
                    try {
                        Bitmap mBitmap = BitmapUtils.getFileBitmap(AppUtil.getInstance().mImageFile);
                        Bitmap bitmap = BitmapUtils.rotateBitmapByDegree(mBitmap, mDegree);
                        if (BitmapUtils.saveBitmapFile(bitmap, AppUtil.getInstance().mImageFile)) {
                            userCenterRealize.startClip(this, AppUtil.getInstance().mImageFile);
                        } else {
                            Toast.makeText(this, "保存图片失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "读取图片失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    userCenterRealize.startClip(this, AppUtil.getInstance().mImageFile);
                }
            }
            //相册
        } else if (AppUtil.getInstance().LOAD_IMAGE_REQUEST == requestCode) {
            if (data != null) {
                Uri uri = data.getData();
                String filepath = BitmapUtils.FileUtils.getImageAbsolutePath(this, uri);
                Log.d("相册获取到的文件路径", filepath);
                File file = new File(filepath);
                userCenterRealize.startClip(this, file);
            }
            //剪裁
        } else if (AppUtil.getInstance().CLIP_IMAGE_REQUEST == requestCode) {
            switch (getpath) {
                case "1":
                    file1 = AppUtil.getInstance().mOutFile;
                    upload_front_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
                    break;
                case "2":
                    file2 = AppUtil.getInstance().mOutFile;
                    upload_front_hold_id.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
                    break;
                case "3":
                    file3 = AppUtil.getInstance().mOutFile;
                    upload_hous.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
                    break;
                case "4":
                    file4 = AppUtil.getInstance().mOutFile;
                    upload_vehicle.setImageResource(R.mipmap.ic_personal_data_upload_front_id_success);
                    break;
            }
            BitmapUtils.deleteFile(AppUtil.getInstance().mImageFile);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra("complete", "2");
            setResult(107, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
