package info.fisherevans.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.*;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsActivity extends Activity
{
	String[] names;
	
	ArrayAdapter<String> nameAdapter;

	TableLayout table;
	  
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getContacts();

        AutoCompleteTextView ac = (AutoCompleteTextView)findViewById(R.id.input);

        nameAdapter = new ArrayAdapter<String>(this,R.layout.popup,names);

        ac.setAdapter(nameAdapter);
        
        ac.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				searchContacts(parent.getItemAtPosition(pos).toString());
			}
        });
        
        table = (TableLayout) findViewById(R.id.results);
    }
    
    public void clearScreen(View v)
    {
    	((AutoCompleteTextView)findViewById(R.id.input)).setText("");
    }
    
    public void getContacts()
    {
		ContentResolver cr = getContentResolver();
		
		String selection = Data.MIMETYPE+" = "+'"'+StructuredName.CONTENT_ITEM_TYPE+'"';
		String[] projection = new String[]{Data._ID,
			Data.MIMETYPE,
			StructuredName.GIVEN_NAME,
			StructuredName.FAMILY_NAME};
		
		Cursor cursor = cr.query(Data.CONTENT_URI, projection, selection, null, null);
		
		Toast.makeText(this, "Number of contacts "+cursor.getCount(), Toast.LENGTH_LONG).show();
		
		names = new String[cursor.getCount()*2];
		
		int firstIndex = cursor.getColumnIndex(StructuredName.GIVEN_NAME);
		int lastIndex = cursor.getColumnIndex(StructuredName.FAMILY_NAME);
		
		for(int loop = 0;cursor.moveToNext();loop += 2)
		{
			names[loop] = cursor.getString(firstIndex);
			names[loop+1] = cursor.getString(lastIndex);
		}
    }
    
    public void searchContacts(String q)
    {
    	Toast.makeText(this, "WORKS - " + q, Toast.LENGTH_SHORT).show();
		
		ContentResolver cr = getContentResolver();
		
		String selection = Data.MIMETYPE+" = "+'"'+StructuredName.CONTENT_ITEM_TYPE+'"';
		String[] projection = new String[]{Data._ID,
			Data.MIMETYPE,
			StructuredName.GIVEN_NAME,
			StructuredName.FAMILY_NAME,
			People.NUMBER};
		
		Cursor cursor = cr.query(Data.CONTENT_URI, projection, selection, null, null);
		
		names = new String[cursor.getCount()*2];
		
		int firstIndex = cursor.getColumnIndex(StructuredName.GIVEN_NAME);
		int lastIndex = cursor.getColumnIndex(StructuredName.FAMILY_NAME);
		int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
		
		table.removeAllViews();
		
		while(cursor.moveToNext())
		{
			String tempString = cursor.getString(firstIndex) + " " + cursor.getString(lastIndex);
			
			if(tempString.contains(q))
			{
	        	Toast.makeText(this, tempString, Toast.LENGTH_SHORT).show();
	        	
	        	TableRow tempRow = new TableRow(this);
	        	TextView tempText = new TextView(this);
	    	    tempText.setText(tempString + " - " + cursor.getString(phoneIndex));
	    	    tempText.setTextColor(Color.WHITE);
	    	    tempRow.addView(tempText);
	    	    
	        	table.addView(tempRow);
			}
    	}
    }
}