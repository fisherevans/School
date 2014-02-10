package info.fisherevans.school.simpleCalculator.functions;

import info.fisherevans.school.simpleCalculator.R;
import info.fisherevans.school.simpleCalculator.SimpleCalculatorActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class NumericalInput implements OnClickListener
{
	
		// App activity and core
	private SimpleCalculatorActivity activity;

	public NumericalInput(SimpleCalculatorActivity activity)
	{
		this.activity = activity;
	}
	
	public void onClick(View v) {
		int clickId = v.getId();
		if(clickId == R.id.BUTTON0)
			activity.functions.affixNumber("0");
		else if(clickId == R.id.BUTTON1)
			activity.functions.affixNumber("1");
		else if(clickId == R.id.BUTTON2)
			activity.functions.affixNumber("2");
		else if(clickId == R.id.BUTTON3)
			activity.functions.affixNumber("3");
		else if(clickId == R.id.BUTTON4)
			activity.functions.affixNumber("4");
		else if(clickId == R.id.BUTTON5)
			activity.functions.affixNumber("5");
		else if(clickId == R.id.BUTTON6)
			activity.functions.affixNumber("6");
		else if(clickId == R.id.BUTTON7)
			activity.functions.affixNumber("7");
		else if(clickId == R.id.BUTTON8)
			activity.functions.affixNumber("8");
		else if(clickId == R.id.BUTTON9)
			activity.functions.affixNumber("9");
		else if(clickId == R.id.BUTTON_PERIOD)
			activity.functions.affixNumber(".");
		else if(clickId == R.id.BUTTON_CLEAR)
			activity.functions.clearNumber();
		else if(clickId == R.id.BUTTON_BACKSPACE)
			activity.functions.backSpaceNumber();
		else if(clickId == R.id.BUTTON_SIGN)
			activity.functions.invertNumber();
	}
}
