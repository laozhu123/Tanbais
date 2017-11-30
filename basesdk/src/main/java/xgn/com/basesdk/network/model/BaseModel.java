package xgn.com.basesdk.network.model;

import com.google.gson.JsonElement;

/**
 * Created by huluzi on 2017/8/9.
 */

public class BaseModel {
    public String resultCode;
    public String resultDesc;
    public JsonElement resultData;

    public BaseModel() {
    }

    public boolean isSuccess() {
        return this.resultCode.equals("0");
    }

    public boolean isCodeInvalid() {
        return !String.valueOf("0").equals(this.resultCode);
    }
}
