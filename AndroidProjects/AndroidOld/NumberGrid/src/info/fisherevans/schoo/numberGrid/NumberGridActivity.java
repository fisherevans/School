package info.fisherevans.schoo.numberGrid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class NumberGridActivity extends Activity
{
	TableLayout table;
	boolean base,sq,cube,sqrt,fact;
	int to,from;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        base = true;
        sq = true;
        cube = true;
        sqrt = false;
        fact = false;
        
        from = 1;
        to = 25;
        
        TableLayout table = (TableLayout) findViewById(R.id.mainTable);
        
        makeTable();
    }
    
    public void makeTable()
    {
        TableRow headerRow = new TableRow(this);
        TextView tempText;

        if(base) {
	        tempText = new TextView(this);
	        tempText.setText("Base");
	        tempText.setTextColor(Color.WHITE);
	        headerRow.addView(tempText); }
        if(sq) {
	        tempText = new TextView(this);
	        tempText.setText("Squared");
	        tempText.setTextColor(Color.WHITE);
	        headerRow.addView(tempText); }
        if(cube) {
	        tempText = new TextView(this);
	        tempText.setText("Cubed");
	        tempText.setTextColor(Color.WHITE);
	        headerRow.addView(tempText); }
        if(sqrt) {
	        tempText = new TextView(this);
	        tempText.setText("Sqrt");
	        tempText.setTextColor(Color.WHITE);
	        headerRow.addView(tempText); }
        if(fact) {
	        tempText = new TextView(this);
	        tempText.setText("Fact.");
	        tempText.setTextColor(Color.WHITE);
	        headerRow.addView(tempText); }
        
        table.addView(headerRow,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        for(int loop = from;loop <= to;loop++)
        {
        	table.addView(makeRow(loop),new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }
    
    public TableRow makeRow(int number)
    {
    	TableRow tempRow = new TableRow(this);
    	TextView tempText;

        if(base) {
	        tempText = new TextView(this);
	        tempText.setText("" + number);
	        tempText.setTextColor(Color.WHITE);
	        tempRow.addView(tempText); }
        if(sq) {
	        tempText = new TextView(this);
	        tempText.setText("" + Math.pow(number, 2));
	        tempText.setTextColor(Color.WHITE);
	        tempRow.addView(tempText); }
        if(cube) {
	        tempText = new TextView(this);
	        tempText.setText("" + Math.pow(number, 3));
	        tempText.setTextColor(Color.WHITE);
	        tempRow.addView(tempText); }
        if(sqrt) {
	        tempText = new TextView(this);
	        tempText.setText("" + Math.sqrt(number));
	        tempText.setTextColor(Color.WHITE);
	        tempRow.addView(tempText); }
        if(fact) {
        	int sum = 0;
        	for(int loop = 1;loop <= number;loop++)
        	{ sum += loop; }
	        tempText = new TextView(this);
	        tempText.setText(sum);
	        tempText.setTextColor(Color.WHITE);
	        tempRow.addView(tempText); }
    	
    	return tempRow;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        case R.id.about:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            Toast.makeText(NumberGridActivity.this, "About Page", Toast.LENGTH_LONG).show();
            setContentView(R.layout.about);
            return true;
 
        case R.id.pref:
            Toast.makeText(NumberGridActivity.this, "Preferences Page", Toast.LENGTH_LONG).show();
            setContentView(R.layout.pref);

            ((CheckBox) findViewById(R.id.base)).setChecked(base);
            ((CheckBox) findViewById(R.id.sq)).setChecked(sq);
            ((CheckBox) findViewById(R.id.cube)).setChecked(cube);
            ((CheckBox) findViewById(R.id.sqrt)).setChecked(sqrt);
            ((CheckBox) findViewById(R.id.fact)).setChecked(fact);

            ((EditText)findViewById(R.id.lowRangeIn)).setText("" + from);
            ((EditText)findViewById(R.id.highRangeIn)).setText("" + to);
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
    
    public void showMain(View v)
    {
        setContentView(R.layout.main);
        makeTable();
    }
    
    public void savePref(View v)
    {
    	base = ((CheckBox) findViewById(R.id.base)).isChecked();
    	sq = ((CheckBox) findViewById(R.id.sq)).isChecked();
    	cube = ((CheckBox) findViewById(R.id.cube)).isChecked();
    	sqrt = ((CheckBox) findViewById(R.id.sqrt)).isChecked();
    	fact = ((CheckBox) findViewById(R.id.fact)).isChecked();

    	from = Integer.parseInt(((EditText)findViewById(R.id.lowRangeIn)).getText().toString());
    	to = Integer.parseInt(((EditText)findViewById(R.id.highRangeIn)).getText().toString());
    	
    	showMain(v);
    }
}