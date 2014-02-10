package info.fisherevans.school.calculator;

/**
 * Handles Numerical inputs
 * @author Fisher
 */
public class NumberInput
{
		// App activity and core
	private CalculatorActivity activity;
	
	/**
	 * Create the number function class
	 * @param activity The main app too call back on
	 */
	public NumberInput(CalculatorActivity activity)
	{
		this.activity = activity;
	}
	
	/**
	 * Add a digit to the current number 0-9 or .
	 * @param number 0-9 or . as a String
	 */
	public void affixNumber(String number)
	{
		if(!number.equals(".")) // If it's just a number...
		{
			activity.curNum += number;
		}
		else if(!activity.curDec) // if it's before the decimal and it is a .
		{
			activity.curDec = !activity.curDec; // Set to after period
			activity.curNum += number;
		}
		
		while(activity.curNum.substring(0,1).equals("0")) // While loop to get rid of any leading zeroes, execpt for "0."
		{
			if(activity.curNum.length() <= 1)
				break;
			if(activity.curNum.substring(1,2).equals("."))
				break;

			activity.curNum = activity.curNum.substring(1);
		}
		
		activity.gui.updateDisplay();
	}
	
	/**
	 * Clear the current number
	 */
	public void clearNumber()
	{
		activity.curNum = "";
		activity.gui.updateDisplay();
	}
	
	/**
	 * Invert the current number sign
	 */
	public void invertNumber()
	{
		activity.curPos = !activity.curPos;
		activity.gui.updateDisplay();
	}
	
	/**
	 * Remove the last number
	 */
	public void backSpaceNumber()
	{
		if(!activity.curNum.equals("")) // If it's not blank
		{
			String backChar = activity.curNum.substring(activity.curNum.length()-1,activity.curNum.length()); // Get the char to be deleted
			
			if(backChar.equals(".")) // If it's a period, set it to left side of period
				activity.curDec = false;
			
			activity.curNum = activity.curNum.substring(0,activity.curNum.length()-1);
			
			if(activity.curNum.equals("")) // No matter what, if the number is empty, revert back to positive
				activity.curPos = true;
	
			activity.gui.updateDisplay();
		}
	}
}
