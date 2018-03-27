package com.test.view.util;

import android.content.Context;
import android.graphics.Paint;

/**
 * author：WangLei
 * date:2018/3/19.
 * QQ:619321796
 */

public class ViewUtil {
    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取文字的高度
     */
    public static float getFontHeight(Paint paint) {
        return paint.ascent() + paint.descent();
    }
}
