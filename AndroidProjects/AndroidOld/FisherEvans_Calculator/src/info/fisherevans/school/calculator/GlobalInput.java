package info.fisherevans.school.calculator;

/**
 * Handles input for global functions
 * @author Fisher
 *
 */
public class GlobalInput
{
		// App activity and core
	private CalculatorActivity activity;

	/**
	 * Create the global function class
	 * @param activity The main app too call back on
	 */
	public GlobalInput(CalculatorActivity activity)
	{
		this.activity = activity;
	}
	
	/**
	 * Calculates the current operation
	 */
	public void equals()
	{
		if(!activity.curOp.equals("") && !activity.curNum.equals("")) // as long as there is a current op and a current number, go for it
		{
			double lastNum = Double.parseDouble(activity.lastNum); // Parse the numbers
			double curNum = Double.parseDouble(activity.curNum);
			double newNum = 0;
			boolean knownOp = true;
			
			if(!activity.curPos) // Set the sign for current number
				curNum *= -1.0;
			
			if(activity.curOp.equals("+")) // Carry out the operation
			{
				newNum = lastNum + curNum;
			}
			else if(activity.curOp.equals("-"))
			{
				newNum = lastNum - curNum;
			}
			else if(activity.curOp.equals("/"))
			{
				newNum = lastNum / curNum;
			}
			else if(activity.curOp.equals("*"))
			{
				newNum = lastNum * curNum;
			}
			else if(activity.curOp.equals("^"))
			{
				newNum = Math.pow(lastNum,curNum);
			}
			else // Otherwise, reset everything!
			{
				reset();
				knownOp = false;
			}
			
			if(knownOp) // if The op is known,a nd everything wasn't reset...
			{
				boolean nextPos = newNum < 0 ? false : true;
				boolean nextDec = newNum == (int)newNum ? false : true; // if int cast is equal to double, no period yet
				
				activity.lastNum = "";
				if(!nextDec)
					activity.curNum = "" + (int)(Math.abs(newNum));
				else
					activity.curNum = "" + (Math.abs(newNum));
				activity.curPos = nextPos;
				activity.curDec = nextDec;
				activity.curOp = "";
			}
			activity.justEntered = true;
			activity.gui.updateDisplay();
		}
	}
	
	/**
	 * Multiplies the cur num by * 100. Equals the two if there is a cur op.
	 */
	public void percent()
	{
		equals();
		
		double newNum = Double.parseDouble(activity.curNum) * 100.0;
		
		reset();
		
		if(newNum < 0)
		{
			activity.curPos = false;
		}
		
		if((int)newNum == newNum)
		{
			activity.curNum = (int)newNum + "";
		}
		else
		{
			activity.curNum = newNum + "";
		}
		activity.gui.updateDisplay();
	}
	

	/**
	 * divides 1 by the cur num. Equals the two if there is a cur op.
	 */
	public void recip()
	{
		equals();
		
		double newNum = 1.0 / Double.parseDouble(activity.curNum);
		
		reset();
		
		if(newNum < 0)
		{
			activity.curPos = false;
		}
		
		if((int)newNum == newNum)
		{
			activity.curNum = (int)newNum + "";
		}
		else
		{
			activity.curNum = newNum + "";
		}
		activity.gui.updateDisplay();
	}
	
	/**
	 * Finds the sqaure root of the cur number. If there is a cur op, it runs equal
	 */
	public void sqrt()
	{
		equals();
		
		String signLead = activity.curPos ? "" : "-";
		
		double newNum = Math.sqrt(Double.parseDouble(signLead + activity.curNum));
		
		reset();
		
		if(newNum < 0)
		{
			activity.curPos = false;
		}
		
		if((int)newNum == newNum)
		{
			activity.curNum = (int)newNum + "";
		}
		else
		{
			activity.curNum = newNum + "";
		}
		activity.gui.updateDisplay();
	}
	
	/**
	 * Resets aLL variables to the default, then updates the display.
	 */
	public void reset()
	{
		activity.curNum = "0";
		activity.curPos = true;
		activity.curDec = false;
		activity.curOp = "";
		activity.lastNum = "";
		activity.justEntered = false;
		activity.gui.updateDisplay();
	}
}
