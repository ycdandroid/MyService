package com.example.service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service{

	private boolean flag = true;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		flag = true;
		Log.i("MyService-->oncreate", "1");
		this.startForeground(1, new Notification());
//		init();
//		checkProcess();
	}
	
//	public int onStartCommand(Intent intent, int flags, int startId){
//		return START_STICKY;
//	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("MyService","startCommand");
        
        flags =  START_STICKY;
        return super.onStartCommand(intent, flags, startId);
	}
	
	private void init() {
		Toast.makeText(this, "init", Toast.LENGTH_SHORT).show();
		new Thread(){
			public void run(){
				try {
					while(flag){
						Thread.sleep(2000);
						Log.i("MyService-->run", "1");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void onStart(Intent intent, int startId){
		super.onStart(intent, startId);
		Log.i("MyService-->onStart", "1");
	}
	
	public void onDestroy(){
//		super.onDestroy();
		flag = false;
		stopForeground(true);
		Log.i("MyService-->ondesotry", "1");
//		Intent localIntent = new Intent();
//        localIntent.setClass(this, MyService.class);  //销毁时重新启动Service
//        this.startService(localIntent);
	}

}
