package com.haikuowuya.bl.retrofit;

import com.google.repacked.kotlin.jvm.Strictfp;
import com.haikuowuya.bl.model.BaseLineStopModel;
import com.haikuowuya.bl.model.BaseSearchLineModel;
import com.haikuowuya.bl.model.BaseStopModel;

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
    /***
     * 精确查询公交线路
     *
     * @param method
     * @param lineName
     * @return
     */
    @GET("Json")
    Call<ResponseBody> searchLine(@Query("method") String method, @Query("lineName") String lineName);

    /****
     * 模糊查询公交线路
     *
     * @param name
     * @return
     */
    @GET("api18/bus/searchLine")
    Call<BaseSearchLineModel> searchLine(@Query("name") String name);

    /***
     * 获取公交线路公交站点详情信息
     * @param method
     * @param guid
     * @return
     */
    @GET("Json")
    Call<BaseLineStopModel> getBusLineDetail(@Query("method")String method,@Query("Guid") String guid);

    @GET("Json")
    Call<BaseStopModel> getBusStopDetail(@Query("method")String method, @Query("NoteGuid")String sCode);

}
