package com.jsy.jsydemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Toast;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.fragment.LoanFragment;
import com.jsy.jsydemo.activity.fragment.LoanSupermarketFragment;
import com.jsy.jsydemo.activity.fragment.PersonalCenterFragment;
import com.jsy.jsydemo.activity.fragment.QuickCardFragment;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.base.BaseActivityManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.BitmapUtils;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.MainActivityView;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 2017-6-23
 * 基础主类
 */
public class MainActivity extends BaseActivity implements MainActivityView.OnItemClickListener {

    private Activity activity;

    private MainActivityView mainActivityView;

    private int[] titles = {R.string.name_loan, R.string.name_loan_supermarket, R.string.name_quick_card, R.string.name_personal_center};
    private int[] selectedImage = {R.mipmap.ic_loan_dark, R.mipmap.ic_loansupermarket_dark, R.mipmap.ic_quickcrd_dark,
            R.mipmap.ic_personalcenter_dark};
    private int[] unSelectedImage = {R.mipmap.ic_loan_brightness, R.mipmap.ic_loansupermarket_brightness, R.mipmap.ic_quickcard_brightness,
            R.mipmap.ic_personalcenter_brightness};

    private int mHeight;
    private boolean isGetHeight = true;

    private int mSelectPosition = 0;

    private List<Fragment> listfragment = new ArrayList<>();

    private List<Fragment> listnewftagment = new ArrayList<>();

    private UserCenterRealize userCenterRealize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setImmerStateBar(false);
        this.activity = this;
        userCenterRealize = new UserCenterRealize();
        setContentView(R.layout.activity_main);
        findViewById();
    }

    @Override
    protected void findViewById() {
        int position = getIntent().getIntExtra("selectPosition", -1);
        if (position == -1) {
            mSelectPosition = 0;
        } else {
            mSelectPosition = position;
        }
        initView();

        if (position == -1) {
        } else {
            showFragment(position);
        }
        SharedPreferencesUtils.put(activity, "STATUS_FONT_COLOR", "WHITE");
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            setTranslucentStatus(true);
            ImmersiveUtils.setStateBar(this, Color.parseColor("#305591"));
            ImmersiveUtils.stateBarTint(this, "#305591", true, false);
        }
        //清除状态栏黑色字体
        statusFragmentBarDarkMode();
    }

    @Override
    protected void initView() {
        for (int i = 0; i <= 3; i++) {
            listfragment.add(null);
        }
        listnewftagment.add(new LoanFragment(MainActivity.this));
        listnewftagment.add(new LoanSupermarketFragment(MainActivity.this));
        listnewftagment.add(new QuickCardFragment(MainActivity.this));
        listnewftagment.add(new PersonalCenterFragment(MainActivity.this));

        // 获取屏幕宽度
        Display dm = getWindowManager().getDefaultDisplay();
        final int screenWith = dm.getWidth();
        mainActivityView = (MainActivityView) findViewById(R.id.act_main_tab);
        // 初始化获取底部导航自身高度
        final ViewTreeObserver vt = mainActivityView.getViewTreeObserver();
        vt.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isGetHeight) {
                    mHeight = mainActivityView.getHeight();
                    mainActivityView.setLayout(titles, selectedImage, unSelectedImage, screenWith, mHeight, MainActivity.this);
                    mainActivityView.setColorLing(mSelectPosition);
                    mainActivityView.setOnItemClickListener(MainActivity.this);
                    isGetHeight = false;
                }
                return true;
            }
        });
        showFragment(mSelectPosition);
    }

    @Override
    public void onItemClick(int position) {
        showFragment(position);
    }

    /**
     * 动态添加和显示fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        setImmerStateBar(false);
        for (int i = 0; i < listfragment.size(); i++) {
            if (position == i) {
                if (listfragment.get(i) == null) {
                    listfragment.remove(i);
                    listfragment.add(i, listnewftagment.get(i));
                    if (!listnewftagment.get(i).isAdded()) {
                        transaction.add(R.id.cat_main_fragment_content, listfragment.get(i));
                    } else {
                        transaction.show(listfragment.get(i));
                    }
                } else {
                    transaction.show(listfragment.get(i));
                }
                //fragment首页和我的页状态栏字体颜色为白色
                SharedPreferencesUtils.put(activity, "STATUS_FONT_COLOR", "WHITE");
                //沉浸式状态设置
                if (ImmersiveUtils.BuildVERSION()) {
                    ImmersiveUtils.setStateBar(this, Color.parseColor("#305591"));
                    ImmersiveUtils.stateBarTint(this, "#305591", true, false);
                }
                //清除状态栏黑色字体
                statusFragmentBarDarkMode();
            }
        }
        transaction.commitAllowingStateLoss();
    }


    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        for (int i = 0; i < listfragment.size(); i++) {
            if (listfragment.get(i) != null) {
                transaction.hide(listfragment.get(i));
            }
        }
    }

    /**
     * 连续按两次返回键就退出
     */
    private int keyBackClickCount = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    ToatUtils.showShort1(this, "再按一次退出");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    BaseActivityManager.getActivityManager().finishAllActivity();
                    finish();
                    break;
                default:
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
            Log.d("剪裁得到图片", AppUtil.getInstance().mOutFile.toString());
            Bitmap bitmap = BitmapUtils.getFileBitmap(AppUtil.getInstance().mOutFile);
//            personal_camera.setImageBitmap(bitmap);
            BitmapUtils.deleteFile(AppUtil.getInstance().mImageFile);
        }
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
}
