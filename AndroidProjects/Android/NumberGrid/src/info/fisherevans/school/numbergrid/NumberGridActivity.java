package info.fisherevans.school.numbergrid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NumberGridActivity extends Activity
{
	private int lowRange, highRange;
	private boolean base, square, cube, root, fact;
	
	private TextView rangeView;
	private TableLayout mainTable;

	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        
        rangeView = (TextView) findViewById(R.id.rangeText); // Get the gen views
        mainTable = (TableLayout) findViewById(R.id.gridTable);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        
        getPrefs(); // get the new/old pref's
        
        Log.v("NumberGrid", "lowRange:"+lowRange+" - highRange:"+highRange+" - base:"+base+" - square:"+square+" - cube:"+cube+" - root:"+root+" - fact:"+fact); // Debugging
        
        rangeView.setText("From " + lowRange + " to " + highRange);
        mainTable.removeAllViews(); // clear the table
        
        createTableRows(); // fill the table
        
        ViewGroup wrapper = (ViewGroup) findViewById (R.id.gridWrapper); // For some strange reason, I have to force a redraw...
        wrapper.invalidate();
    }
    
    public void createTableRows()
    {
    	TableRow headerRow = new TableRow(this); // Make the header
    	if(base) { headerRow.addView(headerTextView("Base")); }
    	if(square) { headerRow.addView(headerTextView("Squared")); }
    	if(cube) { headerRow.addView(headerTextView("Cubed")); }
    	if(root) { headerRow.addView(headerTextView("Square Root")); }
    	if(fact) { headerRow.addView(headerTextView("Factorial")); }
    	mainTable.addView(headerRow); // Add the header row

    	TableRow tempRow;
    	for(int i = lowRange;i <= highRange;i++) // make and add a row for each number
    	{
    		tempRow = new TableRow(this);
        	if(base) { tempRow.addView(rowTextView(i + "")); }
        	if(square) { tempRow.addView(rowTextView(i*i + "")); }
        	if(cube) { tempRow.addView(rowTextView(i*i*i + "")); }
        	if(root) { tempRow.addView(rowTextView(String.format("%.2f", Math.sqrt(i)) + "")); }
        	if(fact) { tempRow.addView(rowTextView(getFact(i) + "")); }
        	mainTable.addView(tempRow);
    	}
    }
    
    public TextView headerTextView(String text) // Header row (bigger)
    {
    	TextView temp = new TextView(this);
    	temp.setText(text);
    	temp.setTextColor(Color.WHITE);
    	temp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
    	temp.setPadding(20, 3, 20, 3);
    	return temp;
    }
    
    public TextView rowTextView(String text) // Normal row text
    {
    	TextView temp = new TextView(this);
    	temp.setText(text);
    	temp.setTextColor(Color.WHITE);
    	temp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    	temp.setGravity(Gravity.CENTER);
    	return temp;
    }
    
    public int getFact(int i) // Simple factorial getter
    {
    	int sum = 0;
    	for(int j = 1;j <= i;j++)
    	{
    		sum += j;
    	}
    	return sum;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) // Look for menu press
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) // Listener for menu
    {
    	switch(item.getItemId())
    	{
    	case R.id.aboutButton:
            Intent aboutScreen = new Intent(getApplicationContext(), AboutPageActivity.class);
            startActivity(aboutScreen);
    		return true;
    		
    	case R.id.preferencesButton:
            Intent prefScreen = new Intent(getApplicationContext(), PreferencesPageActivity.class);
            startActivity(prefScreen);
    		return true;
    			
    	default: 
    		return super.onOptionsItemSelected(item);
    	}
    	
    }
    
    public void getPrefs() // Load the prefs
    {
    	SharedPreferences prefs = getSharedPreferences("info.fisherevans.school.numbergrid", Context.MODE_PRIVATE);
        lowRange = prefs.getInt("info.fisherevans.school.numbergrid.lowrange", 1);
        highRange = prefs.getInt("info.fisherevans.school.numbergrid.highrange", 40);
        base = prefs.getBoolean("info.fisherevans.school.numbergrid.base", true);
        square = prefs.getBoolean("info.fisherevans.school.numbergrid.square", true);
        cube = prefs.getBoolean("info.fisherevans.school.numbergrid.cube", true);
        root = prefs.getBoolean("info.fisherevans.school.numbergrid.root", false);
        fact = prefs.getBoolean("info.fisherevans.school.numbergrid.fact", false);
    }
}