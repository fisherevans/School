package info.fisherevans.school.colorNames;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class ColorNamesActivity extends Activity
{
    boolean testing;
    ToggleButton  buttonRed, buttonOrange, buttonYellow,
			buttonGreen, buttonBlue, buttonPurple,
			buttonWhite, buttonBlack;
    
    EditText input;
    
    TextListener textListener;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        textListener = new TextListener(this);
        
        testing = false;
        
        buttonRed = (ToggleButton)findViewById(R.id.buttonRed);
        buttonOrange = (ToggleButton)findViewById(R.id.buttonOrange);
        buttonYellow = (ToggleButton)findViewById(R.id.buttonYellow);
	    buttonGreen = (ToggleButton)findViewById(R.id.buttonGreen);
        buttonBlue = (ToggleButton)findViewById(R.id.buttonBlue);
        buttonPurple = (ToggleButton)findViewById(R.id.buttonPurple);
	    buttonWhite = (ToggleButton)findViewById(R.id.buttonWhite);
        buttonBlack = (ToggleButton)findViewById(R.id.buttonBlack);
        
        input = (EditText)findViewById(R.id.input);
        input.addTextChangedListener(textListener);
    }
    
    public void colorButtonClick(View v)
    {
    	/*switch(v.getId())
    	{
    	case R.id.buttonRed: break;
    	}*/
    }
    
    public void clearButtonClick(View v)
    {
    	if(testing)
    	{
    		testing = false;
    		
    		buttonRed.setChecked(true);
    		buttonOrange.setChecked(true);
    		buttonYellow.setChecked(true);
    		buttonGreen.setChecked(true);
    		buttonBlue.setChecked(true);
    		buttonPurple.setChecked(true);
    		buttonWhite.setChecked(true);
    		buttonBlack.setChecked(true);

    		input.setText("");
    	}
    	else
    	{
    		testing = true;
    		
    		buttonRed.setChecked(false);
    		buttonOrange.setChecked(false);
    		buttonYellow.setChecked(false);
    		buttonGreen.setChecked(false);
    		buttonBlue.setChecked(false);
    		buttonPurple.setChecked(false);
    		buttonWhite.setChecked(false);
    		buttonBlack.setChecked(false);
    		
    		input.setText("");
    	}
    }
}