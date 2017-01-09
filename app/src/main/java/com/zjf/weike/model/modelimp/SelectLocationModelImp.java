package com.zjf.weike.model.modelimp;

import android.support.v4.app.Fragment;

/**
 * @author :ZJF
 * @version : 2016-12-27 下午 4:36
 */

public interface SelectLocationModelImp extends BaseModelImp<Fragment> {

    String[] getTitles();

    void setCityCode(String cityCode);

    void setLatitude(double latitude);

    void setLongitude(double longitude) ;
}
