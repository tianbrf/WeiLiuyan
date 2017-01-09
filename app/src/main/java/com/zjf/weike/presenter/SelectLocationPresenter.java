package com.zjf.weike.presenter;

import com.zjf.weike.model.SelectLocationModel;
import com.zjf.weike.model.modelimp.SelectLocationModelImp;
import com.zjf.weike.presenter.base.BasePresenter;
import com.zjf.weike.view.viewimp.SelectLocationViewImp;

/**
 * @author :ZJF
 * @version : 2016-12-27 下午 4:32
 */

public class SelectLocationPresenter implements BasePresenter<SelectLocationViewImp> {

    private SelectLocationViewImp mView;
    private SelectLocationModelImp mModel;
    private boolean isAttached = false;

    public SelectLocationPresenter() {
        mModel = new SelectLocationModel();
    }

    public void getData(String cityCode, double latitude, double longitude) {
        mModel.setCityCode(cityCode);
        mModel.setLongitude(longitude);
        mModel.setLatitude(latitude);
        mView.setViewPagerFagment(mModel.getData());
    }

    public String[] getTitle() {
        return mModel.getTitles();
    }

    @Override
    public void onViewAttached(SelectLocationViewImp view) {
        this.mView = view;
        isAttached = true;
    }

    @Override
    public void onViewDeached() {
        isAttached = false;
    }

    @Override
    public void onDestroyed() {
        isAttached = false;
        mView = null;
        mModel = null;
    }
}
