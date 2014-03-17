package com.uiuc.workoutbuddy;

import helperClasses.Exercise;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter extends BaseAdapter implements OnClickListener {
	
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
	public CustomListAdapter(Context context, ArrayList<Exercise> exercises) {
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
            convertView = inflater.inflate(R.layout.row_layout, null);
        }
        
        // Grab both text views in each row
        TextView firstLine = (TextView) convertView.findViewById(R.id.row_name);
        firstLine.setText(entry.getName());

        TextView secondLine = (TextView) convertView.findViewById(R.id.row_type);
        secondLine.setText(entry.getType());

//        TextView tvMail = (TextView) convertView.findViewById(R.id.tvMail);
//        tvMail.setText(entry.getDescription());

        // Set the onClick Listener on this button
        RadioButton radioBtn = (RadioButton) convertView.findViewById(R.id.btn_radio);
        radioBtn.setOnClickListener(this);
        // Set the entry, so that you can capture which item was clicked and
        // then remove it
        // As an alternative, you can use the id/position of the item to capture
        // the item
        // that was clicked.
        radioBtn.setTag(entry);

        // btnRemove.setId(position);

        return convertView;
	}

	@Override
	public void onClick(View v) {
		Exercise selected = (Exercise)v.getTag();
		
		Toast.makeText(this.context, selected.getDescription(), Toast.LENGTH_SHORT).show();
	}
}
