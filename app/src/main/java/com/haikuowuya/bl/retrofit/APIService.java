package com.haikuowuya.bl.retrofit;

import com.haikuowuya.bl.model.BaseSearchLineModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 说明:
 * 日期:2016/4/23
 * 时间:17:22
 * 创建者：hkwy
 * 修改者：
 **/
public class APIService
{
    public static interface   V18
    {
        /****
         * 模糊查询公交线路
         *
         * @param name
         * @return
         */
        @GET("searchLine")
        Call<BaseSearchLineModel> searchLine(@Query("name") String name);
    }
}
