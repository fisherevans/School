/**
 * Piece.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Piece - a description of a single piece in the checkers game
 * @author Craig A. Damon
 *
 */
public class Piece
{
	/**
	 * create a new piece
	 * @param color the color of the piece
	 * @param pos its initial position, in 1-32 notation
	 */
  Piece(Color color,int pos)
  {
  	  _color = color;
  	  _isKing = false;
  	  _pos = (byte)pos;
  }
  
  /**
   * get the position of a piece, in 1-32 notation. 
   * 1-4 are the 4 back-row spaces for Red, with 1 being on the right and 4 being the left back corner.
   * @return the position in 1-32 notation or -1 if the piece is no longer on the board
   */
  public int getPosition()
  {
  	  return _pos;
  }
  
  /**
   * set the position of a piece after it has moved
   * @param pos the new position in 1-32 notation
   */
  public void setPosition(int pos)
  {
  	  _pos = (byte)pos;
  }
  
  /**
   * destroy a piece, removing it from the board
   *
   */
  public void destroy()
  {
  	  _pos = -1;
  }
  
  /**
   * king a piece
   *
   */
  public void kingMe()
  {
  	  _isKing = true;
  }
  
  /**
   * is this piece a king, capable of moving both forward and backwards
   * @return true if the piece is a king
   */
  public boolean isKing()
  {
  	  return _isKing;
  }
  
  /**
   * get the color of the piece
   * @return the color
   */
  public Color getColor()
  {
  	  return _color;
  }
  
  /**
   * make a copy of a piece
   * @return the copy
   */
  public Piece copy()
  {
  	  Piece p = new Piece(_color,getPosition());
  	  if (_isKing)
  	  	  p.kingMe();
  	  return p;
  }
  
  /**
   * get a description of this piece
   * @return one of the strings "R ", "W ", "RK", "WK"
   */
  public String toString()
  {
  	  String s;
  	  if (_color == Color.Red)
  	  	  s = "R";
  	  else
  	  	  s = "W";
  	  if (_isKing)
  	  	  s += "K";
  	  else
  	  	  s += " ";
  	  return s;
  }
  
  /**
   * list all the possible advances (simple moves) that are legal for this piece, given the state of the board
   * @param board
   * @return the advances in no particular order, never null but may be empty
   */
  public Collection<Advance> possibleAdvances(CheckerBoard board)
  {
  	  Collection<Advance> advances = new ArrayList<Advance>(4);
		int leftPos = CheckerBoard.advanceLeft(getColor(), getPosition());
		if (leftPos > 0)
			{
				if (board.getPiece(leftPos) == null)
					advances.add(new Advance(this, getPosition(), leftPos));
			}
		int rightPos = CheckerBoard.advanceRight(getColor(), getPosition());
		if (rightPos > 0)
			{
				if (board.getPiece(rightPos) == null)
					advances.add(new Advance(this, getPosition(), rightPos));
			}
		
		if (isKing())
			{
			  leftPos = CheckerBoard.advanceLeft(getColor().opposite(), getPosition());
				if (leftPos > 0)
					{
						if (board.getPiece(leftPos) == null)
							advances.add(new Advance(this, getPosition(), leftPos));
					}
			  rightPos = CheckerBoard.advanceRight(getColor().opposite(), getPosition());
				if (rightPos > 0)
					{
						if (board.getPiece(rightPos) == null)
							advances.add(new Advance(this, getPosition(), rightPos));
					}
			}
  	  
  	  return advances;
  }
 
  
  /**
   * list all the possible jumps (captures) that are legal for this piece, given the state of the board.
   * Where appropriate the captures include multiple jumps as a single Capture move
   * @param board
   * @return the captures in no particular order, never null but may be empty
   */
  public Collection<Capture> possibleCaptures(CheckerBoard board)
  {
	  Collection<Capture> captures = new ArrayList<Capture>(4);
		int leftJump = CheckerBoard.jumpLeft(getColor(), getPosition());
		if (leftJump > 0 && board.getPiece(leftJump) == null)
			{
				Piece p = board.getPiece(CheckerBoard.advanceLeft(getColor(),
						getPosition()));
				if (p != null && p.getColor() != getColor())
					{
						Capture c = new Capture(this, p, getPosition(), leftJump);
						addCaptures(captures, c,c,board);
					}
			}
		int rightJump = CheckerBoard.jumpRight(getColor(), getPosition());
		if (rightJump > 0 && board.getPiece(rightJump) == null)
			{
				Piece p = board.getPiece(CheckerBoard.advanceRight(getColor(),
						getPosition()));
				if (p != null && p.getColor() != getColor())
					{
						Capture c = new Capture(this, p, getPosition(), rightJump);
						addCaptures(captures, c,c,board);
					}
			}
	
	if (isKing())
		{
		  leftJump = CheckerBoard.jumpLeft(getColor().opposite(), getPosition());
			if (leftJump > 0 && board.getPiece(leftJump) == null)
				{
					Piece p = board.getPiece(CheckerBoard.advanceLeft(getColor().opposite(),getPosition()));
					if (p != null && p.getColor() != getColor())
						{
							Capture c = new Capture(this, p, getPosition(), leftJump);
							addCaptures(captures, c,c, board);
						}
				}
		  rightJump = CheckerBoard.jumpRight(getColor().opposite(), getPosition());
			if (rightJump > 0 && board.getPiece(rightJump) == null)
				{
					Piece p = board.getPiece(CheckerBoard.advanceRight(getColor().opposite(),getPosition()));
					if (p != null && p.getColor() != getColor())
						{
							Capture c = new Capture(this, p, getPosition(), rightJump);
							addCaptures(captures, c,c, board);
						}
				}
		}
	  
	  return captures;
  }
  
  /** helper function to recursively look for extended jumps
	 * @param captures the building list of full capture moves
	 * @param base the entire chain of jumps considered this far
	 * @param lastJump the latest capture move being considered in the chain
	 * @param captured the pieces that have already been captured by this move
	 * @param board the state of the board after the immediate capture
	 */
	private void addCaptures(Collection<Capture> captures, Capture base,Capture lastJump,CheckerBoard board)
	{
		if (lastJump.kingsPiece())
			{
				captures.add(base);
				return;
			}
		board = board.makeMove(lastJump);
		boolean extraMoves = false;
		int leftJump = CheckerBoard.jumpLeft(getColor(), lastJump.endPos());
		if (leftJump > 0 && board.getPiece(leftJump) == null)
			{
				Piece p = board.getPiece(CheckerBoard.advanceLeft(getColor(),lastJump.endPos()));
				if (p != null && p.getColor() != getColor())
					{
						Capture c0 = lastJump.copy();
						Capture c2 = new Capture(this, p, lastJump.endPos(), leftJump);
						c0.jumpAgain(c2);
						addCaptures(captures, base,c2, board);
						extraMoves = true;
					}
			}
		int rightJump = CheckerBoard.jumpRight(getColor(), lastJump.endPos());
		if (rightJump > 0 && board.getPiece(rightJump) == null)
			{
				Piece p = board.getPiece(CheckerBoard.advanceRight(getColor(),lastJump.endPos()));
				if (p != null && p.getColor() != getColor())
					{
						Capture c0 = lastJump.copy();
						Capture c2 = new Capture(this, p, lastJump.endPos(), rightJump);
						c0.jumpAgain(c2);
						addCaptures(captures, base,c2, board);
						extraMoves = true;
					}
			}

		if (isKing())
			{
				leftJump = CheckerBoard.jumpLeft(getColor().opposite(), lastJump.endPos());
				if (leftJump > 0 && board.getPiece(leftJump) == null)
					{
						Piece p = board.getPiece(CheckerBoard.advanceLeft(getColor().opposite(), lastJump.endPos()));
						if (p != null && p.getColor() != getColor())
							{
								Capture c0 = lastJump.copy();
								Capture c2 = new Capture(this, p, lastJump.endPos(), leftJump);
								c0.jumpAgain(c2);
								addCaptures(captures, base,c2, board);
								extraMoves = true;
							}
					}
				rightJump = CheckerBoard.jumpRight(getColor().opposite(), lastJump.endPos());
				if (rightJump > 0 && board.getPiece(rightJump) == null)
					{
						Piece p = board.getPiece(CheckerBoard.advanceRight(getColor().opposite(), lastJump.endPos()));
						if (p != null && p.getColor() != getColor())
							{
								Capture c0 = lastJump.copy();
								Capture c2 = new Capture(this, p, lastJump.endPos(), rightJump);
								c0.jumpAgain(c2);
								addCaptures(captures, base,c2, board);
								extraMoves = true;
							}
					}
			}
		if (!extraMoves)
			captures.add(base);
	}

	// representation
	private Color _color;       // color of the piece, never null
  private boolean _isKing;    //
  private byte _pos;          // if piece is on the board, 1-32, if not on board, -1
}
