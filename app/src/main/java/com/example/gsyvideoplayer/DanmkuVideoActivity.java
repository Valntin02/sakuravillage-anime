package com.example.gsyvideoplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gsyvideoplayer.common.Param;
import com.example.gsyvideoplayer.simple.ViewPagerAdapter;
import com.example.gsyvideoplayer.databinding.ActivityDanmakuLayoutBinding;
import com.example.gsyvideoplayer.simple.CommentFragment;
import com.example.gsyvideoplayer.simple.IntroFragment;
import com.example.gsyvideoplayer.video.DanmakuVideoPlayer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import com.zhy.http.okhttp.callback.StringCallback;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


import okhttp3.Call;


/**
 * Created by guoshuyu on 2017/2/19.
 * 弹幕
 */


public class DanmkuVideoActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private boolean isPlay;
    private boolean isPause;
    private boolean isDestory;


    private OrientationUtils orientationUtils;

    private ActivityDanmakuLayoutBinding binding;

    private VodData videoData;
    private ArrayList<String> videourls;

    private int currentEpisode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_danmaku_layout);//这里需要手动设置 否则会崩溃，之前使用的应该是一个回调getid来获取绑定布局，但不知道在哪里调用


        Param.setStatusBarTransparent(this, false,0x73000000);
        //类型安全：使用 ViewBinding 代替 findViewById() 可以避免空指针异常，并且在编译时就能检测到错误。
        binding = ActivityDanmakuLayoutBinding.inflate(getLayoutInflater());

        View rootView = binding.getRoot();
        setContentView(rootView);


        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        // 初始化适配器
        adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new IntroFragment(), "详情");
        adapter.addFragment(new CommentFragment(), "评论");

        // 设置适配器
        viewPager.setAdapter(adapter);

        // 绑定 TabLayout 和 ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(adapter.getTitle(position));
        }).attach();
        // 获取传递的视频 URL
//        String videoUrl = getIntent().getStringExtra("video_url");
        //使用自定义的全屏切换图片，!!!注意xml布局中也需要设置为一样的
        //必须在setUp之前设置
        //获取视频数据
        videoData = getIntent().getParcelableExtra("video_data");
        currentEpisode=getIntent().getIntExtra("currentEpisode",1);
        dealVideourls();
        // 设置视频数据和 URL 列表
        binding.danmakuPlayer.setVideoData(videoData);
        binding.danmakuPlayer.setVideourls(videourls);
        binding.danmakuPlayer.setContext(this);  // 设置 Context
        binding.danmakuPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        binding.danmakuPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);

        //String url = "https://res.exexm.com/cw_145225549855002";
//        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        //String url = "https://res.exexm.com/cw_145225549855002";

        binding.danmakuPlayer.setUp(videoData.getVod_play_url(), false, null, videoData.getVod_name()+"—第"+currentEpisode+"集");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
        Picasso.get()
            .load(videoData.getVod_pic())
            .placeholder(R.drawable.bg_wiht_shadow) // 加载中显示的图片
            .error(R.drawable.bg_wiht_shadow)             // 加载失败显示的图片
            .into(imageView);
        binding.danmakuPlayer.setThumbImageView(imageView);

        resolveNormalVideoUI();


        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, binding.danmakuPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        binding.danmakuPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        binding.danmakuPlayer.setRotateViewAuto(false);
        binding.danmakuPlayer.setLockLand(false);
        binding.danmakuPlayer.setShowFullAnimation(false);
        binding.danmakuPlayer.setNeedLockFull(true);
        binding.danmakuPlayer.setReleaseWhenLossAudio(false);

        //detailPlayer.setOpenPreView(true);
        binding.danmakuPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                binding.danmakuPlayer.startWindowFullscreen(DanmkuVideoActivity.this, true, true);
            }
        });

        binding.danmakuPlayer.setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(binding.danmakuPlayer.isRotateWithSystem());
                isPlay = true;
                getDanmu();
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);

                // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
                // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }

            }
        });

        binding.danmakuPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
        // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();

        isDestory = true;
    }


    /**
     * orientationUtils 和  detailPlayer.onConfigurationChanged 方法是用于触发屏幕旋转的
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            binding.danmakuPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }


//    private void getDanmu() {
//        //下载demo然后设置
//        OkHttpUtils.get().url("https://comment.bilibili.com/205245882.xml")
//                .build()
//                .execute(new FileCallBack(getApplication().getCacheDir().getAbsolutePath(), "barrage.txt")//
//                {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//
//                    @Override
//                    public void onResponse(File response, int id) {
//                        if (!isDestory) {
//                            ((DanmakuVideoPlayer) binding.danmakuPlayer.getCurrentPlayer()).setDanmaKuStream(response);
//                        }
//
//                    }
//
//                });
//    }
private void getDanmu() {

    //设置使用自定义的okhttp验证方式
    OkHttpUtils.initClient(NetworkHelper.getOkHttpClient());
    // 构建 JSON 数据，int 不需要转换为字符串
    int vod_id=videoData.getVod_id() ,vod_nid= binding.danmakuPlayer.getCurrentEpisode();

    //String json = String.format("{\"vod_id\":%d, \"vod_nid\":%d}", vod_id, vod_nid);


    //下载demo然后设置
    OkHttpUtils.post()
        .url("https://113.45.243.38/api.php/danmaku/get_mobile")
        .addParams("vod_id", String.valueOf(vod_id))    // 添加POST参数
        .addParams("vod_nid", String.valueOf(vod_nid))    // 可继续添加参数
        .build()
        .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("getdanmaku", "请求失败: " + e.getMessage(), e);

                // 如果是网络问题，可以进一步判断
                if (e instanceof java.net.UnknownHostException) {
                    Log.e("getdanmaku", "未知主机异常: " + e.getMessage());
                } else if (e instanceof java.net.SocketTimeoutException) {
                    Log.e("getdanmaku", "请求超时: " + e.getMessage());
                } else if (e instanceof java.io.IOException) {
                    Log.e("getdanmaku", "IO异常: " + e.getMessage());
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try{
                    if (!isDestory) {

                        // 2️⃣ 保存到本地 JSON 文件
                        String fileName=videoData.getVod_name()+binding.danmakuPlayer.getCurrentEpisode();
                        File jsonFile = new File(getApplication().getCacheDir(),fileName );
                        BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));
                        writer.write(response);    // 格式化输出，缩进4空格
                        writer.close();
                        Log.e("getdanmaku", "onResponse: " +response);
                        ((DanmakuVideoPlayer) binding.danmakuPlayer.getCurrentPlayer()).setDanmaKuStream(jsonFile);
                    }
                }catch (Exception e){
                    Log.e("getdanmaku", "解析失败: " + e.getMessage());
                }


            }
        });
    }

    private void dealVideourls(){
        String url_remarks=videoData.getVod_remarks();
        // 提取数字并转换为整数
        String numberStr = url_remarks.replaceAll("[^0-9]", "");  // 只保留数字
        int number = Integer.parseInt(numberStr);
        // 获取视频播放 URL 模板
        String baseUrl = videoData.getVod_play_url();

        videourls= new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            // 格式化数字为两位数，如果是1集则显示"01"
            String episodeStr = String.format("%02d", i);

            // 替换 URL 中的数字部分，这里我们假设 URL 中包含"第{number}集"
            String videoUrl = baseUrl.replaceAll("第\\d{2}集", "第" + episodeStr + "集");  // 替换集数部分
            videourls.add(videoUrl);
        }
        // 输出生成的 URL 列表
        for (String url : videourls) {
            System.out.println(url);
        }
    }
    private void resolveNormalVideoUI() {
        //增加title
        binding.danmakuPlayer.getTitleTextView().setVisibility(View.GONE);
        binding.danmakuPlayer.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (binding.danmakuPlayer.getFullWindowPlayer() != null) {
            return  binding.danmakuPlayer.getFullWindowPlayer();
        }
        return binding.danmakuPlayer;
    }

    public VodData getVideoData() {
        return videoData;
    }

    public ArrayList<String> getVideoUrls() {
        return videourls;
    }

    public ActivityDanmakuLayoutBinding getBinding(){
        return binding;
    }

    public int getCurrentEpisode(){
        return this.currentEpisode;
    }
}
