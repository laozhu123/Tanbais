package xiaogao.zjut.tabbaishuo.main.activity.common;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityJuBao;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityJuBao;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ActivityJuBao extends MyBaseBindPresentActivity<PresenterActivityJuBao> implements IUIActivityJuBao, View.OnClickListener {

    @Inject
    PresenterActivityJuBao mPresenter;
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.submit)
    TextView mSubmit;
    @Bind(R.id.reason)
    TextView mReason;
    @Bind(R.id.reasonSelector)
    RelativeLayout mReasonSelector;

    @Bind(R.id.bg)
    View bg;

    private String[] mRs;

    @Override
    public void success() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void failed(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public PresenterActivityJuBao getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_jubao;
    }

    @Override
    protected void initActivity(View var1) {
        ButterKnife.bind(this);
        hideTitleBar();
        mBack.setOnClickListener(this);
        mRs = getResources().getStringArray(R.array.reasonList);
        mReason.setText(mRs[0]);
        mReasonSelector.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.reasonSelector:
                bg.setVisibility(View.VISIBLE);
                break;
            case R.id.bg:
                bg.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.id1, R.id.id2, R.id.id3, R.id.id4, R.id.id5, R.id.bg})
    public void onViewClicked(View view) {
        bg.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.id1:
                mReason.setText(mRs[0]);
                break;
            case R.id.id2:
                mReason.setText(mRs[1]);
                break;
            case R.id.id3:
                mReason.setText(mRs[2]);
                break;
            case R.id.id4:
                mReason.setText(mRs[3]);
                break;
            case R.id.id5:
                mReason.setText(mRs[4]);
                break;
        }
    }
}
