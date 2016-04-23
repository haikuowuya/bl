package com.haikuowuya.bl.model;

import java.util.LinkedList;

/**
 * 说明:
 * 日期:2016/4/23
 * 时间:16:22
 * 创建者：hkwy
 * 修改者：
 **/
public class BaseStopModel extends  BaseItem
{
    public String errorCode;
    public String errorMessage;
    public LinkedList<StopModel> list;
}
