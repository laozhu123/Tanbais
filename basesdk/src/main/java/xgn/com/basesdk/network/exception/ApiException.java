package xgn.com.basesdk.network.exception;

/**
 * Created by huluzi on 2017/7/10.
 */

public class ApiException extends RuntimeException {
    public final String mMessage;
    public final String mCode;

    public ApiException(String code, String message) {
        this.mCode = code;
        this.mMessage = message;
    }
}
