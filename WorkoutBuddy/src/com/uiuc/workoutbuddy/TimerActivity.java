package com.uiuc.workoutbuddy;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TimerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
	}

	public void startAlert(View view) {
		EditText text = (EditText) findViewById(R.id.time);
		
		int i = Integer.parseInt(text.getText().toString());
		
		Intent intent = new Intent(this, TimerReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 234324243, intent, 0);
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ (i * 1000), pendingIntent);
		
		// Small pop up
		Toast.makeText(this, "Resting for " + i + " seconds",
				Toast.LENGTH_LONG).show();
	}

}
