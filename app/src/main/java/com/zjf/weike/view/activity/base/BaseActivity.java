package com.zjf.weike.view.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.zjf.weike.impl.JumpInto;
import com.zjf.weike.impl.JumpTo;

public abstract class BaseActivity extends AppCompatActivity {

    private JumpTo mTo;
    protected RxPermissions mPermissions;
    protected Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBundle = savedInstanceState;
        mTo = new JumpInto();
        mPermissions = new RxPermissions(this);
        initVariables();
        initView();
        setListener();
    }


    /**
     * 做初始化方面的工作,比如接收上一个界面的Intent
     */
    public abstract void initVariables();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化监听
     */
    public abstract void setListener();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void jumpTo(final Activity activity, final Class<? extends Activity> aClazz, int delay, boolean isFinish) {
        mTo.jumpTo(activity, aClazz, delay, isFinish);
    }

    public void jumpTo(final Activity activity, final Class<? extends Activity> aClazz, int delay, final Bundle bundle, boolean isFinish) {
        mTo.jumpTo(activity, aClazz, delay, bundle, isFinish);
    }


    public void jumpTo(Activity activity, Class<? extends Activity> aClazz, Bundle bundle, boolean isFinish) {
        mTo.jumpTo(activity, aClazz, bundle, isFinish);
    }

    public void jumpTo(Activity activity, Class<? extends Activity> aClazz, boolean isFinish) {
        mTo.jumpTo(activity, aClazz, isFinish);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
