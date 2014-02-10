package com.fisherevans.ai.checkers;

import edu.vtc.cis3310.checkers.CheckerBoard;
import edu.vtc.cis3310.checkers.Move;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 3/1/13
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class FisherNode
{
    private Move _move;
    private CheckerBoard _board;

    public FisherNode(CheckerBoard board, Move move)
    {
        _move = move;
        _board = board;
        if(move == null) System.out.println("MOVE NULL");
        if(board == null) System.out.println("BOARD NULL");
    }

    public CheckerBoard getNextBoard()
    {
        return _board.makeMove(_move);
    }

    public Move getMove()
    {
        return _move;
    }

    public CheckerBoard getBoard()
    {
        return _board;
    }
}
