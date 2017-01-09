package com.zjf.weike.view.viewimp;

import android.app.Activity;
import android.content.Intent;

/**
 * @author :ZJF
 * @version : 2016-12-15 下午 4:57
 */

public interface SplashViewImp extends BaseViewImp {

    void startApp(Class<? extends Activity> aClazz, int delay);

    void showPermisssionDialog(String permissionName, String msg);

    void onAllPermissionPass();

    void showUpdataDialog(String newVersionCode);

    void showForceUpdataDialog();

    void setBackGround(String url);

    boolean isFirstStart(String version);

    String getIgnoreVersionCode();

    void exit();

    void sendUpdataBroadcast(Intent intent,boolean close);

    int getSaveDate();

    String getTodayBG();

    void saveDateAndBg(int date,String url);
}
