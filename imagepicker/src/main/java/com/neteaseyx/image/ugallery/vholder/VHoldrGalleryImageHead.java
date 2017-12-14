package com.neteaseyx.image.ugallery.vholder;

import com.neteaseyx.image.R;
import com.neteaseyx.image.ugallery.UGallery;
import com.neteaseyx.image.ugallery.activity.ActivityTakePhotos;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by kangle on 2016/11/9
 * Modified by xxx
 */
public class VHoldrGalleryImageHead extends RecyclerView.ViewHolder {

    private int mHeight = 0;
    private int mImageNumbers = UGallery.getConfig().getGalleryColumnSize();
    private final ImageView ivImage;
    private final Context mContext;
    private final Activity mActivity;
    //  private final RelativeLayout mImageRoot;

    public VHoldrGalleryImageHead(View itemView) {
        super(itemView);
        mActivity = getRequiredActivity(itemView);
        mContext = itemView.getContext();
        ivImage = (ImageView) itemView.findViewById(R.id.image);
        if (mHeight == 0) {
            DisplayMetrics dm = itemView.getContext().getResources().getDisplayMetrics();
            mHeight = (dm.widthPixels - 18) / mImageNumbers;
        }

        ViewGroup.LayoutParams params = ivImage.getLayoutParams();
        params.height = mHeight;
        ivImage.setLayoutParams(params);

        if (UGallery.getConfig().getCameraImage() == 0) {
            ivImage.setBackgroundResource(R.drawable.selector_vholder_image_gallery_head);
        } else {
            ivImage.setImageResource(UGallery.getConfig().getCameraImage());
        }

        if (UGallery.getConfig().getImageBackGround() == 0) {
            ivImage.setBackgroundResource(UGallery.getConfig().getImageBackGround());
        }
    }

    public void setIsAddClickListener(final boolean setClick, final int mMaxSelectedImageSize) {

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setClick) {
                        //跳转到拍摄视频页面
                        ActivityTakePhotos.startActivityForTakePhoto(mActivity,false);
                } else {
                    Toast.makeText(mContext, "最多只能选择" + mMaxSelectedImageSize + "张图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Activity getRequiredActivity(View req_view) {
        Context context = req_view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
