package com.zjf.weike.view.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zjf.weike.R;
import com.zjf.weike.presenter.ReGetPasswordPresenter;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.ReGetPasswordViewImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReGetPasswordActivity extends MVPActivity<ReGetPasswordPresenter> implements ReGetPasswordViewImp {


    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.textInput_phone)
    TextInputLayout mTextInputPhone;
    @BindView(R.id.btn_getVerifyCode)
    Button mBtnGetVerifyCode;
    @BindView(R.id.edit_vcode)
    EditText mEditVcode;
    @BindView(R.id.textInput_vcode)
    TextInputLayout mTextInputVcode;
    @BindView(R.id.edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.textInput_pwd)
    TextInputLayout mTextInputPwd;
    @BindView(R.id.edit_repwd)
    EditText mEditRepwd;
    @BindView(R.id.textInput_repwd)
    TextInputLayout mTextInputRepwd;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_re_get_password)
    CoordinatorLayout mActivityReGetPassword;

    @Override
    public ReGetPasswordPresenter create() {
        return new ReGetPasswordPresenter();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_re_get_password);
        ButterKnife.bind(this);
        mToolbar.setTitle(getResources().getString(R.string.regetpwd));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void showSnakBar(String msg,int type) {

    }

    @OnClick({R.id.btn_getVerifyCode, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getVerifyCode:
                break;
            case R.id.btn_register:
                break;
        }
    }
}
