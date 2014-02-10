package info.fisherevans.droidmote.client;

import info.fisherevans.anput.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Interface extends View implements View.OnTouchListener, View.OnClickListener
{
	private DroidMote parent;
	
	private Bitmap background, fingerDown, scrollDown, m1Down, m3Down, keyboardDown, disconnect, connect, tint; // pictures to use
	private int[] m1DownPos = { 26, 545 }, // the xy and y of those pictures
			  	  m3DownPos = { 650, 545 },
			  	  keyboardDownPos = { 26, 24 },
			  	  scrollDownPos = { 1084, 37 },
			  	  disconnectPos = { 505, 0 },
			  	  connectPos = { 375, 0 };
	
	public boolean keyboard, dragging, moveDragging; // movement vars
	private int actionID;
	private final int actionKeyboard = 1,
					  actionScroll = 2,
					  actionMouse1 = 3,
					  actionMouse3 = 4,
					  actionMove = 5,
					  actionBlank = 0;
	
	private float newX, newY, curX, curY, initX, initY, scrollMod; // used in input processing
	private long initTime;
	
	private Rect moveArea, mouse1Area, mouse3Area, scrollArea, keyboardArea, disconnectArea; // click areas
	
	public Interface(Context context)
	{
		super(context);
		parent = (DroidMote) context;

		this.setOnTouchListener(this); // define listeners for this view
		this.setOnClickListener(this);

        background = BitmapFactory.decodeResource(parent.getResources(), R.drawable.background); // get the pictures
        fingerDown = BitmapFactory.decodeResource(parent.getResources(), R.drawable.finger);
        scrollDown = BitmapFactory.decodeResource(parent.getResources(), R.drawable.scroll);
        m1Down = BitmapFactory.decodeResource(parent.getResources(), R.drawable.m1);
        m3Down = BitmapFactory.decodeResource(parent.getResources(), R.drawable.m3);
        keyboardDown = BitmapFactory.decodeResource(parent.getResources(), R.drawable.keyboard);
        disconnect = BitmapFactory.decodeResource(parent.getResources(), R.drawable.disconnect);
        connect = BitmapFactory.decodeResource(parent.getResources(), R.drawable.connect);
        tint = BitmapFactory.decodeResource(parent.getResources(), R.drawable.tint);
        
        keyboard = false; // init default values
        dragging = false;
        moveDragging = false;
        actionID = actionBlank;
        
        curX = 0;
        curY = 0;
        initX = 0;
        initY = 0;
        initTime = System.currentTimeMillis();

        moveArea = new Rect(26, 24, 1253, 534); // define click areas
        mouse1Area = new Rect(26, 545, 639, 672);
        mouse3Area = new Rect(650, 545, 1253, 672);
        scrollArea = new Rect(1109, 63, 1209, 496);
        keyboardArea = new Rect(54, 50, 194, 117);
        disconnectArea = new Rect(505, 0, 778, 60);
	}

    protected void onDraw(Canvas canvas)
    {
    	super.onDraw(canvas); // draw method
        Canvas g = canvas;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        
        g.drawBitmap(background,0,0, paint); // always draw bg
        
        if(actionID == actionMouse1 || moveDragging) // if mouse 1, draw push down
        	g.drawBitmap(m1Down, m1DownPos[0], m1DownPos[1], paint);
        else if(actionID == actionMouse3) // if mouse 3
        	g.drawBitmap(m3Down, m3DownPos[0], m3DownPos[1], paint);
        else if(actionID == actionScroll) // if scrolling
        	g.drawBitmap(scrollDown, scrollDownPos[0], scrollDownPos[1], paint);

        if(keyboard) // if keyboard is on
        	g.drawBitmap(keyboardDown, keyboardDownPos[0], keyboardDownPos[1], paint);
        
        if(!parent.connected) // if not connected, draw tint
        	g.drawBitmap(tint, 0, 0, paint);
        
        if(parent.connected) // disconnect btton vs. ocnnection window bg
        	g.drawBitmap(disconnect, disconnectPos[0], disconnectPos[1], paint);
        else
        	g.drawBitmap(connect, connectPos[0], connectPos[1], paint);
        
        if(dragging) // draw finger highlighter
        	g.drawBitmap(fingerDown, curX-(fingerDown.getWidth()/2), curY-(fingerDown.getHeight()/2), paint);
    }

	public void onClick(View v) { }

	public boolean onTouch(View v, MotionEvent me)
	{
		if(!parent.connected) // if not connected, do nothing and reset the values
		{
			actionID = actionBlank;
			keyboard = false;
			dragging = false;
			moveDragging = false;
			this.invalidate();
			return super.onTouchEvent(me);
		}
		
		int touchAction = me.getAction(); // get the movement type
		
		if(touchAction == MotionEvent.ACTION_DOWN || touchAction == MotionEvent.ACTION_MOVE || touchAction == MotionEvent.ACTION_UP) // if it's touch stuff, set new x and y
		{
			newX = me.getX();
			newY = me.getY();
		}
		
		switch(touchAction)
		{
			case(MotionEvent.ACTION_DOWN): // down
			{
				dragging = true;
				
				if(actionID != actionBlank) // if there is already a current touch action
					break;
				
				initX = newX; // set init x and y values
				initY = newY;
				
				long lastInitTime = initTime; // get start time
				initTime = System.currentTimeMillis();
				
				if(keyboardArea.contains((int)newX, (int)newY)) // if it's keyboard
				{
					actionID = actionKeyboard; // invert keyboard 
					keyboard = !keyboard;
					
					if(keyboard)
						parent.showKeyboard();
					else
						parent.hideKeyboard();
				}
				else if(scrollArea.contains((int)newX, (int)newY)) // if scroll set action
				{
					actionID = actionScroll;
					scrollMod = 0;
				}
				else if(mouse1Area.contains((int)newX, (int)newY)) // same for mouse 1
				{
					actionID = actionMouse1;
					parent.outputBufferAdapter(1,"m1p");
				}
				else if(mouse3Area.contains((int)newX, (int)newY)) // and 3
				{
					actionID = actionMouse3;
					parent.outputBufferAdapter(1,"m3p");
				}
				else if(moveArea.contains((int)newX, (int)newY)) // if it's move area
				{
					actionID = actionMove;
					if(initTime - lastInitTime < 200) // if last touch was less than 200 ms, press mouse 1 and set drag true
					{
						parent.outputBufferAdapter(1,"m1p");
						moveDragging = true;
					}
				}
				else if(disconnectArea.contains((int)newX, (int)newY)) // if disconnect button
				{
			    	parent.userDisconnect = true; 
					parent.endConnection();
				}
				break;
			}
			case(MotionEvent.ACTION_MOVE):
			{
				switch(actionID)
				{
					case(actionScroll): // if scrolling
					{
						int totalMod = (int)((initY-newY)/25); // scroll based on every 25 pix's
						if(totalMod != scrollMod)
						{
							parent.outputBufferAdapter(1, "ms" + (int)(scrollMod-totalMod));
							scrollMod = totalMod;
						}
						break;
					}
					case(actionMouse1): case(actionMouse3): case(actionMove): // if its mouse 3, mouse 1 or moving, update mouse delta
					{
						parent.mouseDeltaAdapter(1, newX-curX, newY-curY);
						break;
					}
				}
				break;
			}
			case(MotionEvent.ACTION_UP): // on up
			{
				switch(actionID)
				{
					case(actionMouse1):
					{
						parent.outputBufferAdapter(1,"m1r"); // release mouse 1
						break;
					}
					case(actionMouse3):
					{
						parent.outputBufferAdapter(1,"m3r"); // or 3
						break;
					}
					case(actionMove):
					{
						if(moveDragging)
							parent.outputBufferAdapter(1,"m1r"); // or if you were dragging, release 1 again
						break;
					}
				}
				
				actionID = actionBlank; // reset vars
				dragging = false;
				moveDragging = false;
				break;
			}
		}
		
		curX = newX; // update cur x y values
		curY = newY;

		this.invalidate(); // update display
		return super.onTouchEvent(me);
	}
}
