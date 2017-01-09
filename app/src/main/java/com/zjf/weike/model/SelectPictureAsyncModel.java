package com.zjf.weike.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.zjf.weike.App;
import com.zjf.weike.R;
import com.zjf.weike.bean.ImageFolder;
import com.zjf.weike.impl.OnAsyncModelListener;
import com.zjf.weike.model.modelimp.SelectPictureAsyncModelImp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :ZJF
 * @version : 2016-12-22 下午 3:28
 */

public class SelectPictureAsyncModel implements SelectPictureAsyncModelImp {

    private List<String> mPictures;
    private List<ImageFolder> mFolder;

    public SelectPictureAsyncModel() {
        mPictures = new ArrayList<>();
        mFolder = new ArrayList<>();
    }


    /**
     * 根据文件夹名称获取图片
     *
     * @param folderName
     * @param listener
     */
    @Override
    public void getPicture(final String folderName, final OnAsyncModelListener<List<String>> listener) {
        mPictures.clear();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                File file = new File(folderName);
                File[] files = file.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg")
                                || filename.endsWith(".png")
                                || filename.endsWith(".jpeg"))
                            return true;
                        return false;
                    }
                });
                for (int i = 0; i < files.length; i++) {
                    e.onNext(files[i].getAbsolutePath());
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        mPictures.add(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(App.getInstance().getString(R.string.failure), 3);
                    }

                    @Override
                    public void onComplete() {
                        listener.onSuccess(mPictures);
                    }
                });
    }


    /**
     * 第一次获取文件夹
     *
     * @param listener
     */
    @Override
    public void getData(final OnAsyncModelListener<List<ImageFolder>> listener) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            listener.onFailure(App.getInstance().getString(R.string.nosdcard), 3);
            return;
        }
        Observable.create(new ObservableOnSubscribe<List<ImageFolder>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ImageFolder>> e) throws Exception {

                String firstImage = null;
                int mPicsSize = 0;
                HashSet<String> mDirPaths = new HashSet<>();

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = App.getInstance()
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFolder imageFolder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFolder = new ImageFolder();
                        imageFolder.setDir(dirPath);
                        imageFolder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;

                    imageFolder.setCount(picSize);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mFolder.add(0, imageFolder);
                    } else {
                        mFolder.add(imageFolder);
                    }
                }
                mCursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                e.onNext(mFolder);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ImageFolder>>() {
                    @Override
                    public void accept(List<ImageFolder> folder) throws Exception {
                        listener.onSuccess(folder);
                    }
                });
    }
}
