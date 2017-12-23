package xiaogao.zjut.tabbaishuo.main;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xgn.com.basesdk.commonui.utils.XgFragmentManager;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.events.EventLogout;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIMain;
import xiaogao.zjut.tabbaishuo.main.fragmentTab.FragmentFirst;
import xiaogao.zjut.tabbaishuo.main.fragmentTab.FragmentSecond;
import xiaogao.zjut.tabbaishuo.main.fragmentTab.FragmentThird;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterMain;
import xiaogao.zjut.tabbaishuo.views.BottomBar;
import xiaogao.zjut.tabbaishuo.views.BottomBarTab;

public class MainActivity extends MyBaseBindPresentActivity<PresenterMain> implements IUIMain {


    @Bind(R.id.bottomBar)
    BottomBar mBottomBar;
    private XgFragmentManager mXgFragmentManager;
    private String mFragmentFirst;
    private String mFragmentSecond;
    private String mFragmentThird;

    @Inject
    PresenterMain mPresenter;

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initActivity(View view) {
        ButterKnife.bind(this, view);
        initFragments();
        initView();
        mXgFragmentManager.switchFragmentWithCache(mFragmentFirst, null);
    }

    @Subscribe
    public void Logout(EventLogout eventLogout) {
        finish();
    }

    private void initFragments() {
        mXgFragmentManager = new XgFragmentManager(this);
        mFragmentFirst = FragmentFirst.class.getName();
        mFragmentSecond = FragmentSecond.class.getName();
        mFragmentThird = FragmentThird.class.getName();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        EventBus.getDefault().register(this);
        mBottomBar.addItem(new BottomBarTab(this,
                R.mipmap.icon_tuisong,
                R.mipmap.icon_tuisong_s,
                R.string.appName))
                .addItem(new BottomBarTab(this,
                        R.mipmap.icon_xiaoxi,
                        R.mipmap.icon_xiaoxi_s,
                        R.string.message))
                .addItem(new BottomBarTab(this,
                        R.mipmap.icon_wode,
                        R.mipmap.icon_wode_s,
                        R.string.me));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                switch (position) {
                    case 0:
                        mXgFragmentManager.switchFragmentWithCache(mFragmentFirst, null);
                        break;
                    case 1:
                        mXgFragmentManager.switchFragmentWithCache(mFragmentSecond, null);
                        break;
                    case 2:
                        mXgFragmentManager.switchFragmentWithCache(mFragmentThird, null);
                        break;
                    default:
                        break;
                }
                // 如果不是位于 Tab 中的根页面时，TabBar 处于隐藏状态，这时候如果点击了通知打开 app 时会跳转到 app 的首页，
                // 这时候就需要将 Tab 显示出来
//                if (position == 0) {
//                    mBottomBar.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    public PresenterMain getPresenter() {
        return mPresenter;
    }
}
