package com.example.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class BootBroadcast extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("BootBroadcast-->onReceive", "1");
		Intent intent2 = new Intent(context, AlarmReceiver.class);
		intent2.setAction("com.example.alarm.action");
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent2, 0);
		long firstTime = SystemClock.elapsedRealtime();
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 3000, sender);
	}

}
