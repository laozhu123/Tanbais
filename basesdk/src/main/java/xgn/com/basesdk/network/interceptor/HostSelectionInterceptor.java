package xgn.com.basesdk.network.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import xgn.com.basesdk.network.XgNetWork;
import xgn.com.basesdk.network.interfaces.INetExternalParams;

/**
 * Created by huluzi on 2017/8/9.
 */

public class HostSelectionInterceptor implements Interceptor {
    private final INetExternalParams mParams = XgNetWork.get().externalParams();

    public HostSelectionInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(this.needChangeHost(request)) {
            HttpUrl changeUrl = HttpUrl.parse(this.mParams.httpSecondHost());
            HttpUrl.Builder builder = request.url().newBuilder().scheme(changeUrl.scheme()).host(changeUrl.host()).port(changeUrl.port());
            if(changeUrl.pathSegments() != null && changeUrl.pathSegments().size() > 0) {
                String newUrl = (String)changeUrl.pathSegments().get(0);
                if(!TextUtils.isEmpty(newUrl)) {
                    builder.setPathSegment(0, newUrl);
                }
            }

            HttpUrl newUrl1 = builder.build();
            request = request.newBuilder().url(newUrl1).build();
        }

        return chain.proceed(request);
    }

    private boolean needChangeHost(Request request) {
        return !TextUtils.isEmpty(request.header("change"));
    }
}