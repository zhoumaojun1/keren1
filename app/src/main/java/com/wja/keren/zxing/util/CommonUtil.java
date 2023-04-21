package com.wja.keren.zxing.util;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * @author wj
 */

public class CommonUtil {

    public CommonUtil() {
    }

    /**
     * dp转成px
     */
    public static int dp2px(@NonNull Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int getScreenWidthInPx(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeightInPx(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
