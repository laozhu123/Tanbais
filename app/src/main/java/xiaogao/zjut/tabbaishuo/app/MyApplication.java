package xiaogao.zjut.tabbaishuo.app;

import android.support.annotation.NonNull;

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

/**
 * Created by Administrator on 2017/8/9.
 */

public class MyApplication extends CoreApplication {

    private AppComponent mAppComponent;
    public static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        setupMyGraph();
        inject();
        initNetWork();
        RongIM.init(this);

    }

    private void initNetWork() {
        List<Interceptor> intercepteors = new ArrayList<>();
        List<Interceptor> networkIntercepteors = new ArrayList<>();
        intercepteors.add(new TokenInterceptor());
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

    public static String getToken(){
        return "token";
    }

}
