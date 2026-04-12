package com.sakuravillage.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * 弹幕数据模型
 * 注意：后端返回的颜色为整数，需要转换为十六进制字符串供弹幕库使用
 */
public class Danmaku {
    @SerializedName("text")
    private String text;

    @SerializedName("color")
    private int color;  // 后端返回整数颜色，如 16777215

    @SerializedName("time")
    private float time;

    @SerializedName("type")
    private int type;

    /**
     * 获取十六进制格式的颜色字符串
     * 例如: 16777215 -> "#FFFFFF"
     */
    public String getColorHex() {
        return String.format("#%06X", color);
    }

    // Getters and Setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public float getTime() { return time; }
    public void setTime(float time) { this.time = time; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    /**
     * 将十六进制颜色字符串转换为整数
     * 例如: "#FFFFFF" -> 16777215
     */
    public static int hexToIntColor(String hexColor) {
        if (hexColor == null || hexColor.isEmpty()) {
            return 16777215; // 默认白色
        }
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
        }
        return Integer.parseInt(hexColor, 16);
    }
}
