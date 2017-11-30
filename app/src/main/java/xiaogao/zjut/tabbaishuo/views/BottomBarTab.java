package xiaogao.zjut.tabbaishuo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;


/**
 * Created by YoKeyword on 16/6/3.
 */
public class BottomBarTab extends LinearLayout {

    private static final int[] STATE_NORMAL = new int[0];
    private static final int[] STATE_CHECKED = {android.R.attr.state_checked};

    CheckBox mIcon;
    TextView mTx;
    private int mTabPosition = -1;
    int mUnSelectColor;
    int mSelectColor;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public BottomBarTab(Context context, @DrawableRes int iconUnSelect, @DrawableRes int iconSelected, @StringRes int text) {
        this(context, null, iconUnSelect, iconSelected, text);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public BottomBarTab(Context context, AttributeSet attrs, int iconUnSelect, int iconSelected, int text) {
        this(context, attrs, 0, iconUnSelect, iconSelected, text);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int iconUnSelected, int iconSelected, int text) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
        init(context, iconUnSelected, iconSelected, text);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init(Context context, int iconUnSelect, int iconSelected, int text) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackground});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        mIcon = new AppCompatCheckBox(context);

        int size = context.getResources().getDimensionPixelSize(R.dimen.tab_icon_size);
        mUnSelectColor = ContextCompat.getColor(context, R.color.tab_tx_unselect);
        mSelectColor = ContextCompat.getColor(context, R.color.tab_tx_select);

        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics());

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(STATE_CHECKED, ContextCompat.getDrawable(context, iconSelected));
        stateListDrawable.addState(STATE_NORMAL, ContextCompat.getDrawable(context, iconUnSelect));
        mIcon.setBackground(stateListDrawable);
        // 5.0 之前不能通过设置 null 来隐藏原有的 drawable
        mIcon.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));

        mIcon.setGravity(Gravity.CENTER);
        addView(mIcon, params);

        if (text != -1) {
            mTx = new TextView(context);
            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            mTx.setGravity(Gravity.CENTER);
            mTx.setText(text);
            mTx.setTextSize(10);
            mTx.setTextColor(mUnSelectColor);
            addView(mTx, params);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mIcon.setChecked(selected);
        if (selected) {
            mTx.setTextColor(mSelectColor);
        } else {
            mTx.setTextColor(mUnSelectColor);
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
