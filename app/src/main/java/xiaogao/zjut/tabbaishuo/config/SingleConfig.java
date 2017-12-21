package xiaogao.zjut.tabbaishuo.config;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation.Animator;

import java.io.File;

import xiaogao.zjut.tabbaishuo.utils.ImageUtil;

public class SingleConfig {
    private Context context;
    private boolean ignoreCertificateVerify;
    private String url;
    private float thumbnail;
    private String filePath;
    private File file;
    private int resId;
    private String rawPath;
    private String assertspath;
    private String contentProvider;
    private boolean isGif;
    private View target;
    private int width;
    private int height;
    private int oWidth;
    private int oHeight;
    private boolean isNeedVignette;
    private boolean isNeedSketch;
    private float pixelationLevel;
    private boolean isNeedPixelation;
    private boolean isNeedInvert;
    public float contrastLevel;
    private boolean isNeedContrast;
    private boolean isNeedSepia;
    private boolean isNeedToon;
    private boolean isNeedSwirl;
    private boolean isNeedGrayscale;
    private boolean isNeedBrightness;
    private float brightnessLeve;
    private boolean needBlur;
    private boolean needFilteColor;
    private int filteColor;
    private int priority;
    private int animationType;
    private int animationId;
    private Animation animation;
    private Animator animator;
    private int blurRadius;
    private int placeHolderResId;
    private int errorResId;
    private int shapeMode;
    private int rectRoundRadius;
    private DiskCacheStrategy diskCacheStrategy;
    private int scaleMode;
    private SingleConfig.BitmapListener bitmapListener;
    private boolean asBitmap;

    public SingleConfig(SingleConfig.ConfigBuilder builder) {

        this.url = builder.url;
        this.thumbnail = builder.thumbnail;
        this.filePath = builder.filePath;
        this.file = builder.file;
        this.resId = builder.resId;
        this.rawPath = builder.rawPath;
        this.assertspath = builder.assertspath;
        this.contentProvider = builder.contentProvider;
        this.ignoreCertificateVerify = builder.ignoreCertificateVerify;
        this.target = builder.target;
        this.width = builder.width;
        this.height = builder.height;
        this.oWidth = builder.oWidth;
        this.oHeight = builder.oHeight;
        this.shapeMode = builder.shapeMode;
        if(this.shapeMode == 1) {
            this.rectRoundRadius = builder.rectRoundRadius;
        }

        this.scaleMode = builder.scaleMode;
        this.diskCacheStrategy = builder.diskCacheStrategy;
        this.animationId = builder.animationId;
        this.animationType = builder.animationType;
        this.animator = builder.animator;
        this.animation = builder.animation;
        this.priority = builder.priority;
        this.isNeedVignette = builder.isNeedVignette;
        this.isNeedSketch = builder.isNeedSketch;
        this.pixelationLevel = builder.pixelationLevel;
        this.isNeedPixelation = builder.isNeedPixelation;
        this.isNeedInvert = builder.isNeedInvert;
        this.contrastLevel = builder.contrastLevel;
        this.isNeedContrast = builder.isNeedContrast;
        this.isNeedSepia = builder.isNeedSepia;
        this.isNeedToon = builder.isNeedToon;
        this.isNeedSwirl = builder.isNeedSwirl;
        this.isNeedGrayscale = builder.isNeedGrayscale;
        this.isNeedBrightness = builder.isNeedBrightness;
        this.brightnessLeve = builder.brightnessLeve;
        this.filteColor = builder.filteColor;
        this.needBlur = builder.needBlur;
        this.needFilteColor = builder.needFilteColor;
        this.placeHolderResId = builder.placeHolderResId;
        this.asBitmap = builder.asBitmap;
        this.bitmapListener = builder.bitmapListener;
        this.isGif = builder.isGif;
        this.blurRadius = builder.blurRadius;
        this.errorResId = builder.errorResId;
    }

    public boolean isAsBitmap() {
        return this.asBitmap;
    }

    public Context getContext() {
        if(this.context == null) {
            this.context = GlobalConfig.context;
        }

        return this.context;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return this.diskCacheStrategy;
    }

    public int getErrorResId() {
        return this.errorResId;
    }

    public String getContentProvider() {
        return this.contentProvider;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public File getFile() {
        return this.file;
    }

    public boolean isNeedBlur() {
        return this.needBlur;
    }

    public int getPlaceHolderResId() {
        return this.placeHolderResId;
    }

    public int getRectRoundRadius() {
        return this.rectRoundRadius;
    }

    public int getResId() {
        return this.resId;
    }

    public String getRawPath() {
        return this.rawPath;
    }

    public String getAssertspath() {
        return this.assertspath;
    }

    public int getScaleMode() {
        return this.scaleMode;
    }

    public int getShapeMode() {
        return this.shapeMode;
    }

    public View getTarget() {
        return this.target;
    }

    public String getUrl() {
        return this.url;
    }

    public int getHeight() {
        if(this.height <= 0) {
            if(this.target != null) {
                this.height = this.target.getMeasuredWidth();
            }

            if(this.height <= 0) {
                this.height = GlobalConfig.getWinHeight();
            }
        }

        return this.height;
    }

    public int getWidth() {
        if(this.width <= 0) {
            if(this.target != null) {
                this.width = this.target.getMeasuredWidth();
            }

            if(this.width <= 0) {
                this.width = GlobalConfig.getWinWidth();
            }
        }

        return this.width;
    }

    public int getoWidth() {
        return this.oWidth;
    }

    public int getoHeight() {
        return this.oHeight;
    }

    public int getAnimationType() {
        return this.animationType;
    }

    public int getAnimationId() {
        return this.animationId;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public Animator getAnimator() {
        return this.animator;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getFilteColor() {
        return this.filteColor;
    }

    public float getContrastLevel() {
        return this.contrastLevel;
    }

    public boolean isNeedFilteColor() {
        return this.needFilteColor;
    }

    public float getBrightnessLeve() {
        return this.brightnessLeve;
    }

    public boolean isNeedBrightness() {
        return this.isNeedBrightness;
    }

    public boolean isIgnoreCertificateVerify() {
        return this.ignoreCertificateVerify;
    }

    public SingleConfig.BitmapListener getBitmapListener() {
        return this.bitmapListener;
    }

    public float getThumbnail() {
        return this.thumbnail;
    }

    public void setBitmapListener(SingleConfig.BitmapListener bitmapListener) {
        this.bitmapListener = ImageUtil.getBitmapListenerProxy(bitmapListener);
    }

    private void show() {
        GlobalConfig.getLoader().request(this);
    }

    public boolean isGif() {
        return this.isGif;
    }

    public int getBlurRadius() {
        return this.blurRadius;
    }

    public boolean isNeedGrayscale() {
        return this.isNeedGrayscale;
    }

    public boolean isNeedSwirl() {
        return this.isNeedSwirl;
    }

    public boolean isNeedToon() {
        return this.isNeedToon;
    }

    public boolean isNeedSepia() {
        return this.isNeedSepia;
    }

    public boolean isNeedContrast() {
        return this.isNeedContrast;
    }

    public boolean isNeedInvert() {
        return this.isNeedInvert;
    }

    public boolean isNeedPixelation() {
        return this.isNeedPixelation;
    }

    public float getPixelationLevel() {
        return this.pixelationLevel;
    }

    public boolean isNeedSketch() {
        return this.isNeedSketch;
    }

    public boolean isNeedVignette() {
        return this.isNeedVignette;
    }

    public static class ConfigBuilder {
        private Context context;
        private boolean ignoreCertificateVerify;
        private String url;
        private float thumbnail;
        private String filePath;
        private File file;
        private int resId;
        private String rawPath;
        private String assertspath;
        private String contentProvider;
        private boolean isGif;
        private View target;
        private boolean asBitmap;
        private SingleConfig.BitmapListener bitmapListener;
        private int width;
        private int height;
        private int oWidth;
        private int oHeight;
        private boolean isNeedVignette;
        private boolean isNeedSketch;
        private float pixelationLevel;
        private boolean isNeedPixelation;
        private boolean isNeedInvert;
        private float contrastLevel;
        private boolean isNeedContrast;
        private boolean isNeedSepia;
        private boolean isNeedToon;
        private boolean isNeedSwirl;
        private boolean isNeedGrayscale;
        private boolean isNeedBrightness;
        private float brightnessLeve;
        private boolean needBlur;
        private boolean needFilteColor;
        private int blurRadius;
        private int placeHolderResId;
        private int errorResId;
        private int shapeMode;
        private int rectRoundRadius;
        private DiskCacheStrategy diskCacheStrategy;
        private int scaleMode;
        private int priority;
        private int filteColor;
        public int animationId;
        public int animationType;
        public Animation animation;
        public Animator animator;

        public ConfigBuilder(Context context) {
            this.ignoreCertificateVerify = GlobalConfig.ignoreCertificateVerify;
            this.isGif = false;
            this.isNeedContrast = false;
            this.isNeedSepia = false;
            this.isNeedToon = false;
            this.isNeedSwirl = false;
            this.isNeedGrayscale = false;
            this.isNeedBrightness = false;
            this.needBlur = false;
            this.needFilteColor = false;
            this.context = context;
        }

        public SingleConfig.ConfigBuilder ignoreCertificateVerify(boolean ignoreCertificateVerify) {
            this.ignoreCertificateVerify = ignoreCertificateVerify;
            return this;
        }

        public SingleConfig.ConfigBuilder thumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public SingleConfig.ConfigBuilder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public SingleConfig.ConfigBuilder url(String url) {
            this.url = url;
            if(TextUtils.isEmpty(url)) {
                return this;
            } else {
                if(url.contains("gif")) {
                    this.isGif = true;
                }

                return this;
            }
        }

        public SingleConfig.ConfigBuilder file(String filePath) {
            if(TextUtils.isEmpty(filePath)) {
                return this;
            } else if(filePath.startsWith("file:")) {
                this.filePath = filePath;
                return this;
            } else if(!(new File(filePath)).exists()) {
                Log.e("imageloader", "文件不存在");
                return this;
            } else {
                this.filePath = filePath;
                if(filePath.contains("gif")) {
                    this.isGif = true;
                }

                return this;
            }
        }

        public SingleConfig.ConfigBuilder file(File file) {
            this.file = file;
            return this;
        }

        public SingleConfig.ConfigBuilder res(int resId) {
            this.resId = resId;
            return this;
        }

        public SingleConfig.ConfigBuilder content(String contentProvider) {
            if(TextUtils.isEmpty(contentProvider)) {
                return this;
            } else if(contentProvider.startsWith("content:")) {
                this.contentProvider = contentProvider;
                return this;
            } else {
                if(contentProvider.contains("gif")) {
                    this.isGif = true;
                }

                return this;
            }
        }

        public SingleConfig.ConfigBuilder raw(String rawPath) {
            this.rawPath = rawPath;
            if(TextUtils.isEmpty(rawPath)) {
                return this;
            } else {
                if(rawPath.contains("gif")) {
                    this.isGif = true;
                }

                return this;
            }
        }

        public SingleConfig.ConfigBuilder asserts(String assertspath) {
            this.assertspath = assertspath;
            if(TextUtils.isEmpty(assertspath)) {
                return this;
            } else {
                if(assertspath.contains("gif")) {
                    this.isGif = true;
                }

                return this;
            }
        }

        public void into(View targetView) {
            this.target = targetView;
            (new SingleConfig(this)).show();
        }

        public void asBitmap(SingleConfig.BitmapListener bitmapListener) {
            this.bitmapListener = ImageUtil.getBitmapListenerProxy(bitmapListener);
            this.asBitmap = true;
            (new SingleConfig(this)).show();
        }

        public SingleConfig.ConfigBuilder override(int oWidth, int oHeight) {
            this.oWidth = ImageUtil.dip2px((float)oWidth);
            this.oHeight = ImageUtil.dip2px((float)oHeight);
            return this;
        }

        public SingleConfig.ConfigBuilder placeHolder(int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        public SingleConfig.ConfigBuilder blur(int blurRadius) {
            this.needBlur = true;
            this.blurRadius = blurRadius;
            return this;
        }

        public SingleConfig.ConfigBuilder asCircle() {
            this.shapeMode = 2;
            return this;
        }

        public SingleConfig.ConfigBuilder rectRoundCorner(int rectRoundRadius) {
            this.rectRoundRadius = ImageUtil.dip2px((float)rectRoundRadius);
            this.shapeMode = 1;
            return this;
        }

        public SingleConfig.ConfigBuilder asSquare() {
            this.shapeMode = 3;
            return this;
        }

        public SingleConfig.ConfigBuilder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        public SingleConfig.ConfigBuilder scale(int scaleMode) {
            this.scaleMode = scaleMode;
            return this;
        }

        public SingleConfig.ConfigBuilder animate(int animationId) {
            this.animationType = 1;
            this.animationId = animationId;
            return this;
        }

        public SingleConfig.ConfigBuilder animate(Animator animator) {
            this.animationType = 3;
            this.animator = animator;
            return this;
        }

        public SingleConfig.ConfigBuilder animate(Animation animation) {
            this.animationType = 2;
            this.animation = animation;
            return this;
        }

        public SingleConfig.ConfigBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public SingleConfig.ConfigBuilder colorFilter(int filteColor) {
            this.filteColor = filteColor;
            this.needFilteColor = true;
            return this;
        }

        public SingleConfig.ConfigBuilder brightnessFilter(float level) {
            this.isNeedBrightness = true;
            this.brightnessLeve = level;
            return this;
        }

        public SingleConfig.ConfigBuilder grayscaleFilter() {
            this.isNeedGrayscale = true;
            return this;
        }

        public SingleConfig.ConfigBuilder swirlFilter() {
            this.isNeedSwirl = true;
            return this;
        }

        public SingleConfig.ConfigBuilder toonFilter() {
            this.isNeedToon = true;
            return this;
        }

        public SingleConfig.ConfigBuilder sepiaFilter() {
            this.isNeedSepia = true;
            return this;
        }

        public SingleConfig.ConfigBuilder contrastFilter(float constrasrLevel) {
            this.contrastLevel = constrasrLevel;
            this.isNeedContrast = true;
            return this;
        }

        public SingleConfig.ConfigBuilder invertFilter() {
            this.isNeedInvert = true;
            return this;
        }

        public SingleConfig.ConfigBuilder pixelationFilter(float pixelationLevel) {
            this.pixelationLevel = pixelationLevel;
            this.isNeedPixelation = true;
            return this;
        }

        public SingleConfig.ConfigBuilder sketchFilter() {
            this.isNeedSketch = true;
            return this;
        }

        public SingleConfig.ConfigBuilder vignetteFilter() {
            this.isNeedVignette = true;
            return this;
        }
    }

    public interface BitmapListener {
        void onSuccess(Bitmap var1);

        void onFail();
    }
}

