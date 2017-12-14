package com.neteaseyx.image.imageview.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.neteaseyx.image.imageview.GeneralImageLoader;
import com.neteaseyx.image.imageview.ImageListener;

/**
 * 加载网络图片的ImageView基类
 * @author Byron(hzchenlk&corp.netease.com)
 * @version 1.0
 */
public abstract class NetworkBaseImageView extends ImageView implements ImageListener {
    /** 默认图片的res id */
    private int mDefaultImgResId;
    private int mErrorImageId;

    public NetworkBaseImageView(Context context) {
        this(context, null, 0);
    }

    public NetworkBaseImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkBaseImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public abstract GeneralImageLoader getImageLoader();

    /** 设置默认图片的res id，请求的图片如果为null就使用默认图片 */
    public void setDefaultResId(int resId){
        mDefaultImgResId = resId;
    }

    /** 设置默认出错图片id */
    public void setErrorResId(int resId){
        mErrorImageId = resId;
    }

    /** 加载网络图片。取到的图片和服务器上的图片一样大小。 */
    public void setLoadingImage(String url) {
        // 先显示默认图
        if(mDefaultImgResId > 0){
            setImageResource(mDefaultImgResId);
        }

        GeneralImageLoader loader = getImageLoader();
        if(loader != null){
            loader.loadImage(url, this);
        }
    }

    @Override
    public void onSuccess(Bitmap bitmap) {
        setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        if (mErrorImageId != 0) {
            setImageResource(mErrorImageId);
        }
    }
}
