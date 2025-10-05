package com.example.gsyvideoplayer.simple;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gsyvideoplayer.R;

public class MyMainFragment extends Fragment {
    private Fragment currentFragment;
    private Fragment fragmentHomePage;
    private Fragment fragmentAnime;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fragmentHomePage = new FragmentHomePage();
        fragmentAnime = new FragmentAnime(); // 提前初始化
        currentFragment=fragmentHomePage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mymain, container, false);
        Log.d("myFragment","MyMainFragmentinit");
        // 加载顶部导航栏

            getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_top_navigation, new TopNavigationFragment())
                .commit();

            // 加载中间模块
            getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragmentHomePage)
                .commit();


        return view;
    }

    //XML 中的颜色写法 #f6f6f6，系统默认会加上不透明的 Alpha 值！,所以这里需要加上透明度的数据部分
    public void switchToHome(TextView selected, TextView unselected) {
        boolean flag=switchContent(fragmentHomePage);
        //切换成功那么改变颜色
        if(flag){
            selected.setTextColor(0xFFF6F6F6);
            unselected.setTextColor(0xFF393E46);

            //公告栏的焦点
            FragmentManager childFragmentManager = fragmentHomePage.getChildFragmentManager();
            Fragment announcementFragment = childFragmentManager.findFragmentByTag("announcement");

            //instanceof 是一个关键字，用于判断一个对象是否是某个类的实例
            if (announcementFragment instanceof AnnouncementBar) {
                ((AnnouncementBar) announcementFragment).onShow();
                //Log.d("AnnouncementBar", "onShow: ");
            }

        }
    }

    public void switchToAnime(TextView selected, TextView unselected) {
        boolean flag=switchContent(fragmentAnime);
        if(flag){
            selected.setTextColor(0xFFF6F6F6);
            unselected.setTextColor(0xFF393E46);
        }
    }

    // 切换 fragment_content 内容的方法 使用add
    public boolean switchContent(Fragment targetFragment) {
        if (targetFragment == currentFragment) return false;

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if (!targetFragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.fragment_container, targetFragment);
        } else {
            transaction.hide(currentFragment).show(targetFragment);
        }

        transaction.commit();
        currentFragment = targetFragment;
        return true;
    }
}
