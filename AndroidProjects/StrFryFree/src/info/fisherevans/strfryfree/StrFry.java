package info.fisherevans.strfryfree;

import info.fisherevans.strfryfree.R;
import info.fisherevans.strfryfree.scores.Scores;
import info.fisherevans.strfryfree.sub_activities.About;
import info.fisherevans.strfryfree.sub_activities.Free;
import info.fisherevans.strfryfree.sub_activities.Help;
import info.fisherevans.strfryfree.sub_activities.HighScores;
import info.fisherevans.strfryfree.sub_activities.Preferences;
import info.fisherevans.strfryfree.utils.Counter;
import info.fisherevans.strfryfree.utils.TileSorter;
import info.fisherevans.strfryfree.views.LetterTile;

import java.util.ArrayList;
import java.util.Collections;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StrFry extends Activity
{
	// === GLOBAL OBJECTS ===
	public Words words;
	public static SQL sql;
	public OnClickListener buttons;
	public Res res;
	public Counter timeCounter;
	public Scores scores;
	
	// === TILES ===
	public ArrayList<LetterTile> currentTiles;
	public final int defaultTileBG = Color.rgb(120, 120, 120),
					 pressedTileBG = Color.rgb(90, 95, 115),
					 rightTileBG = Color.rgb(90, 220, 90),
					 wrongTileBG = Color.rgb(220, 95, 85);
	public final int defaultTileText = Color.WHITE;
	
	// === LAYOUT ===
	public static FrameLayout gameWrapper;
	public FrameLayout buttonWrapper;
	public Button checkButton, solveButton, nextButton, googleButton, timerButton;
	
		// Layout vars
	public final static int maxLetters = 9;
	public final static int maxButtons = 5;
	
	public static int screenWidth;
	public static int buttonWidth, buttonHeight, buttonPadding, buttonShift, buttonTextSize;
	public static int currentTileOffset, tileWidth, tileHeight, tilePadding, tileStroke, tileShift;
	
	// === GAME VARS ===
	public String currentWord, currentWordScrambled;
	public long solveTime;
	public boolean solved, solving, skipping, paused, backBurner;
	
	// === PREFS ===
	public int diff;
	public boolean time, google, sound;
	
	// === FREE VERSION ===
	private boolean free = true;
	private AdView adView, adView2;
	
	// =============================================
	//       Over-rides
	// =============================================
	
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    	//Log.i("StrFry","   ---=== NEW INSTANCE OF STRFRY ===---");
        setContentView(R.layout.main);
        
        sql = new SQL(this);
        buttons = (OnClickListener) new Buttons(this);
        res = new Res(this);
        words = new Words(this);
        timeCounter = new Counter(this);
        scores = new Scores();

		currentTiles = new ArrayList<LetterTile>();

		gameWrapper = (FrameLayout) findViewById(R.id.gameWrapper);
		buttonWrapper = (FrameLayout) findViewById(R.id.buttonWrapper);

		Display display = getWindowManager().getDefaultDisplay(); 
		getLayoutParams(display.getWidth());

		googleButton = (Button) findViewById(R.id.googleButton);
		checkButton = (Button) findViewById(R.id.checkButton);
		solveButton = (Button) findViewById(R.id.solveButton);
		nextButton = (Button) findViewById(R.id.nextButton);
		timerButton = (Button) findViewById(R.id.timerButton);

		skipping = false;
		solving = false;
		paused = false;
		
		getPrefs();
		
        if(free)
        {
			Intent splashScreen = new Intent(getApplicationContext(), Free.class);
			startActivity(splashScreen);
        }

        adView = new AdView(this, AdSize.BANNER, "a14f69d0d010099");
        LinearLayout layout = (LinearLayout)findViewById(R.id.wrapper);
        layout.addView(adView);
        adView.setPadding(0, 0, 0, 0);
        adView.setPadding(0,  0, 0, 0);
        adView.loadAd(new AdRequest());
		
		createNewWord(words.getWord(diff));
    }
    
	public void onResume()
	{
		super.onResume();
		
		backBurner = false;
		
		//Log.i("WordToss","Resuming main activity.");

		int oldDiff = diff;
		getPrefs();

		if(time)
			timerButton.setVisibility(View.VISIBLE);
		else
			timerButton.setVisibility(View.INVISIBLE);
		
		if(google)
		{
			googleButton.setVisibility(View.VISIBLE);
			if(solved)
				googleButton.setTextColor(Color.WHITE);
		}
		else
			googleButton.setVisibility(View.INVISIBLE);
		
		initButtons();
		
		if(oldDiff != diff)
			createNewWord(words.getWord(diff));

		Display display = getWindowManager().getDefaultDisplay(); 
		if(screenWidth < display.getHeight())
			Toast.makeText(getBaseContext(), getString(R.string.game_view), Toast.LENGTH_LONG).show();
	}
	
	public void onPause()
	{
		super.onPause();

		backBurner = true;
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		if (adView != null)
			adView.destroy();
	}
	
	// =============================================
	//       Actions
	// =============================================
	
	public static void getLayoutParams(int width)
	{
		screenWidth = width;
		tileWidth = (int) ((screenWidth*0.9)/maxLetters);
		tileHeight = (int) (tileWidth*1.35);
		tilePadding = (int) (tileWidth*0.2);
		tileStroke = ((int) (tileWidth*0.033)) * 2;
		buttonWidth = (int) ((screenWidth*0.9)/(maxButtons));
		buttonHeight = (int) (buttonWidth*0.35);
		buttonTextSize = (int) (buttonHeight*0.5);
		buttonPadding = (int) (buttonWidth*0.1);
		buttonShift = (int) ((screenWidth-(buttonWidth*(maxButtons)))/(maxButtons));
		if(Build.VERSION.SDK_INT != 13)
			gameWrapper.setPadding(0, 0, 0, (2*tilePadding));
	}
	
	public void getPrefs()
	{
		SharedPreferences prefs = getSharedPreferences("info.fisherevans.strfry", Context.MODE_PRIVATE);
		diff = (prefs.getInt(".diff", 30)/20);
		diff = diff > 4 ? 4 : diff;
		diff = diff < 0 ? 0 : diff;
		time = prefs.getBoolean(".time", false);
		google = prefs.getBoolean(".google", false);
		sound = prefs.getBoolean(".sound", true);
		//Log.i("StrFry", "Got PRefs - diff:" + diff + " time:" + time + " google:" + google + " sound:" + sound);
	}
	
	public void createNewWord(String newWord)
	{
		currentWord = newWord;
		currentWordScrambled = words.math.scrambleWord(currentWord);
		solved = false;
		skipping = false;
		clearDisplay();
		initTiles(currentWordScrambled);
		timeCounter.cancel();
		solveTime = 0;
		timeCounter.start();
		//nextButton.setText(getResources().getString(R.string.skipButtonText));
		googleButton.setTextColor(Color.GRAY);
	}
	
	public void initTiles(String word)
	{
		currentTiles = new ArrayList<LetterTile>();
		for(int loop = 0;loop < word.length();loop++)
		{
			LetterTile tempTile = new LetterTile(this, word.charAt(loop));
			currentTiles.add(tempTile);
			tempTile.setBackgroundColor(defaultTileBG);
		}

		for(LetterTile tile : currentTiles)
			gameWrapper.addView(tile);

		tileShift = (int) ((screenWidth-(tileWidth*currentTiles.size()))/(currentTiles.size()+1));
		
		//Log.i("WordToss", currentWord + " - # Letters:" + currentTiles.size() + " - Shift:" + tileShift);
		
		snapTiles();
	}

	// =============================================
	//       Display
	// =============================================

	public void addTime(long add)
	{
		if(!paused && !backBurner)
		{
			solveTime += add;
			timerButton.setText((solveTime/10.0)+"");
		}
	}
	
	public void clearDisplay()
	{
		gameWrapper.removeAllViews();
	}

	
	public void initButtons()
	{
		initButton(googleButton, getString(R.string.googleButtonText));
		initButton(checkButton, getString(R.string.checkButtonText));
		initButton(solveButton, getString(R.string.solveButtonText));
		initButton(nextButton, getString(R.string.skipButtonText));
		initButton(timerButton, (solveTime/10)+"");
		
		int left = (int)(buttonShift/2);

		positionButton(googleButton, left);
		left += buttonShift + buttonWidth;
		positionButton(solveButton, left);
		left += buttonShift + buttonWidth;
		positionButton(checkButton, left);
		left += buttonShift + buttonWidth;
		positionButton(nextButton, left);
		left += buttonShift + buttonWidth;
		positionButton(timerButton, left);
		
		if(Build.VERSION.SDK_INT != 13)
			buttonWrapper.setPadding(0, 0, 0, (3*buttonPadding));

		TextView freeDisc = (TextView) findViewById(R.id.freeDisc);
		freeDisc.setTextSize((int)(buttonTextSize*0.6));
	}

	public void positionButton(Button button, int left)
	{
		FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(buttonWidth, buttonHeight, Gravity.TOP|Gravity.LEFT);
		layout.setMargins(left,buttonPadding,buttonPadding,buttonPadding*3);
		buttonWrapper.updateViewLayout(button, layout);
	}

	public void initButton(Button button, String text)
	{
		button.setWidth(buttonWidth);
		button.setHeight(buttonHeight);
		button.setOnClickListener(buttons);
		button.setText(text);
		button.setTextSize(buttonTextSize);
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
					setTilePosition(getTileTo(tile, -1), target);
				else
					setTilePosition(getTileTo(tile, -1), moveTarget);
			}
			if(x <= target - (tileWidth + tileShift))
				sortTiles();
		}
		else if(x > target)
		{
			if(tileId != currentTiles.size()-1)
			{
				float moveTarget = ((tileId+1)*(tileShift+tileWidth) + tileShift) + (target-x);
				if(moveTarget < target)
					setTilePosition(getTileTo(tile, 1), target);
				else
					setTilePosition(getTileTo(tile, 1), moveTarget);
			}
			
			if(x >= target + (tileWidth + tileShift))
				sortTiles();
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
	
	public void nextWord()
	{
		if(!skipping && !solving)
		{
			res.playSwish();
			skipping = true;
			paused = true;
			TranslateAnimation slide = new TranslateAnimation(0, -(screenWidth + 50), 0,0 );
			slide.setDuration(400);
			slide.setFillAfter(true);
			slide.setAnimationListener(new AnimationListener()
			{
				public void onAnimationRepeat(Animation a) { } public void onAnimationStart(Animation a) { } public void onAnimationEnd(Animation a)
				{
					createNewWord(words.getWord(diff));
					
					TranslateAnimation slide = new TranslateAnimation((screenWidth + 50), 0, 0,0 );   
					slide.setDuration(400);   
					slide.setFillAfter(true); 
					slide.setAnimationListener(new AnimationListener()
					{
						public void onAnimationRepeat(Animation a) { } public void onAnimationStart(Animation a) { } public void onAnimationEnd(Animation a)
						{
							skipping = false;
							paused = false;
						}
					});  
					gameWrapper.startAnimation(slide);
				}
			});  
			gameWrapper.startAnimation(slide);
		}
	}

	public void solveTiles()
	{
		if(!solved)
		{
			solved = true;
			solving = true;
			
			Animation fadeOut = AnimationUtils.loadAnimation(this,R.anim.fadeout);
			fadeOut.setAnimationListener(new AnimationListener()
			{
				public void onAnimationRepeat(Animation a) { } public void onAnimationStart(Animation a) { } public void onAnimationEnd(Animation a)
				{
					currentTiles = new ArrayList<LetterTile>();
					for(int loop = 0;loop < currentWord.length();loop++)
						currentTiles.add(new LetterTile(StrFry.this, currentWord.charAt(loop)));
					
					for(LetterTile temp : currentTiles)
						gameWrapper.addView(temp);
					
					snapTiles();
					colorTiles(rightTileBG);

					gameWrapper.setAnimation(AnimationUtils.loadAnimation(StrFry.this,R.anim.fadein));
					nextButton.setText(getString(R.string.nextButtonText));
					googleButton.setTextColor(Color.WHITE);
					
					solving = false;
				}
			});
			gameWrapper.setAnimation(fadeOut);
		}
	}
	
	// === TILE METHODS ===

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
				return i;
		}
		return 0;
	}
	
	public void paintWrongTiles()
	{
		StringBuilder tempString = new StringBuilder(currentWord);
		
		for(int i = 0;i < currentTiles.size();i++)
		{
			
			if(currentTiles.get(i).getLetter() == tempString.charAt(i))
				colorTile(currentTiles.get(i), rightTileBG);
			else
				colorTile(currentTiles.get(i), wrongTileBG);
		}
	}
	
	public LetterTile getTileFromLetter(char letter)
	{
		for(int i = currentTiles.size() - 1;i >= 0;i--)
		{
			if(currentTiles.get(i).getLetter() == letter)
				return currentTiles.get(i);
		}
		return currentTiles.get(0);
	}
	
	public LetterTile getTileTo(LetterTile tile, int shift)
	{
		return currentTiles.get(getTileId(tile) + shift);
	}
	
	public void colorTileText(int color)
	{
		for(LetterTile tile : currentTiles)
			tile.setTextColor(color);
	}
	
	public void colorTiles(int color)
	{
		for(LetterTile tile : currentTiles)
			tile.setBackgroundColor(color);
	}
	
	public void colorTile(LetterTile tile, int color)
	{
		tile.setBackgroundColor(color);
	}
}