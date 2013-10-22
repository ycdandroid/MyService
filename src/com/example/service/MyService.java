package com.example.service;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.ViewDebug.FlagToString;

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
		init();
	}
	
//	public int onStartCommand(Intent intent, int flags, int startId){
//		return START_STICKY;
//	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("MyService","startCommand");
        
        flags =  START_STICKY;
        return super.onStartCommand(intent, flags, startId);
	}
	
	private void init() {
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
	
	public void onDestory(){
		super.onDestroy();
		flag = false;
		Log.i("MyService-->ondesotry", "1");
//		Intent localIntent = new Intent();
//        localIntent.setClass(this, MyService.class);  //销毁时重新启动Service
//        this.startService(localIntent);
	}

}
