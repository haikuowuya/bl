package com.haikuowuya.bl.model;

import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/23
 * 时间:16:27
 * 创建者：hkwy
 * 修改者：
 **/
public class BusLineInfoModel extends  BaseItem
{
    public String LGUID;
    public String LName;
    public String LDirection;
    public String LFStdFTime;
    public String LFStdETime;
    public LinkedList<LineStopModel> StandInfo;
}
