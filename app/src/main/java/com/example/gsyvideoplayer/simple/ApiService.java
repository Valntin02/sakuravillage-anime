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

    @POST("/api.php/danmaku/get")
    Call<DanmakuResModel> requestData(@Body DanmakuReqModel requestBody);

    @POST("/api.php/danmaku/send_danmaku")
    Call<JsonResModel> sendDanmaku(@Body DanmakuReqModel requestBody,@Header("Authorization") String token);
    @GET("/api.php/Vodlist/get_weekly")
    Call<VodResModel> requestVodData(@Query("vod_weekday") String vod_weekday);

    @GET("/api.php/Vodlist/get_today")
    Call<VodResModel> requestVodData();
    @POST("/api.php/Login/login")
    Call<UserResModel> login(@Body RequestBody body);

    @POST("/api.php/Login/register")
    Call<UserResModel> register(@Body RequestBody body);

    @Multipart
    @POST("/api.php/Login/uploadAvatar")
    Call<JsonResModel> requestUploadAvatar(
        @Part("user_id") RequestBody userId,
        @Part("url_flag") RequestBody urlFlag,
        @Part MultipartBody.Part input
    );
    @GET("/api.php/Login/get_anno")
    Call<JsonResModel> requestAnnoData();

    @GET("/api.php/Comment/comment_vodId")
    Call<CommentResModel> requestComment(@Query("comment_rid") int comment_rid);

    @POST("/api.php/Comment/comment_userAvatar")
    Call<UserAvatarResModel> requestUserAvatar(@Body RequestBody body);

    @POST("/api.php/Comment/replyVod_comment")
    Call<JsonResModel> requestReplyVod(@Body CommentData body,@Header("Authorization") String token);

    @POST("/api.php/Comment/comment_UpDown")
    Call<JsonResModel> requestCommentUpDown(@Body CommentData body,@Header("Authorization") String token);

    @POST("/api.php/Comment/comment_report")
    Call<JsonResModel> requestCommentReport(@Body CommentData body,@Header("Authorization") String token);

    @GET("/api.php/vodlist/suggest")
    Call<Map<String, Object>> requestSuggestData(@Query("input_search") String wd);

    @GET("/api.php/Vodlist/searchVod")
    Call<VodResModel> requestRearchVodData(@Query("input_search") String wd);
    @GET("/api.php/Vodlist/vodlistPage")
    Call<VodPageResModel> requestVideoPage(@Query("page") int page,@Query("limit") int limit);

    @POST("/api.php/Login/userSyncMyStar")
    Call<JsonResModel> syncStarRecords(@Body List<MyStarRecord> records);

    @POST("/api.php/Login/userSyncPlayRecord")
    Call<JsonResModel> syncPlayRecords(@Body List<PlayRecord> records);

    @GET("/api.php/Login/getSyncPlayRecord")
    Call<PlayRecordResModel> requestPlayRecords(@Query("userId") int userId);

    @GET("/api.php/Login/getSyncUserStar")
    Call<MyStarResModel> requestMyStars(@Query("userId") int userId);
}
