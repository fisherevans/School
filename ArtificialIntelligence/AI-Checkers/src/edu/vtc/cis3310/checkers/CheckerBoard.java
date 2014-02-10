/**
 * CheckerBoard.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;


/**
 * CheckerBoard - a basic implementation of the board for a checkers game.
 * Provides the basic mechanisms of the game, including lists of legal moves.
 * @author Craig A. Damon
 *
 */
public class CheckerBoard
{
	/**
	 * create a checkboard in the legal starting state
	 */
	public CheckerBoard()
	{
		_spaces = new Piece[32];
		for (int i = 0; i < 12; i++)
			_spaces[i] = new Piece(Color.Red,i+1);
		for (int i = 20; i < 32; i++)
			_spaces[i] = new Piece(Color.White,i+1);
	}
	
	/**
	 * create a new checkerboard, just like the old one
	 * @param original
	 */
	private CheckerBoard(CheckerBoard original)
	{
		_spaces = new Piece[32];
		for (int n = 0; n < 32; n++)
			{
				if (original._spaces[n] != null)
					_spaces[n] = original._spaces[n].copy();
			}
	}
	
	/**
	 * get the piece at position n on the board
	 * @param n from 1-32. Numbers 1-4 are the back row of the red starting side, from Red's right to left.
	 * @return the piece or null if no piece is there
	 */
	public Piece getPiece(int n)
	{
		return _spaces[n-1];
	}
	
	/**
	 * give a list of all the legal moves for a player.
	 * @param turn the color of the player whose turn it is
	 * @return the moves, in no particular order
	 */
	public Collection<Move> legalMoves(Color turn)
	{
		Collection<Move> moves = new ArrayList<Move>();
		
		for (int i = 0; i < 32; i++)
			{
				if (_spaces[i] == null)
					continue;
				if (_spaces[i].getColor() != turn)
					continue;
				moves.addAll(_spaces[i].possibleCaptures(this));
			}
		
		if (!moves.isEmpty())
			return moves;
		
		for (int i = 0; i < 32; i++)
			{
				if (_spaces[i] == null)
					continue;
				if (_spaces[i].getColor() != turn)
					continue;
				moves.addAll(_spaces[i].possibleAdvances(this));
			}
		
		return moves;
	}
	
	/**
	 * apply a move to a copy of the board, returning the copy with the updates applied
	 * @param move the move to make
	 * @return the copy
	 */
	public CheckerBoard makeMove(Move move)
	{
		CheckerBoard b = new CheckerBoard(this);
		Piece p = b.getPiece(move.startPos());
		b._spaces[move.startPos()-1] = null;
		p.setPosition(move.endPos());
		b._spaces[move.endPos()-1] = p;
		if (move.kingsPiece())
			p.kingMe();
		Collection<Piece> captures = move.captures(b);
		if (captures != null)
		  {
		  	  for (Piece opponent : captures)
		  	  	{
		  	  		int pos = opponent.getPosition();
		  	  		b._spaces[pos-1].destroy();
		  	  		b._spaces[pos-1] = null;
		  	  	}
		  }
		return b;
	}
	
	/**
	 * is this checker board in the same state as another
	 * @param other
	 * @return true if they are, false if not
	 */
	public boolean equals(CheckerBoard other)
	{
		if (other == this)
			return true;
		if (other == null)
			return false;
		for (int i = 0; i < 32; i++)
			{
				Piece thisPiece = _spaces[i];
				Piece otherPiece = other._spaces[i];
				if (thisPiece == otherPiece)
					continue;
				if (thisPiece == null)
					return false;
				if (otherPiece == null)
					return false;
				if (thisPiece.getColor() != otherPiece.getColor())
					return false;
				if (thisPiece.isKing() != otherPiece.isKing())
					return false;
			}
		return true;
	}
	
	/**
	 * is this object the same as another
	 * @param other
	 * @return true if other is also a checker board, and it is in the same state
	 */
	@Override
	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (other instanceof CheckerBoard)
			return equals((CheckerBoard)other);
		return false;
	}
	
	/**
	 * compute a hash code for the state of this checker board.
	 * The hash code must be identical for all identical states and should generally be distinct for different states
	 * @return the hash code
	 */
	@Override
	public int hashCode()
	{
		int redCode = 2;
		int whiteCode = 1;
		for (int i = 0; i < 32; i++)
			{
				Piece p = _spaces[i];
				if (p == null)
					continue;
				if (p.getColor() == Color.Red)
					{
					  redCode *= 8;
					  redCode += p.getPosition();
					  if (p.isKing())
					  	  redCode++;
					}
				else
					{
					  whiteCode *= 8;
					  whiteCode += p.getPosition();
					  if (p.isKing())
					  	  whiteCode++;
					}
			}
		return redCode*whiteCode;
	}
	
	/**
	 * display the state of the board (in simple ASCII) to an output stream
	 * @param out
	 */
	public void display(PrintStream out)
	{
		int n = 0;
		for (int row = 0; row < 8; row++)
			{
				if ((row & 1) == 0)
					{
						for (int j = 0; j < 4; n++, j++)
							{
								out.print("     ");
								if (_spaces[n] == null)
									out.print("__");
								else
									out.print(_spaces[n].toString());
								out.print(" ");
							}
					} 
				else
					{
						for (int j = 0; j < 4; n++, j++)
							{
								out.print(" ");
								if (_spaces[n] == null)
									out.print("__");
								else
									out.print(_spaces[n].toString());
								out.print("     ");
							}
					}
				out.println();
			}
	}
	
	/**
	 * display the numbers of the dark squares on the board, for ease of reading moves
	 * @param out
	 */
	public static void displayPositions(PrintStream out)
	{
		int n = 1;
		for (int row = 0; row < 8; row++)
			{
				if ((row & 1) == 0)
					{
						for (int j = 0; j < 4; n++, j++)
							{
								out.print("     ");
								if (n < 10)
									out.print(" ");
								out.print(n);
								out.print(" ");
							}
					} 
				else
					{
						for (int j = 0; j < 4; n++, j++)
							{
								out.print(" ");
								if (n < 10)
									out.print(" ");
								out.print(n);
								out.print("     ");
							}
					}
				out.println();
			}
	}
	
	/** is the indicated position on the king row for the indicated color
	 * @param color the color of the piece in question
	 * @param pos the position on the board, in standard 1-32 notation
	 * @return true if the position is a king row, false if notr
	 */
	public static boolean isKingRow(Color color, int pos)
	{
		if (color == Color.Red)
		  return pos > 28;
		else
		  	return pos < 5;
	}

	/** what position is jumped over in the indicated jump (the position where the captured piece was)
	 * @param startPos the position of the jumping piece before the jump in 1-32 notation
	 * @param endPos the ending position of the jumping piece after the jump, in 1-32 notation
	 * @return the position jumped over, in 1-32 notation
	 */
	public static int capturePos(int startPos, int endPos)
	{
		int oddRowAdjust = ((startPos-1) & 4) >> 2;  // 1 for odd rows (5-8,13-16,21-24,29-32), 0 otherwise
		switch (endPos - startPos)
			{
			case 7:
				return startPos + 4 - oddRowAdjust;
			case 9:
				return startPos + 5 - oddRowAdjust;
			case -7:
				return startPos - 4 + oddRowAdjust;
			case -9:
				return startPos - 5 + oddRowAdjust;
			default:
				throw new IllegalArgumentException("cannot jump from startPos to endPos");
			}
	}
	
	/**
	 * give the position that would be moved in the indicated color moved forward-left from the indicated position
	 * @param color the color that defines the forward direction to be moved
	 * @param pos the position to move from, in standard 1-32 notation
	 * @return the final position, in 1-32 notation or -1 if no left move can be made because of a side or end of the board
	 */
	public static int advanceLeft(Color color, int pos)
	{
		boolean oddRow = ((pos - 1) & 4) == 4;
		if (isKingRow(color, pos))
			return -1;
		if (color == Color.Red)
			{
				if (oddRow)
					return pos + 4;
				if ((pos & 3) == 0)
					return -1;
				return pos + 5;
			} 
		else
			{
				if (!oddRow)
					return pos - 4;
				if ((pos & 3) == 1)
					return -1;
				return pos - 5;
			}
	}

	
	/**
	 * give the position that would be moved in the indicated color moved forward-right from the indicated position
	 * @param color the color that defines the forward direction to be moved
	 * @param pos the position to move from, in standard 1-32 notation
	 * @return the final position, in 1-32 notation or -1 if no right move can be made because of a side or end of the board
	 */
	public static int advanceRight(Color color, int pos)
	{
		boolean oddRow = ((pos - 1) & 4) == 4;
		if (isKingRow(color, pos))
			return -1;
		if (color == Color.Red)
			{
				if (!oddRow)
					return pos + 4;
				if ((pos & 3) == 1)
					return -1;
				return pos + 3;
			} 
		else
			{
				if (oddRow)
					return pos - 4;
				if ((pos & 3) == 0)
					return -1;
				return pos - 3;
			}
	}
	
	/**
	 * give the position that would be jumped to in the forward direction of the indicated color.
	 * This just computes the space two spaces forward-left diagonally, ignoring whether pieces are in the place to allow the jump.
	 * @param color the color that defines the forward direction to be jumped
	 * @param pos the position to jump from, in standard 1-32 notation
	 * @return the final position, in 1-32 notation or -1 if no left jump can be made because of a side or end of the board
	 */
	public static int jumpLeft(Color color, int pos)
	{
		if (color == Color.Red)
			{
				if (pos > 24)
					return -1;
				if ((pos & 3) == 0)
					return -1;
				return pos + 9;
			} 
		else
			{
				if (pos < 9)
					return -1;
				if ((pos & 3) == 1)
					return -1;
				return pos - 9;
			}
	}

	
	/**
	 * give the position that would be jumped to in the forward direction of the indicated color.
	 * This just computes the space two spaces forward-right diagonally, ignoring whether pieces are in the place to allow the jump.
	 * @param color the color that defines the forward direction to be jumped
	 * @param pos the position to jump from, in standard 1-32 notation
	 * @return the final position, in 1-32 notation or -1 if no right jump can be made because of a side or end of the board
	 */
	public static int jumpRight(Color color, int pos)
	{
		if (color == Color.Red)
			{
				if (pos > 20)
					return -1;
				if ((pos & 3) == 1)
					return -1;
				return pos + 7;
			} 
		else
			{
				if (pos < 13)
					return -1;
				if ((pos & 3) == 0)
					return -1;
				return pos - 7;
			}
	}

	
	private Piece[] _spaces;

	/** run a simple text of the board
	 * @param args
	 */
	public static void main(String[] args)
	{
		CheckerBoard.displayPositions(System.out);
		System.out.println();
		CheckerBoard cb = new CheckerBoard();
		cb.display(System.out);
		Collection<Move> moves = cb.legalMoves(Color.Red);
		for (Move m : moves)
			{
				System.out.println();
				System.out.println(m);
				cb.makeMove(m).display(System.out);
			}
	}


}
