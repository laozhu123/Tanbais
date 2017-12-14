package com.neteaseyx.image.ugallery.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * 整屏(一大块区域)的加载中 View [旋转的棍子, 居中]
 *
 * @author hzwangyufei
 */
public class LoadingStateView extends View {

    private static final String TEXT = "请稍候...";

    private float mStartX, mStartY, mEndX, mEndY;   // 棍子绘制的起点终点坐标
    private float mCenterX, mCenterY;               // 棍子旋转的中心点

    private Paint mPaint;                           // 棍子绘制画笔

    private float mRotateDegree = 0;                // 棍子当前旋转角度

    private Handler mRotateHandler;
    private Runnable mRotateRunnable;
    private Paint mTextPaint;
    private float mTextLength;
    private int mTextLeftPadding;

    private RectF mLayerRect;

    private int mMaxContainerLength;

    private int mTopPadding;

    private int mCanvasWidth, mCanvasHeight;

    public LoadingStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadingStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingStateView(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int alpha = (int) (mRotateDegree / 80 * 255);
        if (alpha > 255) {
            alpha = 255;
        }
//        canvas.drawColor(Color.WHITE);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(TEXT, (mCanvasWidth - mTextLength) / 2 - mTextLeftPadding, mCanvasHeight / 2 + mTopPadding, mTextPaint);
        canvas.saveLayerAlpha(mLayerRect, 255, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.argb(alpha, 13, 13, 13));
        canvas.rotate(mRotateDegree, mCenterX, mCenterY);
        canvas.drawLine(mStartX, mStartY, mEndX, mEndY, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() != 0 && getMeasuredHeight() != 0) {
            mCanvasWidth = getMeasuredWidth();
            mCanvasHeight = (int) (getMeasuredHeight() * 0.85);
            int minorEdge = Math.min(mCanvasHeight, mCanvasWidth);
            float stickLength = minorEdge * 0.091f;

            if (stickLength > mMaxContainerLength) {
                stickLength = mMaxContainerLength;
            } else if (stickLength < mMaxContainerLength / 2) {
                stickLength = mMaxContainerLength / 2;
            }

            mCenterX = mCanvasWidth / 2f;
            mCenterY = mCanvasHeight / 2f;

            mStartX = mEndX = mCanvasWidth / 2f;
            mStartY = mCanvasHeight / 2f - stickLength / 2f;
            mEndY = mCanvasHeight / 2f + stickLength / 2f;

            mLayerRect.set(0, 0, mCanvasWidth, mCanvasHeight);
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRotateHandler.removeCallbacks(mRotateRunnable);
        mRotateHandler.postDelayed(mRotateRunnable, 400);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRotateHandler.removeCallbacks(mRotateRunnable);
    }

    private void init() {
        mMaxContainerLength = Utils.dip2px(getContext(), 20);
        mTopPadding = Utils.dip2px(getContext(), 55);

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#0d0d0d"));
        mPaint.setStrokeWidth(Utils.dip2px(getContext(), 3.5f));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#666666"));
        mTextPaint.setTextSize(Utils.dip2px(getContext(), 13.5f));

        mLayerRect = new RectF();
        mTextLength = mPaint.measureText(TEXT, 0, TEXT.length());
        mTextLeftPadding = Utils.dip2px(getContext(), 10);

        mRotateHandler = new Handler();
        mRotateRunnable = new Runnable() {
            @Override
            public void run() {
                mRotateDegree += 5;
                invalidate();
                mRotateHandler.postDelayed(this, 20);
            }
        };
    }
}
