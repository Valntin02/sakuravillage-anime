package com.example.gsyvideoplayer.simple;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.RetrofitClient;
import com.example.gsyvideoplayer.VodData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class temp extends Activity {

    private static final String TAG = "GetData";
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<VodData> vodDataList = new ArrayList<>();
    // 用来缓存每个星期的数据
    private Map<String, List<VodData>> dataCache = new HashMap<>();
    private String getWeekdayString(int checkedId) {
        switch (checkedId) {
            case R.id.radio_monday:
                return "一";
            case R.id.radio_tuesday:
                return "二";
            case R.id.radio_wednesday:
                return "三";
            case R.id.radio_thursday:
                return "四";
            case R.id.radio_friday:
                return "五";
            case R.id.radio_saturday:
                return "六";
            case R.id.radio_sunday:
                return "日";
            default:
                return "日";
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        // 初始化RecyclerView
        recyclerView = findViewById(R.id.recycler_view_videos);
        // 使用 GridLayoutManager 设置列数为 3
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        videoAdapter = new VideoAdapter(vodDataList);
        recyclerView.setAdapter(videoAdapter);

        // 设置星期选择按钮的监听器
        RadioGroup weekdayRadioGroup = findViewById(R.id.weekday_radio_group);
        weekdayRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String weekday = getWeekdayString(checkedId);
            fetchDataForWeekday(weekday);
        });

        // 默认显示周一的视频数据
        fetchDataForWeekday("日");
    }

    private void fetchDataForWeekday(String weekday) {
        // 首先检查缓存中是否有该星期的数据
        if (dataCache.containsKey(weekday)) {
            // 如果缓存中有数据，则直接使用缓存的数据
            vodDataList.clear();
            vodDataList.addAll(dataCache.get(weekday));
            videoAdapter.notifyDataSetChanged();
            return;
        }

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<VodResModel> call = apiService.requestVodData(weekday);

        ApiClient.requestData(call, new ApiClient.ApiResponseCallback<VodResModel>() {
            @Override
            public void onSuccess(VodResModel data) {
                Log.d(TAG,"msg"+data);
                vodDataList.clear();
                vodDataList.addAll(data.getVodDataList());


                videoAdapter.notifyDataSetChanged();
                // 将请求到的数据缓存到 Map 中
                dataCache.put(weekday, new ArrayList<>(vodDataList));
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "Error: " + error);
            }
        });
    }
}
