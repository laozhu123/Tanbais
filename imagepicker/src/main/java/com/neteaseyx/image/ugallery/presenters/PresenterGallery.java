package com.neteaseyx.image.ugallery.presenters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.neteaseyx.image.ugallery.activity.ActivityPreviewImage;
import com.neteaseyx.image.ugallery.interfaces.IUIGallery;
import com.neteaseyx.image.ugallery.model.PhotoFolderInfo;
import com.neteaseyx.image.ugallery.model.PhotoInfo;
import com.neteaseyx.image.ugallery.utils.PhotoTools;
import com.neteaseyx.image.ugallery.view.Utils;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 选择图像的Presenter
 *
 * @author yuhuibin
 * @date 2016-06-07
 */

public class PresenterGallery {
    //设置最多选择几张图片
    private int mMaxSelectedImageSize = 9;
    private int mSingleSelectImage = 1;
    //返回结果
    private static final int HANDLER_REFRESH_LIST_EVENT = 1002;

    List<PhotoFolderInfo> mAllFolder = new ArrayList<>();
    List<PhotoInfo> mSelectImages = new ArrayList<>();
    private int mCurrentImageFolder = 0;
    private IUIGallery mIUIGallery;
    private ScanImageHandler mHandler = new ScanImageHandler(this);

    private boolean mIsSingleImageSelect = true;

    public PresenterGallery(IUIGallery IUIGallery, boolean isSingleImageSelect, int maxSize) {
        mIUIGallery = IUIGallery;
        mIsSingleImageSelect = isSingleImageSelect;
        mMaxSelectedImageSize = maxSize;
    }

    public void changeFolder() {
        //mIUIGallery.onChangeFolderListStatus();
    }

    /**
     * 扫描本地文件夹，获得所以图片
     *
     * @param selectImage 已选择的图像
     */
    public void getImages(final List<PhotoInfo> selectImage) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i("yhb", "getImageList");
                List<PhotoFolderInfo> allFolder = PhotoTools.getAllPhotoFolder(mIUIGallery.getContext(), null);
                if (selectImage != null && selectImage.size() > 0) {
                    mSelectImages = selectImage;
                }

                if (!Utils.equalLists(allFolder, mAllFolder)) {
                    Log.i("yhb", "allFolder.equals(mAllFolder)=" + allFolder.equals(mAllFolder));
                    mAllFolder.clear();
                    mAllFolder = allFolder;
                    mHandler.sendEmptyMessageAtTime(HANDLER_REFRESH_LIST_EVENT, 100);
                }
            }
        }.start();
    }

    /**
     * 选择图像
     */
    public void imageSelectChangeState(int position) {
        PhotoInfo photoInfo = mAllFolder.get(mCurrentImageFolder).getPhotoList().get(position);
        if (mSelectImages.contains(photoInfo)) {
            mSelectImages.remove(photoInfo);
            //这里要做判断,最新的图片数量
            setISOpenCamera(mMaxSelectedImageSize);
        } else {
            if (mIsSingleImageSelect) {
                mSelectImages.clear();
                mSelectImages.add(photoInfo);
                // setISOpenCamera(mSingleSelectImage);
            } else {
                if (mSelectImages.size() >= mMaxSelectedImageSize) {
                    //在这里我们要禁用拍摄功能
                    mIUIGallery.toast("最多只能选择" + mMaxSelectedImageSize + "张图片");
                } else {
                    mSelectImages.add(photoInfo);
                    //设置是否可以开启摄像头选取图片
                    setISOpenCamera(mMaxSelectedImageSize);
                }
            }
        }
        mIUIGallery.onUpdateGallery(mSelectImages);
    }

    public void setSelectImages(List<PhotoInfo> selectImages) {
        mSelectImages = selectImages;
        mIUIGallery.onUpdateGallery(mSelectImages);
    }

    /**
     * 选择文件夹
     */
    public void imageFolderSelect(int position) {
        mCurrentImageFolder = position;
        mIUIGallery.onUpdateImageFolder(mAllFolder.get(position).getFolderName(), mAllFolder.get(position));
    }

    /**
     * 单击图像
     */
    public void imageClick(int position) {
        ActivityPreviewImage.startActivity(mIUIGallery.getContext(), mAllFolder.get(mCurrentImageFolder).getPhotoList(), mSelectImages, position,
                mIsSingleImageSelect, mMaxSelectedImageSize);
    }

    /**
     * 下一步
     */
    public void next() {
        mIUIGallery.onNext(mSelectImages);
    }

    public void destroy() {
        mSelectImages.clear();
        mAllFolder.clear();
    }

    /**
     * 将图片uri添加到已经选择的序列中,之后执行onNext方法
     */
    public void setImageUris(Uri uri) {
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setPhotoPath(uri);
        mSelectImages.add(photoInfo);
        mIUIGallery.onNext(mSelectImages);
    }

    //    private class ScanImageHandler extends Handler {
    //
    //        @Override
    //        public void handleMessage(Message msg) {
    //            super.handleMessage(msg);
    //            mIUIGallery.onInitGallery(mAllFolder, mSelectImages);
    //            mIUIGallery.onUpdateGallery(mSelectImages);
    //        }
    //    }
    private static class ScanImageHandler extends Handler {
        WeakReference<PresenterGallery> mReference;

        ScanImageHandler(PresenterGallery presenterGallery) {
            mReference = new WeakReference<>(presenterGallery);
        }

        @Override
        public void handleMessage(Message msg) {
            PresenterGallery presenterGallery = mReference.get();
            if (presenterGallery != null) {
                presenterGallery.mIUIGallery.onInitGallery(presenterGallery.mAllFolder, presenterGallery.mSelectImages);
                presenterGallery.mIUIGallery.onUpdateGallery(presenterGallery.mSelectImages);
            }

        }
    }

    private void setISOpenCamera(int number) {
        if (mSelectImages.size() >= number) {
            mIUIGallery.setMeadiaClick(false, number);
        } else {
            mIUIGallery.setMeadiaClick(true, number);
        }
    }
}
