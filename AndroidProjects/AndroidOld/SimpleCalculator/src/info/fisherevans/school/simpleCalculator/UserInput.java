package info.fisherevans.school.simpleCalculator;

import info.fisherevans.school.simpleCalculator.functions.NumericalInput;
import android.view.View;
import android.view.View.OnClickListener;


public class UserInput
{
		// App activity and core
	private SimpleCalculatorActivity activity;

    NumericalInput numInput;
    //OperatorInput opInput;
    //SpecialInput specInput;
    //GlobalInput globInput;

	public UserInput(SimpleCalculatorActivity activity)
	{
		this.activity = activity;

	    numInput = new NumericalInput(activity);
	    //opInput = new OperatorInput(activity);
	    //specInput = new SpecialInput(activity);
	    //globInput = new GlobalInput(activity);
	}
}
