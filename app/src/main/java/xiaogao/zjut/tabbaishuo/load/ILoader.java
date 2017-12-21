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

import xiaogao.zjut.tabbaishuo.config.SingleConfig;
import xiaogao.zjut.tabbaishuo.utils.DownLoadImageService;

public interface ILoader {
    void init(Context var1, int var2, MemoryCategory var3, boolean var4);

    void request(SingleConfig var1);

    void pause();

    void resume();

    void clearDiskCache();

    void clearMomoryCache(View var1);

    void clearMomory();

    boolean isCached(String var1);

    void trimMemory(int var1);

    void clearAllMemoryCaches();

    void saveImageIntoGallery(DownLoadImageService var1);
}

