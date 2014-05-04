package com.uiuc.workoutbuddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Class to receive intents sent by TimerActivity and to alert users by vibrating the phone
 *
 */
public class TimerReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Small pop up
		Toast.makeText(context, "Rest Period Over", Toast.LENGTH_LONG).show();
		
		// Vibrate the phone
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
	}

}
