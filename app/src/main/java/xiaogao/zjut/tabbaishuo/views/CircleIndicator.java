package xiaogao.zjut.tabbaishuo.views;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import xiaogao.zjut.tabbaishuo.R;

public class CircleIndicator extends LinearLayout {
    private static final int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mAnimatorResId;
    private int mAnimatorReverseResId;
    private int mIndicatorBackgroundResId;
    private int mIndicatorUnselectedBackgroundResId;
    private Animator mAnimatorOut;
    private Animator mAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private Animator mImmediateAnimatorIn;
    private boolean mUseAnimation;
    private int mLastPosition;
    private final ViewPager.OnPageChangeListener mInternalPageChangeListener;
    private DataSetObserver mInternalDataSetObserver;

    public CircleIndicator(Context context) {
        super(context);
        this.mAnimatorResId = R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId = R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if(CircleIndicator.this.mViewpager.getAdapter() != null && CircleIndicator.this.mViewpager.getAdapter().getCount() > 0) {
                    if(CircleIndicator.this.mAnimatorIn.isRunning()) {
                        CircleIndicator.this.mAnimatorIn.end();
                        CircleIndicator.this.mAnimatorIn.cancel();
                    }

                    if(CircleIndicator.this.mAnimatorOut.isRunning()) {
                        CircleIndicator.this.mAnimatorOut.end();
                        CircleIndicator.this.mAnimatorOut.cancel();
                    }

                    View currentIndicator;
                    if(CircleIndicator.this.mLastPosition >= 0 && (currentIndicator = CircleIndicator.this.getChildAt(CircleIndicator.this.mLastPosition)) != null) {
                        currentIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorIn.setTarget(currentIndicator);
                            CircleIndicator.this.mAnimatorIn.start();
                        }
                    }

                    View selectedIndicator = CircleIndicator.this.getChildAt(position);
                    if(selectedIndicator != null) {
                        selectedIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorOut.setTarget(selectedIndicator);
                            CircleIndicator.this.mAnimatorOut.start();
                        }
                    }

                    CircleIndicator.this.mLastPosition = position;
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                if(CircleIndicator.this.mViewpager != null) {
                    int newCount = CircleIndicator.this.mViewpager.getAdapter().getCount();
                    int currentCount = CircleIndicator.this.getChildCount();
                    if(newCount != currentCount) {
                        if(CircleIndicator.this.mLastPosition < newCount) {
                            CircleIndicator.this.mLastPosition = CircleIndicator.this.mViewpager.getCurrentItem();
                        } else {
                            CircleIndicator.this.mLastPosition = -1;
                        }

                        CircleIndicator.this.createIndicators();
                    }
                }
            }
        };
        this.init(context, (AttributeSet)null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAnimatorResId = R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId = R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if(CircleIndicator.this.mViewpager.getAdapter() != null && CircleIndicator.this.mViewpager.getAdapter().getCount() > 0) {
                    if(CircleIndicator.this.mAnimatorIn.isRunning()) {
                        CircleIndicator.this.mAnimatorIn.end();
                        CircleIndicator.this.mAnimatorIn.cancel();
                    }

                    if(CircleIndicator.this.mAnimatorOut.isRunning()) {
                        CircleIndicator.this.mAnimatorOut.end();
                        CircleIndicator.this.mAnimatorOut.cancel();
                    }

                    View currentIndicator;
                    if(CircleIndicator.this.mLastPosition >= 0 && (currentIndicator = CircleIndicator.this.getChildAt(CircleIndicator.this.mLastPosition)) != null) {
                        currentIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorIn.setTarget(currentIndicator);
                            CircleIndicator.this.mAnimatorIn.start();
                        }
                    }

                    View selectedIndicator = CircleIndicator.this.getChildAt(position);
                    if(selectedIndicator != null) {
                        selectedIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorOut.setTarget(selectedIndicator);
                            CircleIndicator.this.mAnimatorOut.start();
                        }
                    }

                    CircleIndicator.this.mLastPosition = position;
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                if(CircleIndicator.this.mViewpager != null) {
                    int newCount = CircleIndicator.this.mViewpager.getAdapter().getCount();
                    int currentCount = CircleIndicator.this.getChildCount();
                    if(newCount != currentCount) {
                        if(CircleIndicator.this.mLastPosition < newCount) {
                            CircleIndicator.this.mLastPosition = CircleIndicator.this.mViewpager.getCurrentItem();
                        } else {
                            CircleIndicator.this.mLastPosition = -1;
                        }

                        CircleIndicator.this.createIndicators();
                    }
                }
            }
        };
        this.init(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAnimatorResId = R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId = R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if(CircleIndicator.this.mViewpager.getAdapter() != null && CircleIndicator.this.mViewpager.getAdapter().getCount() > 0) {
                    if(CircleIndicator.this.mAnimatorIn.isRunning()) {
                        CircleIndicator.this.mAnimatorIn.end();
                        CircleIndicator.this.mAnimatorIn.cancel();
                    }

                    if(CircleIndicator.this.mAnimatorOut.isRunning()) {
                        CircleIndicator.this.mAnimatorOut.end();
                        CircleIndicator.this.mAnimatorOut.cancel();
                    }

                    View currentIndicator;
                    if(CircleIndicator.this.mLastPosition >= 0 && (currentIndicator = CircleIndicator.this.getChildAt(CircleIndicator.this.mLastPosition)) != null) {
                        currentIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorIn.setTarget(currentIndicator);
                            CircleIndicator.this.mAnimatorIn.start();
                        }
                    }

                    View selectedIndicator = CircleIndicator.this.getChildAt(position);
                    if(selectedIndicator != null) {
                        selectedIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorOut.setTarget(selectedIndicator);
                            CircleIndicator.this.mAnimatorOut.start();
                        }
                    }

                    CircleIndicator.this.mLastPosition = position;
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                if(CircleIndicator.this.mViewpager != null) {
                    int newCount = CircleIndicator.this.mViewpager.getAdapter().getCount();
                    int currentCount = CircleIndicator.this.getChildCount();
                    if(newCount != currentCount) {
                        if(CircleIndicator.this.mLastPosition < newCount) {
                            CircleIndicator.this.mLastPosition = CircleIndicator.this.mViewpager.getCurrentItem();
                        } else {
                            CircleIndicator.this.mLastPosition = -1;
                        }

                        CircleIndicator.this.createIndicators();
                    }
                }
            }
        };
        this.init(context, attrs);
    }

    @TargetApi(21)
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mAnimatorResId = R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId = R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if(CircleIndicator.this.mViewpager.getAdapter() != null && CircleIndicator.this.mViewpager.getAdapter().getCount() > 0) {
                    if(CircleIndicator.this.mAnimatorIn.isRunning()) {
                        CircleIndicator.this.mAnimatorIn.end();
                        CircleIndicator.this.mAnimatorIn.cancel();
                    }

                    if(CircleIndicator.this.mAnimatorOut.isRunning()) {
                        CircleIndicator.this.mAnimatorOut.end();
                        CircleIndicator.this.mAnimatorOut.cancel();
                    }

                    View currentIndicator;
                    if(CircleIndicator.this.mLastPosition >= 0 && (currentIndicator = CircleIndicator.this.getChildAt(CircleIndicator.this.mLastPosition)) != null) {
                        currentIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorIn.setTarget(currentIndicator);
                            CircleIndicator.this.mAnimatorIn.start();
                        }
                    }

                    View selectedIndicator = CircleIndicator.this.getChildAt(position);
                    if(selectedIndicator != null) {
                        selectedIndicator.setBackgroundResource(CircleIndicator.this.mIndicatorBackgroundResId);
                        if(CircleIndicator.this.mUseAnimation) {
                            CircleIndicator.this.mAnimatorOut.setTarget(selectedIndicator);
                            CircleIndicator.this.mAnimatorOut.start();
                        }
                    }

                    CircleIndicator.this.mLastPosition = position;
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() {
            public void onChanged() {
                super.onChanged();
                if(CircleIndicator.this.mViewpager != null) {
                    int newCount = CircleIndicator.this.mViewpager.getAdapter().getCount();
                    int currentCount = CircleIndicator.this.getChildCount();
                    if(newCount != currentCount) {
                        if(CircleIndicator.this.mLastPosition < newCount) {
                            CircleIndicator.this.mLastPosition = CircleIndicator.this.mViewpager.getCurrentItem();
                        } else {
                            CircleIndicator.this.mLastPosition = -1;
                        }

                        CircleIndicator.this.createIndicators();
                    }
                }
            }
        };
        this.init(context, attrs);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
        this.configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin, R.animator.scale_with_alpha, 0, R.drawable.white_radius, R.drawable.white_radius);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin, @AnimatorRes int animatorId, @AnimatorRes int animatorReverseId, @DrawableRes int indicatorBackgroundId, @DrawableRes int indicatorUnselectedBackgroundId) {
        this.mIndicatorWidth = indicatorWidth;
        this.mIndicatorHeight = indicatorHeight;
        this.mIndicatorMargin = indicatorMargin;
        this.mAnimatorResId = animatorId;
        this.mAnimatorReverseResId = animatorReverseId;
        this.mIndicatorBackgroundResId = indicatorBackgroundId;
        this.mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;
        this.checkIndicatorConfig(this.getContext());
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewpager = viewPager;
        if(this.mViewpager != null && this.mViewpager.getAdapter() != null) {
            this.mLastPosition = -1;
            this.createIndicators();
            this.mViewpager.removeOnPageChangeListener(this.mInternalPageChangeListener);
            this.mViewpager.addOnPageChangeListener(this.mInternalPageChangeListener);
            this.mInternalPageChangeListener.onPageSelected(this.mViewpager.getCurrentItem());
        }

    }

    public DataSetObserver getDataSetObserver() {
        return this.mInternalDataSetObserver;
    }

    /** @deprecated */
    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if(this.mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        } else {
            this.mViewpager.removeOnPageChangeListener(onPageChangeListener);
            this.mViewpager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    public int dip2px(float dpValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    private void init(Context context, AttributeSet attrs) {
        this.handleTypedArray(context, attrs);
        this.checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
            this.mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
            this.mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
            this.mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
            this.mAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator, R.animator.scale_with_alpha);
            this.mAnimatorReverseResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);
            this.mIndicatorBackgroundResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable, R.drawable.white_radius);
            this.mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected, this.mIndicatorBackgroundResId);
            this.mUseAnimation = typedArray.getBoolean(R.styleable.CircleIndicator_ci_use_animation, true);
            int orientation = typedArray.getInt(R.styleable.CircleIndicator_ci_orientation, -1);
            this.setOrientation(orientation == 1?LinearLayout.VERTICAL:LinearLayout.HORIZONTAL);
            int gravity = typedArray.getInt(R.styleable.CircleIndicator_ci_gravity, -1);
            this.setGravity(gravity >= 0?gravity:17);
            typedArray.recycle();
        }
    }

    private void checkIndicatorConfig(Context context) {
        this.mIndicatorWidth = this.mIndicatorWidth < 0?this.dip2px(5.0F):this.mIndicatorWidth;
        this.mIndicatorHeight = this.mIndicatorHeight < 0?this.dip2px(5.0F):this.mIndicatorHeight;
        this.mIndicatorMargin = this.mIndicatorMargin < 0?this.dip2px(5.0F):this.mIndicatorMargin;
        this.mAnimatorResId = this.mAnimatorResId == 0?R.animator.scale_with_alpha:this.mAnimatorResId;
        this.mAnimatorOut = this.createAnimatorOut(context);
        this.mImmediateAnimatorOut = this.createAnimatorOut(context);
        this.mImmediateAnimatorOut.setDuration(0L);
        this.mAnimatorIn = this.createAnimatorIn(context);
        this.mImmediateAnimatorIn = this.createAnimatorIn(context);
        this.mImmediateAnimatorIn.setDuration(0L);
        this.mIndicatorBackgroundResId = this.mIndicatorBackgroundResId == 0?R.drawable.white_radius:this.mIndicatorBackgroundResId;
        this.mIndicatorUnselectedBackgroundResId = this.mIndicatorUnselectedBackgroundResId == 0?this.mIndicatorBackgroundResId:this.mIndicatorUnselectedBackgroundResId;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
    }

    private Animator createAnimatorIn(Context context) {
        Animator animatorIn;
        if(this.mAnimatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
            animatorIn.setInterpolator(new CircleIndicator.ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, this.mAnimatorReverseResId);
        }

        return animatorIn;
    }

    private void createIndicators() {
        this.removeAllViews();
        int count = this.mViewpager.getAdapter().getCount();
        if(count > 0) {
            int currentItem = this.mViewpager.getCurrentItem();
            int orientation = this.getOrientation();

            for(int i = 0; i < count; ++i) {
                if(currentItem == i) {
                    this.addIndicator(orientation, this.mIndicatorBackgroundResId, this.mImmediateAnimatorOut);
                } else {
                    this.addIndicator(orientation, this.mIndicatorUnselectedBackgroundResId, this.mImmediateAnimatorIn);
                }
            }

        }
    }

    private void addIndicator(int orientation, @DrawableRes int backgroundDrawableId, Animator animator) {
        if(animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View Indicator = new View(this.getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        this.addView(Indicator, this.mIndicatorWidth, this.mIndicatorHeight);
        LayoutParams lp = (LayoutParams)Indicator.getLayoutParams();
        if(orientation == 0) {
            lp.leftMargin = this.mIndicatorMargin;
            lp.rightMargin = this.mIndicatorMargin;
        } else {
            lp.topMargin = this.mIndicatorMargin;
            lp.bottomMargin = this.mIndicatorMargin;
        }

        Indicator.setLayoutParams(lp);
        if(this.mUseAnimation) {
            animator.setTarget(Indicator);
            animator.start();
        }

    }

    private class ReverseInterpolator implements Interpolator {
        private ReverseInterpolator() {
        }

        public float getInterpolation(float value) {
            return Math.abs(1.0F - value);
        }
    }
}

