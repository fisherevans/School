/**
 * Move.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.util.Collection;


/**
 * Move - a general move
 * @author Craig A. Damon
 *
 */
public abstract class Move
{
	/**
	 * construct the move
	 * @param p the piece being moved
	 * @param startPos its starting position
	 * @param endPos its ending position after the move
	 */
	 protected Move(Piece p,int startPos,int endPos)
	 {
		 _piece = p;
		 _startPos = startPos;
		 _endPos = endPos;
		 repOK();
	 }
	 
	 /**
	  * force a move specific description
	  * @return a human readable description
	  */
   public abstract String toString();
   
   /**
    * does this move end up with the piece becoming a king?
    * @return true if this move kings the piece
    */
   public boolean kingsPiece()
   {
  	   if (_piece.isKing())
  	  	   return false;
  	   return CheckerBoard.isKingRow(getPlayerColor(),_endPos);
   }
   
   /**
    * what pieces are captured on the board
    * @param board the board where the move is applied
    * @return the pieces captured or null if no captures take place
    */
   public abstract Collection<Piece> captures(CheckerBoard board);
   
 
   /**
    * is this move productive, avoiding a draw, which is called after 40 consecutive unproductive moves
    * A move is productive if it moves an unkinged piece towards the king row or captures a piece
    * @return true if the move is productive
    */
 	 public abstract boolean isProductive();

   /**
    * get the color of the player making the move
    * @return the color
    */
   public Color getPlayerColor()
   {
  	   return _piece.getColor();
   }
   
   /**
    * get the piece being moved
    * @return the piece
    */
   public Piece getPiece()
   {
  	   return _piece;
   }
   
   /**
    * get the starting position of the piece
    * @return the starting position in 1-32 notation
    */
   public int startPos()
   {
  	   return _startPos;
   }
   
   /**
    * get the ending position of the piece being moved after the move is made
    * @return the ending position in 1-32 notation
    */
   public int endPos()
   {
  	   return _endPos;
   }
   
   /**
    * update the final position of the piece. This should only be used within the Move pieces.
    * @param newPos the new position in 1-32
    */
   protected void updateEndPos(int newPos)
   {
  	   _endPos = newPos;
  	   repOK();
   }
   
   protected void repOK()
   {
  	   assert(_piece != null);
  	   assert(_startPos >= 1 && _startPos <= 32);
  	   assert(_endPos >= 1 && _endPos <= 32);
   }
   
   // representation
   private Piece _piece;  // who is making this move, never null
   private int _startPos; // starting position, always 1-32
   private int _endPos;   // ending position of piece, always 1-32
}
