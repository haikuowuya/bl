package com.haikuowuya.bl;

import android.app.Application;

/**
 * 说明:
 * 日期:2016/4/12
 * 时间:20:32
 * 创建者：hkwy
 * 修改者：
 **/
public class BLApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        /**
         * 内存泄漏检测
         */
      // LeakCanary.install(this);
        /***
         * crash展示
         */
    //    CrashWoodpecker.init(this);
    }
}
