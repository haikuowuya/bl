package com.haikuowuya.bl;

/**
 * 说明:
 * 日期:2016/4/25
 * 时间:11:56
 * 创建者：hkwy
 * 修改者：
 **/
public class PREF
{
    private PREF()
    {
        throw  new IllegalArgumentException("PREF can not be initialized ");
    }

    /***
     * 定位的经度
     */
    public static final String  PREF_LOCATION_LNG="pref_location_lng";
    /***
     * 定位的纬度
     */
    public static final String  PREF_LOCATION_LAT="pref_location_lat";

    /***
     * 定位的地址
     */
    public static  final  String PREF_LOCATION_ADDRESS="pref_location_address";

    /***
     * 数据来自APP
     */
    public static final String PREF_DATA_FROM_APP="pref_data_from_app";
    /***
     * 数据来自WEB
     */
    public static final String PREF_DATA_FROM_WEB="pref_data_from_web";
}
