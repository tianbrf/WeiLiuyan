package com.zjf.weike.model.modelimp;

import com.zjf.weike.impl.OnAsyncModelListener;

/**
 * @author :ZJF
 * @version : 2016-12-28 下午 2:47
 */

public interface RegisterModelImp {
    void getVCode(String phoneNum, OnAsyncModelListener<String> listener);

    void register(String vCode,String phoneNum,String nickName,String pwd,OnAsyncModelListener<String> listener);
}
