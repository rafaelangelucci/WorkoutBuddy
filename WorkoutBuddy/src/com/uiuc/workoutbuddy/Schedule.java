package com.uiuc.workoutbuddy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import helperClasses.Workout;
import httpRequests.AsyncHttpPostWrapper;
import httpRequests.HttpRequestListener;


/*
 * This implements some basic schedule modification.
 * The user interface will be implemented later.
 */
public class Schedule implements HttpRequestListener{	
	
	
	
	

	public void changeWorkoutDate(Workout w, String newDateStr)
	{
		if(isNewDateValid(newDateStr))
		{
			w.setDate(newDateStr);

			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				wrapper.updateWorkout(w);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isNewDateValid(String newDateStr) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar newDateCal = Calendar.getInstance();

		try {
			newDateCal.setTime(df.parse(newDateStr));
		} catch (java.text.ParseException e1) {
			// TODO Display Error Message "newDateStr must be in MM//dd/yyyy format."
			return false;
		}

		Calendar nowCal = Calendar.getInstance();
		if(nowCal.before(newDateCal))
			return true;
		else
		{
			// TODO Display Error Message "new date must be schedule in the future."
			return false;	
		}
	}

	@Override
	public void requestComplete() {
		// TODO Auto-generated method stub
	}
}