package com.sakuravillage.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sakuravillage.R;
import com.sakuravillage.network.model.Vod;
import com.sakuravillage.network.NetworkManager;
import com.sakuravillage.network.model.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页Fragment - 今日推荐
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyText;
    private List<Vod> vodList = new ArrayList<>();
    private VodAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        emptyText = view.findViewById(R.id.empty_text);

        // 设置RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new VodAdapter(vodList, vod -> {
            // 点击视频卡片，跳转到详情页
            // TODO: 跳转到视频详情页
            Log.d(TAG, "Clicked vod: " + vod.getVodName());
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        loadTodayVods();
    }

    /**
     * 加载今日推荐视频
     */
    private void loadTodayVods() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyText.setVisibility(View.GONE);

        NetworkManager networkManager = NetworkManager.getInstance(requireContext());

        networkManager.getVodApi().getTodayVods().enqueue(new Callback<ApiResponse<List<Vod>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Vod>>> call, Response<ApiResponse<List<Vod>>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Vod>> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        vodList.clear();
                        vodList.addAll(apiResponse.getData());
                        adapter.notifyDataSetChanged();

                        if (vodList.isEmpty()) {
                            emptyText.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        showError(apiResponse.getMessage());
                    }
                } else {
                    showError("加载失败");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Vod>>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Load vods failed", t);
                showError("网络错误：" + t.getMessage());
            }
        });
    }

    /**
     * 显示错误信息
     */
    private void showError(String message) {
        emptyText.setText(message);
        emptyText.setVisibility(View.VISIBLE);
    }
}
