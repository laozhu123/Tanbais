package xiaogao.zjut.tabbaishuo.main.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xgn.com.basesdk.base.activity.ActivityBase;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.main.activity.setting.ActivityContactUs;
import xiaogao.zjut.tabbaishuo.main.activity.setting.ActivitySuggestionResponse;
import xiaogao.zjut.tabbaishuo.main.activity.setting.ActivityUserBook;
import xiaogao.zjut.tabbaishuo.views.ItemTxArrow;
import xiaogao.zjut.tabbaishuo.views.SwitchButton;

public class ActivitySz extends ActivityBase {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.switchButton)
    SwitchButton switchButton;


    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_sz;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        hideTitleBar();
        title.setText(R.string.setting);
        switchButton.setToggleOn(true);//默认打开。如果参数传false,则打开页面初始化时不会有动画效果(改变状态还是会有动画)
        switchButton.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean isOn) {
                //处理自己的逻辑
                showToast("SwitchButton" + isOn);
            }
        });
    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.modifyPassword, R.id.ideaResponse, R.id.contactUs, R.id.userBook, R.id.clearCash, R.id.logOut})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.modifyPassword:
                //fixme 修改密码
                break;
            case R.id.ideaResponse:
                intent.setClass(ActivitySz.this, ActivitySuggestionResponse.class);
                startActivity(intent);
                break;
            case R.id.contactUs:
                intent.setClass(ActivitySz.this, ActivityContactUs.class);
                startActivity(intent);
                break;
            case R.id.clearCash:
                //fixme 清空缓存
                break;
            case R.id.userBook:
                intent.setClass(ActivitySz.this, ActivityUserBook.class);
                startActivity(intent);
                break;
            case R.id.logOut:
                //fixme 到登录页面
                break;
        }

    }
}
