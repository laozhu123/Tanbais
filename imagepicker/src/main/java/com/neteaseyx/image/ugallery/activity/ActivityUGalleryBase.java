package com.neteaseyx.image.ugallery.activity;

import com.neteaseyx.image.R;
import com.neteaseyx.image.ugallery.UGallery;
import com.neteaseyx.image.ugallery.view.StateLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author yuhuibin
 * @date 2016-05-11
 */
public class ActivityUGalleryBase extends AppCompatActivity {

    private LinearLayout mRoot;
    private FrameLayout mContentContainer;
    private View mContentView;
    private View mToobarLine;
    private Toolbar mToolbar;
    private View mTitleBar;
    private ImageView mTitleBarBack;
    private TextView mTitleBarTitle;
    private TextView mTitleBarRight;
    private TextView mTitleBarLeft;
    private StateLayout mStateLayout;
    protected TextView mSelectorAlbum;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoot = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_gallery_base, null);
        mContentContainer = (FrameLayout) mRoot.findViewById(R.id.content_container);
        mToobarLine = mRoot.findViewById(R.id.toolbar_line);
        super.setContentView(mRoot);

    }

    /**
     * 将子类布局就加载到带有toolbar的父类布局中
     */
    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        mContentView = view;
        mContentContainer.addView(mContentView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initNormalTitlebar();
    }

    /** 设置文本的后退按钮 */
    public void setTextBack() {
        mTitleBarBack.setVisibility(View.GONE);
        mTitleBarLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 添加自定义标题栏
     */
    public void setCustomTitleBar(View titlebarLayout) {
        mToolbar.removeAllViews();  // 先清除掉之前可能加入的

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT);//传入的布局，覆盖整个Toolbar
        mToolbar.addView(titlebarLayout, lp);
    }

    public void setTitle(String string) {
        mTitleBarTitle.setText(string);
    }

    public void setTitleBarRightButton(String string) {
        mTitleBarRight.setText(string);
    }

    public void setTitleBarBackOnClickListener(View.OnClickListener onClickListener) {
        mTitleBarBack.setOnClickListener(onClickListener);
    }

    public void setTitleBarRightButtonEnable(boolean enable) {
        mTitleBarRight.setEnabled(enable);
        if (enable) {
            //     mTitleBarRight.setBackground(getResources().getDrawable(R.drawable.tool_bar_right_enable));
            if (UGallery.getConfig().getTitleBarRightBtnTextColor() != 0) {
                mTitleBarRight.setTextColor(ContextCompat.getColor(this, UGallery.getConfig().getTitleBarRightBtnTextColor()));
            } else {
                mTitleBarRight.setTextColor(ContextCompat.getColor(this,R.color.toolbar_right_text_color_green));
            }
        } else {
            //   mTitleBarRight.setBackground(getResources().getDrawable(R.drawable.tool_bar_right_unable));
            mTitleBarRight.setTextColor(ContextCompat.getColor(this,R.color.toolbar_right_text_color_gray));
        }
    }

    /**
     * 显示或隐藏toobar
     */
    public void setIsShowTitleBar(boolean is) {
        if (mToolbar == null) {
            return;
        }

        if (is) {
            mToolbar.setVisibility(View.VISIBLE);
            mToobarLine.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setVisibility(View.GONE);
            mToobarLine.setVisibility(View.GONE);
        }
    }

    public void setTitleBarRightClickListener(View.OnClickListener listener) {
        mTitleBarRight.setOnClickListener(listener);
    }

    public StateLayout getStateLayout() {
        if (mStateLayout == null) {
            mStateLayout = new StateLayout(this);
            mContentContainer.addView(mStateLayout,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        mStateLayout.bringToFront();
        return mStateLayout;
    }

    private void initNormalTitlebar() {

        mTitleBar = getLayoutInflater().inflate(R.layout.gallery_view_toolbar_title, null);//通用的一种标题栏布局，包含一个返回键、标题、最右两个按钮。
        mTitleBarBack = (ImageView) mTitleBar.findViewById(R.id.toolbar_back);
        mTitleBarTitle = (TextView) mTitleBar.findViewById(R.id.toolbar_title);
        mTitleBarLeft = (TextView) mTitleBar.findViewById(R.id.toolbar_left_button);
        mTitleBarRight = (TextView) mTitleBar.findViewById(R.id.toolbar_right_button);
        mSelectorAlbum = (TextView) mTitleBar.findViewById(R.id.selected_album);
        setCustomStyle();
        mTitleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT);//传入的布局，覆盖整个Toolbar
        mToolbar.addView(mTitleBar, lp);
    }

    /** 根据配置。设置样式 */
    private void setCustomStyle() {
        UGallery.Config config = UGallery.getConfig();
        if (config.getTitleBarBgColor() != 0) {
            mTitleBar.setBackgroundColor(config.getTitleBarBgColor());
        }

//        if (config.getTitleTextColor() != 0) {
//            mTitleBarTitle.setTextColor(config.getTitleTextColor());
//        }

        if (config.getTitleTextSize() != 0) {
            mTitleBarTitle.setTextSize(config.getTitleTextSize());
        }

        if (config.getTitleBarRightBtnSize() != 0) {
            mTitleBarRight.setTextSize(config.getTitleBarRightBtnSize());
        }

        if (config.getTitleBarRightBtnColor() != 0) {
            mTitleBarRight.setBackgroundColor(config.getTitleBarRightBtnColor());
        }

        if (config.getTitleBarBackBtnImage() != 0) {
            mTitleBarBack.setImageResource(config.getTitleBarBackBtnImage());
        }

        if (config.getTitleBarRightBtnTextColor() != 0) {
            mTitleBarRight.setTextColor(config.getTitleBarRightBtnTextColor());
        }

        if (config.getRightBtnTextSize() != 0) {
            mTitleBarRight.setTextSize(config.getRightBtnTextSize());
        }

        if (config.getTitleBarHeight() != 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mToolbar.getLayoutParams();
            layoutParams.height = config.getTitleBarHeight();
            mTitleBar.setLayoutParams(layoutParams);
            mTitleBar.setMinimumHeight(config.getTitleBarHeight());
        }

    }
}
