package com.zjf.weike.presenter;

import com.zjf.weike.model.GuideModel;
import com.zjf.weike.model.modelimp.BaseModelImp;
import com.zjf.weike.presenter.base.BasePresenter;
import com.zjf.weike.view.viewimp.GuideViewImp;

/**
 * @author :ZJF
 * @version : 2016-12-16 下午 4:32
 */

public class GuidePresenter implements BasePresenter<GuideViewImp> {

    private BaseModelImp mModel;
    private GuideViewImp mView;
    private boolean isAttached = false;

    public GuidePresenter() {
        mModel = new GuideModel();
    }


    @Override
    public void onViewAttached(GuideViewImp view) {
        isAttached = true;
        this.mView = view;
        mView.setFragment(mModel.getData());
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
