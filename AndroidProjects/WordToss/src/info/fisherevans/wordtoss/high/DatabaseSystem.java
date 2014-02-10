package info.fisherevans.wordtoss.high;

import info.fisherevans.wordtoss.WordTossActivity;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class DatabaseSystem extends SQLiteOpenHelper
{

	static final String dbName = "wordtoss";
	static final String tableName = "highscores";
	static final String id = "id";
	static final String word = "word";
	static final String wordS = "wordS";
	static final String time = "time";
	static final String diff = "diff";
	
	static final String createScript = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT , %s TEXT, %s TEXT, %s REAL, %s INTEGER)",
			tableName,
			id,
			word,
			wordS,
			time,
			diff);
	
	private WordTossActivity parent;
	
	public DatabaseSystem(Context context)
	{
		super(context, dbName, null, 33);
		parent = (WordTossActivity) context;
	}

	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(createScript);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		
	}
	
	public void addScore(String word, String wordS, float time, float diff)
	{
		time /= 1000;
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(DatabaseSystem.word, word);
		values.put(DatabaseSystem.wordS, wordS);
		values.put(DatabaseSystem.time, time);
		values.put(DatabaseSystem.diff, diff);
		
		db.insert(tableName, null, values);
		db.close();
		
		Log.i("WordToss", "Adding Score -> Word:" + word + " - WordS:" + wordS + " - Time:" + time + " - Diff:" + diff);
	}
	
	public ArrayList<String> scoresToStrings(ArrayList<Score> scores)
	{
		ArrayList<String> strings = new ArrayList<String>();
		for(Score temp : scores)
		{
			strings.add(temp.getWord()+":"+temp.getWordS()+":"+temp.getTime()+":"+temp.getDiff());
		}
		return strings;
	}
	
	public ArrayList<Score> getAllScores()
	{
		ArrayList<Score> tempScores = new ArrayList<Score>();

		String q = "SELECT  * FROM " + tableName;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(q, null);
		
		if(cursor.moveToFirst())
		{
			do
			{
				Score score = new Score(cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getInt(4));
				tempScores.add(score);
			}
			while (cursor.moveToNext());
		}
		
		return tempScores;
	}
	
	public ArrayList<Score> getScores(float diff)
	{
		ArrayList<Score> tempScores = new ArrayList<Score>();
		
		diff = diff + parent.words.highest;

		String q = "SELECT  * FROM " + tableName;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(q, null);
		
		if(cursor.moveToFirst())
		{
			do
			{
				if(diff == -1 || Math.abs(cursor.getFloat(3) - diff) <= 1)
				{
					Score score = new Score(cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getInt(4));
					tempScores.add(score);
				}
			}
			while (cursor.moveToNext());
		}
		
		return tempScores;
	}
}
