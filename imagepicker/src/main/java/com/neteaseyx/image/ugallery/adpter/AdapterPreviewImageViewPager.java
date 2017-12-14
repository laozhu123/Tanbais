package com.neteaseyx.image.ugallery.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.neteaseyx.image.imageview.toolbox.GestureBaseImageView;
import com.neteaseyx.image.ugallery.activity.ActivityPreviewImage;
import com.neteaseyx.image.ugallery.model.PhotoInfo;

import java.util.List;

/**
 * 预览,ViewPager的Adapter
 *
 * @author yuhuibin
 * @date 2016-05-07
 */
public class AdapterPreviewImageViewPager extends PagerAdapter {
    private Context mContext;

    private List<PhotoInfo> mPhotoInfos;

    public AdapterPreviewImageViewPager(Context context, List<PhotoInfo> photoInfos) {
        mPhotoInfos = photoInfos;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GestureBaseImageView gestureBaseImageView = new GestureBaseImageView(mContext);
        gestureBaseImageView.setImageUri(mPhotoInfos.get(position).getPhotoPath().toString());
        (container).addView(gestureBaseImageView);
        gestureBaseImageView.setId(position);
        gestureBaseImageView.setRotateEnabled(false);
        gestureBaseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发生单次点击时更新界面ui
                ActivityPreviewImage activityPreviewImage = (ActivityPreviewImage) mContext;
                activityPreviewImage.showAnimation();
            }
        });
        return gestureBaseImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return mPhotoInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
