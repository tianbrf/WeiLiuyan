package com.zjf.weike.presenter;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.zjf.weike.App;
import com.zjf.weike.R;
import com.zjf.weike.presenter.base.BasePresenter;
import com.zjf.weike.util.SC;
import com.zjf.weike.view.viewimp.PublishViewImp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author :ZJF
 * @version : 2016-12-21 下午 5:02
 */

public class PublishPresenter implements BasePresenter<PublishViewImp> {

    public static final int ABLUM_CODE = 1001;
    public static final int CAMERA_CODE = 2002;
    public static final int LOCATION_CODE = 3003;
    private File mOutputImage;
    private boolean isAttached = false;

    private PublishViewImp mView;

    public PublishPresenter() {
    }

    public void publishInfo() {
        // 上传数据
        mView.published();
    }

    public void getAblumPicture(Intent intent, ArrayList<String> mBitmaps) {
        intent.putStringArrayListExtra("alreadhave", mBitmaps);
        mView.jumpToForResult(intent, ABLUM_CODE);
        mView.dimissBottomSheetDialog();
    }

    public void getLoaction(Intent intent) {
        mView.jumpToForResult(intent, LOCATION_CODE);
    }

    public void startCamera(File outputImage, int count) {
        this.mOutputImage = outputImage;
        mView.dimissBottomSheetDialog();
        if (count < 9) {
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri imageUri = Uri.fromFile(outputImage);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            mView.jumpToForResult(intent, CAMERA_CODE);
        } else {
            mView.showSnakBar(App.getInstance().getString(R.string.nomore), 1);
        }
    }


    @Override
    public void onViewAttached(PublishViewImp view) {
        this.mView = view;
        isAttached = true;
    }

    @Override
    public void onViewDeached() {
        isAttached = false;
    }

    @Override
    public void onDestroyed() {
        isAttached = false;
        mView = null;
        if (mOutputImage != null) {
            mOutputImage = null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, int resultOk) {
        if (resultCode == resultOk) {
            if (requestCode == ABLUM_CODE) {
                if (data != null) {
                    ArrayList<String> picture = data.getStringArrayListExtra("picture");
                    mView.flushData(picture);
                }
            } else if (requestCode == CAMERA_CODE) {
                String path = mOutputImage.getAbsolutePath();
                mView.addCameraPicture(path);
            } else if (requestCode == LOCATION_CODE) {
                if (data != null) {
                    String locationName = data.getStringExtra(SC.LOCATION_NAME);
                    mView.setLoactionName(locationName);
                }
            }
        }
    }
}
