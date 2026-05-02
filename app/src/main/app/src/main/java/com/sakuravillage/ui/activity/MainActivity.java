package com.sakuravillage.ui.activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sakuravillage.R;
import com.sakuravillage.ui.fragment.HomeFragment;
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
        setupBottomNavigation(savedInstanceState);
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    /**
     * 设置底部导航栏
     */
    private void setupBottomNavigation(Bundle savedInstanceState) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // 根据选择的项切换Fragment
            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
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
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
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
