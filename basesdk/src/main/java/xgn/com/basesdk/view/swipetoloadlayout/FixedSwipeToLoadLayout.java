package xgn.com.basesdk.view.swipetoloadlayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Created by huluzi on 2017/8/10.
 */

public class FixedSwipeToLoadLayout extends SwipeToLoadLayout {
    private View mTargetView;

    public FixedSwipeToLoadLayout(Context context) {
        super(context);
    }

    public FixedSwipeToLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedSwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTargetView(View targetView) {
        this.mTargetView = targetView;
    }

    protected boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(this.mTargetView, -1);
    }

    protected boolean canChildScrollDown() {
        return ViewCompat.canScrollVertically(this.mTargetView, 1);
    }
}