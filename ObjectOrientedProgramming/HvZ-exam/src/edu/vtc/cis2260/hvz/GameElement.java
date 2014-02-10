/**
 * GameElement.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

import java.awt.Graphics;

/**
 * GameElement - an element that appears on the board
 * @author Craig A. Damon
 *
 */
public abstract class GameElement
{
	/**
	 * 
	 * @param x
	 * @param y
	 * @param game
	 */
	protected GameElement(int x,int y,HvZGame game)
	{
		_x = x;
		_y = y;
		_game = game;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param game
	 */
	protected GameElement(HvZGame game)
	{
		_x = -1;
		_y = -1;
		_game = game;
	}

	/**
	 * display yourself
	 * @param g the rendering environment
	 * @param field
	 */
	public abstract void display(Graphics g, PlayingField field);
	
  public void setPosition(int x,int y)
	{
		_x = x;
		_y = y;
	}
  
  public void clearPosition()
	{
		_x = -1;
		_y = -1;
	}

	/**
	 * get the x position
	 * @return the x
	 */
   public int getX()
   {
  	   return _x;
   }
   
   /**
    * get the y position
    * @return the y
    */
   public int getY()
   {
  	   return _y;
   }
   
   /**
    * get the game
    * @return the game
    */
   public HvZGame getGame()
   {
  	   return _game;
   }
   
   
   private int _x;   // either between 0 and _game.getWidth()-1 or -1 for not placed on field
   private int _y;   // either between 0 and _game.getHeight()-1 or -1 for not placed on field
   private HvZGame _game;  // never null
}
