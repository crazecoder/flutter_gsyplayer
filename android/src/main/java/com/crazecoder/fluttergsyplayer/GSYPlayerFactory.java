package com.crazecoder.fluttergsyplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;
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
    private Map<String, Bitmap> bitmaps = new HashMap<>();

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
            if (!bitmaps.containsKey(url))
                bitmaps.put(url, getBitmapFormUrl(url));
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
                    ImageView imageView = new ImageView(context);
                    if (!bitmaps.isEmpty() && bitmaps.containsKey(url))
                        if (bitmaps.get(url) == null)
                            imageView.setImageResource(R.drawable.error);
                        else
                            imageView.setImageBitmap(bitmaps.get(url));
                    else
                        imageView.setImageResource(R.drawable.error);
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(context, VideoPlayActivity.class);
//                            intent.putExtra("url", url);
//                            intent.putExtra("cache", cache);
//                            context.startActivity(intent);
//                        }
//                    });
                    return imageView;
                }

            }

            @Override
            public void dispose() {
                GSYVideoManager.releaseAllVideos();
            }
        };
    }

    protected static Bitmap getBitmapFormUrl(String url) {

        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
        /*getFrameAtTime()--->在setDataSource()之后调用此方法。 如果可能，该方法在任何时间位置找到代表性的帧，
         并将其作为位图返回。这对于生成输入数据源的缩略图很有用。**/
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
