package info.fisherevans.strfryfree.sub_activities;

import java.util.ArrayList;

import info.fisherevans.strfryfree.StrFry;
import info.fisherevans.strfryfree.scores.Score;
import info.fisherevans.strfryfree.scores.Scores;
import info.fisherevans.strfryfree.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HighScores extends Activity
{
	private TableLayout mainTable;
	private ArrayList<Score> scoreList;
	Scores scores;
	public Button eButton, emButton, mButton, mhButton, hButton;
	public LinearLayout buttonWrapperHigh;
	public ArrayList<Button> buttonList;

	public int diff;
	public boolean time, google;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high);

		Display display = getWindowManager().getDefaultDisplay(); 
		StrFry.getLayoutParams(display.getWidth());
		
		getPrefs();

		eButton = (Button) findViewById(R.id.eButton);
		emButton = (Button) findViewById(R.id.emButton);
		mButton = (Button) findViewById(R.id.mButton);
		mhButton = (Button) findViewById(R.id.mhButton);
		hButton = (Button) findViewById(R.id.hButton);
		buttonList = new ArrayList<Button>();
		buttonList.add(eButton); buttonList.add(emButton); buttonList.add(mButton); buttonList.add(mhButton); buttonList.add(hButton);
		buttonWrapperHigh = (LinearLayout) findViewById(R.id.highButtonLayout);

		scores = new Scores();
		Toast.makeText(getBaseContext(), "In the free version, only the top 3 scores are kept.", Toast.LENGTH_LONG).show();
		Toast.makeText(getBaseContext(), "In the free version, only the top 3 scores are kept.", Toast.LENGTH_LONG).show();
		Toast.makeText(getBaseContext(), "In the free version, only the top 3 scores are kept.", Toast.LENGTH_LONG).show();
	}
	
	public void onResume()
	{
		super.onResume();
		Display display = getWindowManager().getDefaultDisplay(); 
		StrFry.getLayoutParams(display.getWidth());
		setView(diff);
	}
	
	public void getPrefs()
	{
		SharedPreferences prefs = getSharedPreferences("info.fisherevans.strfry", Context.MODE_PRIVATE);
		diff = (prefs.getInt(".diff", 30)/20);
		diff = diff > 4 ? 4 : diff;
		diff = diff < 0 ? 0 : diff;
		time = prefs.getBoolean(".time", false);
		google = prefs.getBoolean(".google", false);
		//Log.i("StrFry", "Got PRefs - diff:" + diff + " time:" + time + " google:" + google);
	}
	
	public TextView defaultTextView(String text)
	{
		TextView tempText = new TextView(this);
		tempText.setText(text);
		tempText.setTextSize((int)(StrFry.buttonTextSize*0.9));
		tempText.setPadding(StrFry.buttonPadding, StrFry.buttonPadding, StrFry.buttonPadding, StrFry.buttonPadding);
		tempText.setBackgroundColor(Color.BLACK);
		tempText.setTextColor(Color.WHITE);
		tempText.setWidth((int)(StrFry.screenWidth*2.0/7.0));
		tempText.setGravity(Gravity.CENTER);
		tempText.setBackgroundDrawable(getResources().getDrawable(R.drawable.grad_back));
		return tempText;
	}
	
	public TextView headerTextView(String text)
	{
		TextView tempText = new TextView(this);
		tempText.setText(text);
		tempText.setTextSize(eButton.getTextSize());
		tempText.setPadding(0, 10, 0, 10);
		tempText.setBackgroundColor(Color.BLACK);
		tempText.setTextColor(Color.WHITE);
		tempText.setWidth((int)(StrFry.screenWidth*2.0/7.0));
		tempText.setGravity(Gravity.CENTER);
		return tempText;
	}
	
	public void setView(int diff)
	{
		this.diff = diff;
		setButtonDefault();
		highlightButton(buttonList.get(diff));
		scoreList = scores.getScores(diff);
		mainTable = (TableLayout) findViewById(R.id.scoreTable);
		mainTable.removeAllViews();
		TableRow tempRow = new TableRow(this);
		
		TextView rank = headerTextView("Rank");
		rank.setWidth((int)(StrFry.screenWidth/7.0));
		tempRow.addView(rank);
		tempRow.addView(headerTextView("Word"));
		tempRow.addView(headerTextView("Scramble Word"));
		tempRow.addView(headerTextView("Time (Seconds)"));
		mainTable.addView(tempRow);
		
		int loop = 1;
		for(Score tempScore : scoreList)
		{
			tempRow = new TableRow(this);
			rank = defaultTextView(loop+"");
			rank.setWidth((int)(StrFry.screenWidth/7.0));
			tempRow.addView(rank);
			tempRow.addView(defaultTextView(tempScore.getWord()));
			tempRow.addView(defaultTextView(tempScore.getWordS()));
			tempRow.addView(defaultTextView(""+(tempScore.getTime()/10.0)));
			mainTable.addView(tempRow);
			loop++;
		}
	}
	
	public void setButtonDefault()
	{
		for(Button temp : buttonList)
		{
			temp.setBackgroundColor(Color.DKGRAY);
			temp.setTextColor(Color.WHITE);
		}
	}
	
	public void highlightButton(Button temp)
	{
		temp.setBackgroundColor(Color.rgb(120, 150, 255));
		temp.setTextColor(Color.BLACK);
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.eButton:
			{
				setView(0);
				break;
			}
			case R.id.emButton:
			{
				setView(1);
				break;
			}
			case R.id.mButton:
			{
				setView(2);
				break;
			}
			case R.id.mhButton:
			{
				setView(3);
				break;
			}
			case R.id.hButton:
			{
				setView(4);
			}
		}
	}
	
	public void goBack(View v)
	{
		finish();
	}
	
}
