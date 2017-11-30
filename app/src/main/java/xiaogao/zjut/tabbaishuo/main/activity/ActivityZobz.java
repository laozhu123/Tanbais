package xiaogao.zjut.tabbaishuo.main.activity;

import android.view.View;

import xgn.com.basesdk.base.activity.ActivityBase;
import xiaogao.zjut.tabbaishuo.R;

public class ActivityZobz extends ActivityBase {

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_zobz;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        setTitle(R.string.select_standard);
    }

}
