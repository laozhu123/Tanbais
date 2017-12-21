package xiaogao.zjut.tabbaishuo.config;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.bumptech.glide.MemoryCategory;

import xiaogao.zjut.tabbaishuo.load.GlideLoader;
import xiaogao.zjut.tabbaishuo.load.ILoader;

public class GlobalConfig {
    public static String baseUrl;
    public static Context context;
    private static int winHeight;
    private static int winWidth;
    public static int cacheMaxSize;
    public static boolean ignoreCertificateVerify = false;
    private static Handler mainHandler;
    private static ILoader loader;

    public GlobalConfig() {
    }

    public static void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
        context = context;
        cacheMaxSize = cacheSizeInM;
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        winWidth = wm.getDefaultDisplay().getWidth();
        winHeight = wm.getDefaultDisplay().getHeight();
        getLoader().init(context, cacheSizeInM, memoryCategory, isInternalCD);
    }

    public static Handler getMainHandler() {
        if(mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
        }

        return mainHandler;
    }

    public static ILoader getLoader() {
        if(loader == null) {
            loader = new GlideLoader();
        }

        return loader;
    }

    public static int getWinHeight() {
        return context.getResources().getConfiguration().orientation == 2?(winHeight < winWidth?winHeight:winWidth):(context.getResources().getConfiguration().orientation == 1?(winHeight > winWidth?winHeight:winWidth):winHeight);
    }

    public static int getWinWidth() {
        return context.getResources().getConfiguration().orientation == 2?(winHeight > winWidth?winHeight:winWidth):(context.getResources().getConfiguration().orientation == 1?(winHeight < winWidth?winHeight:winWidth):winWidth);
    }
}
