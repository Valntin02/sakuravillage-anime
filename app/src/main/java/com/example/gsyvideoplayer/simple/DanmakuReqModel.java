package com.example.gsyvideoplayer.simple;

public class DanmakuReqModel {

    private Integer vod_id; // 视频ID
    private Integer vod_nid; // 第几集
    private float start_time; // 起始时间
    private float end_time; // 结束时间

    // 构造函数
    public DanmakuReqModel(Integer vod_id, Integer vod_nid, float start_time, float end_time) {
        this.vod_id = vod_id;
        this.vod_nid = vod_nid;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    // Getter 和 Setter
    public Integer getvod_id() {
        return vod_id;
    }

    public void setvod_id(Integer vod_id) {
        this.vod_id = vod_id;
    }

    public Integer getvod_nid() {
        return vod_nid;
    }

    public void setvod_nid(Integer vod_nid) {
        this.vod_nid = vod_nid;
    }

    public float getstart_time() {
        return start_time;
    }

    public void setstart_time(float start_time) {
        this.start_time = start_time;
    }

    public float getend_time() {
        return end_time;
    }

    public void setend_time(float end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "DanmakuReqModel{" +
            "vod_id=" + vod_id +
            ", vod_nid=" + vod_nid +
            ", start_time=" + start_time +
            ", end_time=" + end_time +
            '}';
    }
}
