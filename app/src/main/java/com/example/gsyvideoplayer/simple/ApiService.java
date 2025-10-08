package com.example.gsyvideoplayer.simple;

import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/danmaku/get")
    Call<DanmakuResModel> requestData(@Body DanmakuReqModel requestBody);

    @POST("/api/danmaku/send_danmaku")
    Call<JsonResModel> sendDanmaku(@Body DanmakuReqModel requestBody,@Header("Authorization") String token);
    @GET("/api/vodlist/get_weekly")
    Call<VodResModel> requestVodData(@Query("vod_weekday") String vod_weekday);

    @GET("/api/vodlist/get_today")
    Call<VodResModel> requestVodData();
    @POST("/api/login/login")
    Call<UserResModel> login(@Body RequestBody body);

    @POST("/api/login/register")
    Call<UserResModel> register(@Body RequestBody body);

    @Multipart
    @POST("/api/login/uploadAvatar")
    Call<JsonResModel> requestUploadAvatar(
        @Part("user_id") RequestBody userId,
        @Part("url_flag") RequestBody urlFlag,
        @Part MultipartBody.Part input
    );
    @GET("/api/login/get_anno")
    Call<JsonResModel> requestAnnoData();

    @GET("/api/comment/comment_vodId")
    Call<CommentResModel> requestComment(@Query("comment_rid") int comment_rid);

    @POST("/api/comment/comment_userAvatar")
    Call<UserAvatarResModel> requestUserAvatar(@Body RequestBody body);

    @POST("/api/comment/replyVod_comment")
    Call<JsonResModel> requestReplyVod(@Body CommentData body,@Header("Authorization") String token);

    @POST("/api/comment/comment_UpDown")
    Call<JsonResModel> requestCommentUpDown(@Body CommentData body,@Header("Authorization") String token);

    @POST("/api/comment/comment_report")
    Call<JsonResModel> requestCommentReport(@Body CommentData body,@Header("Authorization") String token);

    @GET("/api/vodlist/suggest")
    Call<Map<String, Object>> requestSuggestData(@Query("input_search") String wd);

    @GET("/api/vodlist/searchVod")
    Call<VodResModel> requestRearchVodData(@Query("input_search") String wd);
    @GET("/api/vodlist/vodlistPage")
    Call<VodPageResModel> requestVideoPage(@Query("page") int page,@Query("limit") int limit);

    @POST("/api/login/userSyncMyStar")
    Call<JsonResModel> syncStarRecords(@Body List<MyStarRecord> records);

    @POST("/api/login/userSyncPlayRecord")
    Call<JsonResModel> syncPlayRecords(@Body List<PlayRecord> records);

    @GET("/api/login/getSyncPlayRecord")
    Call<PlayRecordResModel> requestPlayRecords(@Query("userId") int userId);

    @GET("/api/login/getSyncUserStar")
    Call<MyStarResModel> requestMyStars(@Query("userId") int userId);
}
