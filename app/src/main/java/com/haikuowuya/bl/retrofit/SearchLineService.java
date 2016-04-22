package com.haikuowuya.bl.retrofit;

import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.model.BaseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 说明:
 * 日期:2016/4/22
 * 时间:15:53
 * 创建者：hkwy
 * 修改者：
 **/
public interface SearchLineService
{
    @GET("Json")
    Call<BaseModel>searchLine(@Query("method") String method, @Query("lineName") String lineName);
}
