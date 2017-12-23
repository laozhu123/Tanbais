package xiaogao.zjut.tabbaishuo.main.activity.login;

import android.content.Intent;
import android.os.Bundle;
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
import xiaogao.zjut.tabbaishuo.events.EventLoginSuccess;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityRegisterFirst;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityRegisterFirst;

/**
 * Created by Administrator on 2017/12/23.
 */

public class ActivityRegisterFirst extends MyBaseBindPresentActivity<PresenterActivityRegisterFirst> implements IUIActivityRegisterFirst {

    @Inject
    PresenterActivityRegisterFirst mPresenter;
    @Bind(R.id.zc)
    TextView zc;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.yzm)
    EditText yzm;
    @Bind(R.id.password)
    EditText password;

    @Override
    public PresenterActivityRegisterFirst getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_register_first;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        hideTitleBar();
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void closePage(EventLoginSuccess eventLoginSuccess){
        finish();
    }

    @OnClick({R.id.back,R.id.next,R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.next:
                startActivity(new Intent(ActivityRegisterFirst.this,ActivityRegisterSecond.class));
                finish();
                break;
            case R.id.login:
                startActivity(new Intent(ActivityRegisterFirst.this,ActivityLogin.class));
                break;
        }
    }
}
