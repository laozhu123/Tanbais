package xiaogao.zjut.tabbaishuo.main.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityContactUs;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterContactUs;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ActivityContactUs extends MyBaseBindPresentActivity<PresenterContactUs> implements IUIActivityContactUs {

    @Inject
    PresenterContactUs mPresenter;

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.email1)
    TextView email1;
    @Bind(R.id.email2)
    TextView email2;


    @Override
    protected void inject(ActivityComponent pActivityComponent) {

    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
        loadEmail();
    }

    private void loadEmail() {
        email1.setText("help@tanbaishuo.com");
        email2.setText("hi@tanbaishuo.com");
    }

    private void initView() {
        hideTitleBar();
        ButterKnife.bind(this);
        title.setText(R.string.contactUs);

    }


    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public PresenterContactUs getPresenter() {
        return mPresenter;
    }


    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
