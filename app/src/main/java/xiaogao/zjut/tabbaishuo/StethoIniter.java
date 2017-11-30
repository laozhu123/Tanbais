package xiaogao.zjut.tabbaishuo;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.Interceptor;

/**
 * Created by huluzi on 2017/8/11.
 */

public class StethoIniter {
    public static void init(Context pContext) {
        Stetho.initializeWithDefaults(pContext);
    }

    public static Interceptor getInterptor(){
        return new StethoInterceptor();
    }
}
