package com.uiuc.workoutbuddy;

import helperClasses.Exercise;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomGraphListAdapter extends BaseAdapter implements OnClickListener {

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
	public CustomGraphListAdapter(Context context, ArrayList<Exercise> exercises) {
		this.context = context;
		this.exerciseList = exercises;
	}

	@Override
	public int getCount() {
		return exerciseList.size();
	}

	@Override
	public Object getItem(int position) {
		return exerciseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Exercise entry = exerciseList.get(position);
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.graph_list_row_layout, null);
		}

		// Grab text view
		TextView name = (TextView) convertView.findViewById(R.id.row_name);
		name.setText(entry.getName());

		// Set the onClick Listener on this button
		RadioButton radioBtn = (RadioButton) convertView.findViewById(R.id.btn_radio);
		radioBtn.setOnClickListener(this);
		radioBtn.setTag(entry);

		return convertView;
	}

	@Override
	public void onClick(View v) {
		Exercise selected = (Exercise)v.getTag();

		Intent intent = new Intent(context, GraphActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    context.startActivity(intent);
		
		Log.i( "CustomListAdapter", "onClick : " + selected.getName());
	}
}
