package com.zjf.weike.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zjf.weike.service.UpdataService;

/**
 * @author :ZJF
 * @version : 2016-12-26 下午 3:33
 */

public class UpdataReceiver extends BroadcastReceiver {

    UpdataService mService;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.zjf.weike.updata.broadcast")) {
            mService = new UpdataService("updata");
            context.startService(new Intent(context, UpdataService.class));
        }
    }
}
