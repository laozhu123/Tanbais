package xiaogao.zjut.tabbaishuo.load;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.view.View;

import com.bumptech.glide.MemoryCategory;

import xiaogao.zjut.tabbaishuo.config.GlobalConfig;
import xiaogao.zjut.tabbaishuo.config.SingleConfig;
import xiaogao.zjut.tabbaishuo.utils.DownLoadImageService;

public class ImageLoader {
    public static Context context;
    public static int CACHE_IMAGE_SIZE = 250;

    public ImageLoader() {
    }

    public static void init(Context context) {
        init(context, CACHE_IMAGE_SIZE);
    }

    public static void init(Context context, int cacheSizeInM) {
        init(context, cacheSizeInM, MemoryCategory.NORMAL);
    }

    public static void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory) {
        init(context, cacheSizeInM, memoryCategory, true);
    }

    public static void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
        context = context;
        GlobalConfig.init(context, cacheSizeInM, memoryCategory, isInternalCD);
    }

    public static ILoader getActualLoader() {
        return GlobalConfig.getLoader();
    }

    public static SingleConfig.ConfigBuilder with(Context context) {
        return new SingleConfig.ConfigBuilder(context);
    }

    public static void trimMemory(int level) {
        getActualLoader().trimMemory(level);
    }

    public static void clearAllMemoryCaches() {
        getActualLoader().clearAllMemoryCaches();
    }

    public static void pauseRequests() {
        getActualLoader().pause();
    }

    public static void resumeRequests() {
        getActualLoader().resume();
    }

    public static void clearMomoryCache(View view) {
        getActualLoader().clearMomoryCache(view);
    }

    public static void clearDiskCache() {
        getActualLoader().clearDiskCache();
    }

    public static void clearMomory() {
        getActualLoader().clearMomory();
    }

    public static void saveImageIntoGallery(DownLoadImageService downLoadImageService) {
        getActualLoader().saveImageIntoGallery(downLoadImageService);
    }
}

