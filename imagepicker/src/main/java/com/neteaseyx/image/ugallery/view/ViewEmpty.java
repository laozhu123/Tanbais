package com.neteaseyx.image.ugallery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neteaseyx.image.R;

/**
 * @author yuhuibin
 * @date 2016-06-28
 */

public class ViewEmpty extends RelativeLayout{

    public ViewEmpty(Context context) {
        this(context, null);
    }

    public ViewEmpty(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public ViewEmpty(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_empty, this, true);
        init();
    }

    private TextView mHint;
    private TextView mSubHint;
    private ImageView mImageView;
    private void init(){
        mHint = (TextView)findViewById(R.id.hint);
        mSubHint = (TextView)findViewById(R.id.sub_hint);
        mImageView = (ImageView)findViewById(R.id.empty_image);
    }

    /** 空状态下的提示文字*/
    public void setHintText(String string){
        mHint.setText(string);
    }

    /** 空状态下的补充提示文字*/
    public void setSubHintText(String string){
        mSubHint.setText(string);
    }

    /**空状态下的提示图像 */
    public void setImageView(int id){
        mImageView.setImageResource(id);
    }
}
