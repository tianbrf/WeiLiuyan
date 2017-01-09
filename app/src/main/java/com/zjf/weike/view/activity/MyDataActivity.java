package com.zjf.weike.view.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.zjf.weike.R;
import com.zjf.weike.presenter.MyDataPresener;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.MyDataViewImp;
import com.zjf.weike.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDataActivity extends MVPActivity<MyDataPresener> implements MyDataViewImp {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_head)
    CircleImageView mImgHead;
    @BindView(R.id.text_nickName)
    TextView mTextNickName;
    @BindView(R.id.text_tel)
    TextView mTextTel;
    @BindView(R.id.activity_my_data)
    CoordinatorLayout mActivityMyData;

    @Override
    public void initView() {
        setContentView(R.layout.activity_my_data);
        ButterKnife.bind(this);
        mToolbar.setTitle(getString(R.string.mydata));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void showSnakBar(String msg, int type) {

    }

    @Override
    public MyDataPresener create() {
        return new MyDataPresener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mydata_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_resethead:
                break;
            case R.id.action_resetpwd:
                jumpTo(this, ReGetPasswordActivity.class, false);
                break;
        }

        return true;
    }
}
