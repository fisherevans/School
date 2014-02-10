package info.fisherevans.strfry.sub_activities;

import info.fisherevans.strfry.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Help extends Activity
{
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		Display display = getWindowManager().getDefaultDisplay(); 
		if(display.getWidth() > 450)
		{
			LinearLayout wrap = (LinearLayout) findViewById(R.id.helpWrap);
			int padding = (int)((display.getWidth() - 450)/2);
			wrap.setPadding(padding, 0, padding, 0);
		}
	}
	
	public void goBack(View v)
	{
		finish();
	}
}
