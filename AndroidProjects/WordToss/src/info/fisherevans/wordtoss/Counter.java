package info.fisherevans.wordtoss;

import android.os.CountDownTimer;

public class Counter extends CountDownTimer
{
	private long time, interval;
	private WordTossActivity parent;
	
	public Counter(long time, long interval, WordTossActivity parent)
	{
		super(time, interval);
		this.time = interval;
		this.interval = interval;
		this.parent = parent;
	}
	
	public void onFinish()
	{ }

	public void onTick(long millisUntilFinished)
	{
		parent.addTime(interval);
	}
}
