package com.zjf.weike.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zjf.weike.R;
import com.zjf.weike.adapter.CRecyclerViewAdapter;
import com.zjf.weike.adapter.CRecyclerViewViewHolder;
import com.zjf.weike.adapter.SelectAblumAdapter;
import com.zjf.weike.adapter.SelectPictureAdapter;
import com.zjf.weike.bean.ImageFolder;
import com.zjf.weike.impl.OnPictureSelectListener;
import com.zjf.weike.presenter.SelectPicturePresenter;
import com.zjf.weike.util.LogUtil;
import com.zjf.weike.util.SnackBarUtil;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.SelectPictureViewImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPictureActivity extends MVPActivity<SelectPicturePresenter> implements SelectPictureViewImp {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView_picture)
    RecyclerView mRecyclerViewPicture;
    @BindView(R.id.recyclerView_folder)
    RecyclerView mRecyclerViewFolder;
    @BindView(R.id.bottom_sheet)
    FrameLayout mBottomSheet;
    @BindView(R.id.fab_done)
    FloatingActionButton mFab_done;
    @BindView(R.id.activity_select_picture)
    CoordinatorLayout mActivitySelectPicture;
    @BindView(R.id.switcher)
    TextView mSwitcher;

    private ProgressDialog mDialog;
    private BottomSheetBehavior mBehavior;
    private SelectPictureAdapter mPictureAdapter;
    private SelectAblumAdapter mAblumAdapter;
    private List<String> mPictures;
    private List<ImageFolder> mFolders;
    private int mBeHaviorHeight;
    private String currentFolder;
    private ArrayList<String> mAlreadhave;


    @Override
    public void initVariables() {
        super.initVariables();
        Intent intent = getIntent();
        if (intent != null) {
            mAlreadhave = intent.getStringArrayListExtra("alreadhave");
        }
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage(getString(R.string.progressdiolog_p));
        mDialog.setTitle(getString(R.string.hint));
        mDialog.show();
        mPictures = new ArrayList<>();
        mFolders = new ArrayList<>();
        mPictureAdapter = new SelectPictureAdapter(this, mPictures, R.layout.select_photo);
        mAblumAdapter = new SelectAblumAdapter(this, mFolders, R.layout.select_folder);
        mPictureAdapter.setAlreadhave(mAlreadhave);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_select_picture);
        ButterKnife.bind(this);
        mToolbar.setTitle(getString(R.string.selectpicture));
        mToolbar.setSubtitle(getString(R.string.selectnum_hint)
                + mAlreadhave.size() + getString(R.string.selectnum_half));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBehavior = BottomSheetBehavior.from(mBottomSheet);
        RecyclerView.LayoutManager pictureManager = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager ablumManager = new LinearLayoutManager(this);
        mRecyclerViewPicture.setLayoutManager(pictureManager);
        mRecyclerViewPicture.setAdapter(mPictureAdapter);
        mRecyclerViewFolder.setLayoutManager(ablumManager);
        mRecyclerViewFolder.setAdapter(mAblumAdapter);
        mBeHaviorHeight = mBehavior.getPeekHeight();
    }

    @Override
    public void setListener() {
        mPictureAdapter.setListener(new OnPictureSelectListener() {
            @Override
            public void pictureSelect(int num) {
                getSupportActionBar()
                        .setSubtitle(getString(R.string.selectnum_hint)
                                + num + getString(R.string.selectnum_half));
            }
        });
        mPictureAdapter.setOnItemClickListener(new CRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, CRecyclerViewViewHolder holder, String item) {

            }

        });
        mAblumAdapter.setOnItemClickListener(new CRecyclerViewAdapter.OnItemClickListener<ImageFolder>() {

            @Override
            public void onItemClick(View view, CRecyclerViewViewHolder holder, ImageFolder item) {
                String dir = item.getDir();
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (currentFolder.equals(dir)) {
                    return;
                } else {
                    currentFolder = dir;
                    mPresenter.getPicture(dir);
                    mDialog.show();
                }
            }
        });
    }

    @Override
    public void setPicture(List<String> pictures) {
        mDialog.dismiss();
        mPictureAdapter.flushData(pictures);
    }

    @Override
    public void setFolder(List<ImageFolder> folders) {
        if (folders != null && folders.size() != 0) {
            mAblumAdapter.flushData(folders);
            currentFolder = folders.get(0).getDir();
            mPresenter.getPicture(folders.get(0).getDir());
        } else {
            showSnakBar(getString(R.string.nopicture), 1);
            mDialog.dismiss();
        }
    }

    @Override
    public void showSnakBar(String msg, int type) {
        Snackbar snackbar = SnackBarUtil.ShortSnackbar(mActivitySelectPicture, msg, type);
        snackbar.show();
        resetBehaviorHeight(snackbar);
        mDialog.dismiss();
    }

    private void resetBehaviorHeight(final Snackbar snackbar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewTreeObserver vto = snackbar.getView().getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    int height = snackbar.getView().getMeasuredHeight();
                    LogUtil.e("snackBarheight", height + "");
                    mBehavior.setPeekHeight(mBeHaviorHeight + height / 2);
                    return true;
                }
            });
        }
    }

    @Override
    public SelectPicturePresenter create() {
        return new SelectPicturePresenter();
    }


    @Override
    public void onPause() {
        super.onPause();
        mDialog.dismiss();
    }


    @OnClick({R.id.fab_done, R.id.switcher})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_done:
                Intent intent = new Intent();
                intent.putStringArrayListExtra("picture", mPictureAdapter.getAlreadhave());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.switcher:
                int state = mBehavior.getState();
                if (state == BottomSheetBehavior.STATE_EXPANDED) {
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
        }
    }
}
