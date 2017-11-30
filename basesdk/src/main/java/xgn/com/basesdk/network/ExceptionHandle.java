package xgn.com.basesdk.network;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;
import xgn.com.basesdk.network.exception.ApiException;
import xgn.com.basesdk.network.utils.ContextUtil;

/**
 * Created by huluzi on 2017/7/10.
 */

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public ExceptionHandle() {
    }

    public static ExceptionHandle.ResponseThrowable handleException(Throwable e) {
        ExceptionHandle.ResponseThrowable ex;
        if(e instanceof HttpException) {
            HttpException apiException2 = (HttpException)e;
            ex = new ExceptionHandle.ResponseThrowable(e, 1003);
            switch(apiException2.code()) {
                case 401:
                case 403:
                case 404:
                case 408:
                case 500:
                case 502:
                case 503:
                case 504:
                default:
                    ex.message = "网络错误";
                    return ex;
            }
        } else if(e instanceof ExceptionHandle.ServerException) {
            ExceptionHandle.ServerException apiException1 = (ExceptionHandle.ServerException)e;
            ex = new ExceptionHandle.ResponseThrowable(apiException1, apiException1.code);
            ex.message = apiException1.message;
            return ex;
        } else if(!(e instanceof JsonParseException) && !(e instanceof JSONException) && !(e instanceof ParseException)) {
            if(e instanceof ConnectException) {
                if(!NetworkUtil.isNetworkAvailable(ContextUtil.getContext())) {
                    ex = new ExceptionHandle.ResponseThrowable(e, 1007);
                    ex.message = "网络错误，请检查网络";
                } else {
                    ex = new ExceptionHandle.ResponseThrowable(e, 1002);
                    ex.message = "网络错误，请检查网络";
                }

                return ex;
            } else if(e instanceof UnknownHostException) {
                ex = new ExceptionHandle.ResponseThrowable(e, 1007);
                ex.message = "网络错误，请检查网络";
                return ex;
            } else if(e instanceof SSLHandshakeException) {
                ex = new ExceptionHandle.ResponseThrowable(e, 1005);
                ex.message = "证书验证失败";
                return ex;
            } else if(e instanceof ConnectTimeoutException) {
                ex = new ExceptionHandle.ResponseThrowable(e, 1006);
                ex.message = "连接超时";
                return ex;
            } else if(e instanceof SocketTimeoutException) {
                ex = new ExceptionHandle.ResponseThrowable(e, 1006);
                ex.message = "连接超时";
                return ex;
            } else if(e instanceof ApiException) {
                ApiException apiException = (ApiException)e;

                try {
                    ex = new ExceptionHandle.ResponseThrowable(e, Integer.parseInt(apiException.mCode));
                    ex.message = apiException.mMessage;
                    return ex;
                } catch (Exception var4) {
                    ex = new ExceptionHandle.ResponseThrowable(e, 1008);
                    ex.message = "服务器异常";
                    return ex;
                }
            } else {
                ex = new ExceptionHandle.ResponseThrowable(e, 1000);
                ex.message = "未知错误";
                return ex;
            }
        } else {
            ex = new ExceptionHandle.ResponseThrowable(e, 1001);
            ex.message = "数据错误";
            return ex;
        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;

        public ServerException() {
        }
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    public static class ERROR {
        public static final int UNKNOWN = 1000;
        public static final int PARSE_ERROR = 1001;
        public static final int NETWORD_ERROR = 1002;
        public static final int HTTP_ERROR = 1003;
        public static final int SSL_ERROR = 1005;
        public static final int TIMEOUT_ERROR = 1006;
        public static final int NETWORk_NO_CONNECT = 1007;
        public static final int SERVER_ERROR = 1008;

        public ERROR() {
        }
    }
}
