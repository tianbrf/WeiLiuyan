package com.zjf.weike;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.umeng.analytics.MobclickAgent;
import com.zjf.weike.util.LogUtil;
import com.zjf.weike.util.NativeUtil;
import com.zjf.weike.util.SC;

/**
 * @author :ZJF
 * @version : 2016-12-16 上午 9:58
 */

public class App extends Application {

    private static App instance;
    private static SharedPreferences mPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        LogUtil.isDebug = true;
        String key = NativeUtil.getKey();
        MobclickAgent.startWithConfigure(
                new MobclickAgent.UMAnalyticsConfig(this,
                        key, "wandoujia", MobclickAgent.EScenarioType.E_UM_NORMAL));
    }

    public static App getInstance() {
        return instance;
    }

    public static String getStringRes(int id) {
        return instance.getString(id);
    }

    public static SharedPreferences getPreferences() {
        if (mPreferences == null) {
            mPreferences = instance.getSharedPreferences(SC.CONFIG, Context.MODE_PRIVATE);
        }
        return mPreferences;
    }
}
