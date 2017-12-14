package com.neteaseyx.image.ugallery.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neteaseyx.image.ugallery.listener.FolderSelectListener;
import com.neteaseyx.image.ugallery.model.PhotoFolderInfo;
import com.neteaseyx.image.ugallery.utils.image.ImageUtil;
import com.neteaseyx.image.ugallery.vholder.VHolderGalleryFolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 图片文件夹的适配器
 *
 * @author yuhuibin
 * @date 2016-04-22
 */
public class AdapterGalleryFolder extends RecyclerView.Adapter {
    List<PhotoFolderInfo> mAllFolder = new ArrayList<>();
    private Context mContext;

    private FolderSelectListener mFolderSelectListener;
    private ImageUtil mImageLoader;

    public AdapterGalleryFolder(Context context) {
        mContext = context;
        mImageLoader = new ImageUtil(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(VHolderGalleryFolder.LAYOUT_ID, parent, false);
        return new VHolderGalleryFolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VHolderGalleryFolder vholder = (VHolderGalleryFolder) holder;
        vholder.setGalleryFolder(mAllFolder.get(position), mFolderSelectListener, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mAllFolder.size();
    }

    public void setFolderSelectListener(FolderSelectListener folderSelectListener) {
        mFolderSelectListener = folderSelectListener;
    }

    public void setAllFolder(List<PhotoFolderInfo> allFolder) {
        mAllFolder = allFolder;
    }
}
