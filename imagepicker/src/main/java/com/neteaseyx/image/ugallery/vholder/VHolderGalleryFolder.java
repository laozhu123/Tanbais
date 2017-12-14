package com.neteaseyx.image.ugallery.vholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neteaseyx.image.R;
import com.neteaseyx.image.ugallery.UGallery;
import com.neteaseyx.image.ugallery.listener.FolderSelectListener;
import com.neteaseyx.image.ugallery.model.PhotoFolderInfo;
import com.neteaseyx.image.ugallery.utils.image.ImageUtil;

/**
 * gallery选择时候的 ViewHolder
 *
 * @author yuhuibin
 * @date 2016-04-22
 */
public class VHolderGalleryFolder extends RecyclerView.ViewHolder{
    public static int LAYOUT_ID = R.layout.vholder_gallery;

    private TextView mFolderName;
    private TextView mImageNumbers;
    private ImageView mImageView;
    private LinearLayout mLinearLayout;
    public VHolderGalleryFolder(View itemView) {
        super(itemView);
        mFolderName = (TextView)itemView.findViewById(R.id.folder_name);
        mImageNumbers = (TextView)itemView.findViewById(R.id.image_numbers);
        mImageView = (ImageView) itemView.findViewById(R.id.image);
        mLinearLayout = (LinearLayout)itemView.findViewById(R.id.ly);
    }

    /**
     * 图片文件夹
     * @param folderInfo 文件夹信息
     * @param listener 选择文件夹事件处理
     */
    public void setGalleryFolder(PhotoFolderInfo folderInfo, final FolderSelectListener listener, ImageUtil imageLoader){
        mFolderName.setText(folderInfo.getFolderName());
        mImageNumbers.setText("("+folderInfo.getPhotoList().size() + ")");
        if (folderInfo.getPhotoList().size() != 0) {
            String uri = folderInfo.getCoverPhoto().getPhotoPath().getPath();
            if (mImageView.getTag() != uri){
                mImageView.setImageResource(R.drawable.ic_gf_default_photo);
            }
            mImageView.setTag(uri);
//            imageLoader.bindBitmap(uri, mImageView, 50, 50);
        }else {
            mImageView.setImageResource(UGallery.getConfig().getImagePlaceholder());
        }
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFolderSelectListener(getPosition());
            }
        });
    }
}
