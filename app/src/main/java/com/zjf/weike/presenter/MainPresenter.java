package com.zjf.weike.presenter;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.zjf.weike.App;
import com.zjf.weike.R;
import com.zjf.weike.impl.OnAsyncModelListener;
import com.zjf.weike.model.MainModel;
import com.zjf.weike.model.modelimp.MainModelImp;
import com.zjf.weike.presenter.base.BasePresenter;
import com.zjf.weike.view.viewimp.MainViewImp;

import java.io.File;

/**
 * @author :ZJF
 * @version : 2016-12-19 下午 8:25
 */

public class MainPresenter implements BasePresenter<MainViewImp> {

    private long currentTime = 0;
    private MainViewImp mView;
    private MainModelImp mModel;
    private boolean isAttacded = false;

    public MainPresenter() {
        mModel = new MainModel();
    }

    public void downLoadePicture(String url) {
        if (TextUtils.isEmpty(url)) {
            mView.showSnakBar(App.getStringRes(R.string.downfailure), 3);
            return;
        }
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
        if (file.exists()) {
            mView.showSnakBar(App.getStringRes(R.string.downsuccess), 1);
            return;
        }
        mModel.setUrl(url);
        mModel.getData(new OnAsyncModelListener<String>() {
            @Override
            public void onFailure(String msg, int type) {
                mView.showSnakBar(msg, type);
            }

            @Override
            public void onSuccess(String list) {
                mView.showSnakBar(list, 1);
            }
        });
    }


    public void judgeExit(Context context) {
        long timeMillis = System.currentTimeMillis();
        if (timeMillis - 2000 > currentTime) {
            mView.showSnakBar(context.getString(R.string.pressTwiceExit), 2);
            currentTime = timeMillis;
        } else {
            mView.exit();
        }
    }

    @Override
    public void onViewAttached(MainViewImp view) {
        isAttacded = true;
        this.mView = view;

    }

    @Override
    public void onViewDeached() {
        isAttacded = false;
    }

    @Override
    public void onDestroyed() {
        isAttacded = false;
        mModel = null;
        mView = null;
    }
}
