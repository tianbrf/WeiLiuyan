package com.zjf.weike.view.viewimp;

import android.content.Intent;

import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-21 下午 5:01
 */

public interface PublishViewImp extends BaseViewImp {

    void published();

    void dimissBottomSheetDialog();

    void jumpToForResult(Intent intent, int requestCode);

    void flushData(List<String> pictures);

    void addCameraPicture(String path);

    void setLoactionName(String name);
}
