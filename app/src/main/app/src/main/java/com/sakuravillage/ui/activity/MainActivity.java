package com.sakuravillage.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sakuravillage.R;
import com.sakuravillage.ui.fragment.HomeFragment;
import com.sakuravillage.ui.fragment.ExploreFragment;
import com.sakuravillage.ui.fragment.SearchFragment;
import com.sakuravillage.ui.fragment.ProfileFragment;

/**
 * 主Activity - 包含底部导航栏
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupBottomNavigation();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    /**
     * 设置底部导航栏
     */
    private void setupBottomNavigation() {
        bottomNavigationView.setItemIconTintList(null); // 使用原始图标颜色
        bottomNavigationView.setItemTextColor(getResources().getColorStateList(R.color.nav_item_text_color));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            // 根据选择的项切换Fragment
            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    return true;
                case R.id.nav_explore:
                    replaceFragment(new ExploreFragment());
                    return true;
                case R.id.nav_search:
                    replaceFragment(new SearchFragment());
                    return true;
                case R.id.nav_profile:
                    replaceFragment(new ProfileFragment());
                    return true;
                default:
                    return false;
            }
        });

        // 默认显示首页
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
    }

    /**
     * 替换Fragment
     */
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
