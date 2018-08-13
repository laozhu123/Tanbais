package xiaogao.zjut.tabbaishuo.main.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.app.Constants;
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
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.yzm)
    EditText yzm;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.check)
    ImageView check;

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
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        hideTitleBar();
        title.setText("注册");
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void closePage(EventLoginSuccess eventLoginSuccess) {
        finish();
    }

    @OnClick({R.id.back, R.id.check, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.register:
                Toast.makeText(this, "register success", Toast.LENGTH_SHORT).show();  //fixme 改为注册逻辑
                startActivity(new Intent(ActivityRegisterFirst.this, ActivityRegisterSecond.class));
                finish();
                break;
            case R.id.check:
                if (check.isSelected())
                    check.setSelected(false);
                else
                    check.setSelected(true);
                break;

        }
    }


}
