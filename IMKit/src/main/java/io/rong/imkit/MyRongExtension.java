package io.rong.imkit;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by Administrator on 2017/12/1.
 */

public class MyRongExtension extends RongExtension {
    public MyRongExtension(Context context) {
        super(context);
        this.setBackgroundColor(this.getContext().getResources().getColor(R.color.white));
    }

    public MyRongExtension(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(this.getContext().getResources().getColor(R.color.white));
    }


}
