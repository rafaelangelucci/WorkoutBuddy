package com.uiuc.workoutbuddy;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class for creating new intents and broadcasting them to TimerReciever
 *
 */
public class TimerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
	}

	/**
	 * Get the user input and broadcast a new created intent
	 * 
	 */
	public void startAlert() {
		// Get the amount of seconds that the user wants to wait
		EditText text = (EditText) findViewById(R.id.time_input);
		int i = Integer.parseInt(text.getText().toString());
		
		// Create the intent to be broadcasted
		Intent intent = new Intent(this, TimerReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 234324243, intent, 0);
		
		// Create an AlarmManager with the requested wait time
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ (i * 1000), pendingIntent);
		
		// Small pop up message to confirm a user's input
		Toast.makeText(this, "Resting for " + i + " seconds",
				Toast.LENGTH_LONG).show();
	}

}
