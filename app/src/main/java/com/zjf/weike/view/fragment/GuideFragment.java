package com.zjf.weike.view.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjf.weike.R;
import com.zjf.weike.util.SC;
import com.zjf.weike.view.activity.LoginActivity;
import com.zjf.weike.view.activity.MainActivity;
import com.zjf.weike.view.activity.RegisterActivity;
import com.zjf.weike.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首次启动引导页
 */
public class GuideFragment extends BaseFragment {


    @BindView(R.id.bg)
    ImageView mBg;
    @BindView(R.id.img_top)
    TextView mImgTop;
    @BindView(R.id.img_middle)
    TextView mImgMiddle;
    @BindView(R.id.img_bottom)
    TextView mImgBottom;
    @BindView(R.id.btn_guide)
    Button mBtnGuide;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    private int page;

    public GuideFragment() {
        // Required empty public constructor
    }


    public void setPage(int page) {
        this.page = page;
    }


    @Override
    public void initVariables() {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, view);
        setVisibility();
        return view;

    }

    private void setVisibility() {
        switch (page) {
            case 1:
                Glide.with(this)
                        .load(R.drawable.guide_bg1)
                        .into(mBg);
                break;
            case 2:
                mImgTop.setVisibility(View.INVISIBLE);
                mImgBottom.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(R.drawable.guide_bg2)
                        .into(mBg);
                break;
            case 3:
                mImgTop.setVisibility(View.INVISIBLE);
                mImgMiddle.setVisibility(View.VISIBLE);
                mBtnGuide.setVisibility(View.VISIBLE);
                mBtnRegister.setVisibility(View.VISIBLE);
                mBtnLogin.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(R.drawable.guide_bg3)
                        .into(mBg);
                break;
        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void loaderData() {

    }

    @OnClick({R.id.btn_guide, R.id.btn_register, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_guide:
                jumpTo(getActivity(), MainActivity.class, true);
                break;
            case R.id.btn_register:
                jumpTo(getActivity(), RegisterActivity.class, true);
                break;
            case R.id.btn_login:
                jumpTo(getActivity(), LoginActivity.class, true);
                break;
        }
        saveConfig();
    }

    private void saveConfig() {
        SharedPreferences preferences = getActivity()
                .getSharedPreferences(SC.CONFIG, Context.MODE_PRIVATE);
        preferences
                .edit()
                .putBoolean(preferences.getString(SC.VERSION_CODE, SC.DEFAULT_VERSION_CODE), false)
                .putString(SC.USER_NAME, null)
                .putString(SC.USER_PWD, null)
                .apply();
    }
}
