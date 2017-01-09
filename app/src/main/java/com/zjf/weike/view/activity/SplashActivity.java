package com.zjf.weike.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zjf.weike.R;
import com.zjf.weike.impl.OnAsyncModel2SListener;
import com.zjf.weike.impl.OnAsyncModelListener;
import com.zjf.weike.impl.OnPermissionResultListener;
import com.zjf.weike.presenter.SplashPresenter;
import com.zjf.weike.util.DialogUtil;
import com.zjf.weike.util.RetrofitUtil;
import com.zjf.weike.util.SC;
import com.zjf.weike.util.SnackBarUtil;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.SplashViewImp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends MVPActivity<SplashPresenter> implements SplashViewImp {

    @BindView(R.id.bg)
    ImageView mBg;
    @BindView(R.id.activity_splash)
    CoordinatorLayout mActivitySplash;

    private SharedPreferences mPreferences;

    /**
     * 初始化数据
     */
    @Override
    public void initVariables() {
        super.initVariables();
        mPreferences = getSharedPreferences(SC.CONFIG, Context.MODE_PRIVATE);
        RetrofitUtil.setBaseUrl(mPreferences.getString(SC.BASE_HOST, SC.DEFAULT_HOST));
    }

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mBg.setAlpha(0f);
    }


    @Override
    public void setListener() {

    }


    /**
     * 开启app
     *
     * @param aClazz 目标activity
     * @param delay  延时
     */
    @Override
    public void startApp(Class<? extends Activity> aClazz, int delay) {
        jumpTo(SplashActivity.this, aClazz, delay, true);
    }

    /**
     * 显示权限申请dialog
     *
     * @param permissionName
     * @param msg
     */
    @Override
    public void showPermisssionDialog(final String permissionName, String msg) {
        DialogUtil.showPermissionDialog(SplashActivity.this, msg, new OnPermissionResultListener() {
            @Override
            public void cancel() {
                mPresenter.requestPermission(permissionName);
            }
        });
    }

    /**
     * 全部权限申请后调用
     */
    @Override
    public void onAllPermissionPass() {
        mPresenter.requestVersionCode(getVersionCode());
    }

    /**
     * 获取本地版本号
     *
     * @return
     */
    public String getVersionCode() {
        return mPreferences.getString(SC.VERSION_CODE, SC.DEFAULT_VERSION_CODE);
    }

    /**
     * 获取本地忽略版本号
     *
     * @return
     */
    @Override
    public String getIgnoreVersionCode() {
        return mPreferences.getString(SC.IGNORE_VERSION, "null");
    }

    /**
     * 退出
     */
    @Override
    public void exit() {
        finish();
    }

    /**
     * 发送升级广播
     *
     * @param intent
     * @param close
     */
    @Override
    public void sendUpdataBroadcast(Intent intent, boolean close) {
        sendBroadcast(intent);
        if (close) {
            finish();
        }
    }

    /**
     * 获取保存的日期
     *
     * @return
     */
    @Override
    public int getSaveDate() {
        return mPreferences.getInt(SC.LOCATION_DATE, 0);
    }

    @Override
    public String getTodayBG() {
        return mPreferences.getString(SC.TODAY_BG, null);
    }

    @Override
    public void saveDateAndBg(int date, String url) {
        mPreferences
                .edit()
                .putInt(SC.LOCATION_DATE, date)
                .putString(SC.TODAY_BG, url)
                .apply();
    }

    /**
     * 显示升级提示框
     *
     * @param newVersionCode
     */
    @Override
    public void showUpdataDialog(final String newVersionCode) {
        DialogUtil.showUpdataDialog(this, new OnAsyncModel2SListener<String, String>() {
            @Override
            public void onAction(String list) {
                mPresenter.startApp(getVersionCode());
            }

            @Override
            public void onFailure(String msg, int type) {
                mPreferences.edit().putString(SC.IGNORE_VERSION, newVersionCode).apply();
                mPresenter.startApp(getVersionCode());
            }

            @Override
            public void onSuccess(String list) {
                mPresenter.updata(SC.UPDATA);
            }
        });
    }

    /**
     * 显示强制升级提示框
     */
    @Override
    public void showForceUpdataDialog() {
        DialogUtil.showForceUpataDialog(this, new OnAsyncModelListener<String>() {
            @Override
            public void onFailure(String msg, int type) {

            }

            @Override
            public void onSuccess(String list) {
                mPresenter.updata(SC.FORCE_UPDATE);
            }
        });
    }

    /**
     * 设置背景
     *
     * @param url
     */
    @Override
    public void setBackGround(String url) {
        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(mBg);
        mBg.animate().setDuration(2000)
                .alpha(1)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    /**
     * 获取是否第一次使用新版本
     *
     * @param version
     * @return
     */
    @Override
    public boolean isFirstStart(String version) {
        return mPreferences.getBoolean(version, true);
    }

    /**
     * 显示snackBar
     *
     * @param msg
     * @param type
     */
    @Override
    public void showSnakBar(String msg, int type) {
        SnackBarUtil.ShortSnackbar(mActivitySplash, msg, type).show();
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    @Override
    public SplashPresenter create() {
        return new SplashPresenter(mPermissions);
    }


}
