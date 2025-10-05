package com.example.gsyvideoplayer.simple;

import com.example.gsyvideoplayer.VodData;
import java.util.List;

public class VodPageResModel {

    private int code;              // 状态码
    private List<VodData> data;   // 视频数据
    private int page;             // 当前页码
    private int limit;            // 每页条数
    private int total;            // 总记录数
    private int totalPage;        // 总页数

    // 构造方法（完整）
    public VodPageResModel(int code, int page, int limit, int total, int totalPage, List<VodData> data) {
        this.code = code;
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.totalPage = totalPage;
        this.data = data;
    }

    // Getter 和 Setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<VodData> getVodDataList() {
        return data;
    }

    public void setData(List<VodData> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    @Override
    public String toString() {
        return "VodPageResModel{" +
            "code=" + code +
            ", page=" + page +
            ", limit=" + limit +
            ", total=" + total +
            ", totalPage=" + totalPage +
            ", data=" + data +
            '}';
    }
}
