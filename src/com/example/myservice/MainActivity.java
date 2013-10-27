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
	
//	ScreenStatusReceiver sReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
//		// зЂВс
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
				Intent intent = new Intent(MainActivity.this, MyService.class);
				startService(intent);
				Intent intent2 = new Intent(MainActivity.this, NewService.class);
				startService(intent2);
				Log.i("MainActivity", ProcessUtil.checkIsExist(getApplicationContext(), "com.example.service.MyService")+"");
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
	
	

}
