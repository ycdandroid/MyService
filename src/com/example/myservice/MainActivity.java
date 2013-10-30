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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startBtn = (Button)findViewById(R.id.start);
		stopBtn = (Button)findViewById(R.id.stop);
		bindEvent();
		
	}

	public void onPause(){
		super.onPause();
	}
	private void bindEvent(){
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.i("MainActivity-->startbtn", "1");
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
    }
    
    //设置环境参数
    private native void setJNIEnv();
    //守护进程的开启
    private native void mainThread();
     

	static {
		System.loadLibrary("MyService");
	}
}
