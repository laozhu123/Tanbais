package xgn.com.basesdk.base.mvp;


import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import xgn.com.basesdk.network.ExceptionHandle;


/**
 * Created by huangwenqiang on 2017/5/11.
 */
public interface MvpView {


    //统一处理异常
    void onExceptionHandle(ExceptionHandle.ResponseThrowable restError);


    /**
     * Toast
     * @param resId
     */
    void showToast(int resId);

    void showToast(int resId, int duration);

    void showToast(CharSequence message);

    void showToast(CharSequence message, int duration);

    void showMsg(int title, int des);

    /**
     * 等待框es
     */
    void showWaiting();

    void showWaiting(boolean instantShow);

    void showWaiting(int strId);

    void showWaiting(int strId, boolean isCancelable);

    void showWaiting(String message);

    void showWaiting(String message, boolean isCancelable);

    void stopWaiting();

    /**
     * 错误处理页面
     */

    void showErrorView();

    void showErrorView(ExceptionHandle.ResponseThrowable throwable);

    void showErrorView(@Nullable String pErrorMes, @DrawableRes int pErrorIconRes);

    void showErrorView(View pview);

    void showPageInprossView();

    void showEmptyView(@DrawableRes int pEmptyIconRes, @Nullable String pEpmtyMes);

    void showEmptyView(@DrawableRes int pEmptyIconRes,@StringRes int pEpmtyMes);

    void showEmptyView();
}
