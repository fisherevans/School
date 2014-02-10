package info.fisherevans.strfry.scores;

import info.fisherevans.strfry.SQL;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Scores
{
	private String[] diffs = { "Easy", "Easy/Medium", "Medium", "Medium/Hard", "Hard" };
	private int maxScores = 20;
	
	public void addScore(String word, String wordS, long time, int diff)
	{
		SQLiteDatabase DB = SQLiteDatabase.openDatabase(SQL.DB_PATH + SQL.DB_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		ContentValues values = new ContentValues ();
		values.put("word", word);
		values.put("wordS", wordS);
		values.put("time", time);
		values.put("diff", diff);
		
		//Log.i("StrFry", "trying to add score! " + word + " " + wordS + " " + time + " " + diffs[diff]);
		
		DB.insert("scores", null, values);
		
		Cursor cursor = DB.rawQuery("SELECT * FROM scores WHERE diff=" + diff + ";", null);
		if(cursor.getCount() > maxScores)
			DB.execSQL("DELETE FROM scores WHERE id IN (SELECT id FROM scores WHERE diff=" + diff + " ORDER BY time DESC LIMIT " + (cursor.getCount() - maxScores) + ");");
		
		cursor.close();
		DB.close();
	}
	
	public ArrayList<Score> getScores(int diff)
	{
		SQLiteDatabase DB = SQLiteDatabase.openDatabase(SQL.DB_PATH + SQL.DB_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		//Log.i("StrFry", "Retrieving " + diffs[diff] + " scores.");
		
		Cursor cursor = DB.rawQuery("SELECT * FROM scores WHERE diff=" + diff + " ORDER BY time ASC;", null);
		ArrayList<Score> scores = new ArrayList<Score>();
		if(cursor.moveToFirst())
		{
			do
				scores.add(new Score(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
			while(cursor.moveToNext());
		}
		
		cursor.close();
		DB.close();
		
		return scores;
	}
}
