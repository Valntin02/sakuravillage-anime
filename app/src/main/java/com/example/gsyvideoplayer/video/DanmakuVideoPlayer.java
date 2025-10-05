package com.example.gsyvideoplayer.video;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.gsyvideoplayer.DanmkuVideoActivity;
import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.VodData;
import com.example.gsyvideoplayer.adapter.DanamakuAdapter;
import com.example.gsyvideoplayer.simple.DanmakuOptionsFragment;
import com.example.gsyvideoplayer.utils.BiliDanmukuParser;
import com.example.gsyvideoplayer.utils.SakuraDanmukuParser;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 Created by guoshuyu on 2017/2/16.
 <p>
 配置弹幕使用的播放器，目前使用的是本地模拟数据。
 <p>
 模拟数据的弹幕时常比较短，后面的时长点是没有数据的。
 <p>
 注意：这只是一个例子，演示如何集合弹幕，需要完善如弹出输入弹幕等的，可以自行完善。
 注意：b站的弹幕so只有v5 v7 x86、没有64，所以记得配置上ndk过滤。
 */

public class DanmakuVideoPlayer extends StandardGSYVideoPlayer {

    private BaseDanmakuParser mParser;//解析器对象
    private IDanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;

    private int currentEpisode=1;
    private Context mContext;  // 用于存储传入的 Context
    private VodData videoData;

    private ArrayList<String> videourls;
    private TextView  mToogleDanmaku;

    private long mDanmakuStartSeekPosition = -1;

    private boolean mDanmaKuShow = true;

    private File mDumakuFile;

    public TextView nextPlay,inputSampleDanmaku;




    public DanmakuVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public DanmakuVideoPlayer(Context context) {
        super(context);
    }

    public DanmakuVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //通过 getLayoutId() 这种方法，你可以在自定义组件中返回布局资源 ID，从而将视图与自定义组件绑定。
    @Override
    public int getLayoutId() {
        return R.layout.danmaku_layout;
    }


    @Override
    protected void init(Context context) {
        super.init(context);
        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);

        mToogleDanmaku = (TextView) findViewById(R.id.toogle_danmaku);
        nextPlay= (TextView) findViewById(R.id.next_play);
        inputSampleDanmaku =(TextView) findViewById(R.id.inputsample_danmaku);

        //初始化弹幕显示
        //initDanmaku();


        mToogleDanmaku.setOnClickListener(this);
        nextPlay.setOnClickListener(this);
        inputSampleDanmaku.setOnClickListener(this);
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        onPrepareDanmaku(this);
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        danmakuOnPause();
    }

    @Override
    public void onVideoResume(boolean isResume) {
        super.onVideoResume(isResume);
        danmakuOnResume();
    }

    @Override
    protected void clickStartIcon() {
        super.clickStartIcon();
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            danmakuOnResume();
        } else if (mCurrentState == CURRENT_STATE_PAUSE ) {
            danmakuOnPause();
        }
    }

    @Override
    public void onCompletion() {
        super.onCompletion();
        releaseDanmaku(this);
    }


    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
        long time = mProgressBar.getProgress() * getDuration() / 100;
        //如果已经初始化过的，直接seek到对于位置
        if (mHadPlay && getDanmakuView() != null && getDanmakuView().isPrepared()) {
            resolveDanmakuSeek(this, time);
        } else if (mHadPlay && getDanmakuView() != null && !getDanmakuView().isPrepared()) {
            //如果没有初始化过的，记录位置等待
            setDanmakuStartSeekPosition(time);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.toogle_danmaku:
                mDanmaKuShow = !mDanmaKuShow;
                resolveDanmakuShow();
                break;
            case R.id.next_play:
                playNextEpisode();
                break;
            case R.id.inputsample_danmaku:
                inputDanmaku();
                break;
        }
    }

    //发送弹幕的模块
    private void inputDanmaku() {
        //这里在外面先请求数据 如果为空则不创建弹幕文字 也不初始化mParser 导致一个bug 后续可能需要修改，目前通过判断来解决
        if(mParser==null) return ;
        FragmentActivity activity = (FragmentActivity) getContext();
        DanmakuOptionsFragment fragment = DanmakuOptionsFragment.newInstance();
        fragment.setVodData(videoData);
        fragment.setVodRid(currentEpisode);
        fragment.setPostion((float)(getGSYVideoManager().getCurrentPosition()/1000.0));
        fragment.setmDanmakuView(mDanmakuView);
        fragment.setmDanmakuContext(mDanmakuContext);
        fragment.setfontSize((17*mParser.getDisplayer().getDensity() - 0.6f));
        onVideoPause();
        fragment.show(activity.getSupportFragmentManager(), fragment.getTag());

    }



    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        ((DanmakuVideoPlayer) to).mDumakuFile = ((DanmakuVideoPlayer) from).mDumakuFile;
        super.cloneParams(from, to);
    }

    /**
     处理播放器在全屏切换时，弹幕显示的逻辑
     需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        if (gsyBaseVideoPlayer != null) {
            DanmakuVideoPlayer gsyVideoPlayer = (DanmakuVideoPlayer) gsyBaseVideoPlayer;
            //对弹幕设置偏移记录
            gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
            gsyVideoPlayer.setDanmaKuShow(getDanmaKuShow());
            gsyVideoPlayer.setVideourls(videourls);
            gsyVideoPlayer.setVideoData(videoData);
            gsyVideoPlayer.setCurrentEpisode(currentEpisode);
            gsyVideoPlayer.setDanmaKuStream(mDumakuFile);
           // onPrepareDanmaku(gsyVideoPlayer); //播放弹幕 在设置mDumakufile文件之后在播放，非则崩溃


        }
        return gsyBaseVideoPlayer;
    }

    /**
     处理播放器在退出全屏时，弹幕显示的逻辑
     需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if (gsyVideoPlayer != null) {
            DanmakuVideoPlayer gsyDanmaVideoPlayer = (DanmakuVideoPlayer) gsyVideoPlayer;
            setDanmaKuShow(gsyDanmaVideoPlayer.getDanmaKuShow());
            if (gsyDanmaVideoPlayer.getDanmakuView() != null &&
                    gsyDanmaVideoPlayer.getDanmakuView().isPrepared()) {
                resolveDanmakuSeek(this, gsyDanmaVideoPlayer.getCurrentPositionWhenPlaying());
                resolveDanmakuShow();
                releaseDanmaku(gsyDanmaVideoPlayer);
            }
            // 同步 videourls
            videourls = gsyDanmaVideoPlayer.videourls;
            videoData =gsyDanmaVideoPlayer.videoData;
            currentEpisode=gsyDanmaVideoPlayer.getCurrentEpisode();
           // mDumakuFile=gsyDanmaVideoPlayer.mDumakuFile;

        }
    }

    private void playNextEpisode(){
        // 增加当前集数
        currentEpisode++;
        Log.d("playNextEpisode", "onclik");
        // 检查是否已播放完所有集数
        if (currentEpisode > videourls.size()) {
            // 如果是最后一集，返回或者做其他处理
            Toast.makeText(mContext, "已经是最后一集了", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取下一集的 URL
        String nextVideoUrl = videourls.get(currentEpisode - 1);  // 索引从0开始，因此减去1
        String titleName=videoData.getVod_name();
        titleName+="—第"+currentEpisode+"集";
        // 更新播放的 URL
        setUp(nextVideoUrl, false, null, titleName);
    }
    protected void danmakuOnPause() {
        if (mDanmakuView != null  && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    protected void danmakuOnResume() {
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    public void setDanmaKuStream(File is) {
        mDumakuFile = is;
        Log.d("setDanmaKuStream", "not null: ");
        initDanmaku();
        if (!getDanmakuView().isPrepared()) {
            onPrepareDanmaku((DanmakuVideoPlayer) getCurrentPlayer());
        }
    }

    // 添加一个方法来设置 Context
    public void setContext(Context context) {
        this.mContext = context;
    }
    private void initDanmaku() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        //这里显示行小了可能导致吞弹幕的情况
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 8); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, true);

        DanamakuAdapter danamakuAdapter = new DanamakuAdapter(mDanmakuView);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), danamakuAdapter) // 图文混排使用SpannedCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (mDanmakuView != null) {
            Log.d("setDanmaKuStream", "mDanmakuView not null: ");
            if (mDumakuFile != null) {
                Log.d("setDanmaKuStream", "mDumakuFile not null: ");
                mParser = createParser(getIsStreamMy(mDumakuFile));
            }
            //这个需要修改
            //todo 这是为了demo效果，实际上需要去掉这个，外部传输文件进来
            //mParser = createParser(this.getResources().openRawResource(R.raw.commet));

            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                }

                @Override
                public void prepared() {
                    if (getDanmakuView() != null) {
                        getDanmakuView().start();
                        Log.d("danmakuvedioplayer", "prepared:start ");
                        if (getDanmakuStartSeekPosition() != -1) {
                            resolveDanmakuSeek(DanmakuVideoPlayer.this, getDanmakuStartSeekPosition());
                            setDanmakuStartSeekPosition(-1);
                        }
                        resolveDanmakuShow();
                    }
                }
            });
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }
    // 添加 setter 方法
    public void setVideoData(VodData videoData) {
        this.videoData = videoData;
    }

    public void setVideourls(ArrayList<String> videourls) {
        this.videourls = videourls;
    }
    private InputStream getIsStream(File file) {
        try {
            InputStream instream = new FileInputStream(file);
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            StringBuilder sb1 = new StringBuilder();
            sb1.append("<i>");
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                sb1.append(line);
            }
            sb1.append("</i>");
            Log.e("3333333", sb1.toString());
            instream.close();
            return new ByteArrayInputStream(sb1.toString().getBytes());
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return null;
    }
    private InputStream getIsStreamMy(File file) {
        try {
            // 创建输入流
            InputStream instream = new FileInputStream(file);
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            StringBuilder sb1 = new StringBuilder();
            // 逐行读取文件内容，不添加任何写入操作
            while ((line = buffreader.readLine()) != null) {
                sb1.append(line);
                Log.d("getIsStreamMy", "getIsStreamMy: "+line);
            }
            // 关闭输入流
            instream.close();
            // 将读取到的内容直接转换为 InputStream，返回
            return new ByteArrayInputStream(sb1.toString().getBytes());
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't exist.");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return null;
    }
    /**
     弹幕的显示与关闭
     */
    private void resolveDanmakuShow() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mDanmaKuShow) {
                    if (!getDanmakuView().isShown())
                        getDanmakuView().show();
                    mToogleDanmaku.setText("弹幕关");
                } else {
                    if (getDanmakuView().isShown()) {
                        getDanmakuView().hide();
                    }
                    mToogleDanmaku.setText("弹幕开");
                }
            }
        });
    }

    /**
     开始播放弹幕
     */
    private void onPrepareDanmaku(DanmakuVideoPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer.getDanmakuView() != null && !gsyVideoPlayer.getDanmakuView().isPrepared() && gsyVideoPlayer.getParser() != null) {
            gsyVideoPlayer.getDanmakuView().prepare(gsyVideoPlayer.getParser(),
                    gsyVideoPlayer.getDanmakuContext());
        }
    }

    /**
     弹幕偏移
     */
    private void resolveDanmakuSeek(DanmakuVideoPlayer gsyVideoPlayer, long time) {
        if (mHadPlay && gsyVideoPlayer.getDanmakuView() != null && gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().seekTo(time);
        }
    }

    /**
     创建解析器对象，解析输入流

     @param stream
     @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            Log.d("BaseDanmakuParser", "stream is null");
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }
        Log.d("BaseDanmakuParser", "stream is not null");
        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        //BaseDanmakuParser parser = new BiliDanmukuParser();
        SakuraDanmukuParser parser=new SakuraDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    /**
     释放弹幕控件
     */
    private void releaseDanmaku(DanmakuVideoPlayer danmakuVideoPlayer) {
        if (danmakuVideoPlayer != null && danmakuVideoPlayer.getDanmakuView() != null) {
            Debuger.printfError("release Danmaku!");
            danmakuVideoPlayer.getDanmakuView().release();
        }
    }

    public BaseDanmakuParser getParser() {
        if (mParser == null) {
            if (mDumakuFile != null) {
                mParser = createParser(getIsStream(mDumakuFile));
            }
        }
        return mParser;
    }

    public DanmakuContext getDanmakuContext() {
        return mDanmakuContext;
    }

    public IDanmakuView getDanmakuView() {
        return mDanmakuView;
    }

    public long getDanmakuStartSeekPosition() {
        return mDanmakuStartSeekPosition;
    }

    public void setDanmakuStartSeekPosition(long danmakuStartSeekPosition) {
        this.mDanmakuStartSeekPosition = danmakuStartSeekPosition;
    }

    public void setDanmaKuShow(boolean danmaKuShow) {
        mDanmaKuShow = danmaKuShow;
    }

    public boolean getDanmaKuShow() {
        return mDanmaKuShow;
    }

    public void setCurrentEpisode(int _currentEpisode){currentEpisode=_currentEpisode;}
    public int getCurrentEpisode(){return currentEpisode;}

    //重写弹幕控制 通过父类调用这个函数来实现自动控制弹幕的暂停恢复 主要再视频缓冲的时候做同步
    @Override
    protected void danmakuPauseOrResume(int flag){
        Log.d("standardplayer", "mCurrentState:"+mCurrentState);
        if(flag==1){
            Log.d("standardplayer", "danmakuResume");
            danmakuOnResume();
        }else if(flag==0){
            Log.d("standardplayer", "danmakuPause");
            danmakuOnPause();
        }
    }

}
