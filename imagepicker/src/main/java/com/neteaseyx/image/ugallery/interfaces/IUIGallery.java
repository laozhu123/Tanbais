package com.neteaseyx.image.ugallery.interfaces;

import com.neteaseyx.image.ugallery.model.PhotoFolderInfo;
import com.neteaseyx.image.ugallery.model.PhotoInfo;

import java.util.List;

/**
 * @author yuhuibin
 * @date 2016-06-07
 */

public interface IUIGallery extends IUIBase{
    void onChangeFolderListStatus();

    /** 下一步 */
    void onNext(List<PhotoInfo> selectImages);

    /** 初始化图库 */
    void onInitGallery(List<PhotoFolderInfo> allImage, List<PhotoInfo> selectImage);

    void toast(String string);

    /** 更新选择图像列表 */
    void onUpdateGallery(List<PhotoInfo> selectImages);

    /** 更新选择文件夹 */
    void onUpdateImageFolder(String folderName, PhotoFolderInfo folderInfo);

    /** 设置是否跳转以及选取图片的最大数目*/
    void setMeadiaClick(boolean b,int selectMax);

    /** 是否需要释放内存*/
    boolean getIsReleaseStr();

}
