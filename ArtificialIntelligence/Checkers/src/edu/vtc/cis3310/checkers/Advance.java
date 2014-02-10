/**
 * Advance.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.util.Collection;


/**
 * Advance - A move that is a simple move of a single man
 * @author Craig A. Damon
 *
 */
public class Advance extends Move
{
	/**
	 * @param p the piece being moved
	 * @param startPos the starting position for the piece
	 * @param endPos the ending position for the piece
	 */
	public Advance(Piece p, int startPos, int endPos)
		{
			super(p, startPos, endPos);
		}

	/**
	 * build a human readable description of the move
	 * @return the description
	 * @see edu.vtc.cis3310.checkers.Move#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(10);
		if (getPlayerColor() == Color.Red)
			sb.append('R');
		else
			sb.append('W');
		sb.append(startPos());
		sb.append('-');
		sb.append(endPos());
		if (kingsPiece())
			sb.append('K');
		return String.valueOf(sb);
	}

	/**
	 * what pieces does this move capture
	 * @return null always
	 * @see edu.vtc.cis3310.checkers.Move#captures(CheckerBoard)
	 */
	@Override
	public Collection<Piece> captures(CheckerBoard board)
	{
		return null;
	}
	
	/**
	 * is this move productive, towards avoiding a draw?
	 * for simple moves, a productive move moves a non-king towards becoming a king
	 * @return true if the move is productive
	 * @see edu.vtc.cis3310.checkers.Move#isProductive()
	 */
	 public boolean isProductive()
	 {
		 if (getPiece().isKing())
			 return false;
		 if (getPlayerColor() == Color.Red)
			 return endPos() > startPos();
		 else
			 return endPos() < startPos();
	 }


}
