package customListAdapter;

import helperClasses.Workout;

import java.util.ArrayList;

import com.uiuc.workoutbuddy.R;
import com.uiuc.workoutbuddy.R.id;
import com.uiuc.workoutbuddy.R.layout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WorkoutListAdapter extends ArrayAdapter<Workout>
{
	private final Context context;
	private final ArrayList<Workout> workoutList;	

	/**
	 * Constructor
	 * 
	 * @param context 
	 * 				current context of the application
	 * @param workouts
	 * 				list of workouts to put in list view
	 */
	public WorkoutListAdapter(Context context, ArrayList<Workout> workouts) {
		super(context, NO_SELECTION);
		this.context = context;
		this.workoutList = workouts;		
	}

	@Override
	public int getCount() {
		return workoutList.size();
	}

	@Override
	public Workout getItem(int position) {
		return workoutList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		final Workout entry = workoutList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_layout_workouts, null);
			
			holder = new Holder();
			holder.name = (TextView) convertView.findViewById(R.id.row_name);
			convertView.setTag(holder);
		}
		else{
			holder = (Holder)convertView.getTag();
		}
		
		holder.name.setText(entry.getName());
		
		
		Log.i( "WorkoutListAdapter", "Name : " + entry.getName());
		Log.i( "WorkoutListAdapter", "Description : " + entry.getDescription());

		return convertView;
	}
	
	static class Holder
	{
	    TextView name;
	}
	
}
