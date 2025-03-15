package com.example.gsyvideoplayer.simple;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gsyvideoplayer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {

    private MyMainFragment myMainFragment;
    private MyPageFragment myPageFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 获取 BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.fragment_bottom_navigation);
        // 设置默认选中项
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        // 初始化 Fragment
        myMainFragment = new MyMainFragment();
        myPageFragment = new MyPageFragment();

        // 获取 FragmentTransaction
        transaction = getSupportFragmentManager().beginTransaction();

        // 添加 Fragment 到容器
        transaction.add(R.id.fragment_container, myMainFragment);
        transaction.add(R.id.fragment_container, myPageFragment);

        // 设置默认 Fragment 可见
        transaction.hide(myPageFragment); // 隐藏我的页面 Fragment
        transaction.show(myMainFragment); // 显示首页 Fragment
        transaction.commit();

        // 设置导航栏的点击事件监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        loadFragment(myPageFragment,myMainFragment);

                        return true;

                    case R.id.nav_profile:
                        loadFragment(myMainFragment,myPageFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    // 加载 Fragment 的方法
    private void loadFragment(Fragment form,Fragment to) {
        // 获取当前显示的 Fragment

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(form);
            // 显示目标 Fragment
            transaction.show(to);

            // 提交事务
            transaction.commit();

    }
}
