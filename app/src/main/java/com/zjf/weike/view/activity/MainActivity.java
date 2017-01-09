package com.zjf.weike.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zjf.weike.R;
import com.zjf.weike.impl.OnAsyncModelListener;
import com.zjf.weike.presenter.MainPresenter;
import com.zjf.weike.util.DialogUtil;
import com.zjf.weike.util.SC;
import com.zjf.weike.util.SnackBarUtil;
import com.zjf.weike.view.activity.base.MVPActivity;
import com.zjf.weike.view.viewimp.MainViewImp;
import com.zjf.weike.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MVPActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, MainViewImp {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SharedPreferences mPreferences;
    private CircleImageView mHeadIcon;
    private TextView mNickName;
    private TextView mTel;


    @Override
    public void initVariables() {
        super.initVariables();
        mPreferences = getSharedPreferences(SC.CONFIG, Context.MODE_PRIVATE);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View mNavViewHeaderView = mNavView.getHeaderView(0);
        mHeadIcon = (CircleImageView) mNavViewHeaderView.findViewById(R.id.imageView);
        mNickName = (TextView) mNavViewHeaderView.findViewById(R.id.text_nickName);
        mTel = (TextView) mNavViewHeaderView.findViewById(R.id.text_tel);
        mNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void setListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 是否登录
                jumpTo(MainActivity.this, PublishActivity.class, false);
            }
        });

        mHeadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 是否登录
                jumpTo(MainActivity.this, MyDataActivity.class, false);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mPresenter.judgeExit(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            DialogUtil.showSettingHostDialog(this, new OnAsyncModelListener<String>() {
                @Override
                public void onFailure(String msg, int type) {

                }

                @Override
                public void onSuccess(String msg) {
                    mPreferences
                            .edit()
                            .putString("http://" + SC.BASE_HOST, msg + "/")
                            .apply();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_all) {

        } else if (id == R.id.nav_my) {

        } else if (id == R.id.nav_friend) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_download) {
            String string = mPreferences.getString(SC.TODAY_BG, null);
            mPresenter.downLoadePicture(string);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public MainPresenter create() {
        return new MainPresenter();
    }

    @Override
    public void showSnakBar(String msg, int type) {
        SnackBarUtil.ShortSnackbar(mCoordinatorLayout, msg, type).show();
    }

    @Override
    public void exit() {
        finish();
    }

}
