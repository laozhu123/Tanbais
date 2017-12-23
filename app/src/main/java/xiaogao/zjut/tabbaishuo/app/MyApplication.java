package xiaogao.zjut.tabbaishuo.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import okhttp3.Interceptor;
import xgn.com.basesdk.base.app.CoreApplication;
import xgn.com.basesdk.network.XgNetWork;
import xgn.com.basesdk.network.interfaces.INetExternalParams;
import xiaogao.zjut.tabbaishuo.BuildConfig;
import xiaogao.zjut.tabbaishuo.StethoIniter;
import xiaogao.zjut.tabbaishuo.injecter.AppModule;
import xiaogao.zjut.tabbaishuo.injecter.component.AppComponent;
import xiaogao.zjut.tabbaishuo.injecter.component.DaggerAppComponent;
import xiaogao.zjut.tabbaishuo.net.TokenInterceptor;
import xiaogao.zjut.tabbaishuo.utils.CrashHandler;
import xiaogao.zjut.tabbaishuo.utils.LeakcanaryIniter;

/**
 * Created by Administrator on 2017/8/9.
 */

public class MyApplication extends CoreApplication implements Application.ActivityLifecycleCallbacks {

    private AppComponent mAppComponent;
    public static MyApplication mInstance;

    //当前Activity的弱引用
    private WeakReference<Activity> mActivityReference;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        registerActivityLifecycleCallbacks(this);
        setupMyGraph();
        inject();
        initNetWork();
        RongIM.init(this);
        initCrashHandlerAndLeak();


    }

    private void initCrashHandlerAndLeak() {
        if (BuildConfig.DEBUG == true) {
            CrashHandler.getInstance().init();
            LeakcanaryIniter.init(this);
        }
    }

    public Activity getCurrentActivity() {
        if (mActivityReference != null) {
            return mActivityReference.get();
        }
        return null;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityReference = new WeakReference<>(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }


    @Override
    public void onActivityResumed(Activity activity) {
        mActivityReference = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    private void initNetWork() {
        List<Interceptor> intercepteors = new ArrayList<>();
        List<Interceptor> networkIntercepteors = new ArrayList<>();
//        intercepteors.add(new TokenInterceptor());
        StethoIniter.init(this);
        if (StethoIniter.getInterptor() != null) {
            networkIntercepteors.add(StethoIniter.getInterptor());
        }
        XgNetWork build = new XgNetWork.Builder(this).externalParams(new INetExternalParams() {
            @Override
            public String getUserId() {
                return null;
            }

            @Override
            public String getAppVersion() {
                return null;
            }

            @Override
            public boolean isRelease() {
                return BuildConfig.BUILD_TYPE.equals("release");
            }

            @NonNull
            @Override
            public String httpHost() {
                return "http://172.16.1.180:8005/";  //主域名
            }

            @NonNull
            @Override
            public String httpSecondHost() {
                return "http://sjyxtest.yiqiguang.com/tbbuser/";  //第二个域名
            }

            @NonNull
            @Override
            public String mockHost() {
                return "http://172.16.1.15:8068/mockjsdata/9/";  //mock域名
            }

            @Override
            public int connectTimeOut() {
                return 0;
            }
        })
                .extraHeaders(null)
                .interceptors(intercepteors)
                .networkInterceptors(networkIntercepteors)
                .build();
        XgNetWork.init(build);
    }

    private void inject() {
        mAppComponent.inject(this);
    }

    private void setupMyGraph() {
        mAppComponent = DaggerAppComponent.builder()
                .coreAppComponent(mCoreComponent)
                .appModule(new AppModule(this)).build();
    }

    public static MyApplication getMyAppInstance() {
        return mInstance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static String getToken() {
        return "token";
    }

}
