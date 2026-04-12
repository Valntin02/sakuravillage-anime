package com.sakuravillage.network.api;

import com.sakuravillage.network.ApiConfig;
import com.sakuravillage.network.model.ApiResponse;
import com.sakuravillage.network.model.Comment;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * 评论API服务接口
 */
public interface CommentApi {
    /**
     * 获取视频评论列表
     * @param commentRid 视频ID
     * @param userId 用户ID（可选）
     */
    @GET(ApiConfig.Endpoints.GET_COMMENTS)
    Call<ApiResponse<List<Comment>>> getComments(
            @Query("comment_rid") int commentRid,
            @Query("user_id") Integer userId
    );

    /**
     * 获取子评论（分页）
     * @param parentId 父评论ID
     * @param userId 用户ID（可选）
     * @param page 页码
     * @param pageSize 每页数量
     */
    @GET("/comment/replies")
    Call<ApiResponse<Map<String, Object>>> getCommentReplies(
            @Query("parent_id") int parentId,
            @Query("user_id") Integer userId,
            @Query("page") int page,
            @Query("page_size") int pageSize
    );

    /**
     * 发表评论
     * @param commentRid 视频ID
     * @param commentPid 父评论ID（0表示根评论）
     * @param userId 用户ID
     * @param commentName 用户名
     * @param commentContent 评论内容
     * @param commentTime 评论时间
     * @param commentAtUser 被回复的用户ID（可选）
     */
    @POST(ApiConfig.Endpoints.SEND_COMMENT)
    @FormUrlEncoded
    Call<ApiResponse<Comment>> sendComment(
            @Field("commentRid") int commentRid,
            @Field("commentPid") int commentPid,
            @Field("userId") int userId,
            @Field("commentName") String commentName,
            @Field("commentContent") String commentContent,
            @Field("commentTime") long commentTime,
            @Field("commentAtUser") Integer commentAtUser
    );

    /**
     * 评论点赞/取消点赞
     * @param commentId 评论ID
     * @param isLiked 是否点赞
     * @param userId 用户ID
     */
    @POST(ApiConfig.Endpoints.LIKE_COMMENT)
    @FormUrlEncoded
    Call<ApiResponse<String>> likeComment(
            @Field("commentId") int commentId,
            @Field("isLiked") boolean isLiked,
            @Field("userId") int userId
    );

    /**
     * 举报评论
     * @param commentId 评论ID
     * @param userId 用户ID
     */
    @POST(ApiConfig.Endpoints.REPORT_COMMENT)
    @FormUrlEncoded
    Call<ApiResponse<String>> reportComment(
            @Field("commentId") int commentId,
            @Field("userId") int userId
    );
}
