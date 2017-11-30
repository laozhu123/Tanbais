package xgn.com.basesdk.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import xgn.com.basesdk.R;
import xgn.com.basesdk.utils.DeviceUtil;


/**
 * 加载中的dialog
 * Created by yefeng on 2016/12/13
 * Modified by xxx
 */
public class ViewLoadingDialog extends Dialog {

    private final LoadingDialogMum mMumView;

    public ViewLoadingDialog(Context context, boolean cancelable, String msg) {
        super(context, R.style.loading_dialog);
        mMumView = new LoadingDialogMum(context);
        int with, height;
        if (!TextUtils.isEmpty(msg)) {
            mMumView.setNoteViewShow(msg);
            int textNoteLength = mMumView.getTextNoteLangth();
            with = DeviceUtil.dip2px(context, 59);
            with = with + textNoteLength;
        } else {
            with = DeviceUtil.dip2px(context, 50);
        }
        height = DeviceUtil.dip2px(context, 50);
        setCanceledOnTouchOutside(false);
        setCancelable(cancelable);
        setLoadingView(with, height);
    }

    private void setLoadingView(int with, int height) {
        setContentView(mMumView, new LinearLayout.LayoutParams(with, height));// 设置布局
    }

//    @Override
//    public void show() {
//        super.show();
//        animateContent(true);
//    }

//    @Override
//    public void dismiss() {
      //  animateContent(false);
//    }

    private void animateContent(boolean isShow) {
        ValueAnimator scaleAnimator;
        ValueAnimator alphaAnimator;
        if (isShow) {
            scaleAnimator = ValueAnimator.ofFloat(0, 1);
            alphaAnimator = ValueAnimator.ofFloat(0, 1);
        } else {
            scaleAnimator = ValueAnimator.ofFloat(1, 0);
            alphaAnimator = ValueAnimator.ofFloat(1, 0);
        }
        scaleAnimator.setDuration(150);
        alphaAnimator.setDuration(150);
        scaleAnimator.setEvaluator(new FloatEvaluator());
        alphaAnimator.setEvaluator(new FloatEvaluator());
        scaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMumView.setScaleX((float) animation.getAnimatedValue());
                mMumView.setScaleY((float) animation.getAnimatedValue());
            }
        });
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMumView.setAlpha((float) animation.getAnimatedValue());
            }
        });
        if (!isShow) {
            alphaAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    try {
                        ViewLoadingDialog.super.dismiss();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleAnimator, alphaAnimator);
        set.start();
    }

}
