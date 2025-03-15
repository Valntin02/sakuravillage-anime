package com.example.gsyvideoplayer.simple;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gsyvideoplayer.R;

public class TopNavigationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate布局
        View rootView = inflater.inflate(R.layout.fragment_top_navigation, container, false);

        // 获取搜索按钮并设置点击事件
        Button btnSearch = rootView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(v -> {
            // 跳转到 SearchActivity
            Log.d("serch","点击率");
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        return rootView;
    }
}
