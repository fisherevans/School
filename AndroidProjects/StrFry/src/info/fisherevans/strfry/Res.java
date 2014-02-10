package info.fisherevans.strfry;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Res
{
	private StrFry parent;

	public Res(StrFry newParent)
	{
		parent = newParent;
	}
	
	public void playPick()
	{
		playSound(R.raw.pick);
	}
	
	public void playDrop()
	{
		playSound(R.raw.drop);
	}
	
	public void playDing()
	{
		playSound(R.raw.ding);
	}
	
	public void playBuzz()
	{
		playSound(R.raw.buzz);
	}
	
	public void playSwish()
	{
		playSound(R.raw.swish);
	}

	public void playSound(int id)
	{
		if(parent.sound)
		{
			Thread play = new Thread(new Play(id));
			play.start();
		}
	}
	
	public class Play implements Runnable
	{
		int id;
		public Play(int newId)
		{
			id = newId;
		}
		
		public void run()
		{
			MediaPlayer mp = MediaPlayer.create(parent, id);
			mp.setOnCompletionListener(new OnCompletionListener()
			{
				public void onCompletion(MediaPlayer mp)
				{
					mp.release();
				}
			});
			mp.start();
		}
	}
}
