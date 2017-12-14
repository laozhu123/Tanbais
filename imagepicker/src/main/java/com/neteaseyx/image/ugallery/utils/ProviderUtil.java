package com.neteaseyx.image.ugallery.utils;

/**
 * 用于解决provider冲突的util
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-03-22  18:55
 */

public class ProviderUtil {

//    public static String getFileProviderName(Context context){
//        return context.getPackageName()+".provider";
//    }
    public static String getFileProviderName(){
        return SystemUtils.getAppProcessName()+".provider";
    }
}
