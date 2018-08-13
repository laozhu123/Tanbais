package xiaogao.zjut.tabbaishuo.main.fragmentTab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.fragment.MyBindPresentFragment;
import xiaogao.zjut.tabbaishuo.bean.BaseCardItem;
import xiaogao.zjut.tabbaishuo.injecter.component.FragmentComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIFragmentFirst;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityBetterPush;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityGrzl;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterFragmentFirst;

import static xiaogao.zjut.tabbaishuo.main.activity.my.ActivityGrzl.ISMINE;

/**
 * Created by Administrator on 2017/11/18.
 */

public class FragmentFirst extends MyBindPresentFragment<PresenterFragmentFirst> implements IUIFragmentFirst
        , View.OnClickListener {

    @Inject
    PresenterFragmentFirst mPresenter;

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.age_locate_job)
    TextView ageLocateJob;
    @Bind(R.id.yuanfen)
    TextView yuanfen;
    @Bind(R.id.hour1)
    TextView hour1;
    @Bind(R.id.hour2)
    TextView hour2;
    @Bind(R.id.minute1)
    TextView minute1;
    @Bind(R.id.minute2)
    TextView minute2;
    @Bind(R.id.second1)
    TextView second1;
    @Bind(R.id.second2)
    TextView second2;

    int leaveTime = 500;
    private Timer timer;


    @Override
    public void onDestroy() {
        timer.cancel();
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @Override
    public PresenterFragmentFirst getPresenter() {
        return mPresenter;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void inject(FragmentComponent pFragmentComponent) {
        pFragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initFragment(View view) {
        ButterKnife.bind(this, view);

        initData();
    }

    private void initData() {
        title.setText(R.string.appName);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (leaveTime <= 0) {
                    timer.cancel();
                } else
                    tick();
            }
        }, 0, 1000);

        title.setOnClickListener(this);
        name.setText("千千厥歌");
        ageLocateJob.setText("18岁·杭州·演员");
        yuanfen.setText("缘分指数 98%");
    }

    private void tick() {

        _mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaveTime--;
                int hour = leaveTime / 3600;
                int minute = leaveTime % 3600 / 60;
                int second = leaveTime % 60;

                if (hour < 10) {
                    if (hour1 == null)
                        return;
                    hour1.setText("0");
                    hour2.setText(hour + "");
                } else {
                    if (hour1 == null)
                        return;
                    hour1.setText(hour / 10 + "");
                    hour2.setText(hour % 10 + "");
                }

                if (minute < 10) {
                    if (minute1 == null)
                        return;
                    minute1.setText("0");
                    minute2.setText(minute + "");
                } else {
                    if (minute1 == null)
                        return;
                    minute1.setText(minute / 10 + "");
                    minute2.setText(minute % 10 + "");
                }

                if (second < 10) {
                    if (second1 == null)
                        return;
                    second1.setText("0");
                    second2.setText(second + "");
                } else {
                    if (second1 == null)
                        return;
                    second1.setText(second / 10 + "");
                    second2.setText(second % 10 + "");
                }
            }
        });

    }

    private List<BaseCardItem> loadData(int index) {
        List<BaseCardItem> ls = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ls.add(new BaseCardItem());
        }
        return ls;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }


    @Override
    public void onClick(View view) {
        //TODO delete me after test network
        mPresenter.testInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.img, R.id.howGetGoodPush})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img:
                Intent intent = new Intent(_mActivity, ActivityGrzl.class);
                intent.putExtra(ISMINE, false);
                startActivity(intent);
                break;
            case R.id.howGetGoodPush:
                startActivity(new Intent(_mActivity, ActivityBetterPush.class));
                break;
        }
    }
}
