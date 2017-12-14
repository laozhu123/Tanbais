/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.neteaseyx.image.ugallery.model;

import android.net.Uri;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Desction:图片信息
 * Author:pengjianbo
 * Date:15/7/30 上午11:23
 */
public class PhotoInfo implements Serializable {

//    private int photoId;
    private String photoPath;

    public PhotoInfo() {}



    public Uri getPhotoPath() {
        return Uri.parse(photoPath);
    }

    public void setPhotoPath(Uri photoPath) {
        this.photoPath = photoPath.toString();
    }

//    public int getPhotoId() {
//        return photoId;
//    }
//
//    public void setPhotoId(int photoId) {
//        this.photoId = photoId;
//    }
    //
    //public String getThumbPath() {
    //    return thumbPath;
    //}
    //
    //public void setThumbPath(String thumbPath) {
    //    this.thumbPath = thumbPath;
    //}


    @Override
    public boolean equals(Object o) {
        if ( o == null || !(o instanceof PhotoInfo)) {
            return false;
        }
        PhotoInfo info = (PhotoInfo) o;
        if (info == null) {
            return false;
        }

        return TextUtils.equals(info.getPhotoPath().toString(), getPhotoPath().toString());
    }
}
