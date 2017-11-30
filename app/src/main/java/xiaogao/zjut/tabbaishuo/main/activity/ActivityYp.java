package xiaogao.zjut.tabbaishuo.main.activity;

import android.view.View;

import xgn.com.basesdk.base.activity.ActivityBase;
import xiaogao.zjut.tabbaishuo.R;

public class ActivityYp extends ActivityBase {

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_yp;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        setTitle(R.string.stamp);
    }

}
