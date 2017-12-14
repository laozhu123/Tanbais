package com.neteaseyx.image.imageview.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.neteaseyx.image.imageview.GeneralImageLoader;
import com.neteaseyx.image.imageview.ImageListener;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.view.GestureCropImageView;

/**
 * 支持手势缩放、放大、平移的ImageView基类
 * @author yuhuibin
 * @version 1.0
 */
public class GestureBaseImageView extends GestureCropImageView implements ImageListener {
    private static final String TAG = "GestureBaseImageView";

    public GestureBaseImageView(Context context) {
        super(context);
    }

    public GestureBaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureBaseImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 为减少内存的占有，以宽高的最大值为maxBitmapSize
     * @param imageUri 图片资源，默认支持本地图片（文件路径）；如果GeneralImageLoader支持网络图片则为网络地址。
     */
    public void setImageUri(@NonNull String imageUri) {
        setRotateEnabled(false);

        GeneralImageLoader loader = getImageLoader();
        if(loader != null){
            loader.loadImage(imageUri, this);
        }


    }

    public GeneralImageLoader getImageLoader(){
        return new GeneralImageLoader() {
            @Override
            public void loadImage(String url, final ImageListener l) {
                Uri uri = Uri.parse(url);
                int maxBitmapSize = calculateMaxBitmapSize();
                BitmapLoadUtils.decodeBitmapInBackground(getContext(), uri, uri, maxBitmapSize, maxBitmapSize,
                        new BitmapLoadCallback() {
                            @Override
                            public void onBitmapLoaded(@NonNull final Bitmap bitmap) {
                                if(l != null){
                                    l.onSuccess(bitmap);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Exception bitmapWorkerException) {
                                Log.e(TAG, "onFailure: setImageUri", bitmapWorkerException);
                                if (mTransformImageListener != null) {
                                    mTransformImageListener.onLoadFailure(bitmapWorkerException);
                                }
                            }
                        });
            }
        };
    }

    /**
     * 处理当没有传入Uri的时候，触摸事件所导致的异常
     */
    private float x;
    private float y;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getViewBitmap() == null ){
            return false;
        }
        //多点触控的时候，中断父控件获取到事件
        if (event.getPointerCount() > 1){
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.onTouchEvent(event);
    }

    protected int calculateMaxBitmapSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        int width, height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        return Math.max(width, height);
    }

    @Override
    public void onSuccess(Bitmap bitmap) {
        mBitmapDecoded = true;
        setTargetAspectRatio((float) bitmap.getWidth()/ (float)bitmap.getHeight());
        setImageBitmap(bitmap);
    }

    @Override
    public void onError() {

    }

    public void setOnLongClickListener(@Nullable OnLongClickListener l){
        super.setOnLongClickListener(l);
    }

    public void setOnClickListener(@Nullable OnClickListener l){
        super.setOnClickListener( l);
    }

}

