package info.fisherevans.wordtoss.views;

import info.fisherevans.wordtoss.R;
import info.fisherevans.wordtoss.WordTossActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class LetterTile extends TextView implements View.OnTouchListener
{
	private WordTossActivity parent;
	private char letter;
	
	private int width, height, fontSize;
	
	private float touchOffset;
	
	public LetterTile(WordTossActivity parent, char letter)
	{
		super(parent);
		
		this.parent = parent;
		this.letter = letter;
		this.width = parent.tileWidth;
		this.height = (int) (width*1.2);
		fontSize = (int) (height*0.8);
		
		this.setHeight(height);
		this.setWidth(width);
		this.setBackgroundColor(parent.defaultTileBG);
		this.setText(letter + "");
		this.setGravity(Gravity.CENTER);
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
		this.setTextColor(parent.defaultTileText);
		
		this.setOnTouchListener(this);
	}

	public boolean onTouch(View v, MotionEvent me)
	{
		if(!parent.solved && !parent.solving && !parent.skipping)
		{
			switch(me.getAction())
			{
				case MotionEvent.ACTION_DOWN: // On press
				{
					parent.playSound(R.raw.pick);
					touchOffset = me.getX();
					parent.colorTiles(parent.defaultTileBG);
					parent.colorTile(this, parent.dragTileBG);
					break;
				}
				case MotionEvent.ACTION_MOVE: // On drag
				{
					if(!parent.solved)
					{
						parent.setMovingTilePosition(this, me.getRawX() - touchOffset);
					}
					break;
				}
				case MotionEvent.ACTION_UP: // On release
				{
					parent.playSound(R.raw.drop);
					parent.colorTiles(parent.defaultTileBG);
					parent.sortTiles();
					parent.snapTiles();
					break;
				}
			}
		}
		return true;
	}
	
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		Rect rect = new Rect();
		Paint paint = new Paint();
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(parent.defaultTileText);
		paint.setStrokeWidth(parent.tileStroke);
		getLocalVisibleRect(rect);
		canvas.drawRect(rect, paint);
	}
	
	public char getLetter()
	{
		return letter;
	}
}
