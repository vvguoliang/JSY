package com.jsy.jsydemo.activity.personaldata;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 证件上传
 */

@SuppressWarnings("ConstantConditions")
public class PersonalDataCertificatesActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView title_complete;

    private RelativeLayout personal_data_certificates_positive;

    private RelativeLayout personal_data_certificates_other_sid;

    private RelativeLayout personal_data_certificates_face_recognition;

    private TextView face_recognition_correct_text;

    private ImageView face_recognition_camera;

    private ImageView positive;

    private ImageView other_sid;

    private ImageView face_recognition;

    private String getpath = "";

    private File file1 = null;

    private File file2 = null;

    private byte[] bitmap3 = null;

    private int bitmapint = 0;

    private Bitmap bitmap4 = null;

    private UserCenterRealize userCenterRealize = new UserCenterRealize();

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.act_personal_data_certifcates );
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B( this );
        }
    }

    @Override
    protected void findViewById() {
        findViewById( R.id.title_image ).setVisibility( View.VISIBLE );
        findViewById( R.id.title_image ).setOnClickListener( this );
        title_complete = findViewById( R.id.title_complete );
        title_complete.setVisibility( View.VISIBLE );
        title_complete.setOnClickListener( this );
        title_complete.setText( this.getString( R.string.name_loan_personal_data_preservation ) );
        TextView title_view = findViewById( R.id.title_view );
        title_view.setText( this.getString( R.string.name_loan_basic_identity ) );

        face_recognition_correct_text = findViewById( R.id.face_recognition_correct_text );

        face_recognition_camera = findViewById( R.id.face_recognition_camera );

        positive = findViewById( R.id.positive );
        other_sid = findViewById( R.id.other_sid );
        face_recognition = findViewById( R.id.face_recognition );

        findViewById( R.id.personal_data_certificates_positive ).setOnClickListener( this );
        findViewById( R.id.personal_data_certificates_other_sid ).setOnClickListener( this );
        findViewById( R.id.personal_data_certificates_face_recognition ).setOnClickListener( this );
        getIDcard();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                intent = new Intent();
                intent.putExtra( "operator", "2" );
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
            case R.id.title_complete:
                getHttp();
                break;
            case R.id.personal_data_certificates_positive:
                getpath = "1";
                showDialog( PersonalDataCertificatesActivity.this.getString( R.string.name_loan_personal_camera ),
                        PersonalDataCertificatesActivity.this.getString( R.string.name_loan_personal_album ) );
                break;
            case R.id.personal_data_certificates_other_sid:
                getpath = "2";
                showDialog( PersonalDataCertificatesActivity.this.getString( R.string.name_loan_personal_camera ),
                        PersonalDataCertificatesActivity.this.getString( R.string.name_loan_personal_album ) );
                break;
            case R.id.personal_data_certificates_face_recognition:
                getpath = "3";
                userCenterRealize.getFileByPhotograph( this );
                break;
        }
    }

    private void getHttp() {
        if (file1 != null && file2 != null && TextUtils.isEmpty( Arrays.toString( bitmap3 ) )) {
            bitmapint = 0;
            ToatUtils.showShort1( this, "您还没有上传图片，不能点击完成" );
            return;
        } else {
            bitmapint = 1;
        }
        OkHttpManager.uploadAsync( HttpURL.getInstance().IDCARDADD, "username_add",
                SharedPreferencesUtils.get( this, "uid", "" ).toString(), bitmapint + "", file1, file2, this );
    }

    private void getIDcard() {
        Map<String, Object> map = new HashMap<>();
        map.put( "uid", Long.parseLong( SharedPreferencesUtils.get( this, "uid", "" ).toString() ) );
        OkHttpManager.postAsync( HttpURL.getInstance().USERDETAILIDCARD, "username_idcatd", map, this );
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume( this );
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause( this );
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        ToatUtils.showShort1( this, this.getString( R.string.network_timed ) );
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "username_add":
                JSONObject object = new JSONObject( result );
                intent = new Intent();
                if (object.optString( "code" ).equals( "0000" )) {
                    intent.putExtra( "operator", "1" );
                } else {
                    intent.putExtra( "operator", "2" );
                }
                setResult( RESULT_CANCELED, intent );
                finish();
                break;
            case "username_idcatd":
                object = new JSONObject( result );
                object = new JSONObject( object.optString( "data" ) );
                JSONArray array = new JSONArray( object.optString( "data" ) );
                if (array.length() > 0) {
                    if (TextUtils.isEmpty( object.optString( "front_img" ) ) && "null".equals( object.optString( "front_img" ) ))
                        if (object.optString( "front_img" ).contains( "data/upload" )) {
                            String front_img = object.optString( "front_img" ).replace( "data/upload", "" );
                            getPath( positive, front_img );
                            findViewById( R.id.positive_camera ).setVisibility( View.GONE );
                        } else {
                            getPath( positive, object.optString( "front_img" ) );
                            findViewById( R.id.positive_camera ).setVisibility( View.GONE );
                        }
                    if (TextUtils.isEmpty( object.optString( "back_img" ) ) && "null".equals( object.optString( "back_img" ) ))
                        if (object.optString( "back_img" ).contains( "data/upload" )) {
                            String front_img = object.optString( "back_img" ).replace( "data/upload", "" );
                            getPath( other_sid, front_img );
                            findViewById( R.id.other_sid_camera ).setVisibility( View.GONE );
                        } else {
                            getPath( other_sid, object.optString( "back_img" ) );
                            findViewById( R.id.other_sid_camera ).setVisibility( View.GONE );
                        }
                }
                break;
        }

    }

    private void getPath(final ImageView imageView, String url) {
        Glide.with( this )
                .load( HttpURL.getInstance().HTTP_URL_PATH + url )
                .listener( new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        imageView.setImageResource( R.mipmap.ic_path_in_load );
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        return false;
                    }
                } )
                .into( imageView );

    }

    // 提示对话框方法
    public void showDialog(String btn_take, String btn_pick) {
        final BottomDialog sxsDialog = new BottomDialog( this, R.layout.buttom_dialog );
        sxsDialog.getWindow().setWindowAnimations( R.style.AnimBottom );
        sxsDialog.setWidthHeight( AppUtil.getInstance().Dispay( this )[0], 0 );
        sxsDialog.getWindow().setGravity( Gravity.BOTTOM );
        Button button1 = (Button) sxsDialog.findViewById( R.id.btn_pick_photo1 );
        button1.setText( btn_take );
        Button button = (Button) sxsDialog.findViewById( R.id.btn_pick_photo2 );
        button.setText( btn_pick );
        button1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCenterRealize.getFileByPhotograph( PersonalDataCertificatesActivity.this );//拍照外部调用
                sxsDialog.dismiss();
            }
        } );
        button.setOnClickListener( new View.OnClickListener() {//有
            @Override
            public void onClick(View v) {
                userCenterRealize.getFileByPhotoAlbum( PersonalDataCertificatesActivity.this );//相册外部调用
                sxsDialog.dismiss();
            }
        } );
        sxsDialog.setOnClick( R.id.btn_cancel, new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                sxsDialog.dismiss();
            }
        } );
        if (!isFinishing()) {
            sxsDialog.show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getFileByPhotograph( this );
            } else {
                Toast.makeText( this, "请授予相机权限", Toast.LENGTH_SHORT ).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_READ_SD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getFileByPhotograph( this );
            } else {
                Toast.makeText( this, "请授予读SD卡权限", Toast.LENGTH_SHORT ).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_WRITE_SK) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getFileByPhotograph( this );
            } else {
                Toast.makeText( this, "请授予写SD卡权限", Toast.LENGTH_SHORT ).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_READ_SD_PHOTOALBUM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.startPhotoAlbum( this );
            } else {
                Toast.makeText( this, "请授予读SD卡权限", Toast.LENGTH_SHORT ).show();
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_WRITE_SK_PHOTOALBUM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.startPhotoAlbum( this );
            } else {
                Toast.makeText( this, "请授予写SD卡权限", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        // 拍照
        if (AppUtil.getInstance().CAPTURE_IMAGE_REQUEST == requestCode) {
            if (RESULT_OK == resultCode) {
                Log.d( "拍照得到图片", AppUtil.getInstance().mImageFile.toString() );
                int mDegree = BitmapUtils.getBitmapDegree( AppUtil.getInstance().mImageFile.getAbsolutePath() );
                Log.d( "拍照得到图片的角度：", String.valueOf( mDegree ) );
                if (mDegree == 90 || mDegree == 180 || mDegree == 270) {
                    try {
                        Bitmap mBitmap = BitmapUtils.getFileBitmap( AppUtil.getInstance().mImageFile );
                        Bitmap bitmap = BitmapUtils.rotateBitmapByDegree( mBitmap, mDegree );
                        if (BitmapUtils.saveBitmapFile( bitmap, AppUtil.getInstance().mImageFile )) {
                            userCenterRealize.startClip( this, AppUtil.getInstance().mImageFile );
                        } else {
                            Toast.makeText( this, "保存图片失败", Toast.LENGTH_SHORT ).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText( this, "读取图片失败", Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    userCenterRealize.startClip( this, AppUtil.getInstance().mImageFile );
                }
            }
            //相册
        } else if (AppUtil.getInstance().LOAD_IMAGE_REQUEST == requestCode) {
            if (data != null) {
                Uri uri = data.getData();
                String filepath = BitmapUtils.FileUtils.getImageAbsolutePath( this, uri );
                Log.d( "相册获取到的文件路径", filepath );
                File file = new File( filepath );
                userCenterRealize.startClip( this, file );
            }
            //剪裁
        } else if (AppUtil.getInstance().CLIP_IMAGE_REQUEST == requestCode) {
            bitmap4 = BitmapUtils.getFileBitmap( AppUtil.getInstance().mOutFile );
            switch (getpath) {
                case "1":
                    file1 = AppUtil.getInstance().mOutFile;
                    positive.setImageBitmap( bitmap4 );
                    findViewById( R.id.positive_camera ).setVisibility( View.GONE );
                    break;
                case "2":
                    file2 = AppUtil.getInstance().mOutFile;
                    other_sid.setImageBitmap( bitmap4 );
                    findViewById( R.id.other_sid_camera ).setVisibility( View.GONE );
                    break;
                case "3":
                    bitmap3 = AppUtil.getInstance().bitmap2Bytes( bitmap4 );
                    face_recognition.setImageBitmap( bitmap4 );
                    face_recognition_correct_text.setVisibility( View.VISIBLE );
                    face_recognition_camera.setVisibility( View.VISIBLE );
                    break;
            }
            BitmapUtils.deleteFile( AppUtil.getInstance().mImageFile );
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra( "operator", "2" );
            setResult( RESULT_CANCELED, intent );
            finish();
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }
}
