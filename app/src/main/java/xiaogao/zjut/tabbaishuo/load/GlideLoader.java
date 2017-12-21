package xiaogao.zjut.tabbaishuo.load;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
import xiaogao.zjut.tabbaishuo.config.GlobalConfig;
import xiaogao.zjut.tabbaishuo.config.SingleConfig;
import xiaogao.zjut.tabbaishuo.utils.DownLoadImageService;
import xiaogao.zjut.tabbaishuo.utils.ImageUtil;

public class GlideLoader implements ILoader {
    public GlideLoader() {
    }

    public void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
        Glide.get(context).setMemoryCategory(memoryCategory);
        GlideBuilder builder = new GlideBuilder(context);
        if(isInternalCD) {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        } else {
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        }

    }

    public void request(final SingleConfig config) {
        RequestManager requestManager = Glide.with(config.getContext());
        DrawableTypeRequest request = this.getDrawableTypeRequest(config, requestManager);
        if(config.isAsBitmap()) {
            SimpleTarget target = new SimpleTarget<Bitmap>(config.getWidth(), config.getHeight()) {
                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                    config.getBitmapListener().onSuccess(bitmap);
                }
            };
            this.setShapeModeAndBlur(config, request);
            if(config.getDiskCacheStrategy() != null) {
                request.diskCacheStrategy(config.getDiskCacheStrategy());
            }

            request.asBitmap().into(target);
        } else {
            if(request == null) {
                return;
            }

            if(ImageUtil.shouldSetPlaceHolder(config)) {
                request.placeholder(config.getPlaceHolderResId());
            }

            int scaleMode = config.getScaleMode();
            switch(scaleMode) {
                case 1:
                    request.centerCrop();
                    break;
                case 2:
                    request.fitCenter();
                    break;
                default:
                    request.fitCenter();
            }

            this.setShapeModeAndBlur(config, request);
            if(config.getThumbnail() != 0.0F) {
                request.thumbnail(config.getThumbnail());
            }

            if(config.getoWidth() != 0 && config.getoHeight() != 0) {
                request.override(config.getoWidth(), config.getoHeight());
            }

            if(config.getDiskCacheStrategy() != null) {
                request.diskCacheStrategy(config.getDiskCacheStrategy());
            }

            this.setAnimator(config, request);
            this.setPriority(config, request);
            if(config.getErrorResId() > 0) {
                request.error(config.getErrorResId());
            }

            if(config.isGif()) {
                request.asGif();
            }

            if(config.getTarget() instanceof ImageView) {
                request.into((ImageView)config.getTarget());
            }
        }

    }

    private void setPriority(SingleConfig config, DrawableTypeRequest request) {
        switch(config.getPriority()) {
            case 1:
                request.priority(Priority.LOW);
                break;
            case 2:
                request.priority(Priority.NORMAL);
                break;
            case 3:
                request.priority(Priority.HIGH);
                break;
            case 4:
                request.priority(Priority.IMMEDIATE);
                break;
            default:
                request.priority(Priority.IMMEDIATE);
        }

    }

    private void setAnimator(SingleConfig config, DrawableTypeRequest request) {
        if(config.getAnimationType() == 1) {
            request.animate(config.getAnimationId());
        } else if(config.getAnimationType() == 3) {
            request.animate(config.getAnimator());
        } else if(config.getAnimationType() == 2) {
            request.animate(config.getAnimation());
        }

    }

    @Nullable
    private DrawableTypeRequest getDrawableTypeRequest(SingleConfig config, RequestManager requestManager) {
        DrawableTypeRequest request = null;
        if(!TextUtils.isEmpty(config.getUrl())) {
            request = requestManager.load(ImageUtil.appendUrl(config.getUrl()));
            Log.e("TAG", "getUrl : " + config.getUrl());
        } else if(!TextUtils.isEmpty(config.getFilePath())) {
            request = requestManager.load(ImageUtil.appendUrl(config.getFilePath()));
            Log.e("TAG", "getFilePath : " + config.getFilePath());
        } else if(!TextUtils.isEmpty(config.getContentProvider())) {
            request = requestManager.loadFromMediaStore(Uri.parse(config.getContentProvider()));
            Log.e("TAG", "getContentProvider : " + config.getContentProvider());
        } else if(config.getResId() > 0) {
            request = requestManager.load(Integer.valueOf(config.getResId()));
            Log.e("TAG", "getResId : " + config.getResId());
        } else if(config.getFile() != null) {
            request = requestManager.load(config.getFile());
            Log.e("TAG", "getFile : " + config.getFile());
        } else if(!TextUtils.isEmpty(config.getAssertspath())) {
            request = requestManager.load(config.getAssertspath());
            Log.e("TAG", "getAssertspath : " + config.getAssertspath());
        } else if(!TextUtils.isEmpty(config.getRawPath())) {
            request = requestManager.load(config.getRawPath());
            Log.e("TAG", "getRawPath : " + config.getRawPath());
        } else {
            request = requestManager.load("");
        }

        return request;
    }

    private void setShapeModeAndBlur(SingleConfig config, DrawableTypeRequest request) {
        int count = 0;
        Transformation[] transformation = new Transformation[this.statisticsCount(config)];
        if(config.isNeedBlur()) {
            transformation[count] = new BlurTransformation(config.getContext(), config.getBlurRadius());
            ++count;
        }

        if(config.isNeedBrightness()) {
            transformation[count] = new BrightnessFilterTransformation(config.getContext(), config.getBrightnessLeve());
            ++count;
        }

        if(config.isNeedGrayscale()) {
            transformation[count] = new GrayscaleTransformation(config.getContext());
            ++count;
        }

        if(config.isNeedFilteColor()) {
            transformation[count] = new ColorFilterTransformation(config.getContext(), config.getFilteColor());
            ++count;
        }

        if(config.isNeedSwirl()) {
            transformation[count] = new SwirlFilterTransformation(config.getContext(), 0.5F, 1.0F, new PointF(0.5F, 0.5F));
            ++count;
        }

        if(config.isNeedToon()) {
            transformation[count] = new ToonFilterTransformation(config.getContext());
            ++count;
        }

        if(config.isNeedSepia()) {
            transformation[count] = new SepiaFilterTransformation(config.getContext());
            ++count;
        }

        if(config.isNeedContrast()) {
            transformation[count] = new ContrastFilterTransformation(config.getContext(), config.getContrastLevel());
            ++count;
        }

        if(config.isNeedInvert()) {
            transformation[count] = new InvertFilterTransformation(config.getContext());
            ++count;
        }

        if(config.isNeedPixelation()) {
            transformation[count] = new PixelationFilterTransformation(config.getContext(), config.getPixelationLevel());
            ++count;
        }

        if(config.isNeedSketch()) {
            transformation[count] = new SketchFilterTransformation(config.getContext());
            ++count;
        }

        if(config.isNeedVignette()) {
            transformation[count] = new VignetteFilterTransformation(config.getContext(), new PointF(0.5F, 0.5F), new float[]{0.0F, 0.0F, 0.0F}, 0.0F, 0.75F);
            ++count;
        }

        switch(config.getShapeMode()) {
            case 0:
            default:
                break;
            case 1:
                transformation[count] = new RoundedCornersTransformation(config.getContext(), config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);
                ++count;
                break;
            case 2:
                transformation[count] = new CropCircleTransformation(config.getContext());
                ++count;
                break;
            case 3:
                transformation[count] = new CropSquareTransformation(config.getContext());
                ++count;
        }

        if(transformation.length != 0) {
            request.bitmapTransform(transformation);
        }

    }

    private int statisticsCount(SingleConfig config) {
        int count = 0;
        if(config.getShapeMode() == 2 || config.getShapeMode() == 1 || config.getShapeMode() == 3) {
            ++count;
        }

        if(config.isNeedBlur()) {
            ++count;
        }

        if(config.isNeedFilteColor()) {
            ++count;
        }

        if(config.isNeedBrightness()) {
            ++count;
        }

        if(config.isNeedGrayscale()) {
            ++count;
        }

        if(config.isNeedSwirl()) {
            ++count;
        }

        if(config.isNeedToon()) {
            ++count;
        }

        if(config.isNeedSepia()) {
            ++count;
        }

        if(config.isNeedContrast()) {
            ++count;
        }

        if(config.isNeedInvert()) {
            ++count;
        }

        if(config.isNeedPixelation()) {
            ++count;
        }

        if(config.isNeedSketch()) {
            ++count;
        }

        if(config.isNeedVignette()) {
            ++count;
        }

        return count;
    }

    public void pause() {
        Glide.with(GlobalConfig.context).pauseRequestsRecursive();
    }

    public void resume() {
        Glide.with(GlobalConfig.context).resumeRequestsRecursive();
    }

    public void clearDiskCache() {
        Glide.get(GlobalConfig.context).clearDiskCache();
    }

    public void clearMomoryCache(View view) {
        Glide.clear(view);
    }

    public void clearMomory() {
        Glide.get(GlobalConfig.context).clearMemory();
    }

    public boolean isCached(String url) {
        return false;
    }

    public void trimMemory(int level) {
        Glide.with(GlobalConfig.context).onTrimMemory(level);
    }

    public void clearAllMemoryCaches() {
        Glide.with(GlobalConfig.context).onLowMemory();
    }

    public void saveImageIntoGallery(DownLoadImageService downLoadImageService) {
        (new Thread(downLoadImageService)).start();
    }
}
