package com.candystudio.android.dxzs.student.app;


//import com.studentclienttest.app.service.LocationService;

import android.app.Application;
import android.content.Context;
//import android.content.Intent;

public class MyApplication extends Application
{
	private static MyApplication appInstance;
//	private static HashMap<String, Object> CacheMap; //�����Ļ�������

	@Override
	public void onCreate()
	{
		super.onCreate();
		appInstance=this;
	}
	public static Context getInstance()
	{
		return appInstance;
	}


}
