package xgn.com.basesdk.network.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import xgn.com.basesdk.network.exception.ApiException;
import xgn.com.basesdk.network.model.BaseModel;

/**
 * Created by huluzi on 2017/8/9.
 */

final class CommonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CommonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseModel baseModel = null;

        try {
            baseModel = (BaseModel)this.gson.fromJson(response, BaseModel.class);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        if(baseModel != null && baseModel.isCodeInvalid()) {
            if("101021".equals(baseModel.resultCode)) {
                value.close();
                throw new IOException("");
            } else {
                value.close();
                throw new ApiException(baseModel.resultCode, baseModel.resultDesc);
            }
        } else {
            String jsonReader;
            try {
                JSONObject e = new JSONObject(response);
                String data = e.getString("resultData");
                if(!TextUtils.equals("null", data) && !TextUtils.isEmpty(data)) {
                    JsonReader jsonReader1 = this.gson.newJsonReader(new StringReader(data));
                    Object var7 = this.adapter.read(jsonReader1);
                    return (T) var7;
                }

                jsonReader = new String();
            } catch (Exception var13) {
                var13.printStackTrace();
                throw new JsonParseException(var13.getMessage());
            } finally {
                value.close();
            }

            return (T) jsonReader;
        }
    }
}