package com.zjf.weike.model;

import android.support.v4.app.Fragment;

import com.zjf.weike.model.modelimp.SelectLocationModelImp;
import com.zjf.weike.view.fragment.POIFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-27 下午 4:34
 */

public class SelectLocationModel implements SelectLocationModelImp {

    private String[] poiType = {"餐饮服务", "购物服务", "生活服务",
            "风景名胜", "商务住宅", "公司企业", "地名地址信息"};
    private List<Fragment> mFragments;

    private String cityCode;
    private double latitude, longitude;

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public SelectLocationModel() {
        mFragments = new ArrayList<>();
    }

    @Override
    public List<Fragment> getData() {
        for (int i = 0; i < poiType.length; i++) {
            POIFragment fragment = new POIFragment();
            fragment.setCityCode(cityCode);
            fragment.setPoiType(poiType[i]);
            fragment.setLatitude(latitude);
            fragment.setLongitude(longitude);
            mFragments.add(fragment);
        }
        return mFragments;
    }

    @Override
    public String[] getTitles() {
        return poiType;
    }
}
