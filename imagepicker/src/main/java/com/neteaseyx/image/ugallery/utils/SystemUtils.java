package com.neteaseyx.image.ugallery.utils;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Yefeng on 2017/6/16.
 * Modified by xxx
 */

public class SystemUtils {

    /**
     * 获取当前应用程序的包名
     * @return 返回包名
     */
    public static String getAppProcessName() {
        Context context = AppContextUtils.getContext();
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
}
