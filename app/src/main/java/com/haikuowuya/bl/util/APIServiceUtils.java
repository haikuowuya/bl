package com.haikuowuya.bl.util;

import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.retrofit.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 说明:
 * 日期:2016/4/25
 * 时间:16:20
 * 创建者：hkwy
 * 修改者：
 **/
public class APIServiceUtils
{
    public static APIService.V18  getV18()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URLConstants.BASE_API_18_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService.V18 v18 = retrofit.create(APIService.V18.class);

        return  v18;
    }
}
