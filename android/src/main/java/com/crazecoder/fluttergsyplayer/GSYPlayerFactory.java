package com.crazecoder.fluttergsyplayer;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Map;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

/**
 * Note of this class.
 *
 * @author chendong
 * @since 2018/12/12
 */
public class GSYPlayerFactory extends PlatformViewFactory {

    private StandardGSYVideoPlayer videoPlayer;
    private boolean autoPlay;
    private String url;

    public GSYPlayerFactory(MessageCodec<Object> createArgsCodec,StandardGSYVideoPlayer videoPlayer) {
        super(createArgsCodec);
        this.videoPlayer = videoPlayer;
    }

    @Override
    public PlatformView create(final Context context, int i, Object o) {
        Map<String, Object> param = (Map<String, Object>) o;
        if (param.containsKey("url")) {
            url = param.get("url").toString();
        }
        if (param.containsKey("autoPlay"))
            autoPlay = (boolean) param.get("autoPlay");
        return new PlatformView() {
            @Override
            public View getView() {
                Log.d("GSYPlayerFactory","create");
                if (!TextUtils.isEmpty(url)) {
                    videoPlayer.setUp(url, true, "");
                }
                if (autoPlay) {
                    videoPlayer.startPlayLogic();
                }
                return videoPlayer;
            }

            @Override
            public void dispose() {
                GSYVideoManager.releaseAllVideos();
            }
        };
    }


}
