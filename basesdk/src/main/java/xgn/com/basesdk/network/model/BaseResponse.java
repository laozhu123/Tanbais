package xgn.com.basesdk.network.model;

/**
 * Created by huluzi on 2017/8/11.
 */

public class BaseResponse<T> {
    public String resultCode;
    public String resultDesc;
    public T resultData;

    public BaseResponse() {
    }

    public boolean isCodeInvalid() {
        return !String.valueOf("0").equals(this.resultCode);
    }
}
