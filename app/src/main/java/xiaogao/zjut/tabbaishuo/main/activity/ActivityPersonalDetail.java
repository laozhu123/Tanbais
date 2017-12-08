package xiaogao.zjut.tabbaishuo.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityPersonalDetail;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityPersonalDetail;

/**
 * Created by Administrator on 2017/12/2.
 */

public class ActivityPersonalDetail extends MyBaseBindPresentActivity<PresenterActivityPersonalDetail> implements IUIActivityPersonalDetail {
    @Inject
    PresenterActivityPersonalDetail mPresenter;
    @Bind(R.id.nickName)
    TextView nickName;
    @Bind(R.id.vip)
    ImageView vip;
    @Bind(R.id.ageLocateWork)
    TextView ageLocateWork;
    @Bind(R.id.seePicture)
    ImageView seePicture;
    @Bind(R.id.shenfenz)
    TextView shenfenz;
    @Bind(R.id.xueli)
    TextView xueli;
    @Bind(R.id.seeBaseInfo)
    ImageView seeBaseInfo;
    @Bind(R.id.seeZobz)
    ImageView seeZobz;
    @Bind(R.id.sayHello)
    TextView sayHello;
    @Bind(R.id.dialog1)
    ViewStub dialog1;

    @Override
    public PresenterActivityPersonalDetail getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {

    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_grzl;
    }

    @Override
    protected void initActivity(View var1) {
        ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.seePicture, R.id.seeBaseInfo, R.id.seeZobz})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.seePicture:
                break;
            case R.id.seeBaseInfo:
                intent = new Intent(ActivityPersonalDetail.this, ActivityBaseInfo.class);
                startActivity(intent);
                break;
            case R.id.seeZobz:
                intent = new Intent(ActivityPersonalDetail.this, ActivityZobz.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
