package xiaogao.zjut.tabbaishuo.base.activity;

import xgn.com.basesdk.base.activity.BaseBindPresenterActivity;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.app.MyApplication;
import xiaogao.zjut.tabbaishuo.base.ActivityComponentable;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.injecter.component.DaggerActivityComponent;
import xiaogao.zjut.tabbaishuo.injecter.module.ActivityModule;

/**
 * Created by huluzi on 2017/8/10.
 */

public abstract class MyBaseBindPresentActivity<P extends BasePresenter> extends BaseBindPresenterActivity<P> implements ActivityComponentable {
    protected ActivityComponent mActivityComponent;

    protected abstract void inject(ActivityComponent pActivityComponent);

    @Override
    protected void setComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent(MyApplication.getMyAppInstance().getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        inject(mActivityComponent);
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }


}
