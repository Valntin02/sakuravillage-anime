package com.example.gsyvideoplayer.simple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gsyvideoplayer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyPageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_page, container, false);

        Log.d("myFragment","MyPageFragmentinit");

        // 检查登录状态，是否已经登录
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", requireActivity().MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        Fragment existingFragment = getChildFragmentManager().findFragmentByTag("UserFragment");

        // 根据登录状态加载对应的 Fragment
        if (isLoggedIn) {
            // 如果已登录，加载用户主页
            UserFragment userFragment = new UserFragment();
            getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_login, userFragment)  // 传递用户数据给 UserFragment
                .commit();
        } else {
            // 如果没有登录，加载登录界面
            LoginFragment loginFragment = new LoginFragment();
            getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_login, loginFragment)  // 加载登录界面
                .commit();
        }


        return view;
    }
}
