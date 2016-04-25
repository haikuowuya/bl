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
public class LineStopItem extends BaseObservable implements Serializable
{

    public String SName;
    public String SCode;
    public String InTime;
    /**
     * 当前站点是否在附近
     */
    public String is_vicinity;
    /***
     * 站距
     */
    public String s_num;
    /***
     * 站距提示文字
     */
    public String s_num_str;

    @Override
    public String toString()
    {
        return "SName = "+SName + " SCode = "+SCode+" InTime = "+ InTime+" is_vicinity= " + is_vicinity+ " s_num= "+s_num+" s_num_str = "+s_num_str;
    }
}
