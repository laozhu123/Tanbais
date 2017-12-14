package com.neteaseyx.image.imageview;

import android.graphics.Bitmap;

/**
 * 请求图片后的回调
 * @author Byron(hzchenlk&corp.netease.com)
 * @version 1.0
 */
public interface ImageListener {
    /** 成功获取图片 */
    void onSuccess(Bitmap bitmap);
    /** 图片获取失败 */
    void onError();
}
