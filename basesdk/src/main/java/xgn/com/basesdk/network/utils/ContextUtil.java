package xgn.com.basesdk.network.utils;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by huluzi on 2017/7/10.
 */

public class ContextUtil {
    private static Context CONTEXT_INSTANCE;

    public ContextUtil() {
    }

    public static Context getContext() {
        if(CONTEXT_INSTANCE == null) {
            Class var0 = ContextUtil.class;
            synchronized(ContextUtil.class) {
                if(CONTEXT_INSTANCE == null) {
                    try {
                        Class e = Class.forName("android.app.ActivityThread");
                        Method method = e.getMethod("currentActivityThread", new Class[0]);
                        Object currentActivityThread = method.invoke(e, new Object[0]);
                        Method method2 = currentActivityThread.getClass().getMethod("getApplication", new Class[0]);
                        CONTEXT_INSTANCE = (Context)method2.invoke(currentActivityThread, new Object[0]);
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }
                }
            }
        }

        return CONTEXT_INSTANCE;
    }

    private static Application getApplicationUsingReflection() {
        try {
            return (Application)Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke((Object)null, (Object[])null);
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }
}
