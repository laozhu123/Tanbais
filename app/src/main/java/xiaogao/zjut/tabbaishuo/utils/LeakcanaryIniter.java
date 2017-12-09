package xiaogao.zjut.tabbaishuo.utils;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/12/10.
 */

public class LeakcanaryIniter {
    public static void init(Context pContext) {
        if (LeakCanary.isInAnalyzerProcess(pContext)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install((Application) pContext);
    }
}
