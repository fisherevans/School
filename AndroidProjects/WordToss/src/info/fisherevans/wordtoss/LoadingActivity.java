package info.fisherevans.wordtoss;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;

public class LoadingActivity extends Activity
{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
	    new CountDownTimer(1500,1500)
	    {
	        public void onTick(long millisUntilFinished) { } 
	        public void onFinish()
	        {
	               finish();
	        }
	   }.start();
	}
}
