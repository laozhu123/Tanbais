package xgn.com.basesdk.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import xgn.com.basesdk.view.ViewLoadingDialog;


/**
 * dialog的 正在加载辅助类
 * Created by yefeng on 2017/03/02.
 */
public final class DialogLoadingHelper {
    private static final int DELAY_TIME = 000;

    //private ViewLoadingDialog mLoadingDialog;
    private ViewLoadingDialog mLoadingDialog;
    private Handler mHandler;
    private Context mContext;

    private ShowRunnable mRunnable;

    private boolean mIsWaitingShowing = false;

    public DialogLoadingHelper(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void showWaiting(String message, boolean isActivityFinish) {
        mRunnable = new ShowRunnable(message, isActivityFinish);
        mHandler.postDelayed(mRunnable, DELAY_TIME);
    }

    public void showWaiting(String message, boolean cancelable, boolean isActivityFinish) {
        mRunnable = new ShowRunnable(message, cancelable, isActivityFinish);
        mHandler.postDelayed(mRunnable, DELAY_TIME);
    }

    public void showWaiting(boolean instantShow, boolean isActivityFinish) {
        mRunnable = new ShowRunnable(isActivityFinish);
        if (instantShow) {
            mHandler.post(mRunnable);
        } else {
            showWaiting(null, isActivityFinish);
        }
    }

    public void showWaiting(Activity activity) {
        boolean isFinishing;
        isFinishing = activity == null || activity.isFinishing();
        showWaiting(null, isFinishing);
    }

    public void showWaiting(String message, Activity activity) {
        boolean isFinishing;
        isFinishing = activity == null || activity.isFinishing();
        showWaiting(message, isFinishing);
    }

    public void showWaiting(String message, boolean cancelable, Activity activity) {
        boolean isFinishing;
        isFinishing = activity == null || activity.isFinishing();
        showWaiting(message, cancelable, isFinishing);
    }

    public void showWaiting(boolean instantShow, Activity activity) {
        boolean isFinishing;
        isFinishing = activity == null || activity.isFinishing();
        showWaiting(instantShow, isFinishing);
    }

    public void stopWaiting() {
        mHandler.removeCallbacks(mRunnable);
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mIsWaitingShowing = false;
            mLoadingDialog = null;
        }
    }

    private class ShowRunnable implements Runnable {

        private boolean isActivityFinish = true;
        private String message;
        private boolean cancelable = true;

        public ShowRunnable(boolean isActivityFinish) {
            this.isActivityFinish = isActivityFinish;
        }

        public ShowRunnable(String message, boolean isActivityFinish) {
            this.message = message;
            this.isActivityFinish = isActivityFinish;
        }

        public ShowRunnable(String message, boolean cancelable, boolean isActivityFinish) {
            this.message = message;
            this.cancelable = cancelable;
            this.isActivityFinish = isActivityFinish;
        }

        @Override
        public void run() {
            if (!isActivityFinish && !mIsWaitingShowing) {
                mLoadingDialog = new ViewLoadingDialog(mContext,cancelable,message);
                mIsWaitingShowing = true;
                mLoadingDialog.show();
            }
        }
    }
}