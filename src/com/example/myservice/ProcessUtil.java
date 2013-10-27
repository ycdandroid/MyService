package com.example.myservice;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;

public class ProcessUtil {

	public static void checkProcess(Context context){
		ActivityManager activityManger = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);// 获取Activity管理器
		List<ActivityManager.RunningServiceInfo> list = activityManger.getRunningServices(100);
		
		for (ActivityManager.RunningServiceInfo info : list) {
			Log.d("process-->", info.service.getClassName());
		}
	}
	
	public static boolean checkIsExist(Context context, String service){
		ActivityManager activityManger = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);// 获取Activity管理器
		List<ActivityManager.RunningServiceInfo> list = activityManger.getRunningServices(100);
		
		for (ActivityManager.RunningServiceInfo info : list) {
			if (info.service.getClassName().equals(service)) {
//				Log.w("ProcessUtil-->checkIsExist", info.service.getClassName());
				return true;
			}
		}
		return false;
	}
}
