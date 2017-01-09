package com.zjf.weike.view.activity;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.zjf.weike.R;
import com.zjf.weike.presenter.LoginPresenter;
import com.zjf.weike.util.SnackBarUtil;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.LoginViewImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MVPActivity<LoginPresenter> implements LoginViewImp {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_userName)
    EditText mEditUserName;
    @BindView(R.id.textInput_userName)
    TextInputLayout mTextInputUserName;
    @BindView(R.id.edit_userPwd)
    EditText mEditUserPwd;
    @BindView(R.id.textInput_userPwd)
    TextInputLayout mTextInputUserPwd;
    @BindView(R.id.activity_login)
    CoordinatorLayout mActivityLogin;

    @Override
    public void initVariables() {
        super.initVariables();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mToolbar.setTitle(getResources().getString(R.string.button_login));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setListener() {

    }


    @OnClick({R.id.btn_login, R.id.text_register, R.id.text_forgetPwd, R.id.img_login_wechat, R.id.img_login_qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.login(mEditUserName.getText().toString().trim(),
                        mEditUserPwd.getText().toString().trim());
                break;
            case R.id.text_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.text_forgetPwd:
                startActivity(new Intent(LoginActivity.this, ReGetPasswordActivity.class));
                break;
            case R.id.img_login_wechat:
                break;
            case R.id.img_login_qq:
                break;
        }
    }

    @Override
    public LoginPresenter create() {
        return new LoginPresenter();
    }

    @Override
    public void showSnakBar(String msg, int type) {
        SnackBarUtil.ShortSnackbar(mActivityLogin, msg, 1).show();
    }

    @Override
    public void loginToMain() {
        jumpTo(this, MainActivity.class, true);
    }
}
