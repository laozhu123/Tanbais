package xgn.com.basesdk.base.activity;


import xgn.com.basesdk.base.mvp.BasePresenter;

/**
 * Created by huangwenqiang on 2017/5/11.
 */
public abstract class BaseBindPresenterActivity<P extends BasePresenter> extends ActivityBase {
    public P mPresenter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        this.mPresenter = getPresenter();
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    public abstract P getPresenter();
}
