package com.neteaseyx.image.ugallery.adpter;

import java.util.ArrayList;
import java.util.List;

import com.neteaseyx.image.ugallery.activity.ActivityGalleryImage;
import com.neteaseyx.image.ugallery.listener.ImageSelectListener;
import com.neteaseyx.image.ugallery.model.PhotoFolderInfo;
import com.neteaseyx.image.ugallery.model.PhotoInfo;
import com.neteaseyx.image.ugallery.utils.image.BitmapCache;
import com.neteaseyx.image.ugallery.utils.image.ImageUtil;
import com.neteaseyx.image.ugallery.utils.loader.ImageLoader;
import com.neteaseyx.image.ugallery.vholder.VHolderGalleryImage;
import com.neteaseyx.image.ugallery.vholder.VHoldrGalleryImageHead;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author yuhuibin
 * @date 2016-04-22
 */
public class AdapterGalleryImages extends RecyclerView.Adapter {

    /** 头布局 */
    private final int ITEM_TYPE_HEADER = 0;
    /** 内容 */
    private final int ITEM_TYPE_CONTENT = 1;

    private PhotoFolderInfo mFolderInfo = null;

    /** 头部View个数 */
    private int mHeaderCount = 1;
    /** 添加head布局的点击事件功能 */
    private boolean isSetClick = true;

    private ImageSelectListener mImageSelectListener;
    List<PhotoInfo> mSelectPhoto = new ArrayList<>();
    private ImageLoader mImageLoader;
    private ImageUtil mImageUtil;
    private Context mContext;
    /** 选取最大的图片数量 */
    private int mSelectMax;

    public PhotoFolderInfo getFolderInfo() {
        return mFolderInfo;
    }

    public AdapterGalleryImages(Context context, ImageSelectListener listener) {
        super();
        mContext = context;
        mImageSelectListener = listener;
        mImageLoader = ImageLoader.build(mContext);
        mImageUtil = new ImageUtil(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(VHolderGalleryImage.LAYOUT_ID_HEAD, parent, false);
            return new VHoldrGalleryImageHead(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(VHolderGalleryImage.LAYOUT_ID, parent, false);
            return new VHolderGalleryImage(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHoldrGalleryImageHead) {
            //设置是否要添加点击事件
            ((VHoldrGalleryImageHead) holder).setIsAddClickListener(isSetClick, mSelectMax);
        } else if (holder instanceof VHolderGalleryImage) {
            PhotoInfo info = mFolderInfo.getPhotoList().get(position - mHeaderCount);
            boolean select = mSelectPhoto.contains(info);
            Log.e("select", info.getPhotoPath().toString());
            if (select) {
                Log.e("select", info.getPhotoPath().toString());
            }
            int index = mSelectPhoto.indexOf(info);
            // Log.e("Commend",(index)+"");
            ((VHolderGalleryImage) holder).setImage(info, select, index, mImageSelectListener, mImageUtil);
        }
    }

    @Override
    public int getItemCount() {
        return mFolderInfo == null ? 1 : mFolderInfo.getPhotoList().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    public void setFolderInfo(PhotoFolderInfo folderInfo) {
        mFolderInfo = folderInfo;
    }

    public void setSelectPhoto(List<PhotoInfo> selectPhoto) {
        mSelectPhoto = selectPhoto;
    }

    /** 释放内存 */
    public void release() {
        mImageLoader.releaseBitmap();
        if (mContext instanceof ActivityGalleryImage) {
            ActivityGalleryImage activityGalleryImage = (ActivityGalleryImage) mContext;
            boolean isRelStr = activityGalleryImage.getIsReleaseStr();
            if (isRelStr) {
                BitmapCache.getDefault().clear(true, true);
            }
        }
    }

    /** 设置摄像头的点击事件 */
    public void setMediaClick(boolean b, int selectMax) {
        this.isSetClick = b;
        this.mSelectMax = selectMax;
    }

}
