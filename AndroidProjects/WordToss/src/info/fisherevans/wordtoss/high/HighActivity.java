package info.fisherevans.wordtoss.high;

import java.util.ArrayList;
import java.util.Collections;

import info.fisherevans.wordtoss.R;
import info.fisherevans.wordtoss.R.layout;
import info.fisherevans.wordtoss.high.sorting.SortTime;
import info.fisherevans.wordtoss.views.TileSorter;
import info.fisherevans.wordtoss.WordTossActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighActivity extends Activity
{
	private TableLayout mainTable;
	private ArrayList<Score> scores;
	
	//private WordTossActivity parent;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high);
		
		scores = new ArrayList<Score>();
		
		for(String tempScore : getIntent().getStringArrayListExtra("scores"))
		{
			String[] info = tempScore.split(":");
			scores.add(new Score(info[0], info[1], Float.parseFloat(info[2]), Integer.parseInt(info[3])));
		}
		
		//parent = (WordTossActivity) getIntent().getSerializableExtra("parent");
		
		mainTable = (TableLayout) findViewById(R.id.scoreTable);

		TableRow tempRow = new TableRow(this);

		tempRow.addView(headerTextView("Word"));
		tempRow.addView(headerTextView("Scramble Word"));
		tempRow.addView(headerTextView("Time (Seconds)"));
		tempRow.addView(headerTextView("Difficulty"));
		
		mainTable.addView(tempRow);
		
		Collections.sort(scores, new SortTime());
		
		for(Score tempScore : scores)
		{
			if(tempScore.getDiff() == 3)
			{
				tempRow = new TableRow(this);
				
				tempRow.addView(defaultTextView(tempScore.getWord()));
				tempRow.addView(defaultTextView(tempScore.getWordS()));
				tempRow.addView(defaultTextView(""+tempScore.getTime()));
				tempRow.addView(defaultTextView(getStringDiff(tempScore.getDiff())));
				
				mainTable.addView(tempRow);
			}
		}
	}
	
	public TextView defaultTextView(String text)
	{
		TextView tempText = new TextView(this);
		tempText.setText(text);
		tempText.setTextSize(30);
		tempText.setPadding(30, 10, 30, 10);
		
		return tempText;
	}
	
	public TextView headerTextView(String text)
	{
		TextView tempText = new TextView(this);
		tempText.setText(text);
		tempText.setTextSize(40);
		tempText.setPadding(20, 10, 20, 10);
		
		return tempText;
	}
	
	public String getStringDiff(int diff)
	{
		switch(diff)
		{
			case 0:
				return "Easy";
			case 1:
				return "Easy/Medium";
			case 2:
				return "Medium";
			case 3:
				return "Medium/Hard";
			default:
				return "Hard";
		}
	}
}
