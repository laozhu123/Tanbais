package xiaogao.zjut.tabbaishuo.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import xgn.com.basesdk.network.model.BaseResponse;
import xiaogao.zjut.tabbaishuo.app.Servers;
import xiaogao.zjut.tabbaishuo.db.LoginAccountManager;
import xiaogao.zjut.tabbaishuo.net.requests.LoginRequest;
import xiaogao.zjut.tabbaishuo.utils.AESUtils;

/**
 * Created by huluzi on 2017/8/11.
 */

public class TokenInterceptor implements Interceptor {

    //    private static String format = "\"{\"password\":\"%s\", \"phone\":\"%s\"}\"";
    public static final String TOKEN_INVALID = "101021";
    private Gson mGson = new Gson();
    //    public static final String ACCOUNT_FROZEN = "101011";
    private volatile String mToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        /**
         * 改方法会耗尽输出流、故只能执行一次
         */
        String bodyString = response.body().string();

        BaseResponse baseResponse = mGson.fromJson(bodyString, BaseResponse.class);
        if (baseResponse != null) {
            // Token 过期
            if (TOKEN_INVALID.equals(baseResponse.resultCode)) {
                Log.d("helo", "invalid");
                // 再次进行网络一模一样的网路请求
                boolean flag = handleReLogin(chain);
                if (flag) {
                    request = request.newBuilder()
                            .header("Authorization", mToken)
                            .build();
                    response = chain.proceed(request);
                } else {
                    response = duplicate(response, bodyString);
                }
            } else {
                response = duplicate(response, bodyString);
            }
        } else {
            response = duplicate(response, bodyString);
        }

        return response;
    }


    /**
     * 执行重新登陆的网路请求
     * 从 用户中心数据LoginAccountManager中去取得 用户名密码
     */
    public boolean handleReLogin(Chain chain) throws IOException {

        Request request = provideLoginRequest();
        Response response = chain.proceed(request);

        /**
         * gson解析以后 object默认实例是LinkedTreeMap
         */
        return handleParsedToken(response);
    }

    /**
     * 构造重登陆的request 请求
     */
    public Request provideLoginRequest() {
//        final String format = "{\"password\":\"%s\", \"phone\":\"%s\"}";
        final LoginRequest loginRequest = new LoginRequest();
        final String phone = LoginAccountManager.getInstance().getCurrentAccount().account;
        final String password = LoginAccountManager.getInstance().getCurrentAccount().password;
        loginRequest.password = AESUtils.encrypt(password);
        loginRequest.phone = phone;
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("application/json;charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.phone = phone;
                loginRequest.password = AESUtils.encrypt(password);
                Gson gson = new Gson();
                String content = gson.toJson(loginRequest);
                sink.write(content.getBytes());
            }
        };
        String url = String.format("%s%s", Servers.getTBBUserApi(), "account/login");
        return new Request.Builder()
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * 解析并且存储token
     */
    public boolean handleParsedToken(Response response) throws IOException {
        final String key = "token";
        BaseResponse baseResponse = mGson.fromJson(response.body().string(), BaseResponse.class);
        LinkedTreeMap treeMap = (LinkedTreeMap) (baseResponse.resultData);
        if (treeMap != null && treeMap.containsKey(key)) {
            mToken = (String) treeMap.get(key);
            return true;
        }
        return false;
    }

    public String getToken() {
        return mToken;
    }


    /**
     * 重新构造相同的返回
     */
    private Response duplicate(Response response, String bodyString) {
        return response.newBuilder()
                .headers(response.headers())
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }
}

