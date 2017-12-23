package xiaogao.zjut.tabbaishuo.main.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.contants.SharePrefrenceString;
import xiaogao.zjut.tabbaishuo.events.EventLoginSuccess;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityRegisterSecond;
import xiaogao.zjut.tabbaishuo.main.MainActivity;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityRegisterSecond;
import xiaogao.zjut.tabbaishuo.utils.SPHelper;
import xiaogao.zjut.tabbaishuo.views.CircleImageView;

/**
 * Created by Administrator on 2017/12/23.
 */

public class ActivityRegisterSecond extends MyBaseBindPresentActivity<PresenterActivityRegisterSecond> implements IUIActivityRegisterSecond {

    @Inject
    PresenterActivityRegisterSecond mPresenter;
    @Bind(R.id.headIcon)
    CircleImageView headIcon;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.male)
    TextView male;
    @Bind(R.id.female)
    TextView female;
    @Bind(R.id.age)
    TextView age;
    @Bind(R.id.next)
    TextView next;

    @Override
    public PresenterActivityRegisterSecond getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_register_second;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        hideTitleBar();
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.male, R.id.female, R.id.age, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.male:
                male.setSelected(true);
                male.setTextColor(getResources().getColor(R.color.white));

                female.setSelected(false);
                female.setTextColor(getResources().getColor(R.color.color_a3a3a3));
                break;
            case R.id.female:
                female.setSelected(true);
                female.setTextColor(getResources().getColor(R.color.white));
                male.setSelected(false);
                male.setTextColor(getResources().getColor(R.color.color_a3a3a3));
                break;
            case R.id.age:

                break;
            case R.id.next:
                EventBus.getDefault().post(new EventLoginSuccess());
                SPHelper helper = new SPHelper(ActivityRegisterSecond.this, SharePrefrenceString.USER_LOGIN);
                helper.putValues(new SPHelper.ContentValue(SharePrefrenceString.IS_LOGIN, true));
                startActivity(new Intent(ActivityRegisterSecond.this, MainActivity.class));
                finish();
                break;
        }
    }
}
