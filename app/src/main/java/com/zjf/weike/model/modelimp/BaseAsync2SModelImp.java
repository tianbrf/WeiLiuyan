package com.zjf.weike.model.modelimp;

import com.zjf.weike.impl.OnAsyncModel2SListener;

/**
 * @author :ZJF
 * @version : 2016-12-05 下午 4:12
 */

public interface BaseAsync2SModelImp<T,V> {
    void getData(OnAsyncModel2SListener<T,V> listener);
}
