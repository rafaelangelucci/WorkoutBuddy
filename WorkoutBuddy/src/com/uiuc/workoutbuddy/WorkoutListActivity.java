package com.uiuc.workoutbuddy;

import customListAdapter.WorkoutListAdapter;
import helperClasses.Workout;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 *
 * @author tmadigan7
 *
 * Some code borrowed from: http://www.vogella.com/tutorials/AndroidListView/article.html#listactivity
 *
 */
public class WorkoutListActivity extends ListActivity implements OnItemClickListener
{
	protected Object mActionMode;
	public int selectedItem = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list);

		Log.i("WorkoutListActivity", "onCreate");

		WorkoutListAdapter adapter = new WorkoutListAdapter(this, WorkoutFragment.workouts);

		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (mActionMode != null) {
					return false;
				}
				selectedItem = position;

				// start the CAB using the ActionMode.Callback defined above
				((ActionMode) mActionMode).setTag(position);
				mActionMode = WorkoutListActivity.this.startActionMode(mActionModeCallback);
				view.setSelected(true);
				return true;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Workout wo = WorkoutFragment.workouts.get(position);
		String name = wo.getName();
		Log.i("WorkoutListActivity", "onItemClick : " + name);
		Log.i("WorkoutListActivity", name + " wid : " + wo.getWid());

		// Spawn new work out activity
		Intent intent = new Intent(this, UseWorkoutActivity.class);
		intent.putExtra("wid", wo.getWid());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Function to share a work out via email or other methods
	 * @return true if successfully shared
	 */
	public boolean shareWorkout(Object obj) {
		Log.i("WorkoutListActivity", "ERROR : SHARE FAILED");
		
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		sharingIntent.setType("text/plain");
		String shareBody = "TEST : Here is the share content body";
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
		return true;
	}

	/**
	 * Action mode call back that inflates CAB layout and registers on click functionality
	 */
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.context_menu, menu);
			return true;
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// called when the user selects a contextual menu item
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch(item.getItemId())
			{
			case R.id.action_settings:
				Log.i("WorkoutListActivity", "SETTINGS onActionItemClicked");
				break;
			case R.id.share:
				Log.i("WorkoutListActivity", "SHARE onActionItemClicked");
				int item_postion=Integer.parseInt(mode.getTag().toString());
	            //Workout wo = WorkoutListAdapter.getItem(item_postion);
				shareWorkout(mode.getTag());
				break;
			case R.id.edit:
				Log.i("WorkoutListActivity", "EDIT onActionItemClicked");
				break;
			case R.id.delete:
				Log.i("WorkoutListActivity", "DELETE onActionItemClicked");
				break;
			default:
				Log.i("WorkoutListActivity", "DEFAULT onActionItemClicked");
			}
			return true;
		}

		// called when the user exits the action mode
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
			selectedItem = -1;
		}
	};
}