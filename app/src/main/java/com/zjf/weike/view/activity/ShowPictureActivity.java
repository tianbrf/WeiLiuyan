package com.zjf.weike.view.activity;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zjf.weike.R;
import com.zjf.weike.bean.ImageDetial;
import com.zjf.weike.util.SC;
import com.zjf.weike.view.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowPictureActivity extends BaseActivity {


    @BindView(R.id.img_show)
    ImageView mImgShow;
    @BindView(R.id.bg)
    View mView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ImageDetial mDetial;
    private float mScaleX;
    private float mScaleY;
    private float mDeltaX;
    private float mDeltaY;
    private float src_width, src_height;

    @Override
    public void initVariables() {
        Intent intent = getIntent();
        if (intent != null) {
            mDetial = (ImageDetial) intent.getSerializableExtra(SC.IMAGE_DETIAL);
        }
        src_height = getResources().getDisplayMetrics().heightPixels;
        src_width = getResources().getDisplayMetrics().widthPixels;

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_show_picture);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this)
                .load(mDetial.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mImgShow.setImageDrawable(resource);
                        onUIReady();
                        return true;
                    }
                }).into(mImgShow);

    }

    private void onUIReady() {
        mImgShow.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mImgShow.getViewTreeObserver().removeOnPreDrawListener(this);
                prepareScene();
                runEnterAnimation();
                return true;
            }
        });
    }

    private void prepareScene() {
        int[] screenLocation = new int[2];
        //缩放到起始view大小

        mScaleX = mDetial.getWidth() * 1f / mImgShow.getWidth();
        mScaleY = mDetial.getHeight() * 1f / mImgShow.getHeight();
        mImgShow.setScaleX(mScaleX);
        mImgShow.setScaleY(mScaleY);

        mImgShow.getLocationOnScreen(screenLocation);//获取控件在屏幕中左上角xy的坐标点，并放入screenLocation数组中

        //移动到起始view位置
        mDeltaX = mDetial.getScreenLocation()[0] - screenLocation[0];
        mDeltaY = mDetial.getScreenLocation()[1] - screenLocation[1];
        mImgShow.setTranslationX(mDeltaX);
        mImgShow.setTranslationY(mDeltaY);
    }

    private void runEnterAnimation() {
        mImgShow.setAlpha(1f);
        //执行动画
        ViewPropertyAnimator animator = mImgShow.animate()
                .setDuration(400)
                .setInterpolator(new DecelerateInterpolator())
                .translationX(0)
                .translationY(0);
        if (mScaleX < 0.45 || mScaleY < 0.45) {
            animator.scaleX(1f)
                    .scaleY(1f);
        } else {
            animator.scaleX(mScaleX * 3.3f)
                    .scaleY(mScaleY * 3.3f);
        }
        animator.start();
    }

    private void runExitAnimation() {
        ViewPropertyAnimator animator = mImgShow.animate()
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .alpha(0f)
                .translationX(mDeltaX)
                .translationY(mDeltaY)
                .scaleX(mScaleX)
                .scaleY(mScaleY);
        animator.start();
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ShowPictureActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void setListener() {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runExitAnimation();
            }
        });
    }

    @Override
    public void onBackPressed() {
        runExitAnimation();
    }
}
