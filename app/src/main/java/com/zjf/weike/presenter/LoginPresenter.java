package com.zjf.weike.presenter;

import com.zjf.weike.presenter.base.BasePresenter;
import com.zjf.weike.view.viewimp.LoginViewImp;

/**
 * @author :ZJF
 * @version : 2016-12-16 下午 4:41
 */

public class LoginPresenter implements BasePresenter<LoginViewImp> {

    private LoginViewImp mView;
    private boolean isAttached = false;

    @Override
    public void onViewAttached(LoginViewImp view) {
        isAttached = true;
        this.mView = view;
    }

    public void login(String userName, String pwd) {
        mView.loginToMain();
    }


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
