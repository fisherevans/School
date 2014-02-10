/**
 * PlayingField.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

import java.awt.Graphics;

/**
 * PlayingField - description
 * @author Craig A. Damon
 *
 */
public class PlayingField
{
	/**
	 * create the playing field
	 * @param w
	 * @param h
	 */
  public PlayingField(HvZGame game,int w,int h)
  {
  	  _game = game;
  	  _h = h;
  	  _w = w;
  	  _display = new Display(this);
  }

	/** description
	 * @param g
	 */
	public void display(Graphics g)
	{
		for (Structure structure : _game.getStructures())
			{
				structure.display(g,this);
			}
		
		for (Player p : _game.getActivePlayers())
			{
				p.display(g,this);
			}
	}

	/** force the field to re-draw itself
	 * 
	 */
	public void redisplay()
	{
		_display.updateScores();
		_display.repaint();
	}
	
	/** note that this game has ended
	 * @param ended true if the game has actually ended, false if it is being started again
	 */
	public void gameOver(boolean ended)
	{
		_display.gameOver(ended);
	}


	
	/** display a structure on the playing field
	 * @param g
	 * @param structure
	 * @param doorSide 1 == right, 2 == top, 3 == left, 4 == bottom
	 */
	public void displayStructure(Graphics g,Structure structure,int doorSide)
	{
		_display.displayStructure(g,structure.getX(),structure.getY(),structure.getWidth(),structure.getHeight(), structure.getName(),doorSide);
	}
	
	/** display a player on the playing field
	 * @param g
	 * @param p
	 */
	public void displayPlayer(Graphics g,Player p)
	{
		_display.displayPlayer(g,p.getX(),p.getY(),p.getDirection(),p.getType(), p.getNumber());
	}


	public int getWidth()
	{
		return _w;
	}
	
	public int getHeight()
	{
		return _h;
	}
	
	public HvZGame getGame()
	{
		return _game;
	}
	
	private int _w;
	private int _h;
	private HvZGame _game;
	private Display _display;
}
