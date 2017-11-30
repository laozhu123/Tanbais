package xgn.com.basesdk.base;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import xgn.com.basesdk.R;
import xgn.com.basesdk.base.activity.ActivityBase;
import xgn.com.basesdk.base.fragment.FragmentBase;
import xgn.com.basesdk.network.ExceptionHandle;


/**
 * 页面的 正在加载辅助类.处理以下类型
 * 1. 网络异常 httpcode 400 或者 500以上
 * 2. 脏数据 业务异常  ------》 后期待加上
 * 3. 数据为空 empty页面
 * 4. 加载数据前的loading页面。页面中间一朵菊花转。
 *
 * Created by huangwenqiang on 2017/05/24.
 */
public class PageLoadingHelper {


    private final Activity mActivity;
    private FragmentBase mFragmentBase;
    private LayoutInflater mLayoutInflater;
    private FrameLayout mContainerLayout;
    private View mContentView;


    public PageLoadingHelper(FrameLayout pContainerLayout, FragmentBase pFragment, View pContentView) {
        this.mFragmentBase = pFragment;
        this.mActivity = pFragment.getActivity();
        this.mContainerLayout = pContainerLayout;
        this.mLayoutInflater = mActivity.getLayoutInflater();
        this.mContentView = pContentView;
    }

    public PageLoadingHelper(FrameLayout pContainerLayout, Activity pActivity, View pContentView) {
        this.mActivity = pActivity;
        this.mContainerLayout = pContainerLayout;
        this.mLayoutInflater = pActivity.getLayoutInflater();
        this.mContentView = pContentView;
    }

    public void showErrorView() {
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        mContainerLayout.addView(generateNetworkExceptionView(null));
    }

    public void showErrorView(ExceptionHandle.ResponseThrowable pRestError) {
        if (null == pRestError) return;
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        mContainerLayout.addView(getErrorViewLayoutRes(pRestError));
    }

    /**
     * 自定义错误页面的图片和文案
     * @param pErrorMes
     * @param pErrorIconRes
     */
    public void showErrorView(@Nullable String pErrorMes, @DrawableRes int pErrorIconRes) {
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        View view = mLayoutInflater.inflate(R.layout.error_network, mContainerLayout);
        ((ImageView) view.findViewById(R.id.iv_common_network_error)).setImageResource(pErrorIconRes);
        if (!TextUtils.isEmpty(pErrorMes)) {
            ((TextView) view.findViewById(R.id.tv_common_network_error)).setText(pErrorMes);
        }
        view.findViewById(R.id.btn_refresh_network_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               reload();
            }
        });

    }

    private void reload() {
        if (mFragmentBase != null) {
            mFragmentBase.reLoadData();
            return;
        }

        if (mActivity instanceof ActivityBase) {
            ((ActivityBase) mActivity).reLoadData();
        }
    }

    private View getErrorViewLayoutRes(ExceptionHandle.ResponseThrowable pRestError) {
        if(pRestError == null) return generateNetworkExceptionView(pRestError);
        switch (pRestError.code) {
            case ExceptionHandle.ERROR.PARSE_ERROR:  //数据错误异常
                return generateDirtyDataExceptionView(pRestError);
            default:                                 //网络错误异常
                return generateNetworkExceptionView(pRestError);
        }
    }

    private View generateDirtyDataExceptionView(ExceptionHandle.ResponseThrowable pRestError) {
        View view = mLayoutInflater.inflate(R.layout.error_network, mContainerLayout, false);
        ((TextView) view.findViewById(R.id.tv_common_network_error)).setText(pRestError.message);
        view.findViewById(R.id.btn_refresh_network_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        return view;
    }


    private View generateNetworkExceptionView(ExceptionHandle.ResponseThrowable pRestError) {
        View view = mLayoutInflater.inflate(R.layout.error_network, mContainerLayout, false);
        if (pRestError != null)
            ((TextView) view.findViewById(R.id.tv_common_network_error)).setText(pRestError.message + "");
        view.findViewById(R.id.btn_refresh_network_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        return view;
    }


    /**
     * 后期可支持增加动画等
     */
    public void showInPageProgressView() {
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        mLayoutInflater.inflate(R.layout.xghl_loading_view, mContainerLayout);
    }





    /**
     * 支持传入自己的错误页面
     * @param pViewGroupParent
     */
    public void showErrorView(View pViewGroupParent) {
        if (null == pViewGroupParent) return;
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        mContainerLayout.addView(pViewGroupParent);
    }




    public void showEmptyView(@DrawableRes int pEmptyIconRes,@Nullable String pEpmtyMes){
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        mLayoutInflater.inflate(R.layout.empty_finished_list, mContainerLayout);
        ((ImageView) mContainerLayout.findViewById(R.id.iv_common_empty)).setImageResource(pEmptyIconRes);
        if (!TextUtils.isEmpty(pEpmtyMes)) {
            ((TextView) mContainerLayout.findViewById(R.id.tv_common_empty_tip)).setText(pEpmtyMes);
        }
    }

    public void showEmptyView(@DrawableRes int pEmptyIconRes, @StringRes int pEpmtyMes) {
        if(this.mContainerLayout.getChildCount() > 0) {
            this.mContainerLayout.removeAllViews();
        }

        this.mLayoutInflater.inflate(R.layout.empty_finished_list, this.mContainerLayout);
        ((ImageView)this.mContainerLayout.findViewById(R.id.iv_common_empty)).setImageResource(pEmptyIconRes);
        if(!TextUtils.isEmpty(this.mActivity.getString(pEpmtyMes))) {
            ((TextView)this.mContainerLayout.findViewById(R.id.tv_common_empty_tip)).setText(pEpmtyMes);
        }
    }

    public void showEmptyView(){
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        mLayoutInflater.inflate(R.layout.empty_finished_list, mContainerLayout);
    }

    public void showContent() {
        if (mContainerLayout.getChildCount() > 0) {
            mContainerLayout.removeAllViews();
        }
        mContainerLayout.addView(mContentView);
    }
}
