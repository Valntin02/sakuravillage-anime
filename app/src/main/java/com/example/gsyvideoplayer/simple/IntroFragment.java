package com.example.gsyvideoplayer.simple;



import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gsyvideoplayer.DanmkuVideoActivity;
import com.example.gsyvideoplayer.R;
import com.example.gsyvideoplayer.VodData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import android.widget.LinearLayout;

public class IntroFragment extends Fragment {

    private VodData videoData;
    private ArrayList<String> videourls;

    private TextView textView;
    private ImageView imageView;

    private LinearLayout  episodeContainer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        DanmkuVideoActivity activity=(DanmkuVideoActivity) getActivity();
        if(activity!=null){
            videoData = activity.getVideoData();
            videourls = activity.getVideoUrls();
            episodeContainer = view.findViewById(R.id.episodeContainer); // 初始化 episodeContainer

        }else {
            Log.e("IntroFragment","get activity error");
        }
        textView =view.findViewById(R.id.textIntro);
        imageView=view.findViewById(R.id.videoImage);

        if(videoData!=null){
            String str;

            if(videoData.getVod_content()==null){
                 str="简介: 什么都都没有留下。。。";
            }else {
                 str="简介: " +videoData.getVod_content();
            }
            str="<b>" + videoData.getVod_name() + "</b><br><br>" +str;
            textView.setText(Html.fromHtml(str));
            // 使用 Picasso 来加载图片
            Picasso.get().load(videoData.getVod_pic()).into(imageView);

        }else{
            Log.e("IntroFragment","get videoData error");
        }
        addEpisodeButton();
        return view;
    }

    private void addEpisodeButton(){
        DanmkuVideoActivity activity=(DanmkuVideoActivity) getActivity();
        if (videourls == null || videourls.isEmpty()) return;
        for (int i = 0; i < videourls.size(); i++) {
            final int index = i;
            Button button = new Button(getContext());
            button.setText("第 " + (i + 1) + " 集");
            button.setTextSize(12);
            button.setTypeface(Typeface.DEFAULT_BOLD);
            button.setTextColor(Color.WHITE);
            button.setBackgroundResource(R.drawable.button_style); // 设置按钮背景（可以自定义）
            button.setPadding(10, 5, 10, 5);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                200,
              120);
            params.setMargins(10, 0, 10, 0);
            button.setLayoutParams(params);

            final String nextVideoUrl = videourls.get(i);
            final String titleName=videoData.getVod_name()+"—第"+(i+1)+"集";;


            button.setOnClickListener(v -> {
                // 更新播放的 URL
                // 更新播放的 URL

                activity.getBinding().danmakuPlayer.setUp(nextVideoUrl, true, null, titleName);
            });

            episodeContainer.addView(button);
        }
    }
}
