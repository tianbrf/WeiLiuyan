package com.zjf.weike.model;

import com.zjf.weike.bean.BaseBean;
import com.zjf.weike.impl.OnAsyncModelListener;
import com.zjf.weike.model.modelimp.RegisterModelImp;
import com.zjf.weike.url.ApiService;
import com.zjf.weike.util.LogUtil;
import com.zjf.weike.util.RetrofitUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @author :ZJF
 * @version : 2016-12-28 下午 2:49
 */

public class RegisterModel implements RegisterModelImp {


    public void getVCode(String phoneNum, final OnAsyncModelListener<String> listener) {
        Retrofit client = RetrofitUtil.getClient();
        ApiService service = client.create(ApiService.class);
        service.getVCode(phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean bean) throws Exception {
                        LogUtil.e("123", bean.getMessage());
                        if ("ok".equals(bean.getMessage())) {
                            listener.onSuccess(bean.getMessage());
                        } else {
                            listener.onFailure(bean.getMessage(), 1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e("456");
                    }
                });
    }

    @Override
    public void register(String vCode, String phoneNum, String nickName, String pwd, final OnAsyncModelListener<String> listener) {
        Retrofit client = RetrofitUtil.getClient();
        ApiService service = client.create(ApiService.class);
        service.register(phoneNum, pwd, nickName, vCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean bean) throws Exception {
                        if ("ok".equals(bean.getMessage())) {
                            listener.onSuccess(bean.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.onFailure("", 1);
                    }
                });
    }


}
