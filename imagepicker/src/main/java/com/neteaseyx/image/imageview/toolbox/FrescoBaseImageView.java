package com.neteaseyx.image.imageview.toolbox;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * 基于fresco的ImageView<br>
 *
 * 使用时，该View的width和height不能设为wrap_content，否则无法加载图片。需要match_parent或者具体尺寸。
 * 请求图片时，可以不用带上长宽参数，该View内部会自己计算，直接调用{@link #setLoadingImage(String)}即可。
 * 也可以调用{@link #setLoadingImage(String, int, int)}，传入图片的大小，这个方法效率会比不传尺寸的高一点点。
 *
 * @author Byron(hzchenlk&corp.netease.com)
 * @version 1.0
 */
public abstract class FrescoBaseImageView extends SimpleDraweeView {
    private int mWidth = 0, mHeight = 0;
    private String mUrl;//记录请求url,用于个别界面需要取出比较

    public FrescoBaseImageView(Context context) {
        super(context);
    }

    public FrescoBaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrescoBaseImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /** 合成服务器裁剪参数 */
    public abstract String onUrlClip(String url, int width, int height);

    /**
     * 设置属性
     * @param placeHolder 占位图
     * @param scaleType 缩放类型
     * @param duration 渐显时长
     * @param rp 圆角参数
     */
    public void setProperty(Drawable placeHolder, ScalingUtils.ScaleType scaleType, int duration, RoundingParams rp){
        GenericDraweeHierarchy hierarchy = getHierarchy();
        if(hierarchy == null){
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
            if(placeHolder != null){
                builder.setPlaceholderImage(placeHolder);
            }
            if(scaleType != null){
                builder.setActualImageScaleType(scaleType);
            }
            if(duration >= 0){
                builder.setFadeDuration(duration);
            }
            if(rp != null){
                builder.setRoundingParams(rp);
            }

            hierarchy = builder.build();
        }
        else{
            if(placeHolder != null){
                hierarchy.setPlaceholderImage(placeHolder);
            }
            if(scaleType != null){
                hierarchy.setActualImageScaleType(scaleType);
            }
            if(duration >= 0){
                hierarchy.setFadeDuration(duration);
            }
            if(rp != null){
                hierarchy.setRoundingParams(rp);
            }
        }

        this.setHierarchy(hierarchy);
    }

    public String getUrl(){
        return mUrl;
    }

    /**
     * 加载网络图片。
     * 根据该View的长宽去取图片。所以该View必须是尺寸确定的。
     * 否则需要调用{@link #setLoadingImage(String, int, int)}
     */
    public void setLoadingImage(final String url) {
        if (TextUtils.isEmpty(url)) {
            setURI(null);
            return;
        }

        ViewTreeObserver vo = this.getViewTreeObserver();
        vo.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);

                mWidth = getWidth();
                mHeight = getHeight();
                load(url, mWidth, mHeight);

                return true;
            }
        });
    }

    /**
     * 加载已知尺寸的网络图片。
     */
    public void setLoadingImage(final String url, int width, int height) {
        mUrl = url;
        if (TextUtils.isEmpty(url)) {
            setURI(null);
            return;
        }
        load(url, width, height);
    }

    /**
     * 清除url对应的磁盘缓存以及内存缓存，重新加载图片
     */
    public void loadImageAndEvictCache(String url) {
        Uri uri = Uri.parse(url);
        loadImageAndEvictCache(uri);
    }

    /**
     * 清除url对应的磁盘缓存以及内存缓存，重新加载图片
     */
    public void loadImageAndEvictCache(Uri uri) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);

        setImageURI(uri);
    }

    /**
     * 优先从Cache（Memory、Disk)中加载下载过的图片
     */
    public void setLoadingImageFromCache(String url, int width, int height) {
        mUrl = url;
        Uri uri = Uri.parse(onUrlClip(url.trim(), width, height));

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(imageRequest)
                .build();
        this.setController(controller);
    }

    /**
     * 优先从Cache（Memory、Disk)中加载下载过的图片
     */
    public void setLoadingImageFromCache(String url, int width, int height, final ImageDownloadListener listener) {
        mUrl = url;
        Uri uri = Uri.parse(onUrlClip(url.trim(), width, height));

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(imageRequest)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        if (listener != null) {
                            listener.onImageGet();
                        }
                    }
                })
                .build();
        this.setController(controller);
    }

    /**
     * 把图片下载到磁盘缓存，但是不显示出来
     */
    public void downloadToDisCache(String url, int width, int height) {
        mUrl = url;
        Uri uri = Uri.parse(onUrlClip(url.trim(), width, height));
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();
        Fresco.getImagePipeline().prefetchToDiskCache(request, getContext());
    }

    private void load(String url, int w, int h) {
        if(TextUtils.isEmpty(url)){
            return;
        }

        mUrl = url;
        url = onUrlClip(url.trim(), w, h);
        Uri uri = Uri.parse(url);
        setURI(uri);
    }

    private void setURI(Uri uri) {
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(uri).build();
        this.setController(controller);
    }

    public interface ImageDownloadListener {
        void onImageGet();
    }
}
