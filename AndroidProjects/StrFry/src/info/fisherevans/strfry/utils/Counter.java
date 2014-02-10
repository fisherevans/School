package info.fisherevans.strfry.utils;

import info.fisherevans.strfry.StrFry;
import android.os.CountDownTimer;

public class Counter extends CountDownTimer
{
	private StrFry parent;
	
	public Counter(StrFry parent)
	{
		super((long)9999999, (long)100);
		this.parent = parent;
	}
	
	public void onFinish()
	{ }

	public void onTick(long millisUntilFinished)
	{
		parent.addTime((long)1);
	}
}
