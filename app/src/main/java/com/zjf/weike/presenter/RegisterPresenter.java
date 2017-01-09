package com.zjf.weike.presenter;

import com.zjf.weike.App;
import com.zjf.weike.R;
import com.zjf.weike.impl.OnAsyncModelListener;
import com.zjf.weike.model.RegisterModel;
import com.zjf.weike.model.modelimp.RegisterModelImp;
import com.zjf.weike.presenter.base.Presenter;
import com.zjf.weike.view.viewimp.RegisterViewImp;

/**
 * @author :ZJF
 * @version : 2016-12-16 下午 4:48
 */

public class RegisterPresenter extends Presenter<RegisterViewImp> {

    private RegisterModelImp mModel;

    public RegisterPresenter() {
        mModel = new RegisterModel();
    }

    public void getVCode(String phoneNum) {

        String regex = "1[34578]\\d{9}";
        if (!phoneNum.matches(regex)) {
            mView.showSnakBar(App.getStringRes(R.string.errorphone), 1);
            return;
        }

        mView.setBtnEnable();

        mModel.getVCode(phoneNum, new OnAsyncModelListener<String>() {
            @Override
            public void onFailure(String msg, int type) {

            }

            @Override
            public void onSuccess(String msg) {
                if ("ok".equals(msg)) {
                    mView.showSnakBar(msg, 1);
                }
            }
        });
    }


    public void register(String phoneNum, String vCode, String nickName, String pwd, String rePwd) {
        if (!pwd.equals(rePwd)) {
            mView.showSnakBar(App.getStringRes(R.string.pwdtdiff), 1);
            return;
        }
        mModel.register(vCode, phoneNum, nickName, pwd, new OnAsyncModelListener<String>() {
            @Override
            public void onFailure(String msg, int type) {
                mView.showSnakBar(App.getStringRes(R.string.registerfailure), 3);
            }

            @Override
            public void onSuccess(String msg) {
                mView.registerSuccess();
            }
        });

    }

    @Override
    protected void onViewStart() {

    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        mModel = null;
    }
}
