package xiaogao.zjut.tabbaishuo.main.activity.my;

import android.view.View;

import javax.inject.Inject;

import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityChangeHeadNickName;

/**
 * Created by Administrator on 2017/12/10.
 */

public class ActivityChangeHeadNickName extends MyBaseBindPresentActivity<PresenterActivityChangeHeadNickName>{

    @Inject
    PresenterActivityChangeHeadNickName mPresenter;

    @Override
    public PresenterActivityChangeHeadNickName getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {

    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_change_head_nickname;
    }

    @Override
    protected void initActivity(View var1) {

    }
}
