package com.sakuravillage.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sakuravillage.R;
import com.sakuravillage.data.remote.RetrofitClient;
import com.sakuravillage.data.model.VodData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import com.sakuravillage.data.model.VodPageResModel;
import com.sakuravillage.data.remote.ApiClient;
import com.sakuravillage.data.remote.ApiService;

public class FragmentAnime extends Fragment {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    private RecyclerView recyclerViewYears;
    private List<VodData> vodDataList = new ArrayList<>();

    private List<String> yearList=new ArrayList<>();
    //这里的page 和SQL不一样 php里面做了封装 从1开始，page等于多少 就等于求第多少页
    int page=1,limit=36,total=0,totalPage=0;
    boolean flagRequest=false; //表示是否可以请求
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_anime, container, false);
        //索引设置模块
        recyclerViewYears=view.findViewById(R.id.recycler_view_years);

        recyclerViewYears.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        yearList.add("全部年份");
        for (int year = 2025; year >= 2010; year--) {
            yearList.add(String.valueOf(year));
        }
        // 设置年份索引适配器
        YearIndexAdapter yearIndexAdapter = new YearIndexAdapter(yearList, year -> {
            Log.d("FragmentAnime", "Selected Year: " + year);
            filterVideosByYear(year);  // 根据年份过滤视频列表
        });

        recyclerViewYears.setAdapter(yearIndexAdapter);

        //视频展示模块
        recyclerView = view.findViewById(R.id.recycler_view_videos);
        // 使用 GridLayoutManager 设置列数为 3
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        videoAdapter = new VideoAdapter(vodDataList);
        recyclerView.setAdapter(videoAdapter);
        getVideoPage();

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //RecyclerView 的 onScrolled 方法会被连续触发多次，因为 RecyclerView 每滚动一小段就会调一次这个监听
            //这里RecyclerView 的 onScrolled 方法会被连续触发多次，因为 RecyclerView 每滚动一小段就会调一次这个监听
            //使用flagRequeest来屏蔽触发多次，同时也可以作为分页的判断标志
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 只监听向下滑动
                if (dy > 0) {
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager != null) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();//你滑到了第 24 条记录位置

                        if (!flagRequest && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= limit) {
                            // 到达底部，加载下一页
                            //Log.d("FragmentAnime", "滑动到底部触发" );
                            getVideoPage();
                        }
                    }
                }
            }
        });

        return view;
    }


    private void getVideoPage() {
        if(flagRequest) return;

        flagRequest=true;
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<VodPageResModel> call = apiService.requestVideoPage(page,limit);

        ApiClient.requestData(call, new ApiClient.ApiResponseCallback<VodPageResModel>() {
            @Override
            public void onSuccess(VodPageResModel data) {
                Log.d("FragmentAnime", "msg" + data);
                //vodDataList.clear();
                vodDataList.addAll(data.getVodDataList());
                page++;
                total=data.getTotal();
                totalPage=data.getTotalPage();
                videoAdapter.notifyDataSetChanged();
                flagRequest=false;

                if(page>totalPage) flagRequest=true;
            }

            @Override
            public void onFailure(String error) {
                flagRequest=false;
                Log.e("FragmentAnime", "Error: " + error);

            }
        });
    }

    private void filterVideosByYear(String year) {

        if(year.equals("全部年份")){
            videoAdapter.setVodDataList(vodDataList);
            videoAdapter.notifyDataSetChanged();
            return;
        }
        // 过滤视频列表，这里可以根据年份过滤接口的数据
        List<VodData> filteredList = new ArrayList<>();
        for (VodData video : vodDataList) {
            if (video.getVod_year().equals(year)) {
                filteredList.add(video);
            }
        }
        videoAdapter.setVodDataList(filteredList);
        videoAdapter.notifyDataSetChanged();
    }
}
