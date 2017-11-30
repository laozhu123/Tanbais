package xgn.com.basesdk.base.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xgn.com.basesdk.R;
import xgn.com.basesdk.base.DialogLoadingHelper;
import xgn.com.basesdk.base.PageLoadingHelper;
import xgn.com.basesdk.base.activity.ActivityBase;
import xgn.com.basesdk.base.mvp.MvpView;
import xgn.com.basesdk.dialog.LoadingDialog;
import xgn.com.basesdk.network.ExceptionHandle;
import xgn.com.basesdk.utils.ScreenUtil;
import xgn.com.basesdk.utils.UiUtil;
import xgn.com.basesdk.view.swipetoloadlayout.FixedSwipeToLoadLayout;
import xgn.com.basesdk.view.swipetoloadlayout.UtilFooterHeaderView;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.OnLoadMoreListener;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.OnRefreshListener;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.SwipeToLoadLayout;


/**
 * fragment基类
 * Created by yefeng on 2017/04/07.
 * Modified by yefeng
 */

public abstract class FragmentBase extends Fragment implements MvpView, OnRefreshListener, OnLoadMoreListener {

    public static final int RESULT_OK = 200;

    protected static final String Tag = "BaseFragment";
    protected boolean loaded = false;

    private DialogLoadingHelper mLoadingHelper;
    private LoadingDialog mLoadingDialog;
    private Context mContext;
    private ImageView mImageBack;
    private TextView mTextTitle;
    private TextView mRightText;
    private ImageView mRightIcon;
    private ViewGroup mTitleBar;
    private boolean useActivityTitleBar = false;
    protected FrameLayout mContentContainer;

    /**
     * 在ViewPager中有效，标记当前 Fragment 是否处于可见状态，建议使用{@link #isFragmentVisible()}
     *
     * @see #isFragmentVisible()
     */
    public boolean mIsVisible;
    private boolean mIsVisibleToUser;
    /**
     * 是否在前台，该状态在onStart和onStop更新
     */
    private boolean mIsForeground;

    private PageLoadingHelper mPageLoadingHelper;
    private View mContentView;
    private FixedSwipeToLoadLayout fixedSwipeToLoadLayout;
    public FragmentActivity _mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setComponent();
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        _mActivity = getActivity();
        mLoadingHelper = new DialogLoadingHelper(getActivity());
        mLoadingDialog = new LoadingDialog(_mActivity);
    }

    protected void setComponent() {
    }

    public void setUseActivityTitleBar(boolean useActivityTitleBar) {
        this.useActivityTitleBar = useActivityTitleBar;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState) {
        View view = initBaseView(inflater, container);
        initPresenter();
        initFragment(mContentView);
        initTitleBar();
        initSavadInstance(savedInstanceState);
        mContext = _mActivity;
        showContent();
        return view;
    }

    @Override
    public void onViewCreated(View view,
                              @Nullable
                                      Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsForeground = true;
    }

    protected void setRefreshComplete() {
        if (useSwipeRefreshLayout()) {
            fixedSwipeToLoadLayout.setRefreshing(false);
        }
    }

    protected void setLoadMoreComplete() {
        if (this.useSwipeRefreshLayout()) {
            this.fixedSwipeToLoadLayout.setLoadingMore(false);
        }
    }

    protected FixedSwipeToLoadLayout getRefreshLayout() {
        return fixedSwipeToLoadLayout;
    }

    protected void setRefreshStart() {
        if (useSwipeRefreshLayout()) {
            fixedSwipeToLoadLayout.setRefreshing(true);
        }
    }

    private View initBaseView(LayoutInflater pInflater, ViewGroup pContainer) {
        View view = pInflater
                .inflate(useSwipeRefreshLayout() ? R.layout.fragment_base_refresh_layout : R.layout.fragment_base_layout, pContainer, false);

        if (this.useSwipeRefreshLayout()) {
            this.mContentContainer = (FrameLayout) view.findViewById(R.id.swipe_target);
            this.mContentView = _mActivity.getLayoutInflater().inflate(getLayoutId(), this.mContentContainer, false);
            fixedSwipeToLoadLayout = (FixedSwipeToLoadLayout) view.findViewById(R.id.swipe_to_load_layout);
            fixedSwipeToLoadLayout.setTargetView(mContentContainer);
            fixedSwipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.ABOVE);
            fixedSwipeToLoadLayout.setOnLoadMoreListener(this);
            fixedSwipeToLoadLayout.setOnRefreshListener(this);
            fixedSwipeToLoadLayout.setRefreshHeaderView(
                    UtilFooterHeaderView.getGoogleHookHeader(_mActivity.getLayoutInflater(), fixedSwipeToLoadLayout));
            fixedSwipeToLoadLayout.setLoadMoreFooterView(
                    UtilFooterHeaderView.getGoogleHookFooter(_mActivity.getLayoutInflater(), fixedSwipeToLoadLayout));
        } else {
            this.mContentContainer = (FrameLayout) view.findViewById(R.id.content_container);
            this.mContentView = _mActivity.getLayoutInflater().inflate(getLayoutId(), this.mContentContainer, false);
        }

        mTitleBar = (ViewGroup) view.findViewById(R.id.title_bar);
        mImageBack = (ImageView) view.findViewById(R.id.titlebar_back);
        mTextTitle = (TextView) view.findViewById(R.id.titlebar_title);
        mRightIcon = (ImageView) view.findViewById(R.id.titlebar_right_icon);
        mRightText = (TextView) view.findViewById(R.id.titlebar_right_text);
        mContentView = pInflater.inflate(getLayoutId(), mContentContainer, false);
        mPageLoadingHelper = new PageLoadingHelper(mContentContainer, this, mContentView);
        return view;
    }

    /**
     * 重写此方法  下拉刷新
     */
    public void reLoadData() {
        setRefreshStart();
    }

    /**
     * 如果之类想使用下来刷新功能，需要重写 返回true
     */
    public boolean useSwipeRefreshLayout() {
        return false;
    }

    protected void initSavadInstance(Bundle savedInstanceState) {
    }

    protected void initPresenter() {
    }

    ;

    protected abstract int getLayoutId();

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }


    protected abstract void initFragment(View view);

    /**
     * 返回要显示在标题栏中的标题
     *
     * @return 返回 null 则隐藏标题栏
     */
    public
    @StringRes
    int getTitleText() {
        return -1;
    }


    private void initTitleBar() {
        if (getActivity() instanceof ActivityBase && !useActivityTitleBar) {
            Toolbar toolbar = ((ActivityBase) getActivity()).getToolbar();
            toolbar.setVisibility(View.GONE);
        }
        setTitle(getTitleText());
        mImageBack.setImageResource(R.drawable.icon_left_arrow);
        mImageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });
    }

    /**
     * 该方法是给使用到地图的 Fragment 用的，因为地图中需要用到 savedInstanceState
     */
    protected void onViewInflated(View view, Bundle savedInstanceState) {
    }

    public void setTitleBarBackground(
            @ColorRes
                    int color) {
        mTitleBar.setBackgroundColor(ContextCompat.getColor(_mActivity, color));
    }

    public void hideBackButton() {
        setStatusLeftImage(View.GONE);
    }

    /**
     * 设置标题
     */
    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mTextTitle.setText(title);
            mTitleBar.setVisibility(View.VISIBLE);
        } else {
            mTitleBar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左图标的状态
     */
    public void setStatusLeftImage(int visible) {
        if (mImageBack != null) {
            mImageBack.setVisibility(visible);
        }
    }

    /**
     * 设置左边图标
     */
    public void setLeftImage(@DrawableRes int res) {
        if (mImageBack != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mImageBack.getLayoutParams();
            layoutParams.setMargins(ScreenUtil.dip2px(getContext(), 15), 0, 0, 0);
            mImageBack.setLayoutParams(layoutParams);
            mImageBack.setImageResource(res);
        }
    }

    /**
     * 设置左图标点击事件
     */
    public void setLeftImageClick(OnClickListener onClickListener) {
        if (mImageBack != null) {
            mImageBack.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置右文本
     */
    public void setRightTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mRightText.setText(title);
            mTitleBar.setVisibility(View.VISIBLE);
            mRightIcon.setVisibility(View.INVISIBLE);
            mRightText.setVisibility(View.VISIBLE);
        } else {
            mRightText.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置右文本
     */
    public void setRightIcon(@DrawableRes
                                     int drawableRes) {
        mTitleBar.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(drawableRes);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置右文本点击事件
     */
    public void setRightTitleClick(OnClickListener onClickListener) {
        if (mRightText != null) {
            mRightText.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置右文本点击事件
     */
    public void setRightIconClick(OnClickListener onClickListener) {
        if (mRightIcon != null) {
            mRightIcon.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId) {
        if (titleId == -1) {
            mTitleBar.setVisibility(View.GONE);
        } else {
            mTitleBar.setVisibility(View.VISIBLE);
            mTextTitle.setText(titleId);
        }
    }

    public boolean checkNetworkState() {
        ConnectivityManager cm = (ConnectivityManager) _mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Toast 提示
     */
    @Override
    public void showToast(int resid) {
        UiUtil.showToast(mContext, resid);
    }

    /**
     * Toast 提示
     */
    @Override
    public void showToast(int resid, int duration) {
        UiUtil.showToast(mContext, resid);
    }

    /**
     * Toast 提示
     */
    @Override
    public void showToast(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            UiUtil.showToast(mContext, message.toString());
        }
    }

    /**
     * Toast 提示
     */
    @Override
    public void showToast(CharSequence message, int duration) {
        if (!TextUtils.isEmpty(message)) {
            UiUtil.showToast(mContext, message.toString());
        }
    }

    @Override
    public void showMsg(int title, int des) {
        UiUtil.showToast(mContext, title, des);
    }

    /**
     * 显示等待框
     * 默认显示"加载中..."
     */
    @Override
    public void showWaiting() {
        mLoadingHelper.showWaiting(null, getActivity());
    }

    /**
     * 默认显示"加载中..."
     */
    @Override
    public void showWaiting(boolean instantShow) {
        mLoadingHelper.showWaiting(instantShow, getActivity());
    }

    /**
     * 显示等待框
     * 默认显示"加载中..."
     */
    @Override
    public void showWaiting(int strId) {
        String message = getString(strId);
        mLoadingHelper.showWaiting(message, getActivity());
    }

    /**
     * 显示等待框
     */
    @Override
    public void showWaiting(int strId, boolean isCancelable) {
        //        String message = getString(strId);
        //        mLoadingHelper.showWaiting(message, isCancelable, getActivity());
        mLoadingDialog.setMessage(strId);
        mLoadingDialog.setCancelable(isCancelable);
        mLoadingDialog.show();
    }

    /**
     * 显示等待框
     * 默认显示"加载中..."
     */
    @Override
    public void showWaiting(String message) {
        mLoadingHelper.showWaiting(message, getActivity());
    }

    /**
     * 显示等待框
     * 默认显示"加载中..."
     */
    @Override
    public void showWaiting(String message, boolean isCancelable) {
        mLoadingHelper.showWaiting(message, isCancelable, getActivity());
    }

    /**
     * 如果使用了下拉刷新，重新加载数据成功后需要调此方法
     */
    public void showContent() {
        mPageLoadingHelper.showContent();
        setRefreshComplete();
    }

    /**
     * 隐藏等待框
     * 顺便隐藏refresh
     */
    @Override
    public void stopWaiting() {
        mLoadingHelper.stopWaiting();
        mLoadingDialog.dismiss();
    }

    /**
     * 显示或隐藏输入法
     */
    protected void showKeyboard(boolean isShow) {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (isShow) {
                if (getActivity().getCurrentFocus() == null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } else {
                    imm.showSoftInput(getActivity().getCurrentFocus(), 0);
                }
            } else {
                if (getActivity().getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }

    }

    @Override
    public void onExceptionHandle(ExceptionHandle.ResponseThrowable restError) {

    }

    @Override
    public void showErrorView() {
        setRefreshComplete();
        mPageLoadingHelper.showErrorView();
    }

    @Override
    public void showErrorView(ExceptionHandle.ResponseThrowable throwable) {
        setRefreshComplete();
        mPageLoadingHelper.showErrorView(throwable);
    }

    @Override
    public void showErrorView(
            @Nullable
                    String pErrorMes,
            @DrawableRes
                    int pErrorIconRes) {
        setRefreshComplete();
        mPageLoadingHelper.showErrorView(pErrorMes, pErrorIconRes);
    }

    @Override
    public void showErrorView(View pview) {
        setRefreshComplete();
        mPageLoadingHelper.showErrorView(pview);
    }

    @Override
    public void showPageInprossView() {
        mPageLoadingHelper.showInPageProgressView();
    }

    @Override
    public void showEmptyView(
            @DrawableRes
                    int pEmptyIconRes,
            @Nullable
                    String pEpmtyMes) {
        setRefreshComplete();
        mPageLoadingHelper.showEmptyView(pEmptyIconRes, pEpmtyMes);
    }

    @Override
    public void showEmptyView(
            @DrawableRes
                    int pEmptyIconRes,
            @StringRes
                    int pEpmtyMes) {
        setRefreshComplete();
        mPageLoadingHelper.showEmptyView(pEmptyIconRes, pEpmtyMes);
    }

    @Override
    public void showEmptyView() {
        setRefreshComplete();
        mPageLoadingHelper.showEmptyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisibleToUser = true;
        } else {
            mIsVisibleToUser = false;
        }
        mIsVisible = isFragmentVisible();
    }

    @Override
    public void onStart() {
        super.onStart();
        mIsForeground = true;
        mIsVisible = isFragmentVisible();
    }

    @Override
    public void onStop() {
        super.onStop();
        mIsForeground = false;
        mIsVisible = isFragmentVisible();
    }

    /**
     * 在ViewPager中可用该方法可以判断是否在可见
     */
    public boolean isFragmentVisible() {
        return mIsVisibleToUser && mIsForeground;
    }
}

