package com.sakuravillage.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户数据模型
 */
public class User {
    @SerializedName("user_id")
    private int userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_portrait")
    private String userPortrait;

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("user_password")
    private String userPassword;

    @SerializedName("user_status")
    private int userStatus;

    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserPortrait() { return userPortrait; }
    public void setUserPortrait(String userPortrait) { this.userPortrait = userPortrait; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public int getUserStatus() { return userStatus; }
    public void setUserStatus(int userStatus) { this.userStatus = userStatus; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
