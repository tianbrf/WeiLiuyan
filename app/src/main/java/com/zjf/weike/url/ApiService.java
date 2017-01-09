package com.zjf.weike.url;

import com.zjf.weike.bean.BaseBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author :ZJF
 * @version : 2016-12-26 上午 9:56
 */

public interface ApiService {

    @GET(value = "api/bing_pic")
    Observable<ResponseBody> getBackGround();

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

    @POST(value = "sms")
    Observable<BaseBean> getVCode(@Query("phone") String phone);

    @POST(value = "users")
    Observable<BaseBean> register(@Query("phone") String phone,
                                  @Query("password") String pwd,
                                  @Query("nick_name") String nickName,
                                  @Query("vcode") String vCode);
}
