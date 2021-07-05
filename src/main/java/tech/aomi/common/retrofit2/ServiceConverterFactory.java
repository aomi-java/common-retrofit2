package tech.aomi.common.retrofit2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 服务端参数转换
 *
 * @author 田尘殇Sean Create At 2018/10/10 21:00
 */
public class ServiceConverterFactory extends Converter.Factory {

    private final ObjectMapper mapper;

    private ServiceConverterFactory() {
        this.mapper = new ObjectMapper();
        this.mapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectWriter writer = mapper.writerFor(javaType);
        return new ServiceRequestBodyConverter<>(writer);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ServiceResponseBodyConverter<>(mapper, type);
    }

    public static ServiceConverterFactory create() {
        return new ServiceConverterFactory();
    }

}
