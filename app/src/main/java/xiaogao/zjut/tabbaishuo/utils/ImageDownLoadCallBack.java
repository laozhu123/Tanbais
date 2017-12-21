package xiaogao.zjut.tabbaishuo.utils;

/**
 * Created by Administrator on 2017/12/20.
 */


import android.graphics.Bitmap;

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(Bitmap var1);

    void onDownLoadFailed();
}

