package com.haikuowuya.bl.util;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:11:13
 * 创建者：hkwy
 * 修改者：
 **/
public class SoutUtils
{
    public static void out(String outMsg)
    {
        System.out.println(outMsg);
    }

    public static void out(Object object)
    {
        if (null != object)
        {
            out(object.toString());
        }
    }

    public static void out(String key, String outMsg)
    {
        System.out.println(key = " = " + outMsg);
    }
}
