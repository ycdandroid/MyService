package com.example.broadcast;

import com.example.service.MyService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("AlarmReceiver-->onReceive", "1");
		Intent intent2 = new Intent();
		intent2.setClass(context, MyService.class);
		context.startService(intent2);
	}

}
