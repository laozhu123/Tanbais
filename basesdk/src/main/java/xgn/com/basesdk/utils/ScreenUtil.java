package xgn.com.basesdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ScreenUtil {
    private static final String TAG = "ScreenUtil";

    private static double DIALOG_RATIO = 0.85;

    private static int screenWidth;  //屏幕宽度
    private static int screenHeight;
    private static int screenMin;// 宽与高中较小的值
    private static int screenMax;// 宽与高中较大的值

    private static float density;
    private static float scaleDensity;
    private static float xdpi;
    private static float ydpi;
    private static int densityDpi;

    private static int dialogWidth;

    private static DisplayMetrics dm;
    //状态栏高度
    private static int statusbarheight;
    //导航栏高度
    public static int navbarheight;

    public static void GetInfo(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
        screenMax = (screenWidth < screenHeight) ? screenHeight : screenWidth;
        density = dm.density;
        scaleDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception E) {
            E.printStackTrace();
        }
        return sbar;
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (dm == null) {
            GetInfo(context);
        }
        return dm;
    }

    public static int dip2px(float dipValue, Context context) {
        final float scale = getDisplayDensity(context);
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * dip转pixel
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue, Context context) {
        final float scale = getDisplayDensity(context);
        return (int) (pxValue / scale + 0.5f);
    }

    private static float getDisplayDensity(Context context) {
        if (density == 0) {
            GetInfo(context);
        }
        return density;
    }

    public static int getDisplayWidth(Context context) {
        if (screenWidth == 0) {
            GetInfo(context);
        }
        return screenWidth;
    }

    public static int getDisplayHeight(Context context) {
        if (screenHeight == 0) {
            GetInfo(context);
        }
        return screenHeight;
    }

    public static int getScreenMin(Context context) {
        if (screenMin == 0) {
            GetInfo(context);
        }
        return screenMin;
    }

    public static int getScreenMax(Context context) {
        if (screenMin == 0) {
            GetInfo(context);
        }
        return screenMax;
    }

    public static float getScaleDensity(Context context) {
        if (screenMin == 0) {
            GetInfo(context);
        }
        return scaleDensity;

    }

    public static int getDialogWidth(Context context) {
        dialogWidth = (int) (getScreenMin(context) * DIALOG_RATIO);
        return dialogWidth;
    }

    /**
     * 获取屏幕宽度，单位为px
     *
     * @param context 应用程序上下文
     * @return 屏幕宽度，单位px
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.widthPixels;
        } else {
            return 0;
        }
    }

    /**
     * 获取屏幕高度，单位为px
     *
     * @param context 应用程序上下文
     * @return 屏幕高度，单位px
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.heightPixels;
        } else {
            return 0;
        }
    }

    /**
     * 获取系统dp尺寸密度值
     *
     * @param context 应用程序上下文
     */
    public static float getDensity(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.density;
        } else {
            return 0;
        }
    }

    /**
     * 获取系统字体sp密度值
     *
     * @param context 应用程序上下文
     */
    public static float getScaledDensity(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);

        if (dm != null) {
            return dm.scaledDensity;
        } else {
            return 0;
        }
    }

}

