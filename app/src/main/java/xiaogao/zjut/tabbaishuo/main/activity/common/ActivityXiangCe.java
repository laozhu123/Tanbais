package xiaogao.zjut.tabbaishuo.main.activity.common;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.PictureListAdapter;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityXiangCe;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;
import xiaogao.zjut.tabbaishuo.utils.SizeChange;

/**
 * Created by Administrator on 2017/12/10.
 */

public class ActivityXiangCe extends MyBaseBindPresentActivity<PresenterActivityXiangCe> {

    @Inject
    PresenterActivityXiangCe mPresenter;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.caoZuo)
    ImageView caoZuo;
    @Bind(R.id.picRv)
    RecyclerView picRv;

    List<Picture> pics = new ArrayList<>();
    PictureListAdapter picAdapter;

    @Override
    public PresenterActivityXiangCe getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_xiangce;
    }

    @Override
    protected void initActivity(View var1) {
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

        for (int i = 0; i < 30; i++) {
            pics.add(new Picture());
        }
        picAdapter.notifyDataSetChanged();
    }

    private void initView() {
        hideTitleBar();
        caoZuo.setVisibility(View.GONE);
        title.setText(R.string.xiang_ce);
        picAdapter = new PictureListAdapter(this, pics);
        picAdapter.setResLayoutId(R.layout.xiangce_item);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int itemHeight = (screenWidth - SizeChange.Dp2Px(this, 10 + 10 + 12)) / 3;
        picAdapter.setItemHeight(itemHeight);
        picAdapter.setType(1);
        //GridLayout 3列
        GridLayoutManager mgr = new GridLayoutManager(this, 3);
        picRv.setLayoutManager(mgr);
        picRv.setAdapter(picAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
