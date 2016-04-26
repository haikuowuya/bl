package com.haikuowuya.bl.retrofit;

import com.haikuowuya.bl.util.SoutUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 说明:
 * 日期:2016/4/26
 * 时间:18:02
 * 创建者：hkwy
 * 修改者：
 **/
public class StringConverterFactory extends Converter.Factory
{

    public static StringConverterFactory create()
    {
        return new StringConverterFactory();
    }

    private StringConverterFactory()
    {

    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit)
    {
        return new StringResponseBodyConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit)
    {

        return new StringRequestBodyConverter();
    }

    //=========================================String Converter==========================================================================================
//=========================================String Converter==========================================================================================
    private static class StringResponseBodyConverter implements Converter<ResponseBody, String>
    {
        @Override
        public String convert(ResponseBody value) throws IOException
        {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(value.string());
            SoutUtils.out("value = " + value);
            return stringBuffer.toString();
        }
    }

    private static final class StringRequestBodyConverter implements Converter<String, RequestBody>
    {
        private static final MediaType MEDIA_TYPE = MediaType.parse("text/html; charset=UTF-8");

        @Override
        public RequestBody convert(String value) throws IOException
        {
            return RequestBody.create(MEDIA_TYPE, value);
        }
    }
//=========================================String Converter==========================================================================================
//=========================================String Converter==========================================================================================
}
