package com.neteaseyx.image.ugallery.vholder;

import com.neteaseyx.image.R;
import com.neteaseyx.image.ugallery.UGallery;
import com.neteaseyx.image.ugallery.listener.ImageSelectListener;
import com.neteaseyx.image.ugallery.model.PhotoInfo;
import com.neteaseyx.image.ugallery.utils.image.ImageUtil;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 显示图的ViewHolder
 * @author yuhuibin
 * @date 2016-04-22
 */
public class VHolderGalleryImage extends RecyclerView.ViewHolder{
    public static int LAYOUT_ID = R.layout.vholder_gallery_image;
    public static int LAYOUT_ID_HEAD = R.layout.vholder_gallery_image_head;
    private static int mHeight = 0;
    private ImageView mImageView;
    private LinearLayout mBtnLayout;
    private Context mContext;
    private TextView mSelect;
    private RelativeLayout mOverSizeRL;

    public VHolderGalleryImage(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mImageView = (ImageView) itemView.findViewById(R.id.image);
        mBtnLayout = (LinearLayout) itemView.findViewById(R.id.btn_seletct_layout);
        RelativeLayout rootLayout = (RelativeLayout) itemView.findViewById(R.id.ly);

        if (mHeight == 0){
            DisplayMetrics dm = itemView.getContext().getResources().getDisplayMetrics();
            int imageNumbers = UGallery.getConfig().getGalleryColumnSize();
            mHeight = (dm.widthPixels -18) / imageNumbers;
        }
        rootLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight));
        mSelect = (TextView) itemView.findViewById(R.id.btn_seletct);
        mOverSizeRL = (RelativeLayout) itemView.findViewById(R.id.over_size_layout);
    }

    /**
     *
     * @param info 要显示的图片
     * @param isSelect 更新选中状态
     * @param listener 选中事件处理
     */
    public void setImage(final PhotoInfo info, boolean isSelect, int index, final ImageSelectListener listener, ImageUtil imageLoader){

        String uri = info.getPhotoPath().getPath();
        if (mImageView.getTag() != uri){
            mImageView.setImageResource(UGallery.getConfig().getImagePlaceholder());
        }
        mImageView.setTag(uri);
//        imageLoader.bindBitmap(uri, mImageView, mHeight, mHeight);

        imageLoader.setImageUrl(mImageView,uri,  mHeight, mHeight, true);
        //超过最大尺寸提示不能选择
        if (imageLoader.imageIsOverSize(mImageView.getTag().toString())){
            mOverSizeRL.setVisibility(View.VISIBLE);
            mSelect.setVisibility(View.GONE);
            mImageView.setEnabled(false);
            mBtnLayout.setEnabled(false);
        }else {
            mOverSizeRL.setVisibility(View.GONE);
            mSelect.setVisibility(View.VISIBLE);
            mImageView.setEnabled(true);
            mBtnLayout.setEnabled(true);
        }

        mBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageSelectListener(getPosition()-1);
            }
        });
//        mSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onImageSelectListener(getPosition());
//            }
//        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageClickListener(getPosition()-1);
            }
        });

        if(UGallery.getConfig().getImageSelectNumIsShow()) {
            mSelect.setVisibility(View.VISIBLE);
            if (index == -1){
                mSelect.setText("");
            }else {
                mSelect.setText(index+1+"");
            }
        }else {
            if (index == -1) {
                mSelect.setText("");
            }
        }

        if(isSelect){
            if (UGallery.getConfig().getGalleryImageSelectImage() == 0 ){
                mSelect.setBackground(ContextCompat.getDrawable(mContext,R.drawable.image_icon_selected));
            }else {
                mSelect.setBackground(ContextCompat.getDrawable(mContext,UGallery.getConfig().getGalleryImageSelectImage()));
            }

        }else {
            if (UGallery.getConfig().getGalleryImageUnSelectImage() == 0 ){
                mSelect.setBackground(ContextCompat.getDrawable(mContext,R.drawable.unselect_icon_iv));
            }else {
                mSelect.setBackground(ContextCompat.getDrawable(mContext,UGallery.getConfig().getGalleryImageUnSelectImage()));
            }
        }
    }
}