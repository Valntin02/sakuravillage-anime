package com.example.gsyvideoplayer.simple;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.example.gsyvideoplayer.R;

public class UserFragment extends Fragment {
    private TextView tvUsername;
    private ShapeableImageView ivAvatarFile;  // 修改为 ShapeableImageView

    private String TAG = "user img";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        tvUsername = rootView.findViewById(R.id.user_name);
        ivAvatarFile = rootView.findViewById(R.id.avatar_file);  // 获取 ShapeableImageView

        String baseurl = "https://113.45.243.38/";

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // 检查用户是否已登录
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            String username = sharedPreferences.getString("username", "");
            String avatarFile = sharedPreferences.getString("avatarFile", "");
            baseurl += avatarFile.trim();

            Log.d("userinfo", baseurl);

            tvUsername.setText(username);

            // 使用 Glide 加载头像并设置到 ShapeableImageView 上
            Glide.with(getContext())
                .load(baseurl)
                .circleCrop()  // 通过 Glide 的 circleCrop() 方法实现圆形裁剪
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.button_style)
                .into(ivAvatarFile);  // 设置图片
        }


        return rootView;
    }
}
