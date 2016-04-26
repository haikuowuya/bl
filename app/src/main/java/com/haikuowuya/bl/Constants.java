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
    public static  final  String IN_STOP="已经进站";
}
