package xgn.com.basesdk.view.swipetoloadlayout.view.footer;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import xgn.com.basesdk.R;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.SwipeLoadMoreTrigger;
import xgn.com.basesdk.view.swipetoloadlayout.swipetoloadlayout.SwipeTrigger;


/**
 * Created by huluzi on 2017/6/14.
 */

public class JdLoadMoreFooterView extends RelativeLayout implements SwipeLoadMoreTrigger, SwipeTrigger {

    private ImageView ivSpeed;

    private ImageView ivLoadMore;

    private AnimationDrawable mAnimDrawable;

    private Animation mTwinkleAnim;

    private int mTriggerOffset;

    public JdLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public JdLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JdLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTriggerOffset = context.getResources().getDimensionPixelOffset(R.dimen.load_more_footer_height_jd);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivLoadMore = (ImageView) findViewById(R.id.ivLoadMore);
        ivSpeed = (ImageView) findViewById(R.id.ivSpeed);
        mAnimDrawable = (AnimationDrawable) ivLoadMore.getBackground();
        mTwinkleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.twinkle);
    }

    @Override
    public void onLoadMore() {
        ivSpeed.setVisibility(VISIBLE);
        ivSpeed.startAnimation(mTwinkleAnim);
        if (!mAnimDrawable.isRunning()) {
            mAnimDrawable.start();
        }
    }

    @Override
    public void onPrepare() {
        ivSpeed.clearAnimation();
        ivSpeed.setVisibility(GONE);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {
        if (!mAnimDrawable.isRunning()) {
            mAnimDrawable.start();
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        mAnimDrawable.stop();
        ivSpeed.clearAnimation();
        ivSpeed.setVisibility(GONE);
    }
}
