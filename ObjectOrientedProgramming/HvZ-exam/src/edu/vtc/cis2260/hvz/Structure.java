/**
 * Structure.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Structure - description
 * @author Craig A. Damon
 *
 */
public class Structure extends GameElement
{

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param name
	 * @param game
	 * @param doorSide // 1 == right, 2 == top, 3 == left, 4 == bottom
	 */
	protected Structure(int x, int y, int w, int h, String name, HvZGame game,int doorSide)
		{
			super(x, y, game);
			_height = h;
			_width = w;
			_name = name;
			_inhabitants = new ArrayList<Player>();
			_doorSide = doorSide;
		}
	
	/**
	 * @param field
	 * @see edu.vtc.cis2260.hvz.GameElement#display(Graphics, edu.vtc.cis2260.hvz.PlayingField)
	 */
	@Override
	public void display(Graphics g, PlayingField field)
	{
		field.displayStructure(g,this,_doorSide);
	}

	/** note that someone is now in this structure
	 * @param player
	 */
	public void addInhabitant(Player player)
	{
     _inhabitants.add(player);		
	}

	/** note that someone is no longer in this structure
	 * @param player
	 */
	public void removeInhabitant(Player player)
	{
     _inhabitants.remove(player);		
	}
	
	public int getDoorX()
	{
		switch (_doorSide)
		{
			case 1:
				return getX()+getWidth()+5;
			case 2:
			case 4:
				return getX()+getWidth()/2-15;
			case 3:
				return getX()-20;
		}
		return 0;
	}
	
	public int getDoorY()
	{
		switch (_doorSide)
		{
			case 2:
				return getY()-15;
			case 1:
			case 3:
				return getY()+getHeight()/2-5;
			case 4:
				return getY()+getHeight()+5;
		}
		return 0;
	}

	
	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return _width;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return _height;
	}
	
	/**
	 * get the right side of the structure
	 * @return
	 */
	public int getRight()
	{
		return getX()+_width;
	}
	
	/**
	 * get the right side of the structure
	 * @return
	 */
	public int getBottom()
	{
		return getY()+_height;
	}

	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return _name;
	}

	private int _width;
	private int _height;
	private String _name;
	private Collection<Player> _inhabitants;
	private int _doorSide;  // 1 is right, 2 is top, 3 is left, 4 is bottom
	/** description
	 * @param player
	 * @return
	 */
	public int angleFrom(Player player)
	{
		int targetX = getDoorX();
		int targetY = getDoorY();
		switch (_doorSide)
		{
			case 1:
				if (player.getX() < getRight())
					{
						if (player.getY() <= getY())
							{  // go for top corner
								targetX = getRight();
								targetY = getY();
								break;
							}
						else if (player.getY() >= getBottom())
							{
								targetX = getRight();
								targetY = getBottom();
								break;
							}
						else if (player.getY() >= getY()+getHeight()/2)
							{
								targetX = getX();
								targetY = getBottom();
								break;
							}
						else
							{
								targetX = getX();
								targetY = getY();
								break;
							}
					}
				break;
			case 2:
				if (player.getY() > getY())
					{
						if (player.getX() <= getX())
							{  // go for left corner
								targetX = getX();
								targetY = getY();
								break;
							}
						else if (player.getX() >= getRight())
							{
								targetX = getRight();
								targetY = getY();
								break;
							}
						else if (player.getX() >= getX()+getWidth()/2)
							{
								targetX = getRight();
								targetY = getBottom();
								break;
							}
						else
							{
								targetX = getX();
								targetY = getBottom();
								break;
							}
					}
				break;
			case 3:
				if (player.getX() > getX())
					{
						if (player.getY() <= getY())
							{  // go for top corner
								targetX = getX();
								targetY = getY();
								break;
							}
						else if (player.getY() >= getBottom())
							{
								targetX = getX();
								targetY = getBottom();
								break;
							}
						else if (player.getY() >= getY()+getHeight()/2)
							{
								targetX = getRight();
								targetY = getBottom();
								break;
							}
						else
							{
								targetX = getRight();
								targetY = getY();
								break;
							}
					}
				break;
			case 4:  // door is on bottom
				if (player.getY() < getBottom())
					{
						if (player.getX() <= getX())
							{  // go for left corner
								targetX = getX();
								targetY = getBottom();
								break;
							}
						else if (player.getX() >= getRight())
							{
								targetX = getRight();
								targetY = getBottom();
								break;
							}
						else if (player.getX() >= getX()+getWidth()/2)
							{
								targetX = getRight();
								targetY = getY();
								break;
							}
						else
							{
								targetX = getX();
								targetY = getY();
								break;
							}
					}
				break;
		}
		int xdelt = targetX-player.getX();
		int ydelt = player.getY() - targetY;  // backwards because of screwy display graphics coord system
		if (xdelt == 0)
			{
				if (ydelt >= 0)
					return 90;
				return 270;
			}
		if (ydelt == 0)
			{
				if (xdelt >= 0)
					return 0;
				return 180;
			}
		double tan = ((double)ydelt)/xdelt;
		double radians = Math.atan(tan);
		if (radians < 0)
			radians += 2*Math.PI;
		double degrees = radians*180/Math.PI;
		if (xdelt < 0)
			{
				if (ydelt > 0)
				  degrees -= 180;
				else
					degrees += 180;
			}
		return (int)(degrees+0.5);
	}
}
