/**
 * Capture.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Capture - a move that captures one (or more) of the opponent's pieces.
 * A capture is also used to describe additional jumps on an initial capture
 * @author Craig A. Damon
 *
 */
public class Capture extends Move
{

	/**
	 * @param p the piece being moved
	 * @param captured the piece being captured
	 * @param startPos the starting position at the start of this individual jump
	 * @param endPos the position after this specific jump
	 */
	public Capture(Piece p, Piece captured, int startPos, int endPos)
		{
			super(p, startPos, endPos);
			_additional = null;
			if (captured.getPosition() < 1)
				throw new IllegalArgumentException("creating capture of piece already take "+startPos+"-"+endPos);
			_captured = captured.copy();
		}
	
	/**
	 * make a copy of an existing capture
	 * @param original
	 */
	private Capture(Capture original)
	{
		super(original.getPiece(),original.startPos(),original.endPos());
		if (original._additional != null)
			_additional = original._additional.copy();
		_captured = original._captured.copy();
	}

	/**
	 * @return a human readable description of the move
	 * @see edu.vtc.cis3310.checkers.Move#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(32);
		if (getPlayerColor() == Color.Red)
			sb.append('R');
		else
			sb.append('W');
		formatJump(sb);
		if (kingsPiece())
			sb.append('K');
		return String.valueOf(sb);
	}

	/** format a single jump
	 * @param sb the string buffer for holding the formatted jump
	 */
	private void formatJump(StringBuffer sb)
	{
		sb.append(startPos());
		sb.append("X(");
		sb.append(_captured.getPosition());
		sb.append(')');
		if (_additional == null)
			{
				sb.append(endPos());
			}
		else
			{
				_additional.formatJump(sb);
			}
	}
	
	/**
	 * get the next jump in the chain, if any
	 * @return the next jump or null, if already at the end of the jump
	 */
	public Capture getAdditionalJump()
	{
		return _additional;
	}
	
	/**
	 * add an extra jump onto the end of this move
	 * @param next the additional jump to make
	 */
	public void jumpAgain(Capture next)
	{
		if (_additional != null)
			_additional.jumpAgain(next);
		else
		  _additional = next;
		updateEndPos(next.endPos());
	}
	
	/**
	 * make a copy of this move, used to build up branching moves
	 * @return the copied move
	 */
	public Capture copy()
	{
		return new Capture(this);
	}
	
	/**
	 * what pieces were captured in this move
	 * @return the pieces captured in this move, in no particular order
	 * @see edu.vtc.cis3310.checkers.Move#captures(CheckerBoard)
	 */
	@Override
	public Collection<Piece> captures(CheckerBoard board)
	{
		Collection<Piece> captured;
		if (_additional != null)
			{
				captured = _additional.captures(board);
			}
		else
			{
				captured = new ArrayList<Piece>(5);
			}
		captured.add(_captured);
		return captured;
	}
	
	/**
	 * is this move productive towards avoiding a draw?
	 * @return true always
	 * @see edu.vtc.cis3310.checkers.Move#isProductive()
	 */
	@Override
	 public boolean isProductive()
	 {
     return true;
	 }

	// representation
	private Capture _additional;  // the next jump, may be null
	private Piece _captured;  // a copy of the piece captured, never null
}
