package com.haikuowuya.bl.retrofit;

import com.haikuowuya.bl.URLConstants;
import com.haikuowuya.bl.model.BaseLineStopModel;
import com.haikuowuya.bl.model.BaseSearchLineModel;
import com.haikuowuya.bl.model.BaseStopModel;

import org.jsoup.nodes.Document;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

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

        @GET(URLConstants.LINE_SEARCH)
        Call<Document> fetchPostParams();

        @FormUrlEncoded
        @POST(URLConstants.LINE_SEARCH)
        Call<Document> searchLine(@FieldMap  HashMap<String,String> postParams);

        @GET(URLConstants.STOP_SEARCH)
        Call<Document> getStationInfo(@Query("StandCode")String standCode,@Query("StandName")String standName );
    }


    public static  interface  RX
    {
        @GET(URLConstants.LINE_SEARCH)
        Observable<Document> fetchPostParams();

        @FormUrlEncoded
        @POST(URLConstants.LINE_SEARCH)
        Observable<Document> searchLine(@FieldMap  HashMap<String,String> postParams);
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
