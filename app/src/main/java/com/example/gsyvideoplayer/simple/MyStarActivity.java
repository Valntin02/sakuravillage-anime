package com.example.gsyvideoplayer.simple;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.RetrofitClient;
import com.example.gsyvideoplayer.VodData;
import com.example.gsyvideoplayer.common.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class MyStarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<VodData> vodDataList=new ArrayList<>();
    private List<MyStarRecord> myStarRecordList=new ArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(); // 异步线程池
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_star);
        // 初始化RecyclerView
        recyclerView = findViewById(R.id.my_star_records);
        // 使用 GridLayoutManager 设置列数为 3
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Param.setStatusBarTransparent(this, true,0xFFffe2e2);
        // 异步加载数据
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstancePlayRecord(this);
            myStarRecordList = db.myStarRecordDao().getAllStarRecords();
            //这里是为了复用videoAdapter所有通过这个for来添加数据
            for (MyStarRecord record : myStarRecordList) {
                // 根据 `MyStarRecord` 获取 `VodData` 并添加到 `vodDataList`
                VodData vodData = new VodData(record.getVod_id(), record.getVod_name(), record.getVod_pic(),
                    record.getVod_play_url(), record.getVod_actor(), record.getVod_remarks(),
                    record.getVod_year(), record.getVod_content(), record.getVod_total());
                vodDataList.add(vodData);
            }

            if(vodDataList.isEmpty()){
                getMyStars();
            }
            // 回到主线程更新 UI
            runOnUiThread(() -> videoAdapter.setVodDataList(vodDataList));
        });

        videoAdapter = new VideoAdapter(vodDataList);
        recyclerView.setAdapter(videoAdapter);
    }

    private  void getMyStars(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // 检查用户是否已登录
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if(!isLoggedIn) return;

        int useId=sharedPreferences.getInt("Id", 0);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        //这里就不创建新的接受类了 使用泛 Map
        Call<MyStarResModel> call = apiService.requestMyStars(useId);

        ApiClient.requestData(call, new ApiClient.ApiResponseCallback<MyStarResModel>() {
            @Override
            public void onSuccess(MyStarResModel data) {
                if(data.getCode()==200){
                    myStarRecordList=data.getData();
                    for (MyStarRecord record : myStarRecordList) {
                        // 根据 `MyStarRecord` 获取 `VodData` 并添加到 `vodDataList`
                        VodData vodData = new VodData(record.getVod_id(), record.getVod_name(), record.getVod_pic(),
                            record.getVod_play_url(), record.getVod_actor(), record.getVod_remarks(),
                            record.getVod_year(), record.getVod_content(), record.getVod_total());
                        vodDataList.add(vodData);
                    }
                    videoAdapter.setVodDataList(vodDataList);
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(() -> {
                        AppDatabase db = AppDatabase.getInstanceMyStarRecord(getApplicationContext());
                        MyStarRecordDao syncMyStar=db.myStarRecordDao();
                        syncMyStar.insert(myStarRecordList);
                    });
                    Log.d("requestMyStars", "获取同步成功:" +data.getMsg());
                }else {
                    Log.e("requestMyStars", "获取同步失败:" +data.getMsg());

                }

            }
            @Override
            public void onFailure(String error) {
                Log.e("requestMyStars", "获取同步失败:" +error);

            }
        });
    }
}
