package xiaogao.zjut.tabbaishuo.main.activity.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neteaseyx.image.imageview.toolbox.GestureBaseImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.PreviewPictureAdapter;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityShowPicture;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityGrzl;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityShowPicture;
import xiaogao.zjut.tabbaishuo.net.responses.Picture;

/**
 * Created by Administrator on 2017/12/18.
 */

public class ActivityShowPicture extends MyBaseBindPresentActivity<PresenterActivityShowPicture> implements IUIActivityShowPicture {

    @Inject
    PresenterActivityShowPicture mPresenter;

    @Bind(R.id.viewpage)
    ViewPager mViewPager;
    @Bind(R.id.rl_top_layout)
    RelativeLayout mRlTopLayout;
    @Bind(R.id.rl_button_layout)
    RelativeLayout mRlButtonLayout;
    @Bind(R.id.toolbar_backs)
    ImageView mToorBarBack;
    @Bind(R.id.tv_button_button)
    TextView mTopBottomBtn;

    private boolean isShowFramOpen = false;
    private TranslateAnimation mShowTranslateAnimation;
    private AlphaAnimation mShowAlpaAnimation;
    private TranslateAnimation mHideTranslateAnimation;
    private AlphaAnimation mHideAlpaAnimation;


    PreviewPictureAdapter mAdpter;
    List<Picture> list;

    @Override
    public PresenterActivityShowPicture getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_preview_image;
    }

    @Override
    protected void initActivity(View var1) {
        ButterKnife.bind(this);
        hideTitleBar();
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getBundleExtra(ActivityGrzl.PICS);
        if (bundle != null) {
            list = (List<Picture>) bundle.getSerializable(ActivityGrzl.PICS);
        }
        if (list != null) {
            mAdpter = new PreviewPictureAdapter(this, list);
            mViewPager.setAdapter(mAdpter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        showAnimations();
        hideAnimations();
    }

    /**
     * 点击实现动画的效果
     */
    public void showAnimation() {
        if (isShowFramOpen) {
            isShowFramOpen = false;
            mRlTopLayout.startAnimation(mShowTranslateAnimation);
            mRlButtonLayout.startAnimation(mShowAlpaAnimation);
        } else {
            isShowFramOpen = true;
            mRlTopLayout.startAnimation(mHideTranslateAnimation);
            mRlButtonLayout.startAnimation(mHideAlpaAnimation);
        }
    }

    private void showAnimations() {
        mShowTranslateAnimation = getTranslateAnimation(0, 0, -1, 0, 150);
        mShowAlpaAnimation = getAlpaAnimation(0, 1, 150);
    }

    private void hideAnimations() {
        mHideTranslateAnimation = getTranslateAnimation(0, 0, 0, -1, 150);
        mHideAlpaAnimation = getAlpaAnimation(1, 0, 150);
    }

    /**
     * 平移动画
     */
    private TranslateAnimation getTranslateAnimation(float fromXvelue, float toXvelue, float fromYValue, float toYValue, long duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromXvelue, Animation.RELATIVE_TO_SELF, toXvelue,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue
        );
        translateAnimation.setDuration(duration);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    /**
     * 透明度动画
     */
    private AlphaAnimation getAlpaAnimation(float fromAlpha, float toAlpha, long duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }


    @Override
    public void onDestroy() {
        for (int i = 0; i < mViewPager.getChildCount(); i++) {
            GestureBaseImageView view = (GestureBaseImageView) mViewPager.getChildAt(i);
            Bitmap bitmap = view.getViewBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
