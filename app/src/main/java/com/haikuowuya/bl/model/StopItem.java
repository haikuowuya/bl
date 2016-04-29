package com.haikuowuya.bl.model;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import com.haikuowuya.bl.Constants;

import java.io.Serializable;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:17:21
 * 创建者：hkwy
 * 修改者：
 **/
public class StopItem extends  BaseItem
{
    public String Guid;
    public String LName;
    public String LDirection;
    public String  DBusCard;
    public String  InTime;
    public String SName;
    public String Distince;
    public String Distince_str;

    public String getDistince_str()
    {
        if(TextUtils.isEmpty(Distince_str) &&!TextUtils.isEmpty(Distince))
        {
            Distince_str = Distince+ Constants.STOP;
        }
        return Distince_str;
    }
}
