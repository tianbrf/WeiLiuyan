package com.zjf.weike.presenter.base;

/**
 * @author :ZJF
 * @version : 2016-12-28 下午 4:13
 */

public abstract class Presenter<T> implements BasePresenter<T> {

    protected T mView;
    protected boolean isAttached = false;


    @Override
    public void onViewAttached(T view) {
        this.mView = view;
        isAttached = true;
        onViewStart();
    }

    protected abstract void onViewStart();

    @Override
    public void onViewDeached() {
        isAttached = false;
    }

    @Override
    public void onDestroyed() {
        isAttached = false;
        mView = null;
    }
}
