package com.uiuc.workoutbuddy;

import helperClasses.Exercise;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UseWorkoutAdapter extends ArrayAdapter<Exercise>
{
	private final Context context;
	private final ArrayList<Exercise> exerciseList;

	/**
	 * Constructor
	 * 
	 * @param context 
	 * 				current context of the application
	 * @param exercises
	 * 				list of exercises to put in list view
	 */
	public UseWorkoutAdapter(Context context, ArrayList<Exercise> exercises) {
		super(context, NO_SELECTION);
		this.context = context;
		this.exerciseList = exercises;
	}

	@Override
	public int getCount() {
		return exerciseList.size();
	}

	@Override
	public Exercise getItem(int position) {
		return exerciseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		final Exercise entry = exerciseList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_layout, null);

			holder = new Holder();
			holder.name = (TextView) convertView.findViewById(R.id.row_name);
			holder.desc = (TextView) convertView.findViewById(R.id.row_type);
			convertView.setTag(holder);
		}
		else{
			holder = (Holder)convertView.getTag();
		}

		holder.name.setText(entry.getName());
		holder.desc.setText(entry.getDescription());
		

		Log.i( "CustomListAdapter", "Set text for entry name : " + entry.getName());
		Log.i( "CustomListAdapter", "Set text for entry type : " + entry.getType());

		return convertView;
	}

	/**
	 * Static class to hold info for each list entry
	 * 
	 * @author tmadigan7
	 *
	 */
	static class Holder
	{
		TextView name;
		TextView desc;
	}

}
