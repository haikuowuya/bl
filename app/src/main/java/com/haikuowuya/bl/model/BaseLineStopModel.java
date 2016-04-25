package com.haikuowuya.bl.model;

import com.google.gson.JsonObject;

/**
 * 说明:
 * 日期:2016/4/23
 * 时间:16:22
 * 创建者：hkwy
 * 修改者：
 **/
public class BaseLineStopModel extends  BaseModel
{

    public BusLineInfoModel data;

    @Override
    public String toString()
    {

        return  "errorCode = " + errorCode + " errorMsg = " + errorMsg + " data = " + data.toString();
    }
}
