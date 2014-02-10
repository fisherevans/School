package info.fisherevans.wordtoss;
import info.fisherevans.wordtoss.R;
import info.fisherevans.wordtoss.R.anim;
import info.fisherevans.wordtoss.R.id;
import info.fisherevans.wordtoss.R.layout;
import info.fisherevans.wordtoss.R.menu;
import info.fisherevans.wordtoss.R.string;
import info.fisherevans.wordtoss.high.DatabaseSystem;
import info.fisherevans.wordtoss.high.HighActivity;
import info.fisherevans.wordtoss.pref.PreferencesActivity;
import info.fisherevans.wordtoss.views.LetterTile;
import info.fisherevans.wordtoss.views.TileSorter;
import info.fisherevans.wordtoss.words.WordMath;
import info.fisherevans.wordtoss.words.Words;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WordTossActivity extends Activity
{
	private boolean firstRun = true; // Trying to stop the redraw on screen rotation....
	
	public final int defaultTileBG = Color.GRAY;
	public final int dragTileBG = Color.DKGRAY;
	public final int wrongTileBG = Color.RED;
	public final int rightTileBG = Color.GREEN;
	public final int defaultTileText = Color.WHITE;

	public final int maxLetters = 10;
	public final int maxButtons = 3;
	
	public final long countInterval = 100;
	public long countTime;

	public ArrayList<LetterTile> currentTiles, currentMovingTiles;
	
	public TextView timer;
	
	public FrameLayout gameWrapper, buttonWrapper;
	public Button checkButton, solveButton, nextButton, googleButton, timerButton;

	public ButtonListener buttonListener;
	public Words words;
	
	public Counter counter;
	
	public DatabaseSystem database;
	
	public int screenWidth, currentTileOffset, tileWidth, tileHeight, tilePadding, tileStroke, tileShift, buttonWidth, buttonHeight, buttonPadding, buttonShift, buttonTextSize;
	public boolean solved, solving, skipping;
	public String currentWord, currentWordScrambled;
	
	public int solveTime, solveInterval;
	public float solveStep;
	
	public WordMath wordMath;
	
	public int diff;
	public boolean time, google;
	
	private ProgressDialog loadScreen;
	
	public boolean loading;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Log.i("WordToss","---------NEW INSTANCE---------");

		loadScreen = ProgressDialog.show(this, "WordToss", "Loading the word database. Please wait...");
		
		wordMath = new WordMath(this);
		database = new DatabaseSystem(this);
		counter = new Counter(999999999, countInterval, this);
			
		currentTiles = new ArrayList<LetterTile>();
		currentMovingTiles = new ArrayList<LetterTile>();
		buttonListener = new ButtonListener(this);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenWidth = size.x;
		
		tileWidth = (int) ((screenWidth*0.9)/maxLetters);
		tileHeight = (int) (tileWidth*1.35);
		tilePadding = (int) (tileWidth*0.2);
		tileStroke = ((int) (tileWidth*0.033)) * 2;
		buttonWidth = (int) (screenWidth/(2*maxButtons));
		buttonHeight = (int) (buttonWidth*0.4);
		buttonTextSize = (int) (buttonHeight*0.6);
		buttonPadding = (int) (buttonWidth*0.1);
		buttonShift = (int) ((screenWidth-(buttonWidth*(maxButtons+2)))/((maxButtons+2)+1));
		
		solveTime = 2000;
		solveInterval = 50;
		solveStep = solveTime/solveInterval;
		
		gameWrapper = (FrameLayout) findViewById(R.id.gameWrapper);
		buttonWrapper = (FrameLayout) findViewById(R.id.buttonWrapper);
		
		gameWrapper.setBackgroundColor(Color.DKGRAY);

		googleButton = (Button) findViewById(R.id.googleButton);
		checkButton = (Button) findViewById(R.id.checkButton);
		solveButton = (Button) findViewById(R.id.solveButton);
		nextButton = (Button) findViewById(R.id.nextButton);
		timerButton = (Button) findViewById(R.id.timerButton);

		skipping = false;
		solving = false;
		
		wordLoader load = new wordLoader(this);
		Thread loadThread = new Thread(load);
		loadThread.start();
	}
	
	public void onResume()
	{
		super.onResume();
		resumeActivity();
	}
	
	public void resumeActivity()
	{
		Log.i("WordToss","Resuming main activity.");

		SharedPreferences prefs = getSharedPreferences("info.fisherevans.wordtoss", Context.MODE_PRIVATE);
		diff = (prefs.getInt(".diff", 25)/25);
		time = prefs.getBoolean(".time", false);
		google = prefs.getBoolean(".google", false);

		if(time)
		{
			timerButton.setVisibility(View.VISIBLE);
		}
		else
		{
			timerButton.setVisibility(View.INVISIBLE);
		}
		
		if(google)
		{
			googleButton.setVisibility(View.VISIBLE);
			if(solved)
			{
				googleButton.setTextColor(Color.WHITE);
			}
		}
		else
		{
			googleButton.setVisibility(View.INVISIBLE);
		}
	}
	
	public void createNewWord(String newWord)
	{
		currentWord = newWord;
		currentWordScrambled = wordMath.scrambleWord(currentWord);
		solved = false;
		clearDisplay();
		initiateTiles(currentWordScrambled);
		counter.cancel();
		countTime = 0;
		counter.start();
		googleButton.setTextColor(Color.GRAY);
	}
	
	public void addTime(long add)
	{
		countTime += add;
		String tempTime = timeToString(countTime);
		timerButton.setText(tempTime);
	}
	
	public String timeToString(long time)
	{
		return String.format("%d.%1d", 
				TimeUnit.MILLISECONDS.toSeconds(time),
				(TimeUnit.MILLISECONDS.toMicros(time) - TimeUnit.SECONDS.toMicros(TimeUnit.MILLISECONDS.toSeconds(time)))/100000
			);
	}
	
	public void clearDisplay()
	{
		gameWrapper.removeAllViews();
		initButtons();
	}
	
	public void initButtons()
	{
		initButton(googleButton, getString(R.string.googleButtonText));
		initButton(checkButton, getString(R.string.checkButtonText));
		initButton(solveButton, getString(R.string.solveButtonText));
		initButton(nextButton, getString(R.string.skipButtonText));
		initButton(timerButton, "");
		
		int left = buttonShift;

		positionButton(googleButton, left);
		left += buttonShift + buttonWidth;
		positionButton(solveButton, left);
		left += buttonShift + buttonWidth;
		positionButton(checkButton, left);
		left += buttonShift + buttonWidth;
		positionButton(nextButton, left);
		left += buttonShift + buttonWidth;
		positionButton(timerButton, left);
	}
	
	public void positionButton(Button button, int left)
	{
		FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(buttonWidth, buttonHeight, Gravity.TOP|Gravity.LEFT);
		layout.setMargins(left,buttonPadding,buttonPadding,buttonPadding);
		buttonWrapper.updateViewLayout(button, layout);
	}
	
	public void initButton(Button button, String text)
	{
		button.setWidth(buttonWidth);
		button.setHeight(buttonHeight);
		button.setOnClickListener(buttonListener);
		button.setText(text);
		button.setTextSize(buttonTextSize);
	}
	
	public void initiateTiles(String word)
	{
		currentTiles = new ArrayList<LetterTile>();
		for(int loop = 0;loop < word.length();loop++)
		{
			currentTiles.add(new LetterTile(this, word.charAt(loop)));
		}

		for(LetterTile tile : currentTiles)
		{
			gameWrapper.addView(tile);
		}

		tileShift = (int) ((screenWidth-(tileWidth*currentTiles.size()))/(currentTiles.size()+1));
		
		Log.i("WordToss", currentWord + " - # Letters:" + currentTiles.size() + " - Shift:" + tileShift);
		
		snapTiles();
	}
	
	public void snapTiles()
	{
		int left = tileShift;
		FrameLayout.LayoutParams layout;
		for(LetterTile tile : currentTiles)
		{
			layout = new FrameLayout.LayoutParams
					(tileWidth, tileHeight, Gravity.TOP|Gravity.LEFT);
			layout.setMargins(left,tilePadding,0,tilePadding);
			gameWrapper.updateViewLayout(tile, layout);
			left += tileShift + tileWidth;
		}
	}
	
	public void setMovingTilePosition(LetterTile tile, float x)
	{
		int tileId = getTileId(tile);
		float target = tileId*(tileShift+tileWidth) + tileShift;
		
		setTilePosition(tile, x);
		
		if(x < target)
		{
			if(tileId != 0)
			{
				float moveTarget = ((tileId-1)*(tileShift+tileWidth) + tileShift) + (target-x);
				if(moveTarget > target)
				{
					setTilePosition(getTileTo(tile, -1), target);
				}
				else
				{
					setTilePosition(getTileTo(tile, -1), moveTarget);
				}
			}
			if(x <= target - (tileWidth + tileShift))
			{
				sortTiles();
			}
		}
		else if(x > target)
		{
			if(tileId != currentTiles.size()-1)
			{
				float moveTarget = ((tileId+1)*(tileShift+tileWidth) + tileShift) + (target-x);
				if(moveTarget < target)
				{
					setTilePosition(getTileTo(tile, 1), target);
				}
				else
				{
					setTilePosition(getTileTo(tile, 1), moveTarget);
				}
			}
			if(x >= target + (tileWidth + tileShift))
			{
				sortTiles();
			}
		}
		bringToFront(tile);
	}
	
	public void setTilePosition(LetterTile tile, float x)
	{
		if(x >= 0 && x <= gameWrapper.getWidth() - tileWidth && ((ViewGroup) gameWrapper).indexOfChild(tile) != -1)
		{
			FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams
					(tileWidth, tileHeight, Gravity.TOP|Gravity.LEFT);
			layout.setMargins((int) x, tilePadding, 0, tilePadding);
			gameWrapper.updateViewLayout(tile, layout);
		}
	}
	
	public void nextWordCall()
	{
		if(!skipping && !solving)
		{
			skipping = true;
			TranslateAnimation slide = new TranslateAnimation(0, -screenWidth, 0,0 );
			slide.setDuration(400);
			slide.setFillAfter(true);
			slide.setAnimationListener(new AnimationListener()
			{
				public void onAnimationRepeat(Animation a) { }
				public void onAnimationStart(Animation a) { }
				public void onAnimationEnd(Animation a)
				{
					WordTossActivity.this.nextWordStep();
				}
			});  
			gameWrapper.startAnimation(slide);
		}
	}
	
	public void nextWordStep()
	{
		createNewWord(words.randomWord());
		
		TranslateAnimation slide = new TranslateAnimation(screenWidth, 0, 0,0 );   
		slide.setDuration(400);   
		slide.setFillAfter(true); 
		slide.setAnimationListener(new AnimationListener()
		{
			public void onAnimationRepeat(Animation a) { }
			public void onAnimationStart(Animation a) { }
			public void onAnimationEnd(Animation a)
			{
				WordTossActivity.this.skipping = false;
			}
		});  
		gameWrapper.startAnimation(slide);
	}
	
	public void solveItOut()
	{
		if(!solved)
		{
			solved = true;
			solving = true;
			
			Animation fadeOut = AnimationUtils.loadAnimation(this,R.anim.fadeout);
			fadeOut.setAnimationListener(new AnimationListener()
			{
				public void onAnimationRepeat(Animation a) { }
				public void onAnimationStart(Animation a) { }
				public void onAnimationEnd(Animation a)
				{
					WordTossActivity.this.solveItSolve();
				}
			});
			gameWrapper.setAnimation(fadeOut);
		}
	}
	
	public void solveItSolve()
	{
		currentTiles = new ArrayList<LetterTile>();
		for(int loop = 0;loop < currentWord.length();loop++)
		{
			currentTiles.add(new LetterTile(this, currentWord.charAt(loop)));
		}
		for(LetterTile temp : currentTiles)
		{
			gameWrapper.addView(temp);
		}
		snapTiles();
		colorTiles(rightTileBG);

		gameWrapper.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fadein));
		nextButton.setText(getString(R.string.nextButtonText));
		googleButton.setTextColor(Color.WHITE);
		
		solving = false;
	}
	
	public void sortTiles()
	{
		Collections.sort(currentTiles, new TileSorter());
	}
	
	public void bringToFront(LetterTile tile)
	{
		tile.bringToFront();
		gameWrapper.refreshDrawableState();
	}
	
	public int getTileId(LetterTile tile)
	{
		for(int i = 0;i < currentTiles.size();i++)
		{
			if(currentTiles.get(i) == tile)
			{
				return i;
			}
		}
		return 0;
	}
	
	public void paintWrongTiles()
	{
		StringBuilder tempString = new StringBuilder(currentWord);
		
		for(int i = 0;i < currentTiles.size();i++)
		{
			
			if(currentTiles.get(i).getLetter() == tempString.charAt(i))
			{
				colorTile(currentTiles.get(i), Color.GREEN);
			}
			else
			{
				colorTile(currentTiles.get(i), Color.RED);
			}
		}
	}
	
	public LetterTile getTileFromLetter(char letter)
	{
		for(int i = currentTiles.size() - 1;i >= 0;i--)
		{
			if(currentTiles.get(i).getLetter() == letter)
			{
				return currentTiles.get(i);
			}
		}
		return currentTiles.get(0);
	}
	
	public LetterTile getTileTo(LetterTile tile, int shift)
	{
		return currentTiles.get(getTileId(tile) + shift);
	}
	
	public void colorTiles(int color)
	{
		for(LetterTile tile : currentTiles)
		{
			tile.setBackgroundColor(color);
		}
	}
	
	public void colorTile(LetterTile tile, int color)
	{
		tile.setBackgroundColor(color);
	}
	
	public void playSound(int id)
	{
		MediaPlayer mp = MediaPlayer.create(this, id);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener()
		{
			public void onCompletion(MediaPlayer mp)
			{
				mp.release();
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) // Look for menu press
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) // Listener for menu
	{
		switch(item.getItemId())
		{
			case R.id.prefButton:
				Intent prefScreen = new Intent(getApplicationContext(), PreferencesActivity.class);
				startActivity(prefScreen);
				return true;
				
			case R.id.highButton:
				Intent highScreen = new Intent(getApplicationContext(), HighActivity.class);
				highScreen.putStringArrayListExtra("scores", database.scoresToStrings(database.getAllScores()));
				startActivity(highScreen);
				return true;
					
			default: 
				return super.onOptionsItemSelected(item);
		}
	}
	
	public class wordLoader implements Runnable
	{
		private WordTossActivity parent;
		public wordLoader (WordTossActivity newParent)
		{
			parent = newParent;
		}
		public void run()
		{
			parent.words = new Words(parent);
			parent.loadScreen.dismiss();
			Log.i("WordToss","Done loading the app");
			nextWordCall();
			Intent loadFlash = new Intent(getApplicationContext(), LoadingActivity.class);
			startActivity(loadFlash);
			countTime -= 1500;
		}
	}
}