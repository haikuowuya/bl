package com.haikuowuya.bl.model;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:17:21
 * 创建者：hkwy
 * 修改者：
 **/
public class StopItem extends BaseObservable implements Serializable
{
    public String lineHref;
    public String lineName;
    public String lineNo;
    public String  stopCar;
    public String  stopCarTime;
    public String stopSpacing;
}
