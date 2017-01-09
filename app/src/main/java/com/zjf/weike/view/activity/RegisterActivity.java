package com.zjf.weike.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zjf.weike.R;
import com.zjf.weike.presenter.RegisterPresenter;
import com.zjf.weike.util.SC;
import com.zjf.weike.util.SnackBarUtil;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.RegisterViewImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends MVPActivity<RegisterPresenter> implements RegisterViewImp {


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
    @BindView(R.id.activity_register)
    CoordinatorLayout mActivityRegister;
    @BindView(R.id.edit_nickName)
    EditText mEditNickName;
    @BindView(R.id.textInput_nickName)
    TextInputLayout mTextInputNickName;

    @Override
    public void initVariables() {
        super.initVariables();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mToolbar.setTitle(getResources().getString(R.string.button_register));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setListener() {

    }


    @Override
    public RegisterPresenter create() {
        return new RegisterPresenter();
    }

    @Override
    public void showSnakBar(String msg, int type) {
        SnackBarUtil.ShortSnackbar(mActivityRegister, msg, type).show();
    }


    @OnClick({R.id.btn_getVerifyCode, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getVerifyCode:
                mPresenter.getVCode(mEditPhone.getText().toString().trim());
                break;
            case R.id.btn_register:
                mPresenter.register(
                        mEditPhone.getText().toString().trim(),
                        mEditVcode.getText().toString().trim(),
                        mEditNickName.getText().toString().trim(),
                        mEditPwd.getText().toString().trim(),
                        mEditRepwd.getText().toString().trim()
                );
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                msg.what--;
                mBtnGetVerifyCode.setText(msg.what + "");
                mHandler.sendEmptyMessageDelayed(msg.what, 1000);
            } else {
                mBtnGetVerifyCode.setText(getString(R.string.button_getVcode));
                mBtnGetVerifyCode.setEnabled(true);
            }
        }
    };

    @Override
    public void setBtnEnable() {
        mHandler.sendEmptyMessage(60);
        mBtnGetVerifyCode.setEnabled(false);
    }

    @Override
    public void registerSuccess() {
        Snackbar snackbar = SnackBarUtil.
                LongSnackbar(mActivityRegister,
                        getString(R.string.registersuccess), 1);
        snackbar.setAction("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(SC.USER_NAME, mEditPhone.getText().toString().trim());
                bundle.putString(SC.USER_PWD, mEditPwd.getText().toString().trim());
                jumpTo(RegisterActivity.this,
                        LoginActivity.class,
                        bundle, true);
            }
        });
        snackbar.show();
    }
}
