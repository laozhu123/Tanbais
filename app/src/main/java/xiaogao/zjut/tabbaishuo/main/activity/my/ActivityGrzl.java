package xiaogao.zjut.tabbaishuo.main.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.neteaseyx.image.ugallery.UGallery;

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
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityPersonalDetail;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityShowPicture;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityXiangCe;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityPersonalDetail;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;
import xiaogao.zjut.tabbaishuo.utils.SizeChange;
import xiaogao.zjut.tabbaishuo.views.tags.Tag;
import xiaogao.zjut.tabbaishuo.views.tags.TagListView;

/**
 * Created by Administrator on 2017/12/2.
 */

public class ActivityGrzl extends MyBaseBindPresentActivity<PresenterActivityPersonalDetail> implements IUIActivityPersonalDetail, PictureListAdapter.OnItemClickListener {
    @Inject
    PresenterActivityPersonalDetail mPresenter;
    @Bind(R.id.nickName)
    TextView nickName;
    @Bind(R.id.vip)
    ImageView vip;
    @Bind(R.id.ageLocateWork)
    TextView ageLocateWork;
    @Bind(R.id.shenfenz)
    TextView shenfenz;
    @Bind(R.id.xueli)
    TextView xueli;
    @Bind(R.id.sayHello)
    TextView sayHello;
    @Bind(R.id.dialog1)
    ViewStub dialog1;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.caoZuo)
    View caoZuo;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.pics)
    RecyclerView picRecyclerView;
    @Bind(R.id.selfDescribe)
    TextView selfDescribe;
    @Bind(R.id.chezi)
    TextView chezi;
    @Bind(R.id.fangzi)
    TextView fangzi;
    View dialogView;
    PictureListAdapter picAdapter;
    ArrayList<Picture> pictures;
    @Bind(R.id.tagBaseInfo)
    TagListView mTagBaseInfo;
    @Bind(R.id.tagZobz)
    TagListView mTagZobz;
    UGallery.Config config;

    private final List<Tag> mTagsBaseInfo = new ArrayList<>();
    private final List<Tag> mTagsZobz = new ArrayList<>();
    private final String[] baseInfoTitle = {"24岁", "现住浙江杭州", "程序员", "175cm", "10W-20W", "期望2年内结婚", "从未结婚"};
    private final String[] zobzTitle = {"24-26岁", "170-180cm", "10W-20W", "现住浙江杭州", "户籍浙江杭州", "从未结婚"};

    boolean isMine;
    public final static String ISMINE = "is_mine";
    private boolean canAdd = false;
    private final int MaxSize = 6;  //最多6张照片
    public static final String PICS = "PICS";

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bg1:
                case R.id.cancel1:
                    dialogView.setVisibility(View.GONE);
                    break;
                case R.id.fenxiang:
                    //fixme 分享
                    break;
                case R.id.jubao:
                    //fixme 举报
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public PresenterActivityPersonalDetail getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {

    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_grzl;
    }

    @Override
    protected void initActivity(View var1) {
        hideTitleBar();
        ButterKnife.bind(this);
        isMine = getIntent().getBooleanExtra(ISMINE, false);
        initView();
        initData();
    }

    private void initData() {
        nickName.setText("千千厥歌");
        ageLocateWork.setText("24岁·现在浙江杭州·演员");
        selfDescribe.setText("我自认为是一个喜欢自由而又充满活力的人，我想做的事情一般马上就去做，大概除了秘密，心里一般也不会有不愿意跟人家说的话。我的性格，有时候会被人觉得有些冲动，但还好朋友们都觉得我还不错。");
        shenfenz.setText("*飞 3122616516483");
        xueli.setText("浙江大学 本科");
        chezi.setText("未认证");
        chezi.setTextColor(getResources().getColor(R.color.color_bbccd4));
        fangzi.setText("杭州房产");

        for (int i = 0; i < 5; i++) {
            Picture picture = new Picture();
            picture.resid = R.mipmap.helo;
            pictures.add(picture);
        }
        Picture picture = new Picture();
        picture.resid = R.mipmap.set_add_image;
        pictures.add(picture);
        canAdd = true;
        picAdapter.notifyDataSetChanged();

        for (int i = 0; i < 7; i++) {
            Tag tag = new Tag();
            tag.setId(i);
            tag.setTitle(baseInfoTitle[i]);
            mTagsBaseInfo.add(tag);
        }
        mTagBaseInfo.setTagViewTextColorRes(getResources().getColor(R.color.color_bbccd4));
        mTagBaseInfo.setTagViewBackgroundRes(R.drawable.tag_checked_normal);
        mTagBaseInfo.setTags(mTagsBaseInfo);


        for (int i = 0; i < zobzTitle.length; i++) {
            Tag tag = new Tag();
            tag.setId(i);
            tag.setTitle(zobzTitle[i]);
            mTagsZobz.add(tag);
        }
        mTagZobz.setTagViewTextColorRes(getResources().getColor(R.color.color_bbccd4));
        mTagZobz.setTagViewBackgroundRes(R.drawable.tag_checked_normal);
        mTagZobz.setTags(mTagsZobz);

    }

    private void initView() {
        title.setText(R.string.personal_info);
        if (isMine == false) {
            sayHello.setVisibility(View.VISIBLE);
            caoZuo.setVisibility(View.VISIBLE);
        } else {
            sayHello.setVisibility(View.GONE);
            caoZuo.setVisibility(View.GONE);
        }

        img.setFocusable(true);
        img.setFocusableInTouchMode(true);
        img.requestFocus();
        pictures = new ArrayList<>();
        picAdapter = new PictureListAdapter(this, pictures);
        picAdapter.setResLayoutId(R.layout.item_list_picture_big);
        picAdapter.setOnItemClickListener(this);
        GridLayoutManager ms = new GridLayoutManager(this, 3);
        ms.setOrientation(LinearLayoutManager.VERTICAL);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int itemHeight = (screenWidth - SizeChange.Dp2Px(this, 10 + 12 + 14)) / 3;
        picAdapter.setItemHeight(itemHeight);
        picAdapter.setType(2);

        picRecyclerView.setLayoutManager(ms);
        picRecyclerView.setAdapter(picAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.seePicture, R.id.seeBaseInfo, R.id.seeZobz, R.id.back, R.id.caoZuo})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.seePicture:
                intent = new Intent(ActivityGrzl.this, ActivityXiangCe.class);
                startActivity(intent);
                break;
            case R.id.seeBaseInfo:
                intent = new Intent(ActivityGrzl.this, ActivityBaseInfo.class);
                startActivity(intent);
                break;
            case R.id.seeZobz:
                intent = new Intent(ActivityGrzl.this, ActivityZobz.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.caoZuo:
                if (dialogView == null) {
                    dialogView = dialog1.inflate();
                    dialogView.findViewById(R.id.bg1).setOnClickListener(onClickListener);
                    dialogView.findViewById(R.id.cancel1).setOnClickListener(onClickListener);
                    dialogView.findViewById(R.id.fenxiang).setOnClickListener(onClickListener);
                    dialogView.findViewById(R.id.jubao).setOnClickListener(onClickListener);
                } else {
                    dialogView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(int index) {
        //fixme 设置照片
        if (index == pictures.size() - 1 && canAdd) {
            if (config == null) {
                config = UGallery.getConfig();
                config.setImageSelectNumIsShow(false);
                config.setBarRightText(getString(R.string.upload));
                config.setBarTitle(getString(R.string.xiang_ce));
                config.setCameraImage(R.mipmap.camera_icon);
                config.setTitleTextColor(R.color.color_3bc2ff);
                config.setImageBackGround(R.color.color_979797);
                config.setTitleBarRightBtnTextColor(R.color.color_3bc2ff);
                config.setTitleBarBackBtnImage(R.drawable.icon_fanhui);
                config.setGalleryImageSelect(R.mipmap.choose_photo_selected, R.mipmap.choose_photo_unselected);
            }
            UGallery.selectMultipleImageCompress(this, 6);
        } else {
            Intent intent = new Intent(this, ActivityShowPicture.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(PICS, pictures);
            intent.putExtra(PICS, bundle);
            startActivity(intent);
        }
    }

    @Override
    public void delete(int index) {

    }

}
