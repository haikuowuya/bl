package com.haikuowuya.bl;

import android.app.Application;

import com.amap.api.location.AMapLocation;
import com.haikuowuya.bl.util.LocationUtils;
import com.haikuowuya.bl.util.ToastUtils;

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
        LocationUtils.startLocation(this, new LocationUtils.OnLocationFinishListener()
        {
            @Override
            public void onLocationFinished(AMapLocation amapLocation)
            {
                ToastUtils.showShortToast(getApplicationContext(), amapLocation.getAddress());
            }
        });
    }
}
