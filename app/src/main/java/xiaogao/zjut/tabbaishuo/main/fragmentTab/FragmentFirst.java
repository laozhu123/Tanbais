package xiaogao.zjut.tabbaishuo.main.fragmentTab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beyondsw.lib.widget.StackCardsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.CardAdapter;
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

public class FragmentFirst extends MyBindPresentFragment<PresenterFragmentFirst> implements IUIFragmentFirst, Handler.Callback, StackCardsView.OnCardSwipedListener
        , View.OnClickListener, CardAdapter.OnItemClickListener {

    @Inject
    PresenterFragmentFirst mPresenter;

    @Bind(R.id.hour)
    TextView hourV;
    @Bind(R.id.minute)
    TextView minuteV;
    @Bind(R.id.seconds)
    TextView secondsV;
    @Bind(R.id.cards)
    StackCardsView cards;
    @Bind(R.id.title)
    TextView title;

    CardAdapter mAdapter;
    private HandlerThread mWorkThread;
    private Handler mWorkHandler;
    private Handler mMainHandler;
    private static final int MSG_START_LOAD_DATA = 1;
    private static final int MSG_DATA_LOAD_DONE = 2;
    private volatile int mStartIndex;
    private static final int PAGE_COUNT = 10;

    private Handler.Callback mCallback;
    int leaveTime = 500;
    private Timer timer;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_START_LOAD_DATA: {
                List<BaseCardItem> data = loadData(mStartIndex);
                mMainHandler.obtainMessage(MSG_DATA_LOAD_DONE, data).sendToTarget();
                break;
            }
            case MSG_DATA_LOAD_DONE: {
                List<BaseCardItem> data = (List<BaseCardItem>) msg.obj;
                mAdapter.appendItems(data);
                mStartIndex += data.size();
                break;
            }

        }
        return true;
    }

    @Override
    public void onItemClick(int index) {
        Intent intent = new Intent(_mActivity, ActivityGrzl.class);
        intent.putExtra(ISMINE, false);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        timer.cancel();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick(R.id.howGetGoodPush)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.howGetGoodPush:
                startActivity(new Intent(_mActivity, ActivityBetterPush.class));
                break;
        }
    }

    public interface Callback {
        void onViewPagerCbChanged(boolean checked);
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
        title.setText(R.string.appName);
        cards.addOnCardSwipedListener(this);
        mAdapter = new CardAdapter(_mActivity);
        mAdapter.setOnItemClickListener(this);
        cards.setAdapter(mAdapter);
        mMainHandler = new Handler(this);
        mWorkThread = new HandlerThread("data_loader");
        mWorkThread.start();
        mWorkHandler = new Handler(mWorkThread.getLooper(), this);
        mWorkHandler.obtainMessage(MSG_START_LOAD_DATA).sendToTarget();
        List<BaseCardItem> data = loadData(0);
        mAdapter.appendItems(data);

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
                    if (hourV == null)
                        return;
                    hourV.setText("0" + hour);
                } else {
                    if (hourV == null)
                        return;
                    hourV.setText("" + hour);
                }

                if (minute < 10) {
                    if (minuteV == null)
                        return;
                    minuteV.setText("0" + minute);
                } else {
                    if (minuteV == null)
                        return;
                    minuteV.setText("" + minute);
                }

                if (second < 10) {
                    if (secondsV == null)
                        return;
                    secondsV.setText("0" + second);
                } else {
                    if (secondsV == null)
                        return;
                    secondsV.setText("" + second);
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
        cards.removeOnCardSwipedListener(this);
        mWorkThread.quit();
        mWorkHandler.removeMessages(MSG_START_LOAD_DATA);
        mMainHandler.removeMessages(MSG_DATA_LOAD_DONE);
        mStartIndex = 0;
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onCardDismiss(int direction) {
        mAdapter.remove(0);
        if (mAdapter.getCount() < 5) {
            if (!mWorkHandler.hasMessages(MSG_START_LOAD_DATA)) {
                mWorkHandler.obtainMessage(MSG_START_LOAD_DATA).sendToTarget();
            }
        }
    }

    @Override
    public void onCardScrolled(View view, float progress, int direction) {

    }

    @Override
    public void onClick(View view) {
        //TODO delete me after test network
        mPresenter.testInterface();
    }
}
