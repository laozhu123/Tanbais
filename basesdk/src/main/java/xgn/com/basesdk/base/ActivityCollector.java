package xgn.com.basesdk.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 *
 * 保存所有打开的 Activity ，用于在注销时关闭所有打开的 Activity
 */

public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }
}
