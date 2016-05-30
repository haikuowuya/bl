package com.haikuowuya.bl;

/**
 * 说明:
 * 日期:2016/4/14
 * 时间:20:18
 * 创建者：hkwy
 * 修改者：
 **/
public class URLConstants
{
    /**
     * 无线苏州公交API
     */
    private static final String BASE_API_URL = "http://content.2500city.com/";
    /***
     * 无线苏州公交API 18
     */
    public static final String BASE_API_18_URL = BASE_API_URL + "api18/bus/";
    /***
     * 根据公交线路编号查询公交线路信息
     */
    public static final String SEARCH_BUS_LINE_BY_LINE_NO = BASE_API_URL + "Json?method=SearchBusLine&lineName=%s";
    /***
     * 公交线路查询前缀
     */
    public static final String BUS_LINE_QUERY_PREFIX = "http://www.szjt.gov.cn/BusQuery/";
    /***
     * 查询公交线路的URL
     */
    //public static final String LINE_SEARCH = BUS_LINE_QUERY_PREFIX + "APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4";
    public static final String LINE_SEARCH =  "APTSLine.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4";
    /***
     * 查询公交站点对应的线路URL
     */
    public static final String  STOP_SEARCH="default.aspx?cid=175ecd8d-c39d-4116-83ff-109b946d7cb4";

    public static final String  WEB_LINE_SEARCH=BUS_LINE_QUERY_PREFIX+LINE_SEARCH;














    public static String getSearchBusLineByLineNoUrl(String lineNo)
    {
        return String.format(SEARCH_BUS_LINE_BY_LINE_NO, lineNo);
    }
}
