package info.fisherevans.strfry.sub_activities;

import info.fisherevans.strfry.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class Preferences extends Activity
{
	private SeekBar diffView;
	private ToggleButton timeView, googleView, soundView;
	
	private int diff;
	private boolean time, google, sound;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref);
		
		diffView = (SeekBar) findViewById(R.id.difficulty);
		timeView = (ToggleButton) findViewById(R.id.prefTime);
		googleView = (ToggleButton) findViewById(R.id.prefGoogle);
		soundView = (ToggleButton) findViewById(R.id.prefSound);
	}
	
	public void onStart()
	{
		super.onStart();
		
		getPrefs();
		
		diffView.setProgress(diff);
		timeView.setChecked(time);
		googleView.setChecked(google);
		soundView.setChecked(sound);
		
		Display display = getWindowManager().getDefaultDisplay(); 
		if(display.getWidth() > 450)
		{
			LinearLayout wrap = (LinearLayout) findViewById(R.id.prefWrap);
			int padding = (int)((display.getWidth() - 450)/2);
			wrap.setPadding(padding, 0, padding, 0);
		}
	}
	
	public void getPrefs()
	{
		SharedPreferences prefs = getSharedPreferences("info.fisherevans.strfry", Context.MODE_PRIVATE);
		diff = prefs.getInt(".diff", 30);
		time = prefs.getBoolean(".time", false);
		google = prefs.getBoolean(".google", false);
		sound = prefs.getBoolean(".sound", true);
	}
	
	public void savePrefs(View v)
	{
		time = timeView.isChecked();
		google = googleView.isChecked();
		sound = soundView.isChecked();
		diff = diffView.getProgress();
		

		SharedPreferences prefs = getSharedPreferences("info.fisherevans.strfry", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		editor.putBoolean(".time", time);
		editor.putBoolean(".google", google);
		editor.putBoolean(".sound", sound);
		editor.putInt(".diff", diff);
		editor.commit();
		
		finish();
	}
	
	public void closeActivity(View v)
	{
		finish();
	}
}
