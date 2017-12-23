package xiaogao.zjut.tabbaishuo.main.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityBaseInfo;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityGrzl;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityZobz;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ActivityBetterPush extends MyBaseBindPresentActivity {
    @Bind(R.id.title)
    TextView title;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {

    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_better_push;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        hideTitleBar();
        title.setText(R.string.how_to_get_better_push);
    }


    @OnClick({R.id.back, R.id.personInfoBetter, R.id.zobz, R.id.xxrz, R.id.vip})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.personInfoBetter:
                intent.setClass(ActivityBetterPush.this, ActivityBaseInfo.class);
                startActivity(intent);
                break;
            case R.id.zobz:
                intent.setClass(ActivityBetterPush.this, ActivityZobz.class);
                startActivity(intent);
                break;
            case R.id.xxrz:
//                startActivity(new Intent(ActivityBetterPush.this, .class));
                break;
            case R.id.vip:
                break;
        }
    }
}
