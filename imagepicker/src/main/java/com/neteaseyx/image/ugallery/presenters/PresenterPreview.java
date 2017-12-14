package com.neteaseyx.image.ugallery.presenters;


import com.neteaseyx.image.ugallery.interfaces.IUIPreview;
import com.neteaseyx.image.ugallery.model.PhotoInfo;

import java.util.List;


/**
 * 预览页面的Presenter
 * @author yuhuibin
 * @date 2016-06-07
 */

public class PresenterPreview {

    private IUIPreview mIUIPreview;
    private List<PhotoInfo> mSelectPhotos;

    private List<PhotoInfo> mCurrentFolderPhotos;

    private int mPosition;
    private boolean mIsSingleImageSelct;

    private int mMaxSelectedImageSize;
    public PresenterPreview(IUIPreview IUIPreview, List<PhotoInfo> selectPhotos, List<PhotoInfo> currentFolderPhotos, boolean isSingleImageSelect, int maxSize) {
        mIUIPreview = IUIPreview;
        mSelectPhotos = selectPhotos;
        mCurrentFolderPhotos = currentFolderPhotos;
        mIsSingleImageSelct = isSingleImageSelect;
        mMaxSelectedImageSize = maxSize;
    }

    /** 选择图像*/
    public void imageSelectChangeState(){
        PhotoInfo photoInfo = mCurrentFolderPhotos.get(mPosition);
        if (mSelectPhotos.contains(photoInfo)){
            mSelectPhotos.remove(photoInfo);
        }else {
            if (mIsSingleImageSelct){
                mSelectPhotos.clear();
                mSelectPhotos.add(photoInfo);
            }else {
                if (mSelectPhotos.size() >= mMaxSelectedImageSize) {
                    mIUIPreview.toast("最多只能选择" + mMaxSelectedImageSize + "张图片");
                }
                else {
                    mSelectPhotos.add(photoInfo);
                }
            }
        }

        imageScrollChangeState(mPosition);

    }

    /** 更新ViewPager状态*/
    public void imageScrollChangeState(int position){
        mPosition = position;
        boolean isSelect = mSelectPhotos.contains(mCurrentFolderPhotos.get(position));
        int index = mSelectPhotos.indexOf(mCurrentFolderPhotos.get(position));
        mIUIPreview.onSelectImage((position+1)+"/"+mCurrentFolderPhotos.size(), position, isSelect, index, mSelectPhotos.size());
    }

    /** 选择完图像,返回数据 */
    public void selectFinish(boolean finish){
        if(finish){// 如果点击下一步，才会默认将照片选择
            if (mSelectPhotos.size() == 0){
                mSelectPhotos.add(mCurrentFolderPhotos.get(mPosition));
            }
        }
        mIUIPreview.onFinish(mSelectPhotos, finish);
    }
}
