package xiaogao.zjut.tabbaishuo.views;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;

import io.rong.photoview.IPhotoView;
import io.rong.photoview.PhotoViewAttacher;

public class PhotoView extends android.support.v7.widget.AppCompatImageView implements IPhotoView {
    private final PhotoViewAttacher mAttacher;
    private ScaleType mPendingScaleType;

    public PhotoView(Context context) {
        this(context, (AttributeSet)null);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        super.setScaleType(ScaleType.MATRIX);
        this.mAttacher = new PhotoViewAttacher(this);
        if(null != this.mPendingScaleType) {
            this.setScaleType(this.mPendingScaleType);
            this.mPendingScaleType = null;
        }

    }

    public void setPhotoViewRotation(float rotationDegree) {
        this.mAttacher.setPhotoViewRotation(rotationDegree);
    }

    public boolean canZoom() {
        return this.mAttacher.canZoom();
    }

    public RectF getDisplayRect() {
        return this.mAttacher.getDisplayRect();
    }

    public Matrix getDisplayMatrix() {
        return this.mAttacher.getDrawMatrix();
    }

    @Override
    public void getDisplayMatrix(Matrix matrix) {

    }

    public boolean setDisplayMatrix(Matrix finalRectangle) {
        return this.mAttacher.setDisplayMatrix(finalRectangle);
    }

    /** @deprecated */
    @Deprecated
    public float getMinScale() {
        return this.getMinimumScale();
    }

    public float getMinimumScale() {
        return this.mAttacher.getMinimumScale();
    }

    /** @deprecated */
    @Deprecated
    public float getMidScale() {
        return this.getMediumScale();
    }

    public float getMediumScale() {
        return this.mAttacher.getMediumScale();
    }

    /** @deprecated */
    @Deprecated
    public float getMaxScale() {
        return this.getMaximumScale();
    }

    public float getMaximumScale() {
        return this.mAttacher.getMaximumScale();
    }

    public float getScale() {
        return this.mAttacher.getScale();
    }

    public ScaleType getScaleType() {
        return this.mAttacher.getScaleType();
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    /** @deprecated */
    @Deprecated
    public void setMinScale(float minScale) {
        this.setMinimumScale(minScale);
    }

    public void setMinimumScale(float minimumScale) {
        this.mAttacher.setMinimumScale(minimumScale);
    }

    /** @deprecated */
    @Deprecated
    public void setMidScale(float midScale) {
        this.setMediumScale(midScale);
    }

    public void setMediumScale(float mediumScale) {
        this.mAttacher.setMediumScale(mediumScale);
    }

    /** @deprecated */
    @Deprecated
    public void setMaxScale(float maxScale) {
        this.setMaximumScale(maxScale);
    }

    public void setMaximumScale(float maximumScale) {
        this.mAttacher.setMaximumScale(maximumScale);
    }

    @Override
    public void setScaleLevels(float v, float v1, float v2) {

    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if(null != this.mAttacher) {
            this.mAttacher.update();
        }

    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if(null != this.mAttacher) {
            this.mAttacher.update();
        }

    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if(null != this.mAttacher) {
            this.mAttacher.update();
        }

    }

    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {
        this.mAttacher.setOnMatrixChangeListener(listener);
    }

    public void setOnLongClickListener(OnLongClickListener l) {
        this.mAttacher.setOnLongClickListener(l);
    }

    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {
        this.mAttacher.setOnPhotoTapListener(listener);
    }

    public PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener() {
        return this.mAttacher.getOnPhotoTapListener();
    }

    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {
        this.mAttacher.setOnViewTapListener(listener);
    }

    @Override
    public void setRotationTo(float v) {

    }

    @Override
    public void setRotationBy(float v) {

    }

    public PhotoViewAttacher.OnViewTapListener getOnViewTapListener() {
        return this.mAttacher.getOnViewTapListener();
    }

    public void setScale(float scale) {
        this.mAttacher.setScale(scale);
    }

    public void setScale(float scale, boolean animate) {
        this.mAttacher.setScale(scale, animate);
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        this.mAttacher.setScale(scale, focalX, focalY, animate);
    }

    public void setScaleType(ScaleType scaleType) {
        if(null != this.mAttacher) {
            this.mAttacher.setScaleType(scaleType);
        } else {
            this.mPendingScaleType = scaleType;
        }

    }

    public void setZoomable(boolean zoomable) {
        this.mAttacher.setZoomable(zoomable);
    }

    public Bitmap getVisibleRectangleBitmap() {
        return this.mAttacher.getVisibleRectangleBitmap();
    }

    public void setZoomTransitionDuration(int milliseconds) {
        this.mAttacher.setZoomTransitionDuration(milliseconds);
    }

    @Override
    public IPhotoView getIPhotoViewImplementation() {
        return null;
    }

    @Override
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {

    }

    @Override
    public void setOnScaleChangeListener(PhotoViewAttacher.OnScaleChangeListener onScaleChangeListener) {

    }

    @Override
    public void setOnSingleFlingListener(PhotoViewAttacher.OnSingleFlingListener onSingleFlingListener) {

    }

    protected void onDetachedFromWindow() {
        this.mAttacher.cleanup();
        super.onDetachedFromWindow();
    }
}
