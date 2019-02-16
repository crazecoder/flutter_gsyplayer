package com.crazecoder.fluttergsyplayer;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Map;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

/**
 * Note of this class.
 *
 * @author crazecoder
 * @since 2018/12/12
 */
public class GSYPlayerFactory extends PlatformViewFactory {

    private StandardGSYVideoPlayer videoPlayer;
    private boolean autoPlay;
    private String url;
    private boolean cache;
    private boolean isPreview;
    private ImageView imageView;


    public GSYPlayerFactory(MessageCodec<Object> createArgsCodec, StandardGSYVideoPlayer videoPlayer) {
        super(createArgsCodec);
        this.videoPlayer = videoPlayer;
    }

    @Override
    public PlatformView create(final Context context, int i, Object o) {
        Map<String, Object> param = (Map<String, Object>) o;
        if (param.containsKey("url")) {
            url = param.get("url").toString();
        }
        if (param.containsKey("cache")) {
            cache = (boolean) param.get("cache");
        }
        if (param.containsKey("autoPlay"))
            autoPlay = (boolean) param.get("autoPlay");
        if (param.containsKey("isPreview")) {
            isPreview = (boolean) param.get("isPreview");
        }
        return new PlatformView() {
            @Override
            public View getView() {
                if (!isPreview) {
                    if (!TextUtils.isEmpty(url)) {
                        videoPlayer.setUp(url, cache, "");
                    }
                    if (autoPlay) {
                        videoPlayer.startPlayLogic();
                    }
                    return videoPlayer;
                } else {
                    imageView = new ImageView(context);
                    loadCover(imageView,url,context);
                    return imageView;
                }

            }

            @Override
            public void dispose() {
                GSYVideoManager.releaseAllVideos();
            }
        };
    }
    /**
     * 加载第1秒的帧数作为封面
     *  url就是视频的地址
     */
    private static void loadCover(ImageView imageView, String url, Context context) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(R.drawable.error)//可以忽略
                )
                .load(url)
                .into(imageView);
    }
}
