package xiaogao.zjut.tabbaishuo.base.fragment;

import xgn.com.basesdk.base.fragment.BaseBindPresenterFragment;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.base.ActivityComponentable;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.injecter.component.DaggerFragmentComponent;
import xiaogao.zjut.tabbaishuo.injecter.component.FragmentComponent;
import xiaogao.zjut.tabbaishuo.injecter.module.FragmentModule;

/**
 * Created by huluzi on 2017/8/14.
 */

public abstract class MyBindPresentFragment<P extends BasePresenter> extends BaseBindPresenterFragment<P> implements FragmentComponentable {

    protected FragmentComponent mFragmentComponent;

    @Override
    protected void setComponent() {
        if (getActivity() instanceof ActivityComponentable) {
            inject(((ActivityComponentable) getActivity()).getActivityComponent());
        }
    }


    private void inject(ActivityComponent pActivityComponent) {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .activityComponent(pActivityComponent)
                .fragmentModule(new FragmentModule(this))
                .build();

        inject(mFragmentComponent);
    }


    protected abstract void inject(FragmentComponent pFragmentComponent);

    @Override
    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    @Override
    public P getPresenter() {
        return null;
    }
}
