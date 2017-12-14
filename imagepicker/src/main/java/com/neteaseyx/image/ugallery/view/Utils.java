package com.neteaseyx.image.ugallery.view;

import android.content.Context;

import com.neteaseyx.image.ugallery.model.PhotoFolderInfo;
import com.neteaseyx.image.ugallery.model.PhotoInfo;

import java.util.List;

/**
 * Created by hzwangyufei on 2015/9/16.
 */
public class Utils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue / scale + 0.5f);
    }


    public  static boolean equalLists(List<PhotoFolderInfo> one, List<PhotoFolderInfo> two){
        if (one == null && two == null){
            return true;
        }

        if((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()){
            return false;
        }


        PhotoFolderInfo twoFolder = two.get(0);
        PhotoFolderInfo oneFolder = one.get(0);

        List<PhotoInfo> oneList = oneFolder.getPhotoList();
        List<PhotoInfo> twoList = twoFolder.getPhotoList();

        if (oneList.size() != twoList.size()){
            return false;
        }else {
            for (int i=0; i<oneList.size(); i++){
                if (!oneList.get(i).getPhotoPath().equals(twoList.get(i).getPhotoPath())){
                    return false;
                }
            }
        }

        return true;
    }
}
