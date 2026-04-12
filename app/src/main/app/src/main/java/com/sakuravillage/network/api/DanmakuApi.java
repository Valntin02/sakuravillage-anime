package com.sakuravillage.network.api;

import com.sakuravillage.network.ApiConfig;
import com.sakuravillage.network.model.ApiResponse;
import com.sakuravillage.network.model.Danmaku;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * 弹幕API服务接口
 */
public interface DanmakuApi {
    /**
     * 获取弹幕列表
     * @param vodId 视频ID
     * @param vodNid 视频集数
     */
    @POST(ApiConfig.Endpoints.GET_DANMAKU)
    @FormUrlEncoded
    Call<ApiResponse<List<Map<String, Object>>>> getDanmakus(
            @Field("vod_id") int vodId,
            @Field("vod_nid") int vodNid
    );

    /**
     * 发送弹幕
     * @param vodId 视频ID
     * @param vodNid 视频集数
     * @param content 弹幕内容
     * @param time 弹幕时间（秒）
     * @param color 弹幕颜色（整数）
     * @param type 弹幕类型
     */
    @POST(ApiConfig.Endpoints.SEND_DANMAKU)
    @FormUrlEncoded
    Call<ApiResponse<String>> sendDanmaku(
            @Field("vodId") int vodId,
            @Field("vodNid") int vodNid,
            @Field("content") String content,
            @Field("time") float time,
            @Field("color") int color,
            @Field("type") int type
    );
}
