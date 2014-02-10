package info.fisherevans.school.simpleCalculator;

import android.app.Activity;
import android.os.Bundle;

public class SimpleCalculatorActivity extends Activity
{
		// Create the activity and GUI manager
	public GUIManager gui;
	public UserInput userInput;
	public Functions functions;
	
		// Create number vars
	public String lastNum, curOp, curNum;
	public boolean curDec,curPos;
	
    	// Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		gui = new GUIManager(this);
		userInput = new UserInput(this);
		functions = new Functions(this);
		
		lastNum = "";
		curDec = false;
		curPos = true;
		curOp = "";
		curNum = "";
    }
}