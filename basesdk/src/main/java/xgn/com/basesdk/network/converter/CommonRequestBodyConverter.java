package xgn.com.basesdk.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by huluzi on 2017/8/9.
 */

final class CommonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CommonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        OutputStreamWriter writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = this.gson.newJsonWriter(writer);
        this.adapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}