package com.crazecoder.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.crazecoder.fluttergsyplayer.R;
import com.crazecoder.utils.VideoPlayUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Note of this class.
 *
 * @author chendong
 * @since 2018/12/13
 */
public class VideoPlayActivity extends Activity {
    private OrientationUtils orientationUtils;
    private StandardGSYVideoPlayer player;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分（电池等图标和一起修饰部分）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        TextView tips = findViewById(R.id.tv_tips);
        player = findViewById(R.id.video_player);
        //设置旋转
        orientationUtils = new OrientationUtils(this, player);
        player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        player.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        VideoPlayUtil.init(player);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        if(TextUtils.isEmpty(url)){
            tips.setVisibility(View.VISIBLE);
        }else {
            tips.setVisibility(View.GONE);
            player.setUp(url, true, TextUtils.isEmpty(title)?"":title);
            player.startPlayLogic();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            player.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        player.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
