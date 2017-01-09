package com.zjf.weike.model.modelimp;

import com.zjf.weike.impl.OnAsyncModelListener;

/**
 * @author :ZJF
 * @version : 2016-12-24 上午 10:51
 */

public interface SplashModelImp extends BaseAsyncModelImp<String>{
    void getBackGround(OnAsyncModelListener<String> listener);
}
