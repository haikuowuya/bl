package com.haikuowuya.bl.model;

/**
 * 说明:
 * 日期:2016/4/23
 * 时间:14:59
 * 创建者：hkwy
 * 修改者：
 **/
public   class  SearchLineModel  extends  BaseItem
{
    public String Guid;
    public String LName;
    public String LDirection;

    @Override
    public String toString()
    {

        return  "Guid = " + Guid + " LName = " +LName + " LDirection = " + LDirection;
    }
}