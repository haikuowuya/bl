package com.haikuowuya.bl.model;

import com.google.gson.JsonArray;

import java.io.Serializable;

/**
 * 说明:
 * 日期:2016/4/22
 * 时间:15:32
 * 创建者：hkwy
 * 修改者：
 **/
public class BaseModel implements Serializable
{
    public String errorCode;
    public String errorMessage;
    public JsonArray list;
    public JsonArray list2;
    public String str;

    @Override
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("errorCode = " + errorCode + " errorMessage = " + errorMessage + " list = " + list.toString() + " list2 = " + list2 + " str = " + str);
        return stringBuffer.toString();
    }
}
