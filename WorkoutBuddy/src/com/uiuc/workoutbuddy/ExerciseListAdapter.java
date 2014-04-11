package com.uiuc.workoutbuddy;

import helperClasses.Exercise;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ExerciseListAdapter extends ArrayAdapter<Exercise> //implements OnCheckedChangeListener
{
	private final Context context;
	private final ArrayList<Exercise> exerciseList;
	private ArrayList<Exercise> exercisesChecked;
	private boolean [] itemChecked;


	/**
	 * Constructor
	 * 
	 * @param context 
	 * 				current context of the application
	 * @param exercises
	 * 				list of exercises to put in list view
	 */
	public ExerciseListAdapter(Context context, ArrayList<Exercise> exercises) {
		super(context, NO_SELECTION);
		this.context = context;
		this.exerciseList = exercises;
		this.exercisesChecked = new ArrayList<Exercise>();
		itemChecked = new boolean[exerciseList.size()];
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
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		final Exercise entry = getItem(position);
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_layout_exercises, null);

			holder = new Holder();
			holder.ckbox = (CheckBox) convertView.findViewById(R.id.btn_check_box);
			holder.name = (TextView) convertView.findViewById(R.id.row_name);
			holder.desc = (TextView) convertView.findViewById(R.id.row_type);
			
			convertView.setTag(holder);
		}
		else {
			holder = (Holder)convertView.getTag();
		}
		
		holder.name.setText(entry.getName());
		holder.desc.setText(entry.getDescription());
		holder.ckbox.setChecked(false);
		if(itemChecked[position])
		{
			holder.ckbox.setChecked(true);
		}
		else {
			holder.ckbox.setChecked(false);
		}
		
		holder.ckbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(holder.ckbox.isChecked())
				{
					itemChecked[position] = true;
				}
				else {
					itemChecked[position] = false;
				}
			}			
		});

		Log.i( "ExerciseListAdapter", "Name : " + entry.getName());
		Log.i( "ExerciseListAdapter", "Type : " + entry.getType());

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
		CheckBox ckbox;
	}

	public ArrayList<Exercise> getExercisesChecked() {
		return exercisesChecked;
	}

	public void setExercisesChecked(ArrayList<Exercise> exercisesChecked) {
		this.exercisesChecked = exercisesChecked;
	}


}
