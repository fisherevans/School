package info.fisherevans.school.simpleCalculator;

public class Functions
{
		// App activity and core
	private SimpleCalculatorActivity activity;
	
	public Functions(SimpleCalculatorActivity activity)
	{
		this.activity = activity;
	}
	
	public void affixNumber(String number)
	{
		if(!number.equals("."))
		{
			activity.curNum += number;
		}
		else if(!activity.curDec)
		{
			activity.curDec = !activity.curDec;
			activity.curNum += number;
		}
		
		while(activity.curNum.substring(0,1).equals("0") && !activity.curNum.substring(0,2).equals("0.") && activity.curNum.length() > 1)
		{
			activity.curNum = activity.curNum.substring(1);
		}
		
		activity.gui.updateDisplay();
	}
	
	public void clearNumber()
	{
		activity.curNum = "";
		activity.gui.updateDisplay();
	}
	
	public void invertNumber()
	{
		activity.curPos = !activity.curPos;
		activity.gui.updateDisplay();
	}
	
	public void backSpaceNumber()
	{
		if(!activity.curNum.equals(""))
		{
			String backChar = activity.curNum.substring(activity.curNum.length()-1,activity.curNum.length());
			
			if(backChar.equals("."))
				activity.curDec = false;
			
			activity.curNum = activity.curNum.substring(0,activity.curNum.length()-1);
			
			if(activity.curNum.equals(""))
				activity.curPos = true;
	
			activity.gui.updateDisplay();
		}
	}
}
