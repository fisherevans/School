package info.fisherevans.school.calculator;

import info.fisherevans.school.calculator.CalculatorActivity;
import info.fisherevans.school.calculator.R;
import android.view.View;

/**
 * Handles user input traffic and sections it off
 * @author Fisher
 *
 */
public class UserInput
{
	
		// App activity and core
	private CalculatorActivity activity;

	/**
	 * Create the User input for button listeners. Go through separate switch statements to organize things/speed them up
	 * @param activity The main app too call back on
	 */
	public UserInput(CalculatorActivity activity)
	{
		this.activity = activity;
	}
	
	/**
	 * If a number is clicked, go through this switch statement
	 * @param v Button click is from...
	 */
	public void onNumericalClick(View v)
	{
		switch(v.getId())
		{
		case R.id.button0:
			activity.numberInput.affixNumber("0"); break;
		case R.id.button1:
			activity.numberInput.affixNumber("1"); break;
		case R.id.button2:
			activity.numberInput.affixNumber("2"); break;
		case R.id.button3:
			activity.numberInput.affixNumber("3"); break;
		case R.id.button4:
			activity.numberInput.affixNumber("4"); break;
		case R.id.button5:
			activity.numberInput.affixNumber("5"); break;
		case R.id.button6:
			activity.numberInput.affixNumber("6"); break;
		case R.id.button7:
			activity.numberInput.affixNumber("7"); break;
		case R.id.button8:
			activity.numberInput.affixNumber("8"); break;
		case R.id.button9:
			activity.numberInput.affixNumber("9"); break;
		case R.id.buttonPeriod:
			activity.numberInput.affixNumber("."); break;
		case R.id.buttonClear:
			activity.numberInput.clearNumber(); break;
		case R.id.buttonBackSpace:
			activity.numberInput.backSpaceNumber(); break;
		case R.id.buttonPlusMinus:
			activity.numberInput.invertNumber(); break;
		}
	}

	/**
	 * If a function is clicked, go through this switch statement
	 * @param v Button click is from...
	 */
	public void onFunctionClick(View v)
	{
		switch(v.getId())
		{
		case R.id.buttonPlus:
			activity.functionInput.setOp("+"); break;
		case R.id.buttonMinus:
			activity.functionInput.setOp("-"); break;
		case R.id.buttonTimes:
			activity.functionInput.setOp("*"); break;
		case R.id.buttonDivide:
			activity.functionInput.setOp("/"); break;
		case R.id.buttonPower:
			activity.functionInput.setOp("^"); break;
		}
	}

	/**
	 * If a global button is clicked, go through this switch statement
	 * @param v Button click is from...
	 */
	public void onGlobalClick(View v)
	{
		switch(v.getId())
		{
		case R.id.buttonEquals:
			activity.globalInput.equals(); break;
		case R.id.buttonPercent:
			activity.globalInput.percent(); break;
		case R.id.buttonRecip:
			activity.globalInput.recip(); break;
		case R.id.buttonSqrt:
			activity.globalInput.sqrt(); break;
		case R.id.buttonReset:
			activity.globalInput.reset(); break;
		}
	}
}
