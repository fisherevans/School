package info.fisherevans.strfry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQL
{
	private StrFry parent;

	public static final String DB_VER = "/data/data/info.fisherevans.strfry/databases/wordsVersion.txt";
	public static final String DB_PATH = "/data/data/info.fisherevans.strfry/databases/";
	public static final String DB_NAME = "words.db";
    
    private static final short CUR_DB_VERSION = 5;
	
	public SQL(StrFry newParent)
	{
		parent = newParent;
		try
		{
			createDB();
		}
		catch(Exception e)
		{
			Log.e("StrFry", e.toString());
		}
	}
	
	public void createDB() throws IOException
	{
        boolean createDb = false;

        File dbVer = new File(DB_VER);
        File dbDir = new File(DB_PATH);
        File dbFile = new File(DB_PATH + DB_NAME);
        
        //Log.i("StrFry", "Checking database");
        
        if (!dbDir.exists())
        {
            dbDir.mkdir();
            createDb = true;
            //Log.i("StrFry", "No databse directory");
        }
        else if (!dbFile.exists())
        {
            createDb = true;
            //Log.i("StrFry", "No databse file");
        }
        else
        {
            boolean doUpgrade = false;
            
            //Log.i("StrFry", "Database is there");

            if (dbVer.exists())
            {
                //Log.i("StrFry", "Database ver is there");
            	FileReader  fileRead = new FileReader(dbVer);
            	BufferedReader fileBuff = new BufferedReader(fileRead);
            	if(Integer.parseInt(fileBuff.read()+"") < CUR_DB_VERSION)
            		doUpgrade = true;
            	fileBuff.close();
                //Log.i("StrFry", "Database ver outdated:" + doUpgrade);
            }
            else
            {
            	doUpgrade = true;
                //Log.i("StrFry", "no database file");
            }
            
            if (doUpgrade)
            {
                //Log.i("StrFry", "UPGRADING");
                dbFile.delete();
                createDb = true;
            }
        }

        if (createDb)
        {
            //Log.i("StrFry", "Creating database");
            InputStream dbInput = parent.getAssets().open(DB_NAME);
            OutputStream dbOutput = new FileOutputStream(dbFile);
            FileWriter dbVerWrite = new FileWriter(DB_VER);
            BufferedWriter dbVerBuff = new BufferedWriter(dbVerWrite);

            //Log.i("StrFry", "Make ver file");
            dbVerBuff.write(CUR_DB_VERSION);
            byte[] buffer = new byte[1024];
            int length;
            //Log.i("StrFry", "write database file");
            while ((length = dbInput.read(buffer)) > 0)
            	dbOutput.write(buffer, 0, length);

            dbVerBuff.flush();
            dbVerBuff.close();
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
            //Log.i("StrFry", "Close all file handles");
        }
    }
}
