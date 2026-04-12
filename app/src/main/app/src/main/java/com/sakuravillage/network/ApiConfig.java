package com.sakuravillage.network;

/**
 * API配置类 - 统一管理后端接口地址
 */
public class ApiConfig {
    // 基础URL - 根据后端文档，实际部署时需要修改
    public static final String BASE_URL = "http://192.168.1.100:8000/api/";

    // 超时设置
    public static final long CONNECT_TIMEOUT = 15L;  // 连接超时15秒
    public static final long READ_TIMEOUT = 30L;      // 读取超时30秒
    public static final long WRITE_TIMEOUT = 15L;     // 写入超时15秒

    // API端点路径
    public static class Endpoints {
        // 用户认证
        public static final String LOGIN = "login/login";
        public static final String REGISTER = "login/register";
        public static final String UPLOAD_AVATAR = "login/upload-avatar";
        public static final String GET_PROFILE = "login/get-anno";

        // 视频相关
        public static final String GET_TODAY = "vodlist/get-today";
        public static final String GET_WEEKLY = "vodlist/get-weekly";
        public static final String SEARCH_VOD = "vodlist/search-vod";
        public static final String SUGGEST = "vodlist/suggest";
        public static final String VODLIST_PAGE = "vodlist/vodlist-page";
        public static final String GET_BY_IDS = "vodlist/get-by-ids";

        // 弹幕相关
        public static final String GET_DANMAKU = "danmaku/get";
        public static final String SEND_DANMAKU = "danmaku/send-danmaku";

        // 评论相关
        public static final String GET_COMMENTS = "comment/comment-vod-id";
        public static final String SEND_COMMENT = "comment/comment-reply";
        public static final String LIKE_COMMENT = "comment/comment-up-down";
        public static final String REPORT_COMMENT = "comment/comment-report";

        // 用户相关
        public static final String TOGGLE_STAR = "login/toggle-star";
        public static final String ADD_PLAY_RECORD = "login/add-play-record";
        public static final String GET_PLAY_RECORD = "login/play-record";
        public static final String GET_USER_STAR = "login/user-star";
    }
}
