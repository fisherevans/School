package info.fisherevans.wordtoss.pref;

import info.fisherevans.wordtoss.R;
import info.fisherevans.wordtoss.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class PreferencesActivity extends Activity
{
	private SeekBar diffView;
	private ToggleButton timeView, googleView;
	
	private int diff;
	private boolean time, google;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref);
		
		diffView = (SeekBar) findViewById(R.id.difficulty);
		timeView = (ToggleButton) findViewById(R.id.prefTime);
		googleView = (ToggleButton) findViewById(R.id.prefGoogle);
	}
	
	public void onStart()
	{
		super.onStart();
		
		getPrefs();
		
		diffView.setProgress(diff);
		timeView.setChecked(time);
		googleView.setChecked(google);
	}
	
	public void getPrefs()
	{
		SharedPreferences prefs = getSharedPreferences("info.fisherevans.wordtoss", Context.MODE_PRIVATE);
		diff = prefs.getInt(".diff", 25);
		time = prefs.getBoolean(".time", false);
		google = prefs.getBoolean(".google", false);
	}
	
	public void savePrefs(View v)
	{
		time = timeView.isChecked();
		google = googleView.isChecked();
		diff = diffView.getProgress();
		

		SharedPreferences prefs = getSharedPreferences("info.fisherevans.wordtoss", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		editor.putBoolean(".time", time);
		editor.putBoolean(".google", google);
		editor.putInt(".diff", diff);
		editor.commit();
		
		finish();
	}
	
	public void closeActivity(View v)
	{
		finish();
	}
}
