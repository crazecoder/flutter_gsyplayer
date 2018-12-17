package com.crazecoder.utils;

import android.view.View;

import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;

/**
 * Note of this class.
 *
 * @author crazecoder
 * @since 2018/12/13
 */
public class VideoPlayUtil {
    public static void init(StandardGSYVideoPlayer videoPlayer){
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);//EXO模式
//        PlayerFactory.setPlayManager(SystemPlayerManager.class);//系统模式
//        PlayerFactory.setPlayManager(IjkPlayerManager.class);//ijk模式
        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);//exo缓存模式，支持m3u8，只支持exo
//        CacheFactory.setCacheManager(ProxyCacheManager.class);//代理缓存模式，支持所有模式，不支持m3u8等
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        videoPlayer.setSeekRatio(5);
//        WebView webview = new WebView(videoPlayer.getContext());
//
//        // 得到WebSettings对象
//        WebSettings settings = webview.getSettings();
//        // 如果访问的页面中有JavaScript，则WebView必须设置支持JavaScript，否则显示空白页面
//        webview.getSettings().setJavaScriptEnabled(true);
//        // 获取到UserAgentString
//        String userAgent = settings.getUserAgentString();
//        Map<String,String> map = new HashMap<>();
//        map.put("User-Agent",userAgent);
//        videoPlayer.setMapHeadData(map);
        //设置旋转
//        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                orientationUtils.resolveByClick();
//            }
//        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
    }
}
