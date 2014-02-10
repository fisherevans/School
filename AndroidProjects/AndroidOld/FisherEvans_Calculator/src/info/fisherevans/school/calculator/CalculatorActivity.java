package info.fisherevans.school.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CalculatorActivity extends Activity
{
	/**
	 * The main class for the calculator app.
	 * @author Fisher
	 *
	 */
		// Create the activity and GUI manager
	public GUIManager gui;
	public UserInput userInput;
	public NumberInput numberInput;
	public FunctionInput functionInput;
	public GlobalInput globalInput;
	
		// Create number vars
	public String lastNum, curOp, curNum;
	public boolean curDec,curPos, justEntered;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		/**
		 * Create the App. (non-Javadoc)
		 * @see android.app.Activity#onCreate(android.os.Bundle)
		 */
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
		gui = new GUIManager(this);
		userInput = new UserInput(this);
		numberInput = new NumberInput(this);
		functionInput = new FunctionInput(this);
		globalInput = new GlobalInput(this);
		
		lastNum = "";
		curDec = false;
		curPos = true;
		curOp = "";
		curNum = "";
		justEntered = false;
	}
	
	public void numberButtonInput(View v)
	{
		/**
		 * Sends numerical button pressed to numerical click
		 * @param v View from where clicked
		 */
		if(justEntered && !curNum.equals("")) // If a calc. was just issued, over write current number.
			globalInput.reset();
		userInput.onNumericalClick(v);
	}
	
	public void functionButtonInput(View v)
	{
		/**
		 * Sends function button pressed to funtion click
		 * @param v View from where clicked
		 */
		justEntered = false;
		userInput.onFunctionClick(v);
	}
	
	public void globalButtonInput(View v)
	{
		/**
		 * Sends global button pressed to global click
		 * @param v View from where clicked
		 */
		justEntered = false;
		userInput.onGlobalClick(v);
	}
}