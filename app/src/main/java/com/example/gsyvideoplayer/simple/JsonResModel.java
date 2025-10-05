package com.example.gsyvideoplayer.simple;

public class JsonResModel {
    private int code;

    private String data;
    private String msg;

    // Getter 和 Setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {return data;}
    public void setData(String data) {this.data=data;}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    @Override
    public String toString() {
        return "JsonResModel{" +
            "data" +data+
            "code=" + code +
            ", msg=" + msg +
            '}';
    }
}
