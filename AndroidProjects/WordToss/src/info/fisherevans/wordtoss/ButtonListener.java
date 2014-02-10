package info.fisherevans.wordtoss;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class ButtonListener implements OnClickListener
{
	private WordTossActivity parent;
	
	public ButtonListener(WordTossActivity parent)
	{
		this.parent = parent;
	}

	public void onClick(View v)
	{
		if(v instanceof Button && !parent.solving)
		{
			Button button = (Button) v;
			if(button.getText().toString().equals(parent.getString(R.string.checkButtonText)) && !parent.solved)
			{
				parent.solved = parent.wordMath.isEqual();
				if(parent.solved)
				{
					parent.counter.cancel();
					parent.database.addScore(parent.currentWord, parent.currentWordScrambled, parent.countTime, parent.diff);
					parent.colorTiles(parent.rightTileBG);
					parent.nextButton.setText(parent.getString(R.string.nextButtonText));
					parent.googleButton.setTextColor(Color.WHITE);
				}
				else
				{
					parent.countTime += 5000;
					parent.paintWrongTiles();
				}
				  
			}
			else if(button.getText().toString().equals(parent.getString(R.string.solveButtonText)) && !parent.solved)
			{
				parent.counter.cancel();
				parent.solveItOut();
				Log.i("WordToss","Solving it....");
			}
			else if(button.getText().toString().equals(parent.getString(R.string.nextButtonText)))
			{
				parent.nextWordCall();
			}
			else if(button.getText().toString().equals(parent.getString(R.string.skipButtonText)))
			{
				parent.nextWordCall();
			}
			else if(button.getText().toString().equals(parent.getString(R.string.googleButtonText)) && parent.solved)
			{
				Uri uri = Uri.parse("http://www.google.com/search?q=define:" + parent.currentWord.toLowerCase());
				Intent google = new Intent( Intent.ACTION_VIEW, uri );
				parent.startActivity(google);
			}
		}
	}
}
