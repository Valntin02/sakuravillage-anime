package com.sakuravillage.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sakuravillage.R;
import com.sakuravillage.network.NetworkManager;
import com.sakuravillage.network.model.ApiResponse;
import com.sakuravillage.network.model.Vod;
import com.sakuravillage.ui.adapter.VodAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 搜索Fragment - 搜索视频
 */
public class SearchFragment extends BaseFragment {

    private static final String TAG = "SearchFragment";

    private EditText searchEditText;
    private ImageButton searchButton;
    private RecyclerView recyclerView;

    private List<Vod> searchResults = new ArrayList<>();
    private VodAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {
        searchEditText = view.findViewById(R.id.et_search);
        searchButton = view.findViewById(R.id.btn_search);
        recyclerView = view.findViewById(R.id.recycler_view);

        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VodAdapter(searchResults, vod -> {
            // 点击视频卡片
            // TODO: 跳转到视频详情页
        });
        recyclerView.setAdapter(adapter);

        // 搜索按钮点击事件
        searchButton.setOnClickListener(v -> performSearch());

        // 回车键搜索
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            performSearch();
            return true;
        });
    }

    @Override
    protected void initData() {
        // 初始不需要加载数据
    }

    /**
     * 执行搜索
     */
    private void performSearch() {
        String keyword = searchEditText.getText().toString().trim();

        if (TextUtils.isEmpty(keyword)) {
            showToast("请输入搜索关键词");
            return;
        }

        searchVideos(keyword);
    }

    /**
     * 搜索视频
     */
    private void searchVideos(String keyword) {
        NetworkManager networkManager = NetworkManager.getInstance(requireContext());

        networkManager.getVodApi().searchVods(keyword).enqueue(new Callback<ApiResponse<List<Vod>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Vod>>> call, Response<ApiResponse<List<Vod>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Vod>> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        searchResults.clear();
                        searchResults.addAll(apiResponse.getData());
                        adapter.notifyDataSetChanged();
                        showToast("找到 " + searchResults.size() + " 个结果");
                    } else {
                        showToast("搜索失败: " + apiResponse.getMessage());
                    }
                } else {
                    showToast("搜索失败");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Vod>>> call, Throwable t) {
                showToast("网络错误: " + t.getMessage());
            }
        });
    }

}
