package com.crazecoder.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.crazecoder.fluttergsyplayer.R;
import com.crazecoder.utils.NetSpeedUtil;
import com.crazecoder.utils.VideoPlayUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Note of this class.
 *
 * @author crazecoder
 * @since 2018/12/13
 */
public class VideoPlayActivity extends Activity {
    private OrientationUtils orientationUtils;
    private StandardGSYVideoPlayer player;
    private TextView tvSpeed;
    private long speed;
    private Timer timer;
    private long lastTotalRxBytes = 0; // 最后缓存的字节数
    private long lastTimeStamp = 0; // 当前缓存时间

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tvSpeed.setText(String.valueOf(speed) + "kb/s");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分（电池等图标和一起修饰部分）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        TextView tips = findViewById(R.id.tv_tips);
        tvSpeed = findViewById(R.id.tv_speed);
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
        boolean cache = getIntent().getBooleanExtra("cache",true);
        if(TextUtils.isEmpty(url)){
            tips.setVisibility(View.VISIBLE);
        }else {
            tips.setVisibility(View.GONE);
            player.setUp(url, cache, TextUtils.isEmpty(title)?"":title);
            player.startPlayLogic();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                long nowTotalRxBytes = NetSpeedUtil.getTotalRxBytes(VideoPlayActivity.this); // 获取当前数据总量
                long nowTimeStamp = System.currentTimeMillis(); // 当前时间
                // kb/s
                speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp == lastTimeStamp ? nowTimeStamp : nowTimeStamp
                        - lastTimeStamp));// 毫秒转换
                lastTimeStamp = nowTimeStamp;
                lastTotalRxBytes = nowTotalRxBytes;
                handler.sendEmptyMessage(0);
            }
        };
        timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 1000;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
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
        timer.cancel();
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
        timer.cancel();
        //释放所有
        player.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
