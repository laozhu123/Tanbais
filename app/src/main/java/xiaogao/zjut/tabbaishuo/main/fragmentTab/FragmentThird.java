package xiaogao.zjut.tabbaishuo.main.fragmentTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.FunctionListAdapter;
import xiaogao.zjut.tabbaishuo.adapter.PictureListAdapter;
import xiaogao.zjut.tabbaishuo.base.fragment.MyBindPresentFragment;
import xiaogao.zjut.tabbaishuo.bean.FunctionItemBean;
import xiaogao.zjut.tabbaishuo.injecter.component.FragmentComponent;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityXiangCe;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityChangeHeadNickName;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityGrzl;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityRzzx;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivitySz;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityWdxy;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityYqhy;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityYywt;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityZobz;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;
import xiaogao.zjut.tabbaishuo.views.MyImageView;

import static xiaogao.zjut.tabbaishuo.main.activity.my.ActivityGrzl.ISMINE;

/**
 * Created by Administrator on 2017/11/18.
 */

public class FragmentThird extends MyBindPresentFragment<BasePresenter> implements FunctionListAdapter.OnItemClickListener {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.id)
    TextView id;
    @Bind(R.id.vip)
    ImageView vip;
    @Bind(R.id.photo)
    LinearLayout photo;
    @Bind(R.id.pictures)
    RecyclerView pictureRv;
    @Bind(R.id.list_function)
    RecyclerView listFunction;
    @Bind(R.id.headIcon)
    ImageView headIcon;
    @Bind(R.id.title)
    TextView title;
    private List<Picture> pls;
    private PictureListAdapter pAdapter;


    @Bind(R.id.bigPic)
    ViewStub vsBigPic;
    View vBigPic;
    ImageView vsImg;

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void inject(FragmentComponent pFragmentComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_third;
    }

    @Override
    protected void initFragment(View view) {
        ButterKnife.bind(this, view);
        title.setText(R.string.me);
        initFunctionList();
        initPictures();
        loadInfo();
    }

    private void loadInfo() {
        for (int i = 0; i < 5; i++) {
            pls.add(new Picture());
        }
        pAdapter.notifyDataSetChanged();
        headIcon.setImageResource(R.mipmap.helo);
    }

    private void initPictures() {
        pls = new ArrayList<>();
        pAdapter = new PictureListAdapter(_mActivity, pls);
        pAdapter.setOnItemClickListener(new PictureListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                //fixme 设置照片
                if (vBigPic == null) {
                    vBigPic = vsBigPic.inflate();
                    vsImg = (ImageView) vBigPic.findViewById(R.id.vsImg);
                    vsImg.setImageDrawable(getResources().getDrawable(R.mipmap.helo));
                    vBigPic.findViewById(R.id.vsClose).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            vBigPic.setVisibility(View.GONE);
                        }
                    });

                } else {
                    ((MyImageView) vsImg).initImgeView();
                    vBigPic.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void delete(int index) {

            }
        });
        LinearLayoutManager ms = new LinearLayoutManager(_mActivity);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        pictureRv.setLayoutManager(ms);
        pictureRv.setAdapter(pAdapter);
    }

    private void initFunctionList() {
        List<FunctionItemBean> lists = new ArrayList<>();

        lists.add(new FunctionItemBean(R.mipmap.icon_gerenziliao, R.string.personal_info));
        lists.add(new FunctionItemBean(R.mipmap.icon_zeou, R.string.select_standard));
        lists.add(new FunctionItemBean(R.mipmap.icon_huiyuan, R.string.yywt));
        lists.add(new FunctionItemBean(R.mipmap.icon_xinxirenz, R.string.authorized_center));
        lists.add(new FunctionItemBean(R.mipmap.icon_huiyuan, R.string.wdxy));
        lists.add(new FunctionItemBean(R.mipmap.icon_youpiao, R.string.yqhy));
        lists.add(new FunctionItemBean(R.mipmap.icon_shezhi, R.string.setting));

        FunctionListAdapter mAdapter = new FunctionListAdapter(_mActivity, lists);
        mAdapter.setmOnItemClickListener(this);
        listFunction.setLayoutManager(new LinearLayoutManager(_mActivity));
        listFunction.setAdapter(mAdapter);
    }

    @Override
    public boolean useSwipeRefreshLayout() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(int index) {
        Intent intent;
        switch (index) {
            case 0:
                intent = new Intent(_mActivity, ActivityGrzl.class);
                break;
            case 1:
                intent = new Intent(_mActivity, ActivityZobz.class);
                break;
            case 2:
                intent = new Intent(_mActivity, ActivityYywt.class);
                break;
            case 3:
                intent = new Intent(_mActivity, ActivityRzzx.class);
                break;
            case 4:
                intent = new Intent(_mActivity, ActivityWdxy.class);
                break;
            case 5:
                intent = new Intent(_mActivity, ActivityYqhy.class);
                break;
            case 6:
                intent = new Intent(_mActivity, ActivitySz.class);
                break;
            default:
                intent = new Intent();
                break;
        }
        intent.putExtra(ISMINE, true);
        startActivity(intent);
    }


    @OnClick({R.id.headIcon, R.id.photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headIcon:
                startActivity(new Intent(_mActivity, ActivityChangeHeadNickName.class));
                break;
            case R.id.photo:
                //fixme 相册
                Intent intent = new Intent(_mActivity, ActivityXiangCe.class);
                startActivity(intent);
                break;
        }
    }
}
