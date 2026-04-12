package com.sakuravillage.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * 评论数据模型
 */
public class Comment {
    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("comment_name")
    private String commentName;

    @SerializedName("comment_content")
    private String commentContent;

    @SerializedName("comment_time")
    private long commentTime;

    @SerializedName("comment_up")
    private int commentUp;

    @SerializedName("comment_down")
    private int commentDown;

    @SerializedName("comment_reply")
    private int commentReply;

    @SerializedName("comment_rid")
    private int commentRid;

    @SerializedName("comment_pid")
    private int commentPid;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_portrait")
    private String userPortrait;

    @SerializedName("is_liked")
    private boolean isLiked;

    @SerializedName("is_reported")
    private boolean isReported;

    @SerializedName("reply_target_name")
    private String replyTargetName;

    // Getters and Setters
    public int getCommentId() { return commentId; }
    public void setCommentId(int commentId) { this.commentId = commentId; }

    public String getCommentName() { return commentName; }
    public void setCommentName(String commentName) { this.commentName = commentName; }

    public String getCommentContent() { return commentContent; }
    public void setCommentContent(String commentContent) { this.commentContent = commentContent; }

    public long getCommentTime() { return commentTime; }
    public void setCommentTime(long commentTime) { this.commentTime = commentTime; }

    public int getCommentUp() { return commentUp; }
    public void setCommentUp(int commentUp) { this.commentUp = commentUp; }

    public int getCommentDown() { return commentDown; }
    public void setCommentDown(int commentDown) { this.commentDown = commentDown; }

    public int getCommentReply() { return commentReply; }
    public void setCommentReply(int commentReply) { this.commentReply = commentReply; }

    public int getCommentRid() { return commentRid; }
    public void setCommentRid(int commentRid) { this.commentRid = commentRid; }

    public int getCommentPid() { return commentPid; }
    public void setCommentPid(int commentPid) { this.commentPid = commentPid; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserPortrait() { return userPortrait; }
    public void setUserPortrait(String userPortrait) { this.userPortrait = userPortrait; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    public boolean isReported() { return isReported; }
    public void setReported(boolean reported) { isReported = reported; }

    public String getReplyTargetName() { return replyTargetName; }
    public void setReplyTargetName(String replyTargetName) { this.replyTargetName = replyTargetName; }
}
