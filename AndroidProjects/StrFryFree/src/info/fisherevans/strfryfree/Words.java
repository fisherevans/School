package info.fisherevans.strfryfree;

import info.fisherevans.strfryfree.views.LetterTile;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Words
{
	private StrFry parent;
	public WordMath math;
	
	public Words(StrFry newParent)
	{
		parent = newParent;
		math = new WordMath();
	}
	
	public String getWord(int diff)
	{
		SQLiteDatabase DB = SQLiteDatabase.openDatabase(SQL.DB_PATH + SQL.DB_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		Cursor cursor = DB.rawQuery("SELECT word FROM words WHERE diff=" + diff + " ORDER BY RANDOM() LIMIT 1", null);
		String word;
		if(cursor.moveToFirst())
			word = cursor.getString(0);
		else
			word = "broken";
		
		cursor.close();
		DB.close();
		//Log.i("StrFry", "Got new word:" + word + " with diff:" + diff);
		return word;
	}
	
	public class WordMath
	{
		public String scrambleWord(String word)
		{
			StringBuilder tempWord = new StringBuilder(word);
			String wordScramble = "";
			int rand;
			
			while(tempWord.length() > 0)
			{
				rand = (int)(Math.random()*tempWord.length());
				wordScramble += tempWord.charAt(rand);
				tempWord.deleteCharAt(rand);
			}
			
			if(wordScramble.equals(word))
				wordScramble = scrambleWord(word);
			
			return wordScramble;
		}
		
		public boolean isEqual()
		{
			String temp = "";
			for(LetterTile tile : parent.currentTiles)
			{
				temp += tile.getLetter();
			}
			
			return temp.equals(parent.currentWord);
		}
	}
}
