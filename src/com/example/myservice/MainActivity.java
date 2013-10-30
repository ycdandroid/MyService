package com.example.myservice;

import java.util.List;

import com.example.broadcast.BootBroadcast;
import com.example.service.MyService;
import com.example.service.NewService;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button startBtn;
	private Button stopBtn;
	
	static Context mContext = null;
//	ScreenStatusReceiver sReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = getApplicationContext();
		startBtn = (Button)findViewById(R.id.start);
		stopBtn = (Button)findViewById(R.id.stop);
		bindEvent();
		
//		Intent intent2 = new Intent();
//		intent2.setAction("com.example.broad.action");
//		sendBroadcast(intent2);
		
//		sReceiver = new ScreenStatusReceiver();
//		IntentFilter screenStatusIF = new IntentFilter();
//		screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
//		screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
//		// 注册
//		registerReceiver(sReceiver, screenStatusIF);
	}

	public void onPause(){
		super.onPause();
//		unregisterReceiver(sReceiver);
	}
	private void bindEvent(){
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.i("MainActivity-->startbtn", "1");
//				Intent intent = new Intent(MainActivity.this, MyService.class);
//				startService(intent);
//				Intent intent2 = new Intent(MainActivity.this, NewService.class);
//				startService(intent2);
//				Log.i("MainActivity", ProcessUtil.checkIsExist(getApplicationContext(), "com.example.service.MyService")+"");
				
				setContext(getApplicationContext());
				setJNIEnv();
				mainThread();
				
			}
		});
		
		stopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("MainActivity-->stopbtn", "1");
				Intent intent = new Intent(MainActivity.this, MyService.class);
				stopService(intent);
			}
		});
	}
	
	 //由JNI中的线程回调
    private static void fromJNI(final int i)
    {
    	Log.i("Java------>fromJNI", ""+i);
////		Intent intent = new Intent(mContext, MyService.class);
//    	Intent intent = new Intent("com.example.myservice");
//		mContext.startService(intent);
////    	Intent intent = new Intent();
////    	intent.setAction("com.example.myndktest.action.services");
////    	context.startService(intent);
        
    }
    
    public static void callback(Context context){
    	Log.w("MainActivity-->callback","111=============");
    	android.content.Intent intent = new android.content.Intent("com.example.myservice");
		context.startService(intent);
    }
    
    //本地方法
    private native void mainThread();
    private native void setJNIEnv(); 
    private native void setContext(Context context);

	static {
		System.loadLibrary("MyService");
	}
}
