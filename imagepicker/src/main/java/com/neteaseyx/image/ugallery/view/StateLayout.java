package com.neteaseyx.image.ugallery.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.neteaseyx.image.R;

/**
 * @author hzwangyufei
 * @date 2015/12/15.
 */
public class StateLayout extends FrameLayout {


    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mContext = context;
    }

    public StateLayout(Context context) {
        super(context);
        init();
        mContext = context;
    }

    private Context mContext;
    private void init() {
        setBackgroundColor(Color.WHITE);
        setOnClickListener(null);
    }

    public void onLoading() {
        replaceView(new LoadingStateView(mContext));
    }

    public void onEmpty() {
        ViewEmpty empty = new ViewEmpty(mContext);
        empty.setHintText(getResources().getString(R.string.empty_photo));
        empty.setImageView(R.drawable.unselect_icon_iv);
        replaceView(empty);
    }


    private void replaceView(View view) {
        if (view != null) {
            removeAllViews();
            addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 根据argb设置背景
     * @param alpha
     * @param red
     * @param green
     * @param blue
     */
    public void setBackGroundColor(int alpha, int red, int green, int blue) {
        setBackgroundColor(Color.argb(alpha, red, green, blue));
    }
}
