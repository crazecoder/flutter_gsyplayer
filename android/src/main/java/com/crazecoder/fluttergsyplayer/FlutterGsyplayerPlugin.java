package com.crazecoder.fluttergsyplayer;

import android.app.Activity;
import android.content.Intent;

import com.crazecoder.activity.VideoPlayActivity;
import com.crazecoder.utils.VideoPlayUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.StandardMessageCodec;

/**
 * FlutterGsyplayerPlugin
 */
public class FlutterGsyplayerPlugin implements MethodCallHandler {
    /**
     * Plugin registration.
     */
    private static StandardGSYVideoPlayer videoPlayer;
    private Activity activity;

    private FlutterGsyplayerPlugin(Activity activity) {
        this.activity = activity;
        initVideoPlayer();
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_gsyplayer");
        channel.setMethodCallHandler(new FlutterGsyplayerPlugin(registrar.activity()));
        registrar.platformViewRegistry().registerViewFactory("gsyplayer", new GSYPlayerFactory(new StandardMessageCodec(), videoPlayer));
    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        if (call.method.equals("onBackPress")) {
            result.success("");
        } else if (call.method.equals("play")) {
            Intent intent = new Intent(activity, VideoPlayActivity.class);
            if (call.hasArgument("url"))
                intent.putExtra("url", call.argument("url").toString());
            if (call.hasArgument("title") && call.argument("title") != null)
                intent.putExtra("title", call.argument("title").toString());
            if (call.hasArgument("cache") && call.argument("cache") != null)
                intent.putExtra("cache", (Boolean) call.argument("cache"));
            activity.startActivity(intent);
            result.success("");
        } else if (call.method.equals("seekTo")) {
            long position = 0;
            if (call.hasArgument("position"))
                position = call.argument("position");
            videoPlayer.seekTo(position);
            result.success("");
        }else {
            result.notImplemented();
        }
    }

    private void initVideoPlayer() {
        videoPlayer = new StandardGSYVideoPlayer(activity);
        //设置返回按键功能
//        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        VideoPlayUtil.init(videoPlayer);
    }

}
