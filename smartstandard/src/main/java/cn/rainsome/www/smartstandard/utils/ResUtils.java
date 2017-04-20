package cn.rainsome.www.smartstandard.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;

/**
 * 获取资源的工具类
 * Created by Yomii on 2017/2/10.
 */
public class ResUtils {

    private ResUtils() {
    }


    /**
     * 获取图片
     * @param context context
     * @param resId resId
     *
     * @return Drawable
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId){
        return ResourcesCompat.getDrawable(context.getResources(), resId, context.getTheme());
    }

    /**
     * 获取颜色值
     * @param context context
     * @param resId resId
     *
     * @return @ColorInt int
     */
    public static @ColorInt int getColor(Context context, @ColorRes int resId){
        return ResourcesCompat.getColor(context.getResources(), resId, context.getTheme());
    }

    /**
     * 获取Dimen的Pixel值
     * @param context context
     * @param id dimens resId
     *
     * @return pixel
     */
    public static float getDimensPixel(Context context, @DimenRes int id) {
        return context.getResources().getDimensionPixelOffset(id);
    }

}
