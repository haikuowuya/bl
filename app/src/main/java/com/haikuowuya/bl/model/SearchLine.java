package com.haikuowuya.bl.model;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.Observable;

/**
 * 说明:
 * 日期:2016/4/15
 * 时间:13:39
 * 创建者：hkwy
 * 修改者：
 **/
public class SearchLine extends BaseObservable implements Serializable
{
    public String lineNo;
    public String lineName;
    public String lineHref;

    @Override
    public String toString()
    {

        return  "lineNo = " + lineNo + " lineName = " + lineName + " lineHref = " + lineHref;
    }
}
