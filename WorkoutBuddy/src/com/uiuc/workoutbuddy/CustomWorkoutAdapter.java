package com.uiuc.workoutbuddy;

import helperClasses.Workout;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomWorkoutAdapter extends ArrayAdapter<Workout> //implements OnItemClickListener//implements OnCheckedChangeListener
{
	private final Context context;
	private final ArrayList<Workout> workoutList;
	//private ArrayList<Workout> workoutsChecked;
	private ArrayList<Boolean> positionArray;
	

	/**
	 * Constructor
	 * 
	 * @param context 
	 * 				current context of the application
	 * @param workouts
	 * 				list of workouts to put in list view
	 */
	public CustomWorkoutAdapter(Context context, ArrayList<Workout> workouts) {
		super(context, NO_SELECTION);
		this.context = context;
		this.workoutList = workouts;
//		this.workoutsChecked = new ArrayList<Workout>();
		positionArray = new ArrayList<Boolean>(workoutList.size());
	    for(int i = 0; i < workoutList.size(); i++){
	        positionArray.add(false);
	    }
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
		
		
		Log.i( "CustomListAdapter", "Set text for entry name : " + entry.getName());
		Log.i( "CustomListAdapter", "Set text for entry type : " + entry.getDescription());

//        TextView tvMail = (TextView) convertView.findViewById(R.id.tvMail);
//        tvMail.setText(entry.getDescription());

		return convertView;
	}
	
	static class Holder
	{
	    TextView name;
//	    TextView desc;
//	    CheckBox ckbox;
	}

//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		Log.i("CustomWorkoutAdapter", "onItemClick");
//		Toast.makeText(this.context, "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
//	}
	
}
