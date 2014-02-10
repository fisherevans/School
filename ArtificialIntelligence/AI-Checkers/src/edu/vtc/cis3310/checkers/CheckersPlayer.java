/**
 * CheckersPlayer.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.util.Collection;


/**
 * CheckersPlayer - behavior needed for a checker's player
 * @author Craig A. Damon
 *
 */
public interface CheckersPlayer
{
	/**
	 * get the color that this player is using
	 * @return the color of the player
	 */
  public Color getColor();

  /**
   * choose a move from all the legal moves
   * @param legalMoves the collection of possible moves for the indicated state of the board, in no guaranteed order
   * @param game the game being played, including the state of the board
   * @return the chosen move, never null
   */
  public Move chooseMove(Collection<Move> legalMoves, CheckersGame game);
}
