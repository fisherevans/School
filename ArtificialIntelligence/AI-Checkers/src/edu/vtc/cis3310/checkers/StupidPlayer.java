/**
 * StupidPlayer.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.util.Collection;


/**
 * StupidPlayer - a very stupid checkers player
 * @author Craig A. Damon
 *
 */
public class StupidPlayer implements CheckersPlayer
{
	/** 
	 * create stupidity
	 * @param color
	 */
  public StupidPlayer(Color color)
  {
  	  _color = color;
  }
  
  /**
   * get the color being played
   * @return Red or White
   * @see CheckersPlayer#getColor()
   */
  public Color getColor()
  {
  	  return _color;
  }
  
	/**
	 * @param legalMoves
	 * @param game
	 * @return the move chosen
	 * @see CheckersPlayer#chooseMove(java.util.Collection, edu.vtc.cis3310.checkers.CheckersGame)
	 */
	@Override
	public Move chooseMove(Collection<Move> legalMoves, CheckersGame game)
	{
		for (Move move : legalMoves)
			return move;
		return null;
	}
	
	private Color _color;  // the color who will not win
}
