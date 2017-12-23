package xiaogao.zjut.tabbaishuo.main.activity.common;

import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivitySetOccuption;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivitySetOccuption;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ActivitySetOccuption extends MyBaseBindPresentActivity<PresenterActivitySetOccuption> implements IUIActivitySetOccuption {

    @Inject
    PresenterActivitySetOccuption mPresenter;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.titleRightTv)
    TextView titleRightTv;

    @Override
    public PresenterActivitySetOccuption getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_set_occuption;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        hideTitleBar();
        title.setText(R.string.input_oc);
        titleRightTv.setText(R.string.sure);
        titleRightTv.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.back, R.id.titleRightTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.titleRightTv:

                break;
        }
    }
}
