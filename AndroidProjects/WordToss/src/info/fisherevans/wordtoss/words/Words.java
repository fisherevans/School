package info.fisherevans.wordtoss.words;

import info.fisherevans.wordtoss.R;
import info.fisherevans.wordtoss.WordTossActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

import android.app.ProgressDialog;
import android.util.Log;

public class Words
{
	private Random randomGenerator;
	public ArrayList<String> wordsE, wordsEM, wordsM, wordsMH, wordsH;
	
	private WordTossActivity parent;
	
	public float lowest, highest;
	
	
	public Words(WordTossActivity parent)
	{
		randomGenerator = new Random();
		
		this.parent = parent;

		wordsE = new ArrayList<String>();
		wordsEM = new ArrayList<String>();
		wordsM = new ArrayList<String>();
		wordsMH = new ArrayList<String>();
		wordsH = new ArrayList<String>();
		
		try
		{
			getWords();
		}
		catch(Exception e)
		{
			wordsE.add("BROKEN");
			wordsM.add("BROKEN");
			wordsH.add("BROKEN");
			Log.i("WordToss","There has been an error reading in the files.");
			Log.i("WordToss",e.toString());
		}

		Log.i("WordToss","Lowest Diff:" + lowest + " - Highest Diff:" + highest);
	}
	
	public void setParent(WordTossActivity parent)
	{
		this.parent = parent;
	}
	
	public void getWords() throws Exception
	{
		BufferedReader buffer = new BufferedReader(new InputStreamReader(parent.getResources().openRawResource(R.raw.words)));
		
		String line;
		String tempWord;
		float tempDiff;
		lowest = 20;
		
		while ((line = buffer.readLine()) != null)
		{
			tempWord = line.split(":")[0].toUpperCase();
			tempDiff = (float) ((Float.parseFloat(line.split(":")[1])-2.8)/(14.9-2.8));
			if(tempDiff <= 0.2)
			{
				wordsE.add(tempWord);
			}
			else if(tempDiff <= 0.4)
			{
				wordsEM.add(tempWord);
			}
			else if(tempDiff <= 0.6)
			{
				wordsM.add(tempWord);
			}
			else if(tempDiff <= 0.8)
			{
				wordsMH.add(tempWord);
			}
			else
			{
				wordsH.add(tempWord);
			}
		}
	}
	
	public String randomWord()
	{
		String temp;
		switch(parent.diff)
		{
			case 0:
				temp = wordsE.get(randomGenerator.nextInt(wordsE.size()));
				Log.i("WordToss","Got new word:" + temp + " - Easy");
				break;
			case 1:
				temp = wordsEM.get(randomGenerator.nextInt(wordsEM.size()));
				Log.i("WordToss","Got new word:" + temp + " - EasyMedium");
				break;
			case 2:
				temp = wordsM.get(randomGenerator.nextInt(wordsM.size()));
				Log.i("WordToss","Got new word:" + temp + " - Medium");
				break;
			case 3:
				temp = wordsMH.get(randomGenerator.nextInt(wordsMH.size()));
				Log.i("WordToss","Got new word:" + temp + " - MediumHard");
				break;
			default:
				temp = wordsH.get(randomGenerator.nextInt(wordsH.size()));
				Log.i("WordToss","Got new word:" + temp + " - Hard");
		}
		return temp;
	}
}
