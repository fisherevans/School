package info.fisherevans.strfryfree;

import info.fisherevans.strfryfree.R;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Buttons implements OnClickListener
{
	private StrFry parent;

	public Buttons(StrFry newParent)
	{
		parent = newParent;
	}
	
	public void onClick(View v)
	{
		if(v instanceof Button && !parent.solving)
		{
			Button button = (Button) v;
			if(v.getId() == R.id.checkButton && !parent.solved && !parent.paused)
			{
				parent.solved = parent.words.math.isEqual();
				if(parent.solved)
				{
					//Log.i("StrFry", "User checked right answer");
					parent.timeCounter.cancel();
					parent.colorTiles(parent.rightTileBG);
					parent.nextButton.setText(parent.getString(R.string.nextButtonText));
					parent.googleButton.setTextColor(Color.WHITE);
					parent.scores.addScore(parent.currentWord, parent.currentWordScrambled, parent.solveTime, parent.diff);
					parent.res.playDing();
				}
				else
				{
					//Log.i("StrFry", "User checked wrong answer");
					parent.addTime(50);
					parent.paintWrongTiles();
					parent.res.playBuzz();
				}
				  
			}
			else if(v.getId() == R.id.solveButton && !parent.solved)
			{
				//Log.i("StrFry", "User gave up. Solving");
				parent.timeCounter.cancel();
				parent.solveTiles();
				//Log.i("WordToss","Solving it....");
			}
			else if(v.getId() == R.id.nextButton)
			{
				//Log.i("StrFry", "Skipping word");
				parent.nextWord();
			}
			else if(v.getId() == R.id.googleButton && parent.solved)
			{
				//Log.i("StrFry", "Opening google");
				Uri uri = Uri.parse("http://www.google.com/search?q=define:" + parent.currentWord.toLowerCase());
				Intent google = new Intent( Intent.ACTION_VIEW, uri );
				parent.startActivity(google);
			}
			else if(v.getId() == R.id.timerButton && !parent.solved && !parent.skipping)
			{
				//Log.i("StrFry", "Pausing");
				parent.paused = !parent.paused;
				int newColor = parent.paused ? parent.defaultTileBG : Color.WHITE;
				parent.colorTileText(newColor);
				parent.colorTiles(parent.defaultTileBG);
			}
		}
	}
}
