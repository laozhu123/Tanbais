package xgn.com.basesdk.network;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import xgn.com.basesdk.network.converter.CommonConverterFactory;
import xgn.com.basesdk.network.interceptor.HostSelectionInterceptor;
import xgn.com.basesdk.network.interceptor.MockHostInterceptor;
import xgn.com.basesdk.network.interfaces.INetExternalParams;

/**
 * Created by huluzi on 2017/8/9.
 */

public class XGRest {
    private final Retrofit mRetrofit;
    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;

    public XGRest() {
        INetExternalParams networkParams = XgNetWork.get().externalParams();
        OkHttpClient.Builder builder = generateDefaultOkHttpBuilder(networkParams.connectTimeOut());
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        HostSelectionInterceptor hostSelectionInterceptor = new HostSelectionInterceptor();
//        MockHostInterceptor mockHostInterceptor = new MockHostInterceptor();
//        if(networkParams.isRelease()) {
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        } else {
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        }

//        builder.addInterceptor(hostSelectionInterceptor).addInterceptor(mockHostInterceptor).addNetworkInterceptor(loggingInterceptor);
        List interceptorList = XgNetWork.get().interceptors();
        Iterator networkInterceptorsList = interceptorList.iterator();

        while(networkInterceptorsList.hasNext()) {
            Interceptor client = (Interceptor)networkInterceptorsList.next();
            builder.addInterceptor(client);
        }

        List networkInterceptorsList1 = XgNetWork.get().networkInterceptors();
        Iterator client1 = networkInterceptorsList1.iterator();

        while(client1.hasNext()) {
            Interceptor interceptor = (Interceptor)client1.next();
            builder.addNetworkInterceptor(interceptor);
        }

        OkHttpClient client2 = builder.build();
        this.mRetrofit = (new retrofit2.Retrofit.Builder())
                .baseUrl(networkParams.httpHost())
                .addConverterFactory(CommonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client2)
                .validateEagerly(true)
                .build();
    }

    public <T> T create(Class<T> service) {
        return this.mRetrofit.create(service);
    }

    private static Cache getCache() {
        File cacheFile = new File(XgNetWork.get().mApplicationContext.getExternalCacheDir().toString(), "xghl/reponse");
        int cacheSize = 104857600;
        return new Cache(cacheFile, (long)cacheSize);
    }

    private static OkHttpClient.Builder generateDefaultOkHttpBuilder(int timeOut) {
        return (new OkHttpClient.Builder()).writeTimeout(timeOut == 0?15L:(long)timeOut, TimeUnit.SECONDS).connectTimeout(timeOut == 0?15L:(long)timeOut, TimeUnit.SECONDS).readTimeout(timeOut == 0?15L:(long)timeOut, TimeUnit.SECONDS);
    }
}
