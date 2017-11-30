package xgn.com.basesdk.base.fragment;


import xgn.com.basesdk.base.mvp.BasePresenter;

/**
 * Created by huangwenqiang on 2017/5/11.
 *
 */
public abstract class BaseBindPresenterFragment<P extends BasePresenter> extends FragmentBase{

    public P mPresenter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        this.mPresenter = getPresenter();
        if (null != this.mPresenter) {
            this.mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != this.mPresenter) {
            this.mPresenter.detachView();  //presenter和view解绑  防止内存泄露
        }
    }

    public abstract P getPresenter();


}
