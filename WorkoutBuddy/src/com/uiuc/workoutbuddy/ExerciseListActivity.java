package com.uiuc.workoutbuddy;

import customListAdapter.ExerciseListAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

public class ExerciseListActivity extends ListActivity implements OnItemClickListener
{
//	static Object mActionMode;
//	static int selectedItem = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_list);
		
        ExerciseListAdapter adapter = new ExerciseListAdapter(this, ExerciseFragment.exercises);

        setListAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		CheckBox cb = (CheckBox)view.findViewById(R.id.btn_check_box);
		cb.performClick();
		if(cb.isChecked())
		{
			String name = ExerciseFragment.exercises.get(position).getName();
			Toast.makeText(this.getApplicationContext(), name, Toast.LENGTH_SHORT).show();
			Log.i("ExerciseListActivity", "onItemClick : cb is checked");
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//	 /**
//	  *  Action mode call back that inflates CAB layout and registers on click functionality
//	  */
//	public static ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//
//		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//			MenuInflater inflater = mode.getMenuInflater();
//			inflater.inflate(R.menu.context_menu, menu);
//			return true;
//		}
//
//		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//			return false; // Return false if nothing is done
//		}
//
//		// called when the user selects a contextual menu item
//		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//			//TODO: implement button clicks
//			return true;
//		}
//
//		// called when the user exits the action mode
//		public void onDestroyActionMode(ActionMode mode) {
//			mActionMode = null;
//			selectedItem = -1;
//		}
//	};
}
