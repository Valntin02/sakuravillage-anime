package com.sakuravillage.network.api;

import com.sakuravillage.network.ApiConfig;
import com.sakuravillage.network.model.ApiResponse;
import com.sakuravillage.network.model.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * 用户认证API服务接口
 */
public interface AuthApi {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     */
    @POST(ApiConfig.Endpoints.LOGIN)
    @FormUrlEncoded
    Call<ApiResponse<User>> login(
            @Field("username") String username,
            @Field("password") String password
    );

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     */
    @POST(ApiConfig.Endpoints.REGISTER)
    @FormUrlEncoded
    Call<ApiResponse<User>> register(
            @Field("username") String username,
            @Field("password") String password
    );

    /**
     * 上传头像
     * @param file 头像文件
     * @param urlFlag 是否返回URL
     * @param userId 用户ID（从token获取）
     */
    @Multipart
    @POST(ApiConfig.Endpoints.UPLOAD_AVATAR)
    Call<ApiResponse<String>> uploadAvatar(
            @Part MultipartBody.Part file,
            @Part("url_flag") RequestBody urlFlag,
            @Part("user_id") RequestBody userId
    );

    /**
     * 获取公告
     */
    @GET(ApiConfig.Endpoints.GET_PROFILE)
    Call<ApiResponse<Map<String, Object>>> getAnnouncement();

    /**
     * 切换收藏状态（追番/取消追番）
     * @param vodId 视频ID
     * @param userId 用户ID（从token获取）
     */
    @POST(ApiConfig.Endpoints.TOGGLE_STAR)
    @FormUrlEncoded
    Call<ApiResponse<Object>> toggleStar(
            @Field("vodId") int vodId
    );

    /**
     * 添加/更新播放记录
     * @param vodId 视频ID
     * @param episodeIndex 集数索引
     */
    @POST(ApiConfig.Endpoints.ADD_PLAY_RECORD)
    @FormUrlEncoded
    Call<ApiResponse<Object>> addPlayRecord(
            @Field("vodId") int vodId,
            @Field("episodeIndex") int episodeIndex
    );

    /**
     * 获取播放记录
     */
    @GET(ApiConfig.Endpoints.GET_PLAY_RECORD)
    Call<ApiResponse<Object>> getPlayRecord();

    /**
     * 获取用户收藏
     */
    @GET(ApiConfig.Endpoints.GET_USER_STAR)
    Call<ApiResponse<Object>> getUserStar();
}
