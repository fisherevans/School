package info.fisherevans.school.numbergrid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutPageActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about); // load the about page
    }
    
    public void goBack(View v)
    {
    	finish(); // when the button is pressed, quit the activity
    }
}
