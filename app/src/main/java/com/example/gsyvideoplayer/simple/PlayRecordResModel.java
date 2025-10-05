package com.example.gsyvideoplayer.simple;

import java.util.List;

public class PlayRecordResModel {
    private int code;          // 状态码
    private List<PlayRecord> data;
    private String msg;
    // Getter 和 Setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public List<PlayRecord> getData() {
        return data;
    }

    public void setData(List<PlayRecord> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PlayRecordResModel{" +
            "code=" + code +
            ", data=" + data +
            '}';
    }
}
