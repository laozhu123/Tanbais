package xgn.com.basesdk.base.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import xgn.com.basesdk.R;
import xgn.com.basesdk.base.ActivityCollector;
import xgn.com.basesdk.base.DialogLoadingHelper;
import xgn.com.basesdk.base.PageLoadingHelper;
import xgn.com.basesdk.base.mvp.MvpView;
import xgn.com.basesdk.network.ExceptionHandle;
import xgn.com.basesdk.utils.UiUtil;
import xgn.com.basesdk.view.swipetoloadlayout.FixedSwipeToLoadLayout;
import xgn.com.basesdk.view.swipetoloadlayout.UtilFooterHeaderView;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.OnLoadMoreListener;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.OnRefreshListener;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.SwipeToLoadLayout;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;


/**
 * 全局Activity基类
 * Created by yefeng on 2017/04/07.
 * Modified by  yefeng
 */
public abstract class ActivityBase extends AppCompatActivity implements MvpView, OnRefreshListener, OnLoadMoreListener {
    private Toolbar mToolbar;
    private FrameLayout mContentContainer;
    private TextView mTitleBarTitle;
    private View mContentView;
    protected DialogLoadingHelper mLoadingHelper;
    private View mTitleBarBack;
    protected PageLoadingHelper mPageLoadingHelper;
    private TextView mTitleBarRightText;
    private FixedSwipeToLoadLayout fixedSwipeToLoadLayout;

    public ActivityBase() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.setComponent();
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        this.setContentView(this.getBaseContentLayoutResId());
        this.mLoadingHelper = new DialogLoadingHelper(this);
        this.initBaseView();
        this.mPageLoadingHelper = new PageLoadingHelper(this.mContentContainer, this, this.mContentView);
        this.initBaseToolBar();
        this.initPresenter();
        this.showContent();
        this.initActivity(this.mContentView);
        this.initInstanceState(savedInstanceState);
    }

    protected int getBaseContentLayoutResId() {
        return this.useSwipeRefreshLayout() ? R.layout.activity_base_refresh_layout : R.layout.activity_base_layout;
    }

    protected void setRefreshComplete() {
        if (this.useSwipeRefreshLayout()) {
            this.fixedSwipeToLoadLayout.setRefreshing(false);
        }

    }

    protected void setLoadMoreComplete(){
        if (this.useSwipeRefreshLayout()) {
            this.fixedSwipeToLoadLayout.setLoadingMore(false);
        }
    }

    protected void initInstanceState(Bundle savedInstanceState) {
    }

    private void initBaseView() {
        if (this.useSwipeRefreshLayout()) {
            this.mContentContainer = (FrameLayout) super.findViewById(R.id.swipe_target);
            this.mContentView = this.getLayoutInflater().inflate(this.getContentLayoutResId(), this.mContentContainer, false);
            fixedSwipeToLoadLayout = (FixedSwipeToLoadLayout) super.findViewById(R.id.swipe_to_load_layout);
            Log.e("helo", "mContentContainer" + (mContentContainer == null ? "null" : "you"));
            Log.e("helo", "mContentView" + (mContentView == null ? "null" : "you"));
            fixedSwipeToLoadLayout.setTargetView(mContentContainer);
            fixedSwipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.ABOVE);
            fixedSwipeToLoadLayout.setOnLoadMoreListener(this);
            fixedSwipeToLoadLayout.setOnRefreshListener(this);
            fixedSwipeToLoadLayout.setRefreshHeaderView(
                    UtilFooterHeaderView.getGoogleHookHeader(getLayoutInflater(), fixedSwipeToLoadLayout));
            fixedSwipeToLoadLayout.setLoadMoreFooterView(
                    UtilFooterHeaderView.getGoogleHookFooter(getLayoutInflater(), fixedSwipeToLoadLayout));
        } else {
            this.mContentContainer = (FrameLayout) super.findViewById(R.id.content_container);
            this.mContentView = this.getLayoutInflater().inflate(this.getContentLayoutResId(), this.mContentContainer, false);
        }

    }

    public void reLoadData() {

    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {
    }

    public boolean useSwipeRefreshLayout() {
        return false;
    }

    public View findViewById(@IdRes int id) {
        return this.mContentView.findViewById(id);
    }

    private void initBaseToolBar() {
        this.mToolbar = (Toolbar) super.findViewById(R.id.toolbar);
        this.setNormalTitlebar();
    }

    protected abstract int getContentLayoutResId();

    public Toolbar getToolbar() {
        return this.mToolbar;
    }

    protected void setComponent() {
    }

    protected void initPresenter() {
    }

    protected abstract void initActivity(View var1);

    public void setNormalTitlebar() {
        View titleBar = this.getLayoutInflater().inflate(R.layout.view_simple_title_bar, (ViewGroup) null);
        this.mTitleBarBack = titleBar.findViewById(R.id.titlebar_back);
        this.mTitleBarTitle = (TextView) titleBar.findViewById(R.id.titlebar_title);
        this.mTitleBarRightText = (TextView) titleBar.findViewById(R.id.titlebar_right_text);
        this.mTitleBarBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ActivityBase.this.onBackPressed();
            }
        });
        this.setCustomTitleBar(titleBar);
    }

    public void setCustomTitleBar(View titlebarLayout) {
        this.mToolbar.removeAllViews();
        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(-1, -1);
        this.mToolbar.addView(titlebarLayout, lp);
    }

    public void hideTitleBar() {
            this.mToolbar.setVisibility(View.GONE);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        if (this.getRequestedOrientation() != SCREEN_ORIENTATION_PORTRAIT) {
            this.setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    public void setTitle(CharSequence title) {
        if (this.mTitleBarTitle != null) {
            this.mTitleBarTitle.setText(title);
        }

    }

    public void setTitle(int TitleId) {
        if (this.mTitleBarTitle != null) {
            this.mTitleBarTitle.setText(TitleId);
        }

    }

    public void setRightTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            this.mTitleBarRightText.setVisibility(View.VISIBLE);
            this.mTitleBarRightText.setText(title);
        }

    }

    public void setRightTitleClick(View.OnClickListener onClickListener) {
        if (this.mTitleBarRightText != null) {
            this.mTitleBarRightText.setOnClickListener(onClickListener);
        }

    }

    public void setRightTitleEnable(boolean enable) {
        if (this.mTitleBarRightText != null) {
            this.mTitleBarRightText.setEnabled(enable);
        }

    }

    public void showToast(int resid) {
        this.showToast(resid, 0);
    }

    public void showToast(int resid, int duration) {
        UiUtil.showToast(this, resid);
    }

    public void showToast(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            UiUtil.showToast(this, message.toString());
        }

    }

    public void showToast(CharSequence message, int duration) {
        if (!TextUtils.isEmpty(message)) {
            UiUtil.showToast(this, message.toString());
        }

    }

    public void showMsg(int title, int des) {
        UiUtil.showToast(this, title, des);
    }

    public void showWaiting() {
        this.mLoadingHelper.showWaiting((String) null, this.isFinishing());
    }

    public void showWaiting(boolean instantShow) {
        this.mLoadingHelper.showWaiting(instantShow, this.isFinishing());
    }

    public void showWaiting(int strId) {
        String message = this.getString(strId);
        this.mLoadingHelper.showWaiting(message, this.isFinishing());
    }

    public void showWaiting(int strId, boolean isCancelable) {
        String message = this.getString(strId);
        this.mLoadingHelper.showWaiting(message, isCancelable, this.isFinishing());
    }

    public void showWaiting(String message) {
        this.mLoadingHelper.showWaiting(message, this.isFinishing());
    }

    public void showWaiting(String message, boolean isCancelable) {
        this.mLoadingHelper.showWaiting(message, isCancelable, this.isFinishing());
    }

    public void stopWaiting() {
        this.mLoadingHelper.stopWaiting();
    }

    public void showContent() {
        this.mPageLoadingHelper.showContent();
        this.setRefreshComplete();
    }

    protected FixedSwipeToLoadLayout getRefreshLayout() {
        return this.fixedSwipeToLoadLayout;
    }

    public void setToolBarVisible(boolean is) {
        if (this.mToolbar != null) {
            if (is) {
                this.mToolbar.setVisibility(View.VISIBLE);
            } else {
                this.mToolbar.setVisibility(View.GONE);
            }

        }
    }

    public void setBackIconVisiable(boolean visiable) {
        this.mTitleBarBack.setVisibility(visiable ? View.VISIBLE : View.INVISIBLE);
    }

    public void showOrHideToolbarSmooth(boolean show) {
        if (this.mToolbar != null) {
            ObjectAnimator animator;
            if (show && ((android.widget.LinearLayout.LayoutParams) this.mToolbar.getLayoutParams()).topMargin < 0) {
                animator = ObjectAnimator.ofInt(this.mToolbar, "translateY", new int[]{this.mToolbar.getLayoutParams().height, 0}).setDuration(500L);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = ((Integer) animation.getAnimatedValue()).intValue();
                        android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) ActivityBase.this.mToolbar.getLayoutParams();
                        lp.topMargin = -height;
                        ActivityBase.this.mToolbar.setLayoutParams(lp);
                    }
                });
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
            } else if (!show && ((android.widget.LinearLayout.LayoutParams) this.mToolbar.getLayoutParams()).topMargin == 0) {
                animator = ObjectAnimator.ofInt(this.mToolbar, "translateY", new int[]{0, this.mToolbar.getLayoutParams().height}).setDuration(500L);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = ((Integer) animation.getAnimatedValue()).intValue();
                        android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) ActivityBase.this.mToolbar.getLayoutParams();
                        lp.topMargin = -height;
                        ActivityBase.this.mToolbar.setLayoutParams(lp);
                    }
                });
                animator.setInterpolator(new AccelerateInterpolator(2.0F));
                animator.start();
            }

        }
    }

    @TargetApi(16)
    public void setTitleBarBackgroundColor(int color) {
        if (this.mToolbar != null) {
            this.mToolbar.setBackgroundColor(color);
        }

    }

    @TargetApi(16)
    public void setTitleBarBackgroundColor(Drawable barBackgroundColor) {
        if (this.mToolbar != null) {
            this.mToolbar.setBackground(barBackgroundColor);
        }

    }

    public void toActivity(Class<?> cls) {
        this.startActivity(new Intent(this, cls));
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (this.getCurrentFocus() == null) {
                imm.toggleSoftInput(2, 0);
            } else {
                imm.showSoftInput(this.getCurrentFocus(), 0);
            }
        } else if (this.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 2);
        }

    }

    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        this.mLoadingHelper.stopWaiting();
    }

    public void onExceptionHandle(ExceptionHandle.ResponseThrowable restError) {
    }

    public void showErrorView() {
        this.setRefreshComplete();
        this.mPageLoadingHelper.showErrorView();
    }

    public void showErrorView(ExceptionHandle.ResponseThrowable throwable) {
        this.setRefreshComplete();
        this.mPageLoadingHelper.showErrorView(throwable);
    }

    public void showErrorView(@Nullable String pErrorMes, @DrawableRes int pErrorIconRes) {
        this.setRefreshComplete();
        this.mPageLoadingHelper.showErrorView(pErrorMes, pErrorIconRes);
    }

    public void showErrorView(View pview) {
        this.setRefreshComplete();
        this.mPageLoadingHelper.showErrorView(pview);
    }

    public void showPageInprossView() {
        this.mPageLoadingHelper.showInPageProgressView();
    }

    public void showEmptyView(@DrawableRes int pEmptyIconRes, @Nullable String pEpmtyMes) {
        this.setRefreshComplete();
        this.mPageLoadingHelper.showEmptyView(pEmptyIconRes, pEpmtyMes);
    }

    public void showEmptyView(@DrawableRes int pEmptyIconRes, @StringRes int pEpmtyMes) {
        this.setRefreshComplete();
        this.mPageLoadingHelper.showEmptyView(pEmptyIconRes, pEpmtyMes);
    }

    public void showEmptyView() {
        this.setRefreshComplete();
        this.mPageLoadingHelper.showEmptyView();
    }
}