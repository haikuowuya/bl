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
    public LinkedList<LineStopItem> StandInfo;
    public String is_favorite;

    @Override
    public String toString()
    {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("LGUID = " + LGUID);
        stringBuffer.append(" LName = " + LName);
        stringBuffer.append(" LDirection = " + LDirection);
        stringBuffer.append(" LFStdFTime = " + LFStdFTime);
        stringBuffer.append(" LFStdETime = " + LFStdETime);
        stringBuffer.append(" is_favorite = " + is_favorite);
        if(null != StandInfo)
            for(LineStopItem model : StandInfo)
            {
                stringBuffer.append(" model = " + model.toString() );
            }
        return  stringBuffer.toString();
    }
}
