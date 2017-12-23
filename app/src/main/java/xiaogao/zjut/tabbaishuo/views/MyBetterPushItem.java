package xiaogao.zjut.tabbaishuo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import xiaogao.zjut.tabbaishuo.R;

/**
 * Created by Administrator on 2017/12/21.
 */

public class MyBetterPushItem extends LinearLayout {
    public MyBetterPushItem(Context context) {
        super(context);
    }

    public MyBetterPushItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
    }

    public MyBetterPushItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.myBetterPushItem);
        String title = ta.getString(R.styleable.myBetterPushItem_title1);
        String content = ta.getString(R.styleable.myBetterPushItem_content);
        Drawable drawable = ta.getDrawable(R.styleable.myBetterPushItem_img);

        View container = inflate(getContext(), R.layout.my_better_push_item, this);
        TextView tvTitle = (TextView) container.findViewById(R.id.title);
        TextView tvContent = (TextView) container.findViewById(R.id.content);
        ImageView img = (ImageView) container.findViewById(R.id.img);
        View bg = container.findViewById(R.id.bg);

        tvTitle.setText(title);
        tvContent.setText(content);
        img.setImageDrawable(drawable);
    }
}
