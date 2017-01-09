package com.zjf.weike.presenter.base;

/**
 * @author :ZJF
 * @version : 2016-12-16 下午 4:16
 */

public interface PresenterFactory<T extends BasePresenter> {
    T create();
}
