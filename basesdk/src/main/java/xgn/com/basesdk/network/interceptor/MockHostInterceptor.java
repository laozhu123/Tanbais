package xgn.com.basesdk.network.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import xgn.com.basesdk.network.XgNetWork;
import xgn.com.basesdk.network.interfaces.INetExternalParams;

/**
 * Created by huluzi on 2017/8/9.
 */

public class MockHostInterceptor implements Interceptor {
    private final INetExternalParams mParams = XgNetWork.get().externalParams();

    public MockHostInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        try {
            if(this.needChange2Mock(request) && !this.mParams.isRelease()) {
                HttpUrl changeUrl = HttpUrl.parse(this.mParams.mockHost());
                List originPaths = request.url().pathSegments();
                List mockPaths = changeUrl.pathSegments();
                HttpUrl.Builder builder = request.url().newBuilder().scheme(changeUrl.scheme()).host(changeUrl.host()).port(changeUrl.port());
                if(originPaths != null && originPaths.size() > 0) {
                    int newUrl = originPaths.size();

                    for(int path = 0; path < newUrl; ++path) {
                        builder.removePathSegment(0);
                    }
                }

                Iterator var10 = mockPaths.iterator();

                String var12;
                while(var10.hasNext()) {
                    var12 = (String)var10.next();
                    builder.addPathSegments(var12);
                }

                var10 = originPaths.iterator();

                while(var10.hasNext()) {
                    var12 = (String)var10.next();
                    builder.addPathSegments(var12);
                }

                HttpUrl var11 = builder.build();
                request = request.newBuilder().url(var11).build();
            }
        } catch (Exception var9) {
            ;
        }

        return chain.proceed(request);
    }

    private boolean needChange2Mock(Request request) {
        return !TextUtils.isEmpty(request.header("mock"));
    }
}
