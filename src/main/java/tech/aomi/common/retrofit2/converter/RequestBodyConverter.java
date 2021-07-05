package tech.aomi.common.retrofit2.converter;

import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import tech.aomi.common.retrofit2.constant.Common;

import java.io.IOException;

/**
 * 使用指定json转换工具去掉不需要的参数
 *
 * @param <T>
 */
final class RequestBodyConverter<T> implements Converter<T, RequestBody> {

    private final ObjectWriter adapter;

    RequestBodyConverter(ObjectWriter adapter) {
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        byte[] bytes = adapter.writeValueAsBytes(value);
        return RequestBody.create(Common.MEDIA_TYPE, bytes);
    }
}