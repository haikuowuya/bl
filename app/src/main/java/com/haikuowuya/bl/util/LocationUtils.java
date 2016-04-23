package com.haikuowuya.bl.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;

public class LocationUtils {

	public static AMapLocationClient sAMapLocationClient;
	/**
	 * 开始定位，获取当前位置
	 */
	public static  void startLocation(Context context ,final OnLocationFinishListener listener)
	{
		if(sAMapLocationClient ==null)
		{
			sAMapLocationClient =  new AMapLocationClient(context);
		}
		//初始化定位
		//声明mLocationOption对象
		  AMapLocationClientOption mLocationOption;
		//初始化定位参数
		mLocationOption = new AMapLocationClientOption();
		//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		//设置是否返回地址信息（默认返回地址信息）
		mLocationOption.setNeedAddress(true);
		//设置是否只定位一次,默认为false
		mLocationOption.setOnceLocation(false);
		//设置是否强制刷新WIFI，默认为强制刷新
		mLocationOption.setWifiActiveScan(true);
		//设置是否允许模拟位置,默认为false，不允许模拟位置
		mLocationOption.setMockEnable(false);
		//设置定位间隔,单位毫秒,默认为2000ms
		mLocationOption.setInterval(2000);
		//给定位客户端对象设置定位参数
		sAMapLocationClient.setLocationOption(mLocationOption);
		//启动定位
		sAMapLocationClient.startLocation();
		sAMapLocationClient.setLocationListener(new AMapLocationListener() {		
			@Override
			public void onLocationChanged(AMapLocation amapLocation) {
				 stopLocation();
				 if (amapLocation.getErrorCode() == 0) {
					 System.out.println("定位成功 = " + amapLocation.getAddress());	
					 	if(null != listener)
					 	{
					 		listener.onLocationFinished(amapLocation);
					 	}
				    }
				 else
				 {
					 System.out.println("定位失败");
				 }
			}
		});
	}
	public static void stopLocation()
	{
		if(null != sAMapLocationClient && sAMapLocationClient.isStarted())
		{
			sAMapLocationClient.stopLocation();
		}
	}
	
	public static interface OnLocationFinishListener
	{
		public void onLocationFinished(AMapLocation amapLocation);
	}
}
