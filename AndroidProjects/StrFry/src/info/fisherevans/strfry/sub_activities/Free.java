package info.fisherevans.strfry.sub_activities;

import info.fisherevans.strfry.R;
import info.fisherevans.strfry.StrFry;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Free extends Activity
{
	private static boolean done = false;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free);
		
		Display display = getWindowManager().getDefaultDisplay(); 
		if(display.getWidth() > 450)
		{
			LinearLayout wrap = (LinearLayout) findViewById(R.id.freeWrap);
			int padding = (int)((display.getWidth() - 450)/2);
			wrap.setPadding(padding, 0, padding, 0);
		}
		
		TextView link = (TextView)findViewById(R.id.freeLink);
		link.setText(Html.fromHtml("<a href=\"market://details?id=info.fisherevans.strfry&hl=pt_PT\">StrFry (Paid)</a>"));
		link.setMovementMethod(LinkMovementMethod.getInstance());

	    FreeCounter splashCounter = new FreeCounter(15000, 15000);
	    splashCounter.start();
	}
	
	public void setButtonText()
	{
		((Button)findViewById(R.id.freeCont)).setTextColor(Color.WHITE);
	}
	
	public void goOn(View v)
	{
		if(done)
			finish();
		else
			Toast.makeText(getBaseContext(), "Not yet...", Toast.LENGTH_SHORT).show();
	}
	
	public class FreeCounter extends CountDownTimer
	{
		public FreeCounter(long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
		}
		public void onFinish()
		{
			setButtonText();
			done = true;
		}
		public void onTick(long millisUntilFinished) { }
	}
}
