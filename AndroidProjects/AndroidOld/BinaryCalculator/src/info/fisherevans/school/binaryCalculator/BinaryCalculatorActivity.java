package info.fisherevans.school.binaryCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BinaryCalculatorActivity extends Activity
{
		// Declare Instance variables
	private String output, decOutput;
	private TextView outputTextView, decOutputTextView;
	

    /*
     * Init. method call
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        output = ""; // init output text
        outputTextView = (TextView) findViewById(R.id.OUTPUT); // Grab output textview
        decOutputTextView = (TextView) findViewById(R.id.DECOUTPUT); // Grab output textview
    }

    /*
     * Affixes a one to output variables
     * @param v button pressed
     */
    public void buttonOneClick(View v)
    {
    	output += "1";
    	updateOutput();
    }
    
    /*
     * Affixes a zero to output variables
     * @param v button pressed
     */
    public void buttonZeroClick(View v)
    {
    	output += "0";
    	updateOutput();
    }
    
    public void buttonClearClick(View v)
    {
    	output = "";
    	updateOutput();
    }
    
    /*
     * Sets OUTPUT to output variable
     */
    public void updateOutput()
    {
    	outputTextView.setText(output);
    	decOutputTextView.setText("" + convertToDec());
    }
    
    public long convertToDec()
    {
    	int place = 1;
    	long sum = 0;
    	for(int i = output.length() - 1;i >= 0;i--)
    	{
    		sum += place*Integer.parseInt(output.substring(i,i+1));
    		place *= 2;
    	}
    	return(sum);
    }
}