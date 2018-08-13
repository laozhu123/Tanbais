package xiaogao.zjut.tabbaishuo.main.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.contants.SharePrefrenceString;
import xiaogao.zjut.tabbaishuo.events.EventLoginSuccess;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityLogin;
import xiaogao.zjut.tabbaishuo.main.MainActivity;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityLogin;
import xiaogao.zjut.tabbaishuo.utils.SPHelper;
import xiaogao.zjut.tabbaishuo.views.CircleImageView;

/**
 * Created by Administrator on 2017/12/23.
 */

public class ActivityLogin extends MyBaseBindPresentActivity<PresenterActivityLogin> implements IUIActivityLogin {

    @Inject
    PresenterActivityLogin mPresenter;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.title)
    TextView title;

    @Override
    public PresenterActivityLogin getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_login;
    }

    @Subscribe
    public void closePage(EventLoginSuccess eventLoginSuccess) {
        finish();
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        hideTitleBar();
        title.setText("登录");
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.forgetPassword, R.id.login,R.id.register,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgetPassword:
                //fixme
                break;
            case R.id.login:
                //fixme real login
                EventBus.getDefault().post(new EventLoginSuccess());
                SPHelper helper = new SPHelper(ActivityLogin.this, SharePrefrenceString.USER_LOGIN);
                helper.putValues(new SPHelper.ContentValue(SharePrefrenceString.IS_LOGIN,true));
                startActivity(new Intent(ActivityLogin.this, MainActivity.class));
                finish();
                break;
            case R.id.register:
                //fixme
                startActivity(new Intent(ActivityLogin.this,ActivityRegisterFirst.class));
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
