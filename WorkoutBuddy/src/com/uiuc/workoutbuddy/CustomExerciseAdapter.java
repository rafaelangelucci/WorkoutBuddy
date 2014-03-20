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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomExerciseAdapter extends ArrayAdapter<Exercise> //implements OnCheckedChangeListener
{
	private final Context context;
	private final ArrayList<Exercise> exerciseList;
	private ArrayList<Exercise> exercisesChecked;
	private ArrayList<Boolean> positionArray;
	

	/**
	 * Constructor
	 * 
	 * @param context 
	 * 				current context of the application
	 * @param exercises
	 * 				list of exercises to put in list view
	 */
	public CustomExerciseAdapter(Context context, ArrayList<Exercise> exercises) {
		super(context, NO_SELECTION);
		this.context = context;
		this.exerciseList = exercises;
		this.exercisesChecked = new ArrayList<Exercise>();
		positionArray = new ArrayList<Boolean>(exerciseList.size());
	    for(int i = 0; i < exerciseList.size(); i++){
	        positionArray.add(false);
	    }
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
			holder.ckbox = (CheckBox) convertView.findViewById(R.id.btn_check_box);
			holder.name = (TextView) convertView.findViewById(R.id.row_name);
			holder.desc = (TextView) convertView.findViewById(R.id.row_type);
			convertView.setTag(holder);
		}
		else{
			holder = (Holder)convertView.getTag();
		}
		
		holder.ckbox.setFocusable(false);
		holder.ckbox.setChecked(positionArray.get(position));
		holder.name.setText(entry.getName());
		holder.desc.setText(entry.getDescription());
		holder.ckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	            if(isChecked ){
	            	Log.i( "OnCheckChanged", "Checked " + entry.getName());
	                positionArray.add(position, true);
	            }else
	            {
	            	Log.i( "OnCheckChanged", "Unchecked " + entry.getName());
	            	positionArray.add(position, false);
	            }
	        }
	    });
		
		Log.i( "CustomListAdapter", "Set text for entry name : " + entry.getName());
		Log.i( "CustomListAdapter", "Set text for entry type : " + entry.getType());

//        TextView tvMail = (TextView) convertView.findViewById(R.id.tvMail);
//        tvMail.setText(entry.getDescription());

		return convertView;
	}
	
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
