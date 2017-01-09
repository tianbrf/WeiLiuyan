package com.zjf.weike.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zjf.weike.App;
import com.zjf.weike.R;
import com.zjf.weike.impl.OnAsyncModel2SListener;
import com.zjf.weike.impl.OnAsyncModelListener;
import com.zjf.weike.impl.OnPermissionResultListener;

/**
 * @author :ZJF
 * @version : 2016-12-21 上午 9:10
 */

public class DialogUtil {

    public static void showPermissionDialog(final Context context, String perName, final OnPermissionResultListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(App.getStringRes(R.string.less)
                + perName + App.getStringRes(R.string.permission))
                .setMessage(App.getStringRes(R.string.gotogrant)
                        + perName).setPositiveButton(App.getStringRes(R.string.gotosetting),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                        context.startActivity(intent);
                    }
                }).setNegativeButton(App.getStringRes(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.cancel();
            }
        }).setCancelable(false)
                .show();
    }

    public static void showUpdataDialog(Context context, final OnAsyncModel2SListener<String, String> listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(App.getStringRes(R.string.hint))
                .setMessage(App.getStringRes(R.string.newversion))
                .setNegativeButton(App.getStringRes(R.string.nohint), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onFailure("Negative", 1);
                    }
                })
                .setNeutralButton(App.getStringRes(R.string.nexttime), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAction("Neutral");
                    }
                })
                .setPositiveButton(App.getStringRes(R.string.instanceupdata), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSuccess("Positive");
                    }
                })
                .setCancelable(false)
                .show();
    }

    public static void showForceUpataDialog(Context context, final OnAsyncModelListener<String> listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(App.getStringRes(R.string.hint))
                .setMessage(App.getStringRes(R.string.mustupdata))
                .setPositiveButton(App.getStringRes(R.string.instanceupdata), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSuccess("Positive");
                    }
                })
                .setCancelable(false)
                .show();
    }

    public static void showSettingHostDialog(Context context, final OnAsyncModelListener<String> listener) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Setting");
        View view = LayoutInflater.from(context).inflate(R.layout.customdialog_layout, null);
        dialog.setView(view);
        final EditText text = (EditText) view.findViewById(R.id.edit_settingHost);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onSuccess(text.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
