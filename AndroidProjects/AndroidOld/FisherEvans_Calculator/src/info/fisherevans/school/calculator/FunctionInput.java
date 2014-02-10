package info.fisherevans.school.calculator;

/**
 * Handles the stanard functions. - + / * ^
 * @author Fisher
 *
 */
public class FunctionInput
{
		// App activity and core
	private CalculatorActivity activity;

	/**
	 * Create the normal function class
	 * @param activity The main app too call back on
	 */
	public FunctionInput(CalculatorActivity activity)
	{
		this.activity = activity;
	}
	
	/**
	 * Issue an operation
	 * @param nextOp Pass -, +, /, * or ^ as a String
	 */
	public void setOp(String nextOp)
	{
		if(!activity.curOp.equals("") && !activity.curNum.equals("")) // If there is a current op that hasn't been calculated, do that now.
			activity.globalInput.equals();
		
		if(activity.curNum.equals("") && !activity.lastNum.equals("")) // If there is no cur Number, just change the operation
			activity.curOp = nextOp;
		else if(!activity.curNum.equals("") && activity.lastNum.equals("")) // If it's a new Op (fresh) push all number to last num, and set the cur op
		{
			activity.lastNum = activity.curPos ? "" : "-";
			
			activity.lastNum += activity.curNum;
			
			activity.curOp = nextOp;
			
			activity.curNum = "";
			activity.curPos = true;
			activity.curDec = false;
		}
		activity.gui.updateDisplay();
	}
}
