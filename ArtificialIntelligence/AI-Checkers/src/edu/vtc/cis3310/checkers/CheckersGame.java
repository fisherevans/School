/**
 * CheckersGame.java
 * Copyright 2013, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis3310.checkers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;



/**
 * CheckersGame - a game of checkers
 * @author Craig A. Damon
 *
 */
public class CheckersGame
{
	/**
	 * create the game between 2 players
	 * @param redPlayer
	 * @param whitePlayer
	 */
	public CheckersGame(CheckersPlayer redPlayer,CheckersPlayer whitePlayer)
	{
		_board = new CheckerBoard();
		_oneTimeBoards = new HashSet<CheckerBoard>();
		_twoTimeBoards = new HashSet<CheckerBoard>();
		_oneTimeBoards.add(_board);
		_redPlayer = redPlayer;
		_whitePlayer = whitePlayer;
		_displayMoves = true;
		_displayMoveBoards = true;
	}
	
	/**
	 * turn on or off showing each move taken.
	 * if turned off, will also suppress showing the boards by default.
	 * @param display if true, show each move, if false, hide each move
	 */
	public void displayMoves(boolean display)
	{
		_displayMoves = display;
		if (!display)
			_displayMoveBoards = display;
	}
	
	/**
	 * turn on or off showing the board (game state) after each move
	 * @param display if true, show the board
	 */
	public void displayBoards(boolean display)
	{
		_displayMoveBoards = display;
	}
	
	/**
	 * get the average time take by the red player to choose moves
	 * @return the average time in seconds
	 */
	public double getAverageRedTime()
	{
		if (_totalMoves < 1)
			return 0.0;
		return ((double)_redTimeTotal)/1000/((_totalMoves+1)/2);
	}
	
	/**
	 * get the average time take by the white player to choose moves
	 * @return the average time in seconds
	 */
	public double getAverageWhiteTime()
	{
		if (_totalMoves < 2)
			return 0.0;
		return ((double)_whiteTimeTotal)/1000/(_totalMoves/2);
	}
	
	/**
	 * return the board at the current state of the game
	 * @return the board
	 */
	public CheckerBoard getCurrentBoard()
	{
		return _board;
	}
	
	/**
	 * play the game
	 * @return Color.Red if red wins, Color.White if white wins, null if its a draw
	 */
	public Color play()
	{
		_unProductiveMoves = 0; 
		while (true)
			{
				Color moveMade = makeRedMove();
				if (moveMade != Color.Red)
					return moveMade;
				_totalMoves++;
				
				moveMade = makeWhiteMove();
				if (moveMade != Color.White)
					return moveMade;
				_totalMoves++;
			}
	}
	
	/**
	 * tell the red player to make its move and perform that move
	 * @return Color.White if red loses, null if its a draw, Color.Red if game should continue
	 */
	public Color makeRedMove()
	{
		Collection<Move> legalMoves = _board.legalMoves(Color.Red);
		if (legalMoves.isEmpty())
			{
				System.out.println("No moves for red -- red loses");
				return Color.White;
			}
		long start = System.currentTimeMillis();
		Move move = _redPlayer.chooseMove(legalMoves,this);
		long end = System.currentTimeMillis();
		_redTimeTotal += (int)(end-start);
		if (_displayMoves)
		  System.out.println(move.toString());
		if (isMoveUnproductiveDraw(move))
			{
				System.out.println("Game is draw -- No productive moves for 40 moves");
				return null;
			}
		if (move.isProductive())
			_unProductiveMoves = 0;
		else
			_unProductiveMoves++;
		_board = _board.makeMove(move);
		if (_displayMoveBoards)
			{
				System.out.println();
				_board.display(System.out);
				System.out.println();
			}
		if (isRepeatDraw(_board))
			{
				System.out.println("Game is draw, same board repeated 3 times");
				return null;
			}
		else if (_oneTimeBoards.contains(_board))
			{
				_oneTimeBoards.remove(_board);
				_twoTimeBoards.add(_board);
			}
		else
			{
				_oneTimeBoards.add(_board);
			}
		
		return Color.Red;
	}
	
	
	
	/**
	 * tell the red player to make its move and perform that move
	 * @return Color.Red if white loses, null if its a draw, Color.White if game should continue
	 */
	public Color makeWhiteMove()
	{
		Collection<Move> legalMoves = _board.legalMoves(Color.White);
		if (legalMoves.isEmpty())
			{
				System.out.println("No moves for white -- white loses");
				return Color.Red;
			}
		long start = System.currentTimeMillis();
	  Move move = _whitePlayer.chooseMove(legalMoves,this);
		long end = System.currentTimeMillis();
		_whiteTimeTotal += (int)(end-start);
	  if (_displayMoves)
		  System.out.println(move.toString());
		if (isMoveUnproductiveDraw(move))
			{
				System.out.println("Game is draw -- No productive moves for 40 moves");
				return null;
			}
		_board = _board.makeMove(move);
		if (_displayMoveBoards)
			{
				System.out.println();
				_board.display(System.out);
				System.out.println();
			}
		if (isRepeatDraw(_board))
			{
				System.out.println("Game is draw, same board repeated 3 times");
				return null;
			}
		else if (_oneTimeBoards.contains(_board))
			{
				_oneTimeBoards.remove(_board);
				_twoTimeBoards.add(_board);
			}
		else
			{
				_oneTimeBoards.add(_board);
			}
    return Color.White;
	}

	/**
	 * will this move induce a draw
	 * @param move
	 * @return true if it is a draw
	 */
	public boolean isDraw(Move move)
	{
		if (isMoveUnproductiveDraw(move))
			return true;
		CheckerBoard board = _board.makeMove(move);
		return isRepeatDraw(board);
	}

	/** is this board repeating enough to force a draw
	 * @return
	 */
	private boolean isRepeatDraw(CheckerBoard board)
	{
		return _twoTimeBoards.contains(board);
	}

	/** is this move the unproductive straw to draw the camel's back
	 * @param move
	 */
	private boolean isMoveUnproductiveDraw(Move move)
	{
		return !move.isProductive() && _unProductiveMoves >= 40;
	}
	
	// representation
	private CheckerBoard _board;                // never null
	private Set<CheckerBoard> _oneTimeBoards;   // all the boards (game states) seen exactly once, never null
	private Set<CheckerBoard> _twoTimeBoards;   // all the boards (game states) seen exactly twice, never null
	private CheckersPlayer _redPlayer;          // the player (decision maker) for red, never null
	private CheckersPlayer _whitePlayer;        // the player (decision maker) for white, never null
	private int _unProductiveMoves;             // the number of moves made since a productive move, >= 0
	private int _totalMoves;                    // the number of moves, >= 0
	private int _redTimeTotal;                  // the total time red has taken to choose moves, in ms
	private int _whiteTimeTotal;                  // the total time red has taken to choose moves, in ms
	private boolean _displayMoves;              // should each move be displayed
	private boolean _displayMoveBoards;         // should the resulting board be displayed after each move

	/** test a simple game between two stupid players, always draws
	 * @param args
	 */
	public static void main(String[] args)
	{
		CheckersPlayer redPlayer = new StupidPlayer(Color.Red);
		CheckersPlayer whitePlayer = new StupidPlayer(Color.White);
    CheckersGame game = new CheckersGame(redPlayer,whitePlayer);
    System.out.println(game.play());
    System.out.println("Red required "+game.getAverageRedTime()+" seconds average");
    System.out.println("White required "+game.getAverageWhiteTime()+" seconds average");
	}

}
