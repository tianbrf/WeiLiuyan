package com.zjf.weike.model;

import android.support.v4.app.Fragment;

import com.zjf.weike.model.modelimp.BaseModelImp;
import com.zjf.weike.view.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ZJF
 * @version : 2016-12-05 下午 4:16
 */

public class GuideModel implements BaseModelImp<Fragment> {

    private List<Fragment> mFragments;

    public GuideModel() {
        mFragments = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            GuideFragment fragment = new GuideFragment();
            fragment.setPage(i);
            mFragments.add(fragment);
        }
    }

    @Override
    public List<Fragment> getData() {
        return mFragments;
    }
}
