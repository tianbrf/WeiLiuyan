package com.zjf.weike.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.zjf.weike.R;
import com.zjf.weike.adapter.TabAdapter;
import com.zjf.weike.presenter.SelectLocationPresenter;
import com.zjf.weike.util.SC;
import com.zjf.weike.util.SnackBarUtil;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.fragment.POIFragment;
import com.zjf.weike.view.viewimp.SelectLocationViewImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLocationActivity extends MVPActivity<SelectLocationPresenter>
        implements LocationSource, AMapLocationListener, SelectLocationViewImp {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.bottom_sheet)
    FrameLayout mBottomSheet;
    @BindView(R.id.fab_done)
    FloatingActionButton mFabDone;
    @BindView(R.id.activity_map)
    CoordinatorLayout mActivityMap;

    private AMap aMap;//定义一个地图对象
    private OnLocationChangedListener mListener;
    private AMapLocationClient mClient;
    private AMapLocationClientOption mOption;
    private ProgressDialog mDialog;
    private TabAdapter mAdapter;
    private List<Fragment> mFragments;
    private double mLongitude;
    private double mLatitude;


    @Override
    public void initVariables() {
        super.initVariables();
        mDialog = new ProgressDialog(this);
        mDialog.setTitle(getString(R.string.hint));
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage(getString(R.string.getlocation));
        mDialog.show();

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mToolbar.setTitle(getString(R.string.chooselocation));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMap.onCreate(mBundle);
        if (aMap == null) {
            aMap = mMap.getMap();
        }

        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.ic_location_on_blue_500_24dp));
        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mClient == null) {
            mClient = new AMapLocationClient(this);
            mOption = new AMapLocationClientOption();
            mClient.setLocationListener(this);
            mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mOption.setOnceLocationLatest(true);
            mClient.setLocationOption(mOption);
            mClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);
                String code = amapLocation.getCityCode();
                mLongitude = amapLocation.getLongitude();
                mLatitude = amapLocation.getLatitude();
                mPresenter.getData(code, mLatitude, mLongitude);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            } else {
                showSnakBar(getString(R.string.locationfailure), 1);
            }
            mDialog.dismiss();
        }
    }

    @Override
    public void setViewPagerFagment(List<Fragment> fragments) {
        this.mFragments = fragments;
        mAdapter = new TabAdapter(getSupportFragmentManager()
                , fragments, mPresenter.getTitle());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }


    @OnClick(R.id.fab_done)
    public void onClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(SC.LOCATION_NAME, mToolbar.getSubtitle().toString());
        intent.putExtra(SC.LOCATION_LATITUDE, mLatitude);
        intent.putExtra(SC.LOCATION_LONGITUDE, mLongitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
        if (null != mClient) {
            mClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        deactivate();
        mMap.onPause();
    }


    @Override
    public void deactivate() {
        mListener = null;
        if (mClient != null) {
            mClient.stopLocation();
            mClient.onDestroy();
        }
        mClient = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }

    @Override
    public SelectLocationPresenter create() {
        return new SelectLocationPresenter();
    }

    @Override
    public void showSnakBar(String msg, int type) {
        SnackBarUtil.ShortSnackbar(mActivityMap, msg, type).show();
    }

    public void setSubTitle(String title) {
        mToolbar.setSubtitle(title);
        for (Fragment fragment : mFragments) {
            ((POIFragment) fragment).notifyCheck(title);
        }
    }
}
