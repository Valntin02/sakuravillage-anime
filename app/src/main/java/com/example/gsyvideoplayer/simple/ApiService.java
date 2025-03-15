package com.example.gsyvideoplayer.simple;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api.php/danmaku/get")
    Call<DanmakuResModel> requestData(@Body DanmakuReqModel requestBody);

    @GET("/api.php/Vodlist/get_weekly")
    Call<VodResModel> requestVodData(@Query("vod_weekday") String vod_weekday);

    @GET("/api.php/Vodlist/get_today")
    Call<VodResModel> requestVodData();
    @POST("/api.php/Login/login")
    Call<UserResModel> login(@Body RequestBody body);
}
