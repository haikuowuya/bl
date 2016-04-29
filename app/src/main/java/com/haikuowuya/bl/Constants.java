package com.haikuowuya.bl;

/**
 * 说明:
 * 日期:2016/4/26
 * 时间:17:35
 * 创建者：hkwy
 * 修改者：
 **/
public class Constants
{
    private Constants()
    {
        throw  new IllegalArgumentException("Constants 不能被实例化");
    }

    /***
     * 更新频率
     */
    public static  final  long DELAY_TIME_IN_MM = 10*1000L;
    /***
     * WEB搜索公交线路 POST提交参数key
     */
    public static final String LINE_NO = "ctl00$MainContent$LineName";

    /***
     * &符号的常量代表
     */
    public  static  final  String AMP="&";


    public static final String LINE_GUID="LineGuid=";


    public static final String STOP="站";
    public static  final  String IN_STOP="已经进站";
}
