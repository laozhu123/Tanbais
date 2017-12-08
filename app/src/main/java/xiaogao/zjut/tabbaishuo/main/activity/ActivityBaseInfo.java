package xiaogao.zjut.tabbaishuo.main.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.InfoCommonAdapter;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityBaseInfo;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityZobz;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityBaseInfo;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityZobz;

/**
 * Created by Administrator on 2017/12/8.
 */

public class ActivityBaseInfo extends MyBaseBindPresentActivity<PresenterActivityBaseInfo> implements IUIActivityBaseInfo, View.OnClickListener {
    @Inject
    PresenterActivityBaseInfo mPresenter;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.caoZuo)
    ImageView caoZuo;
    @Bind(R.id.head)
    RelativeLayout head;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    InfoCommonAdapter mAdapter;
    List<String> lefts;
    List<String> rights;

    @Override
    public PresenterActivityBaseInfo getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_base_info;
    }

    @Override
    protected void initActivity(View var1) {
        ButterKnife.bind(this);
        hideTitleBar();
        lefts = Arrays.asList(getResources().getStringArray(R.array.base_info));
        rights = new ArrayList<>();
        for (int i = 0; i < lefts.size(); i++) {
            rights.add("");
        }
        initView();

    }

    private void initView() {
        back.setOnClickListener(this);
        caoZuo.setVisibility(View.GONE);
        title.setText(R.string.base_info);
        mAdapter = new InfoCommonAdapter(this, lefts, rights);
        mAdapter.setmOnItemClickListener(new InfoCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                showToast(index + "");
            }
        });
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

