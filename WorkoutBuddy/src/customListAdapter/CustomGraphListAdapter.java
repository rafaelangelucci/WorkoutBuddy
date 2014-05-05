package customListAdapter;

import helperClasses.Exercise;

import java.util.ArrayList;

import com.uiuc.workoutbuddy.GraphActivity;
import com.uiuc.workoutbuddy.R;
import com.uiuc.workoutbuddy.R.id;
import com.uiuc.workoutbuddy.R.layout;

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

/**
 * Extending ListAdapator class for the exercise list in the graph activity
 *
 */
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
	
	/**
	 * Constructor 
	 * 
	 * @param exercises list of exercises to put in list view
	 */
	public CustomGraphListAdapter(ArrayList<Exercise> exercises) {
		this.context = null;
		this.exerciseList = exercises;
	}

	/**
	 * return the exercise list size
	 */
	@Override
	public int getCount() {
		return exerciseList.size();
	}

	/**
	 * Gets the exercise object at the given position
	 * 
	 * @param position The position in the exerciseList to return
	 */
	@Override
	public Object getItem(int position) {
		return exerciseList.get(position);
	}

	/**
	 * Gets the exercise's id at the given position
	 * 
	 * @param position The position on the exerciseList to look at
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Function to set the behavior of the exercise list's graphical layout
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the exercise object
		Exercise entry = exerciseList.get(position);
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.graph_list_row_layout, null);
		}

		// Grab the value in the text view
		TextView name = (TextView) convertView.findViewById(R.id.row_name);
		name.setText(entry.getName());

		// Set the onClick Listener on this button
		RadioButton radioBtn = (RadioButton) convertView.findViewById(R.id.btn_radio);
		radioBtn.setOnClickListener(this);
		radioBtn.setTag(entry);

		return convertView;
	}

	/**
	 * Creates an intent to go to the GraphActivity
	 */
	@Override
	public void onClick(View v) {
		Exercise selected = (Exercise)v.getTag();
		String name = selected.getName();

		Intent intent = new Intent(context, GraphActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("exercise", name);
	    context.startActivity(intent);
		
		Log.i( "CustomListAdapter", "onClick : " + name);
	}
}
