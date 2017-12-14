package com.neteaseyx.image.ugallery.interfaces;

import com.neteaseyx.image.ugallery.model.PhotoInfo;

import java.util.List;

/**
 * @author yuhuibin
 * @date 2016-06-07
 */

public interface IUIPreview extends IUIBase{
    void onSelectImage(String state, int position, boolean isSelect, int selectIndex, int selectImageSize);
    /** 返回数据 finish = false 只是返回*/
    void onFinish(List<PhotoInfo> photoInfos, boolean finish);
    void toast(String string);
}
