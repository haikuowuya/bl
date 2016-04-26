package com.haikuowuya.bl.retrofit;

import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.model.BaseLineStopModel;
import com.haikuowuya.bl.model.BaseSearchLineModel;
import com.haikuowuya.bl.model.BaseStopModel;

import org.jsoup.nodes.Document;

import okhttp3.ResponseBody;
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
    public static  interface WEB
    {
        @GET(URLConstants.LINE_SEARCH)
        Call<Document> getLineInfo(@Query("LineGuid")String LineGuid);


    }





















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
        @GET("getLineInfo")
        Call<BaseLineStopModel> getLineInfo(@Query("Guid")String Guid ,@Query("lng")String lng,@Query("lat")String lat);
        @GET("getStationInfo")
        Call<BaseStopModel> getStationInfo(@Query("NoteGuid")String NoteGuid, @Query("lng")String lng,@Query("lat")String lat);
    }
}
