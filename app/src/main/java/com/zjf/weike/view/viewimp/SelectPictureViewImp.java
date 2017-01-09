package com.zjf.weike.view.viewimp;

import com.zjf.weike.bean.ImageFolder;

import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-22 下午 3:17
 */

public interface SelectPictureViewImp extends BaseViewImp {

    void setPicture(List<String> pictures);

    void setFolder(List<ImageFolder> folders);

}
