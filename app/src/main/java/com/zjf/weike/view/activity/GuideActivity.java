package com.zjf.weike.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.zjf.weike.R;
import com.zjf.weike.adapter.FragmentAdapter;
import com.zjf.weike.presenter.GuidePresenter;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.GuideViewImp;
import com.zjf.weike.widget.CircleIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends MVPActivity<GuidePresenter> implements GuideViewImp {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.circleindicator)
    CircleIndicator mCircleindicator;

    private FragmentAdapter mAdapter;


    @Override
    public void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);


    }

    @Override
    public void setListener() {

    }


    @Override
    public GuidePresenter create() {
        return new GuidePresenter();
    }

    @Override
    public void showSnakBar(String msg, int type) {

    }



    @Override
    public void setFragment(List<Fragment> fragments) {
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mCircleindicator.setIndicatorMode(CircleIndicator.Mode.OUTSIDE);
        mCircleindicator.setIndicatorRadius(7);//圆的大小
        mCircleindicator.setIndicatorMargin(10);//间隔
        mCircleindicator.setViewPager(mViewPager, fragments.size());
        mViewPager.setAdapter(mAdapter);
    }
}
