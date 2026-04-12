package com.sakuravillage.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sakuravillage.R;
import com.sakuravillage.ui.fragment.BaseFragment;
import com.sakuravillage.ui.fragment.HomeFragment;
import com.sakuravillage.ui.fragment.ExploreFragment;
import com.sakuravillage.ui.fragment.SearchFragment;
import com.sakuravillage.ui.fragment.ProfileFragment;

/**
 * 主Activity - 包含底部底部导航栏
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupBottomNavigation();

        // 默认显示首页
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 替换Fragment
     */
    private void replaceFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }
}
