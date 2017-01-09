package com.zjf.weike.model.modelimp;

import com.zjf.weike.impl.OnAsyncModelListener;

/**
 * @author :ZJF
 * @version : 2016-12-05 下午 4:12
 */

public interface BaseAsyncModelImp<T> {
    void getData(OnAsyncModelListener<T> listener);
}
