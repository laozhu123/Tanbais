package xiaogao.zjut.tabbaishuo.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityPersonalDetail;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityPersonalDetail;

/**
 * Created by Administrator on 2017/12/2.
 */

public class ActivityPersonalDetail extends MyBaseBindPresentActivity<PresenterActivityPersonalDetail> implements IUIActivityPersonalDetail {
    @Inject
    PresenterActivityPersonalDetail mPresenter;
    @Bind(R.id.nickName)
    TextView nickName;
    @Bind(R.id.vip)
    ImageView vip;
    @Bind(R.id.ageLocateWork)
    TextView ageLocateWork;
    @Bind(R.id.seePicture)
    ImageView seePicture;
    @Bind(R.id.shenfenz)
    TextView shenfenz;
    @Bind(R.id.xueli)
    TextView xueli;
    @Bind(R.id.seeBaseInfo)
    ImageView seeBaseInfo;
    @Bind(R.id.seeZobz)
    ImageView seeZobz;
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
    View dialogView;


    boolean isMine;
    public final static String ISMINE = "is_mine";

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
                //fixme 看相册
                break;
            case R.id.seeBaseInfo:
                intent = new Intent(ActivityPersonalDetail.this, ActivityBaseInfo.class);
                startActivity(intent);
                break;
            case R.id.seeZobz:
                intent = new Intent(ActivityPersonalDetail.this, ActivityZobz.class);
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
}
