package com.uiuc.workoutbuddy;

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
import android.widget.Toast;
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

		CustomWorkoutAdapter adapter = new CustomWorkoutAdapter(this, WorkoutFragment.workouts);

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

		// Spawn new workout activity
		Intent intent = new Intent(this, UseWorkoutActivity.class);
		intent.putExtra("wid", wo.getWid());
    	startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// called when the action mode is created; startActionMode() was called
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			// assumes that you have "contexual.xml" menu resources
			inflater.inflate(R.menu.context_menu, menu);
			return true;
		}

		// the following method is called each time 
		// the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// called when the user selects a contextual menu item
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			//			switch (item.getItemId()) {
			//			case R.id.menuitem1_show:
			//				show();
			//				// the Action was executed, close the CAB
			//				mode.finish();
			//				return true;
			//			default:
			//				return false;
			//			}
			return true;
		}

		// called when the user exits the action mode
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
			selectedItem = -1;
		}
	};

	private void show() {
		Toast.makeText(WorkoutListActivity.this,
				String.valueOf(selectedItem), Toast.LENGTH_LONG).show();
	}
}
