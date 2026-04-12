package com.sakuravillage.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * 统一API响应格式
 * 对应后端响应: { "code": 200, "message": "...", "data": ... }
 */
public class ApiResponse<T> {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    @SerializedName("msg")
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message != null ? message : msg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return code == 200;
    }
}
