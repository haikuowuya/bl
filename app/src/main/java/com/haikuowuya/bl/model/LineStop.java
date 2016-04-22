package com.haikuowuya.bl.model;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:16:31
 * 创建者：hkwy
 * 修改者：
 **/
public class LineStop extends BaseObservable implements Serializable
{
    public String stopName;
    public String stopHref;
    public String stopCode;
    public String stopCar;
    public String stopCarTime;
}
