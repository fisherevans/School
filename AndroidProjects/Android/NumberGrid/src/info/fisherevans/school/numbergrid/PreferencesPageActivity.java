package info.fisherevans.school.numbergrid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PreferencesPageActivity extends Activity
{
	private int lowRange, highRange;
	private boolean base, square, cube, root, fact;
	
	private EditText fromIn, toIn;
	private ToggleButton baseIn, squareIn, cubeIn, rootIn, factIn;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref);

        fromIn = (EditText) findViewById(R.id.fromInput); // get views
        toIn = (EditText) findViewById(R.id.toInput);

        baseIn = (ToggleButton) findViewById(R.id.baseInput);
        squareIn = (ToggleButton) findViewById(R.id.squareInput);
        cubeIn = (ToggleButton) findViewById(R.id.cubeInput);
        rootIn = (ToggleButton) findViewById(R.id.rootInput);
        factIn = (ToggleButton) findViewById(R.id.factInput);
    }
    
    public void onStart()
    {
    	super.onStart();
    	
    	getPrefs();
    	
    	fromIn.setText(lowRange + ""); // set view content after get prefs
    	toIn.setText(highRange + "");

    	baseIn.setChecked(base);
    	squareIn.setChecked(square);
    	cubeIn.setChecked(cube);
    	rootIn.setChecked(root);
    	factIn.setChecked(fact);
    }
    
    public void save(View v)
    {
    	lowRange = Integer.parseInt(fromIn.getText().toString()); // grab current input
    	highRange = Integer.parseInt(toIn.getText().toString());

    	base = baseIn.isChecked();
    	square = squareIn.isChecked();
    	cube = cubeIn.isChecked();
    	root = rootIn.isChecked();
    	fact = factIn.isChecked();
    	
    	editPrefs(); // try saving
    }
	
    public void getPrefs()
    {
    	SharedPreferences prefs = getSharedPreferences("info.fisherevans.school.numbergrid", Context.MODE_PRIVATE); // load the prefs into locals
        lowRange = prefs.getInt("info.fisherevans.school.numbergrid.lowrange", 1);
        highRange = prefs.getInt("info.fisherevans.school.numbergrid.highrange", 40);
        base = prefs.getBoolean("info.fisherevans.school.numbergrid.base", true);
        square = prefs.getBoolean("info.fisherevans.school.numbergrid.square", true);
        cube = prefs.getBoolean("info.fisherevans.school.numbergrid.cube", true);
        root = prefs.getBoolean("info.fisherevans.school.numbergrid.root", false);
        fact = prefs.getBoolean("info.fisherevans.school.numbergrid.fact", false);
    }
    
    public void editPrefs()
    {
    	if(lowRange >=  highRange) // error checking
    	{
    		Toast offsides = Toast.makeText(this, "The low range must be below the high range.", Toast.LENGTH_LONG);
    		offsides.show();
    	}
    	else if(highRange - lowRange > 200)
    	{
    		Toast bigrange = Toast.makeText(this, "You may only have a maximum of 200 rows. Decrease your range.", Toast.LENGTH_LONG);
    		bigrange.show();
    	}
    	else if(!(base || square || cube || root || fact))
    	{
    		Toast nocolumn = Toast.makeText(this, "You must choose at least one column.", Toast.LENGTH_LONG);
    		nocolumn.show();
    	}
    	else // if it all checks out, go for it
    	{
        	SharedPreferences prefs = getSharedPreferences("info.fisherevans.school.numbergrid", Context.MODE_PRIVATE);
        	
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("info.fisherevans.school.numbergrid.lowrange", lowRange);
            editor.putInt("info.fisherevans.school.numbergrid.highrange", highRange);
            editor.putBoolean("info.fisherevans.school.numbergrid.base", base);
            editor.putBoolean("info.fisherevans.school.numbergrid.square", square);
            editor.putBoolean("info.fisherevans.school.numbergrid.cube", cube);
            editor.putBoolean("info.fisherevans.school.numbergrid.root", root);
            editor.putBoolean("info.fisherevans.school.numbergrid.fact", fact);
            editor.commit();
    		finish(); // save the prefs and close
    	}
    }
}
