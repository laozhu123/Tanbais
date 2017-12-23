package xiaogao.zjut.tabbaishuo.main.activity.setting;

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

/**
 * Created by Administrator on 2017/12/20.
 */

public class ActivityUserBook extends MyBaseBindPresentActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.teBieTiShi)
    TextView teBieTiShi;
    @Bind(R.id.zongZe)
    TextView zongZe;
    @Bind(R.id.fuWuFeiYong)
    TextView fuWuFeiYong;
    @Bind(R.id.fuWuNeiRong)
    TextView fuWuNeiRong;
    @Bind(R.id.serviceStartEnd)
    TextView serviceStartEnd;
    @Bind(R.id.userRightDuty)
    TextView userRightDuty;
    @Bind(R.id.companyRightDuty)
    TextView companyRightDuty;
    @Bind(R.id.others)
    TextView others;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_user_book;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
        loadContract();
    }

    private void loadContract() {

    }

    private void initView() {
        ButterKnife.bind(this);
        hideTitleBar();
        title.setText(R.string.userBook);

    }


    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
