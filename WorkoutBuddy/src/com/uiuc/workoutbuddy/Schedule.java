package com.uiuc.workoutbuddy;

import java.text.DateFormat;
import java.text.ParseException;
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
	


	public void repeatWorkout(Workout w1, String dateStr) {
		if(isDateStrValid(dateStr))
		{
			Workout w2 = new Workout(w1.getName(), dateStr, w1.getDescription(), w1.getUsername(), w1.getExercises());
			

			AsyncHttpPostWrapper wrapper = new AsyncHttpPostWrapper(this);
			try {
				wrapper.addWorkout(w2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	


	public void repeatWorkout(Workout w, String startDateStr, int numWeeks, boolean everyWeek) {
		if(isDateStrValid(startDateStr))
		{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(df.parse(startDateStr));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			
			for(int idx = 0; idx < numWeeks; idx++)
			{
				repeatWorkout(w, df.format(cal.getTime()));
				if(everyWeek)
					cal.add(Calendar.DATE, 7);
				else
					cal.add(Calendar.DATE, 14);
			}
		}
	}

	

	public void changeWorkoutDate(Workout w, String newDateStr)
	{
		if(isDateStrValid(newDateStr))
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

	private boolean isDateStrValid(String newDateStr) {
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