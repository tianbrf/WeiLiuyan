package com.zjf.weike.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * @author :ZJF
 * @version : 2016-12-26 下午 3:54
 */

public class UpdataService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdataService(String name) {
        super(name);
    }

    public UpdataService() {
        super("");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO 下载新版本
    }
}
