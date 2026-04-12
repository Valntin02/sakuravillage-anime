package com.sakuravillage.network.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * 视频数据模型
 */
public class Vod {
    @SerializedName("vod_id")
    private int vodId;

    @SerializedName("vod_name")
    private String vodName;

    @SerializedName("vod_sub")
    private String vodSub;

    @SerializedName("vod_pic")
    private String vodPic;

    @SerializedName("vod_content")
    private String vodContent;

    @SerializedName("vod_play_url")
    private String vodPlayUrl;

    @SerializedName("type_id")
    private int typeId;

    @SerializedName("type_name")
    private String typeName;

    @SerializedName("vod_time")
    private String vodTime;

    @SerializedName("vod_remarks")
    private String vodRemarks;

    @SerializedName("vod_year")
    private String vodYear;

    @SerializedName("vod_area")
    private String vodArea;

    @SerializedName("vod_director")
    private String vodDirector;

    @SerializedName("vod_actor")
    private String vodActor;

    // Getters and Setters
    public int getVodId() { return vodId; }
    public void setVodId(int vodId) { this.vodId = vodId; }

    public String getVodName() { return vodName; }
    public void setVodName(String vodName) { this.vodName = vodName; }

    public String getVodSub() { return vodSub; }
    public void setVodSub(String vodSub) { this.vodSub = vodSub; }

    public String getVodPic() { return vodPic; }
    public void setVodPic(String vodPic) { this.vodPic = vodPic; }

    public String getVodContent() { return vodContent; }
    public void setVodContent(String vodContent) { this.vodContent = vodContent; }

    public String getVodPlayUrl() { return vodPlayUrl; }
    public void setVodPlayUrl(String vodPlayUrl) { this.vodPlayUrl = vodPlayUrl; }

    public int getTypeId() { return typeId; }
    public void setTypeId(int typeId) { this.typeId = typeId; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getVodTime() { return vodTime; }
    public void setVodTime(String vodTime) { this.vodTime = vodTime; }

    public String getVodRemarks() { return vodRemarks; }
    public void setVodRemarks(String vodRemarks) { this.vodRemarks = vodRemarks; }

    public String getVodYear() { return vodYear; }
    public void setVodYear(String vodYear) { this.vodYear = vodYear; }

    public String getVodArea() { return vodArea; }
    public void setVodArea(String vodArea) { this.vodArea = vodArea; }

    public String getVodDirector() { return vodDirector; }
    public void setVodDirector(String vodDirector) { this.vodDirector = vodDirector; }

    public String getVodActor() { return vodActor; }
    public void setVodActor(String vodActor) { this.vodActor = vodActor; }
}
