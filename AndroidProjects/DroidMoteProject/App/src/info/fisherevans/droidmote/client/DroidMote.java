package info.fisherevans.droidmote.client;

import info.fisherevans.anput.R;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DroidMote extends Activity
{
	public boolean connected, userDisconnect;
	
	private SocketConnection socketConnection;
	
	private List<String> outputBuffer;
	private float dXTotal, dYTotal;
	
	private Handler timeoutHandler;
	private Runnable timeoutRunnable;
	
	private FrameLayout mainLayout;
	private LinearLayout connectionWindow;
	
	private AlertDialog alertPupUp;
	
	private Interface userInterface;
	private EditText serverInfoInput;
	private Button connectButton;
	private TextView connectionText;

	private FrameLayout.LayoutParams layoutParamsFrame;
	private LinearLayout.LayoutParams layoutParamsLinear;
	
	private ConnectionHandler connectionHandler;

    private InputMethodManager keyboardManager;
    private EditText keyboardView;
    private KeyboardListener keyboardListener;
    
    private DroidMote self;
	
    public void onCreate(Bundle savedInstanceState)
    {
    	self = this;
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        keyboardListener = new KeyboardListener(); // Listener for key strokes
        connectionHandler = new ConnectionHandler(); // Connect button listener
        
        keyboardManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE); // Used to hide/show keyboard
        
        setupLayout();
    }
    
    public void onPause()
    {
    	super.onPause();
    }
    
    private void setupLayout()
    {
        mainLayout = ((FrameLayout)findViewById(R.id.mainLayout));

        alertPupUp = new AlertDialog.Builder(this).create(); // Popup for warnings
        alertPupUp.setButton("OK", new DialogInterface.OnClickListener() {
        	   public void onClick(DialogInterface dialog, int which) { } });
        
        userInterface = new Interface(this); // The actual interface to control remotley
        connectionWindow = new LinearLayout(this); // LAyout to hold connection info
        	connectionWindow.setOrientation(LinearLayout.VERTICAL);
        	connectionWindow.setGravity(Gravity.CENTER);
        keyboardView = new EditText(this); // Edittext keyboard types into
        	keyboardView.setFocusable(true);
        	keyboardView.setOnClickListener(keyboardListener);
        	keyboardView.setOnClickListener(keyboardListener);
        	keyboardView.addTextChangedListener(keyboardListener);
        	keyboardView.setText("-+");
        	keyboardView.setSelection(1);
        connectButton = new Button(this); // Buttont o connect
        	connectButton.setText("CONNECT");
        	connectButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font.ttf"));
        	connectButton.setTextSize(20);
        	connectButton.setOnClickListener(connectionHandler);
        	connectButton.setTextColor(Color.WHITE);
        	connectButton.setBackgroundColor(Color.DKGRAY);
        connectionText = new TextView(this); // Titles of connect window
        	connectionText.setGravity(Gravity.CENTER);
        	connectionText.setTextColor(Color.BLACK);
        	connectionText.setTextSize(36);
        	connectionText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font.ttf"));
        	connectionText.setText("SERVER:PORT");
        serverInfoInput = new EditText(this); // input form for server and port
        	serverInfoInput.setTextColor(Color.LTGRAY);
        	serverInfoInput.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/font.ttf"));
        	serverInfoInput.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.input));
        	serverInfoInput.setTextSize(20);
        	serverInfoInput.setHint("192.168.1.2:1234");

        connectionWindow.addView(connectionText); // the views to the connect window
        connectionWindow.addView(serverInfoInput);
        connectionWindow.addView(connectButton);

        mainLayout.addView(userInterface); // add the connect window, interface and keyboard typer
        mainLayout.addView(connectionWindow);
        mainLayout.addView(keyboardView);

        layoutParamsFrame = new FrameLayout.LayoutParams(1280,752); // set the positions
        	userInterface.setLayoutParams(layoutParamsFrame);
        layoutParamsFrame = new FrameLayout.LayoutParams(200,1);
        	keyboardView.setLayoutParams(layoutParamsFrame);
        layoutParamsFrame = new FrameLayout.LayoutParams(409,153);
        	layoutParamsFrame.setMargins(436, 11, 0, 0);
        	connectionWindow.setLayoutParams(layoutParamsFrame);

        layoutParamsLinear = new LinearLayout.LayoutParams(220, 37); // again
        	connectButton.setLayoutParams(layoutParamsLinear);
        layoutParamsLinear = new LinearLayout.LayoutParams(350, 42);
        	layoutParamsLinear.setMargins(0, 10, 0, 18);
        	serverInfoInput.setLayoutParams(layoutParamsLinear);
        	
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE); // load the last server entered
	    String lastServer = sharedPreferences.getString("serverinfo", "");
	    if(!lastServer.equals("")) serverInfoInput.setText(lastServer);
    }
    
    public void alert(String title, String message)
    {
        alertPupUp.setTitle(title); // popup with title and message
        alertPupUp.setMessage(message);
        alertPupUp.show();
    }
    
    public void showKeyboard()
    {
    	keyboardView.requestFocus(); // show the keyboard
        keyboardManager.showSoftInput(keyboardView, 0);
    }
    
    public void hideKeyboard() // and hide it
    {
        keyboardManager.hideSoftInputFromWindow(keyboardView.getWindowToken(), 0); 
    }
    
    public void resetClientVars()
    {
    	connected = true; // used to re initialize the variables
    	outputBuffer = new ArrayList<String>();
    }
    
    public synchronized List<String> outputBufferAdapter(int actionId, String command)
    {
    	List<String> tempBuffer = outputBuffer; // method to add and get commands. Thread safe
    	
    	if(!connected) // if not connected, do nothing
    		return tempBuffer;
    	
    	if(actionId == 1) // Input commands
    		outputBuffer.add(command);
    	else if(actionId == 2) // Grab commands
    		outputBuffer = new ArrayList<String>();
    	
    	return tempBuffer;
    }
    
    public synchronized float[] mouseDeltaAdapter(int actionId, float dX, float dY)
    {
    	float[] dTotal = { 0, 0 }; // thread safe methods to schaneg mouse delta, or retrieve it
    	
    	if(actionId == 1)
    	{
    		dXTotal += dX;
    		dYTotal += dY;
    	}
    	else if(actionId == 2)
    	{
    		dTotal[0] = dXTotal;
    		dTotal[1] = dYTotal;
    		dXTotal = 0;
    		dYTotal = 0;
    	}
    	
    	return dTotal;
    }
    
    public void connectionTimedOut()
    {
    	if(!userDisconnect) // popup about time out and end connection
    		alert("Connection Timed Out", "You were either unable to start or maintain a connection with the specified server.");

		endConnection();
    }
    
    public void connectionClosed() // popup about closed and end connection
    {
    	alert("Connection Closed by the Server", "The Server has forced the connection to be closed.");

		endConnection();
    }
    
    public void endConnection()
    {
		connected = false; // rsstart the socket and bring up the connection window
		socketConnection.endConnection();
		
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("serverinfo", serverInfoInput.getText().toString());
		editor.commit();
		
		connectButton.setText("CONNECT");
		connectionWindow.setVisibility(View.VISIBLE);
		
		userInterface.invalidate();
    }
    
    public void connected()
    {
		userInterface.invalidate(); // When connectioni is verfied, hide connection window and synch keyboard state
		connectionWindow.setVisibility(View.INVISIBLE);
		
		if(userInterface.keyboard)
			showKeyboard();
		else
			hideKeyboard();
    }
    
    private class ConnectionHandler implements OnClickListener
    {
    	public void onClick(View v) // button click listener for connect
    	{
    		String input[] = serverInfoInput.getText().toString().split(":"); // Get the server and port
        	userDisconnect = false; // Tell it the user hasn't clicked disconnect
    		
    		if(input.length != 2) // If there aren't two fields, tell them
    		{
    			alert("Invalid Server and Port", "Please use the following format: \"Server:Port\" when entering the connection information.");
    			return;
    		}
    		
    		socketConnection = new SocketConnection(self, input[0], Integer.parseInt(input[1])); // Create a new socket connection
    		socketConnection.startConnection(); // and start it
            
            timeoutHandler = new Handler(); // Create a handler
            timeoutRunnable = new Runnable(){ // Create a runnable that tracks time outs
            	public void run()
            	{
            		if(connected) // If still connected
            		{
            			timeoutHandler.postDelayed(timeoutRunnable, 10000); // Recall this timout checker
            			connected();
            		}
            		else // if not connected, tellt he user, and reset the connection
            			connectionTimedOut();
            	}
            };
            timeoutHandler.postDelayed(timeoutRunnable, 4000); // FIRST CALL of the time out "fake thread"
			
	        keyboardManager.hideSoftInputFromWindow(serverInfoInput.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
	        
    		connectButton.setText("CONNECTING..."); // Tell the user we're connecting
    	}
    }
    
    private class KeyboardListener implements TextWatcher, OnClickListener, OnKeyListener
    {
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			String text = keyboardView.getText().toString(); // keyboard lister
			int textCount = text.length();
			
			if(text.equals("-+")) // if text hasnt changed
				return;
			
			if(textCount == 3) // if there are 3 characters
			{
        		if(text.replaceAll("[-+]", "").equals("\n")) // if theres a new line, send it
        			outputBufferAdapter(1,"kvv");
            	else
            		outputBufferAdapter(1,"k" + text.charAt(1)); // else send the new char
			}
			else if(textCount == 1) // only one char, it's a back space
    			outputBufferAdapter(1,"k<<");
			
			keyboardView.setText("-+"); // set text back to generic state
			keyboardView.setSelection(1);
		}
		
		public void afterTextChanged(Editable edit) { } // blank listeners
		public void beforeTextChanged(CharSequence s, int start, int before, int count) { }
		public void onClick(View v) { }
		public boolean onKey(View v, int keyCode, KeyEvent event)
		{ return false; }
    }
}