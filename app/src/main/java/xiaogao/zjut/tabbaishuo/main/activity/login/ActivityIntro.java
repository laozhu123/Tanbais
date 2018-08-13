package xiaogao.zjut.tabbaishuo.main.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.UltraPagerAdapter;
import xiaogao.zjut.tabbaishuo.app.Constants;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.events.EventLoginSuccess;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;

/**
 * Created by Administrator on 2017/12/28.
 */

public class ActivityIntro extends MyBaseBindPresentActivity {


    @Bind(R.id.ultra_viewpager)
    UltraViewPager ultraViewPager;
    private UltraViewPager.Orientation gravity_indicator;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {

    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_intro;
    }

    @Override
    protected void initActivity(View var1) {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        hideTitleBar();
        initView();
    }

    private void initView() {
        //设置图片集合
        Integer[] images = {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5};

        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//initialize UltraPagerAdapter，and add child view to UltraViewPager
        PagerAdapter adapter = new UltraPagerAdapter(Arrays.asList(images), this);
        ultraViewPager.setAdapter(adapter);
        ultraViewPager.setMultiScreen(0.6f);
        ultraViewPager.setItemRatio(1.0f);
        //                ultraViewPager.setRatio(2.0f);
        //                ultraViewPager.setMaxHeight(800);
        ultraViewPager.setAutoMeasureHeight(true);
//initialize built-in indicator
        ultraViewPager.initIndicator();
//set style of indicators
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getResources().getColor(R.color.color_ff4b3d))
                .setNormalColor(getResources().getColor(R.color.color_bbbbbb)).setIndicatorPadding(20)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
//set the alignment
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.getIndicator().build();
        ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
//set an infinite loop
        ultraViewPager.setInfiniteLoop(true);
//enable auto-scroll mode
        ultraViewPager.setAutoScroll(2000);


    }

    @Subscribe
    public void closePage(EventLoginSuccess eventLoginSuccess) {
        finish();
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(ActivityIntro.this, ActivityLogin.class));
                break;
            case R.id.register:
                startActivity(new Intent(ActivityIntro.this, ActivityRegisterFirst.class));
                break;
        }
    }

}
