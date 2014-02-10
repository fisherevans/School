/**
 * Color.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

/**
 * 
 * Color - the two colors in use in the checkers game
 * @author Craig A. Damon
 *
 */
public enum Color {
	/** The first player to go */
	Red,
	/** The second player */
	White;
	
	/**
	 * get this color's opposite, their opponent
	 * @return the opposite color, Red <-> White
	 */
	public Color opposite()
	{
		switch (this)
		{
			case Red:
				return White;
			case White:
				return Red;
		}
		return null;
	}
}