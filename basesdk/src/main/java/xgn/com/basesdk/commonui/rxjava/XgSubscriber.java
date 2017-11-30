package xgn.com.basesdk.commonui.rxjava;

import android.support.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xgn.com.basesdk.base.mvp.MvpView;
import xgn.com.basesdk.network.ExceptionHandle;

/**
 * Created by huluzi on 2017/8/10.
 */

public abstract class XgSubscriber<T> implements Observer<T> {
    private BasePresenter mBasePresenter;
    private boolean mShowLoadingDialog = true;

    public XgSubscriber(BasePresenter pBasePresenter) {
        this.mBasePresenter = pBasePresenter;
    }

    public XgSubscriber(BasePresenter pBasePresenter, boolean pShowLoadingDialog) {
        this.mBasePresenter = pBasePresenter;
        this.mShowLoadingDialog = pShowLoadingDialog;
    }

    public void onSubscribe(@NonNull Disposable d) {
        if(this.isViewAttached() && this.mShowLoadingDialog) {
            if(this.getDialogMessage() != 0) {
                this.mBasePresenter.getMvpView().showWaiting(this.getDialogMessage());
            } else {
                this.mBasePresenter.getMvpView().showWaiting();
            }

        }
    }

    public int getDialogMessage() {
        return 0;
    }

    public void onError(Throwable t) {
        if(this.isViewAttached()) {
            MvpView mvpView = this.mBasePresenter.getMvpView();
            mvpView.stopWaiting();
            ExceptionHandle.ResponseThrowable restError = ExceptionHandle.handleException(t);
            if(20500 != restError.code && 20600 != restError.code) {
                if(this.onFailed(restError)) {
                    mvpView.onExceptionHandle(restError);
                }

            } else {
                mvpView.onExceptionHandle(restError);
            }
        }
    }

    public void onComplete() {
        if(this.isViewAttached() && this.mShowLoadingDialog) {
            this.mBasePresenter.getMvpView().stopWaiting();
        }
    }

    private boolean isViewAttached() {
        return this.mBasePresenter != null && this.mBasePresenter.isViewAttached();
    }

    public abstract boolean onFailed(ExceptionHandle.ResponseThrowable var1);
}