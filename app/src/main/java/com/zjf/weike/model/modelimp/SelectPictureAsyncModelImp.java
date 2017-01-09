package com.zjf.weike.model.modelimp;

import com.zjf.weike.bean.ImageFolder;
import com.zjf.weike.impl.OnAsyncModelListener;

import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-22 下午 3:34
 */

public interface SelectPictureAsyncModelImp extends BaseAsyncModelImp<List<ImageFolder>> {
    void getPicture(String folderName,OnAsyncModelListener<List<String>> listener);
}
