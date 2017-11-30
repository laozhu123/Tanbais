package xiaogao.zjut.tabbaishuo.main.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import javax.inject.Inject;

import xgn.com.basesdk.base.activity.ActivityBase;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUISplash;
import xiaogao.zjut.tabbaishuo.main.MainActivity;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterSplash;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ActivitySplash extends MyBaseBindPresentActivity<PresenterSplash> implements IUISplash {

    private final static int SPLASH_DURATION = 0;
    @Inject
    PresenterSplash mPresenter;

    @Override
    public PresenterSplash getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initActivity(View var1) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mPresenter.connect("ixFNO8GJAI/R/JdIvmSTX2Qy4S4qYM+VXAGQ8VMtXWE3iqrYVfU+gUD5VsHJau8BRugnFYF6a4hTabASaUe4s9iIoJSWR1cb");
            }
        });
        thread.start();
    }

    private boolean isLogined() {
        return true;
    }

    @Override
    public void success() {
        jump();
    }

    @Override
    public void fail() {
        jump();
    }

    private void jump() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogined()) {
                    startActivity(new Intent(ActivitySplash.this, MainActivity.class));
                } else {
//                    startActivity(new Intent(ActivitySplash.this, ActivityLogin.class));
                }
                finish();
            }
        }, SPLASH_DURATION);
    }
}
