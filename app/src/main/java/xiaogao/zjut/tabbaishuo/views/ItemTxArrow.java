package xiaogao.zjut.tabbaishuo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ItemTxArrow extends LinearLayout{
    public ItemTxArrow(Context context) {
        super(context);
    }

    public ItemTxArrow(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        getAttrs(attrs);
    }

    public ItemTxArrow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.itemTxArrow);
        String attr1 = ta.getString(R.styleable.itemTxArrow_title);
        View container = inflate(getContext(), R.layout.item_tx_arrow,this);
        TextView title = (TextView) container.findViewById(R.id.title);
        title.setText(attr1);
    }

}
