package com.neteaseyx.image.ugallery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.neteaseyx.image.R;

/**
 * @author yuhuibin
 * @date 2016-06-28
 */

public class ViewNoPermission extends RelativeLayout{

    public ViewNoPermission(Context context) {
        this(context, null);
    }

    public ViewNoPermission(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public ViewNoPermission(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_no_permission, this, true);
    }
}
