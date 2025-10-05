package com.example.gsyvideoplayer;

import java.io.File;

public class CachedVideo {
    private File videoFile;
    private String coverUrl; // 视频封面图 URL

    public CachedVideo(File videoFile, String coverUrl) {
        this.videoFile = videoFile;
        this.coverUrl = coverUrl;
    }

    public File getVideoFile() {
        return videoFile;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
}

