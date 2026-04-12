package com.sakuravillage.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.sakuravillage.R;
import com.sakuravillage.network.NetworkManager;

/**
 * 个人中心Fragment
 */
public class ProfileFragment extends BaseFragment {

    private static final String TAG = "ProfileFragment";

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView(View view) {
        // 设置按钮点击事件
        view.findViewById(R.id.btn_login).setOnClickListener(v -> {
            // TODO: 跳转到登录页
        });

        view.findViewById(R.id.btn_register).setOnClickListener(v -> {
            // TODO: 跳转到注册页
        });

        view.findViewById(R.id.btn_my_star).setOnClickListener(v -> {
            // TODO: 跳转到我的收藏页
        });

        view.findViewById(R.id.btn_play_history).setOnClickListener(v -> {
            // TODO: 跳转到播放历史页
        });

        view.findViewById(R.id.btn_settings).setOnClickListener(v -> {
            // TODO: 跳转到设置页
        });

        view.findViewById(R.id.btn_logout).setOnClickListener(v -> showLogoutDialog());
    }

    @Override
    protected void initData() {
        // 检查登录状态
        updateLoginStatus();
    }

    /**
     * 更新登录状态UI
     */
    private void updateLoginStatus() {
        NetworkManager networkManager = NetworkManager.getInstance(requireContext());
        boolean isLoggedIn = networkManager.isLoggedIn();

        // TODO: 根据登录状态更新UI显示
        if (isLoggedIn) {
            // 显示已登录状态
        } else {
            // 显示未登录状态
        }
    }

    /**
     * 显示退出登录对话框
     */
    private void showLogoutDialog() {
        NetworkManager networkManager = NetworkManager.getInstance(requireContext());

        if (!networkManager.isLoggedIn()) {
            showToast("当前未登录");
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("退出登录")
                .setMessage("确定要退出登录吗?")
                .setPositiveButton("确定", (dialog, which) -> {
                    networkManager.logout();
                    updateLoginStatus();
                    showToast("已退出登录");
                })
                .setNegativeButton("取消", null)
                .show();
    }

}
