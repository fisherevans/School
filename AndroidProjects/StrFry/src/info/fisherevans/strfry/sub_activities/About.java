package info.fisherevans.strfry.sub_activities;

import info.fisherevans.strfry.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

public class About extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		Display display = getWindowManager().getDefaultDisplay(); 
		if(display.getWidth() > 450)
		{
			LinearLayout wrap = (LinearLayout) findViewById(R.id.aboutWrap);
			int padding = (int)((display.getWidth() - 450)/2);
			wrap.setPadding(padding, 0, padding, 0);
		}
	}
	
	public void goBack(View v)
	{
		finish();
	}
}
