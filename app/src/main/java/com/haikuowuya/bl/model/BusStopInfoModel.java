package com.haikuowuya.bl.model;

import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/25
 * 时间:15:53
 * 创建者：hkwy
 * 修改者：
 **/
public class BusStopInfoModel extends BaseItem
{
    public String is_favorite;
    public StopInfo  info;
    public LinkedList<StopItem> list;

    public static  final  class  StopInfo extends  BaseItem
    {
        public String SCode;
        public String SName;
        public String baidu_lat;
        public String baidu_lng;
        public String address;
        public String long_info;
    }
}
