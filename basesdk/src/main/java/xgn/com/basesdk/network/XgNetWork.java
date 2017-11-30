package xgn.com.basesdk.network;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import xgn.com.basesdk.network.interfaces.INetExternalParams;

/**
 * Created by huluzi on 2017/8/9.
 */

public class XgNetWork {
    private static XgNetWork sNetwork;
    private final Map<String, String> mExtraHeaders;
    private final INetExternalParams mExternalParams;
    private final List<Interceptor> networkInterceptors;
    private final List<Interceptor> interceptors;
    public final Context mApplicationContext;

    private XgNetWork(Context context, INetExternalParams params, Map<String, String> extraHeaders, List<Interceptor> networkInterceptors, List<Interceptor> interceptors) {
        this.mApplicationContext = context;
        this.mExternalParams = params;
        this.mExtraHeaders = extraHeaders;
        this.networkInterceptors = networkInterceptors;
        this.interceptors = interceptors;
    }

    public static void init(XgNetWork xgNetwork) {
        if(xgNetwork == null) {
            throw new RuntimeException("Please using XKNetwork.Builder(context).build() to init XKNetwork");
        } else {
            sNetwork = xgNetwork;
        }
    }

    public static XgNetWork get() {
        if(sNetwork == null) {
            throw new RuntimeException("Please using XgNetWork.init() in Application first");
        } else {
            return sNetwork;
        }
    }

    public INetExternalParams externalParams() {
        return this.mExternalParams;
    }

    public List<Interceptor> networkInterceptors() {
        return this.networkInterceptors;
    }

    public List<Interceptor> interceptors() {
        return this.interceptors;
    }

    public Map<String, String> headers() {
        HashMap map = new HashMap();
        map.put("os", "android");
        map.put("version", this.mExternalParams.getAppVersion());
        String userId = this.mExternalParams.getUserId();
        if(!TextUtils.isEmpty(userId)) {
            map.put("userId", userId);
        }

        map.put("User-Agent", "(" + Build.MODEL + ";Android " + Build.VERSION.RELEASE + ";) deviceID/");
        if(this.mExtraHeaders != null) {
            map.putAll(this.mExtraHeaders);
        }

        return map;
    }

    private static void checkNotNull(Object object, String err) {
        if(object == null) {
            throw new IllegalArgumentException(err + " can not be null !");
        }
    }

    public static final class Builder {
        private Context context;
        private Map<String, String> extraHeaders;
        private INetExternalParams networkParams;
        private List<Interceptor> networkInterceptors;
        private List<Interceptor> interceptors;
        private int timeOut;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public XgNetWork.Builder extraHeaders(Map<String, String> extraHeaders) {
            this.extraHeaders = extraHeaders;
            return this;
        }

        public XgNetWork.Builder externalParams(INetExternalParams params) {
            this.networkParams = params;
            return this;
        }

        public XgNetWork.Builder networkInterceptors(List<Interceptor> networkInterceptors) {
            this.networkInterceptors = networkInterceptors;
            return this;
        }

        public XgNetWork.Builder interceptors(List<Interceptor> interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public XgNetWork build() {
            XgNetWork.checkNotNull(this.context, "context");
            XgNetWork.checkNotNull(this.networkParams, "INetExternalParams");
            if(this.interceptors == null) {
                this.interceptors = new ArrayList();
            }

            if(this.networkInterceptors == null) {
                this.networkInterceptors = new ArrayList();
            }

            return new XgNetWork(this.context, this.networkParams, this.extraHeaders, this.networkInterceptors, this.interceptors);
        }
    }
}
