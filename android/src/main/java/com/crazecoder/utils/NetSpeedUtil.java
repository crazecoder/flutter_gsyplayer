package com.crazecoder.utils;

import android.content.Context;
import android.net.TrafficStats;

/**
 * Note of this class.
 *
 * @author crazecoder
 * @since 2018/12/17
 */
public class NetSpeedUtil {
    public static long getTotalRxBytes(Context context) {
        // 得到整个手机的流量值
//        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0
//                : (TrafficStats.getTotalRxBytes() / 1024);// 转为KB
        // // 得到当前应用的流量值
        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) ==
                TrafficStats.UNSUPPORTED ? 0 : (TrafficStats
                .getUidRxBytes(context.getApplicationInfo().uid) / 1024);// 转为KB
    }
}
