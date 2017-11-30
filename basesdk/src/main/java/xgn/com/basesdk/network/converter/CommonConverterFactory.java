package xgn.com.basesdk.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by huluzi on 2017/8/9.
 */

public final class CommonConverterFactory extends Converter.Factory {
    private final Gson gson;

    public static CommonConverterFactory create() {
        return create(new Gson());
    }

    public static CommonConverterFactory create(Gson gson) {
        return new CommonConverterFactory(gson);
    }

    private CommonConverterFactory(Gson gson) {
        if(gson == null) {
            throw new NullPointerException("gson == null");
        } else {
            this.gson = gson;
        }
    }

    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter adapter = this.gson.getAdapter(TypeToken.get(type));
        return new CommonResponseBodyConverter(this.gson, adapter);
    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter adapter = this.gson.getAdapter(TypeToken.get(type));
        return new CommonRequestBodyConverter(this.gson, adapter);
    }
}
