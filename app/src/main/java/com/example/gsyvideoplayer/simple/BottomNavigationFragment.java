//package com.example.gsyvideoplayer.simple;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.gsyvideoplayer.R;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class BottomNavigationFragment extends Fragment {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true); // 保证Fragment在配置变化时保持实例
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // 通过 inflater 加载布局
//        View rootView = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
//
//        // 获取 BottomNavigationView
//        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottomNavigation);
//
//        // 设置导航栏点击监听器
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    // 首页按钮点击事件
//                    Intent homeIntent = new Intent(getActivity(), MyMainActivity.class);
//                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 如果 MyMainActivity 已经在栈中，跳转到该 Activity
//                    startActivity(homeIntent);
//                    return true;
//
//                case R.id.nav_search:
//                    // 搜索按钮点击事件
//                    // 可以根据需求实现相应操作，例如跳转到搜索页面
//                    return true;
//
//                case R.id.nav_profile:
//                    // 我的按钮点击事件
//                    Intent myIntent = new Intent(getActivity(), MyPageActivity.class);
//                    myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // 如果 MyPageActivity 已经在栈中，跳转到该 Activity
//                    startActivity(myIntent);
//                    return true;
//
//                default:
//                    return false;
//            }
//        });
//
//        return rootView;
//    }
//}
