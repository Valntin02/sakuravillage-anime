package com.sakuravillage.network.api;

import com.sakuravillage.network.ApiConfig;
import com.sakuravillage.network.model.ApiResponse;
import com.sakuravillage.network.model.Vod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 视频API服务接口
 */
public interface VodApi {
    /**
     * 获取今日推荐视频
     */
    @GET(ApiConfig.Endpoints.GET_TODAY)
    Call<ApiResponse<List<Vod>>> getTodayVods();

    /**
     * 根据星期获取视频列表
     * @param vodWeekday 星期几（如"一"、"二"...）
     */
    @GET(ApiConfig.Endpoints.GET_WEEKLY)
    Call<ApiResponse<List<Vod>>> getWeeklyVods(@Query("vod_weekday") String vodWeekday);

    /**
     * 搜索视频
     * @param inputSearch 搜索关键词
     */
    @GET(ApiConfig.Endpoints.SEARCH_VOD)
    Call<ApiResponse<List<Vod>>> searchVod(@Query("input_search") String inputSearch);

    /**
     * 视频搜索建议
     * @param inputSearch 搜索关键词
     */
    @GET(ApiConfig.Endpoints.SUGGEST)
    Call<List<String>> suggestVod(@Query("input_search") String inputSearch);

    /**
     * 分页获取视频列表
     * @param page 页码
     * @param limit 每页数量
     * @param typeName 分类名称（可选）
     * @param year 年份（可选）
     * @param status 状态：0全部，1连载，2完结（可选）
     */
    @GET(ApiConfig.Endpoints.VODLIST_PAGE)
    Call<ApiResponse<Object>> getVodListPage(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("type_name") String typeName,
            @Query("year") String year,
            @Query("status") Integer status
    );

    /**
     * 根据ID获取视频详情
     * @param vodIds 视频ID列表，逗号分隔
     */
    @GET(ApiConfig.Endpoints.GET_BY_IDS)
    Call<ApiResponse<List<Vod>>> getVodByIds(@Query("vod_ids") String vodIds);
}
