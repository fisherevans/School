package info.fisherevans.school.calculator;

import android.widget.TextView;

/**
 * Deals with the textareas (updating the display)
 * @author Fisher
 *
 */
public class GUIManager
{
	private CalculatorActivity activity;
	
	public TextView lastInput, curInput;
	
	/**
	 * Create the GUI Manager
	 * @param activity The main app too call back on
	 */
	public GUIManager(CalculatorActivity activity)
	{
		this.activity = activity;
		
		initDisplays();
	}
	
	/**
	 * Grab the text views
	 */
	public void initDisplays()
	{
		lastInput = (TextView) activity.findViewById(R.id.lastInput);
		curInput = (TextView) activity.findViewById(R.id.curInput);
	}
	
	/**
	 * update the text in the text views
	 */
	public void updateDisplay()
	{
		lastInput.setText(activity.lastNum);
		if(!activity.curOp.equals("")) // If there is a current op, print it
			lastInput.setText(lastInput.getText() + " " + activity.curOp);
		
		if(!activity.curPos) // If the current number is negative, print a "-"
			curInput.setText("-" + activity.curNum);
		else // Other wise just the number
			curInput.setText(activity.curNum);
	}
}
