package com.fisherevans.ai.checkers;

import edu.vtc.cis3310.checkers.*;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 2/13/13
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FisherPlayer implements CheckersPlayer
{
    private static Color _color;

    public static final int MAX_DEPTH = 12;
    private int _turn = 0;

    public FisherPlayer(Color color)
    {
        _color = color;
    }

    @Override
    public Color getColor()
    {
        return _color;
    }

    @Override
    public Move chooseMove(Collection<Move> legalMoves, CheckersGame game)
    {
        Move bestMove = null;
        float bestH = -1f*Float.MAX_VALUE;


        for(Move move:legalMoves)
        {
            float thisH = calcAlphaBeta(new FisherNode(game.getCurrentBoard(), move), _color.opposite(), 1, bestH, Float.MAX_VALUE, 0);
            if(thisH > bestH)
            {
                bestH = thisH;
                bestMove = move;
            }
        }

        if (bestMove == null)
        {
            for(Move move:legalMoves)
                return move;
        }

        //System.out.println("H: " + bestH);

        return bestMove;
    }

    private float calcAlphaBeta(FisherNode node, Color color, int depth, float alpha, float beta, float lastH)
    {
        float thisH = getH(node, color.opposite()) + lastH;
        if(depth >= MAX_DEPTH)
            return thisH;

        CheckerBoard board = node.getNextBoard();
        Collection<Move> moves = board.legalMoves(color);

        for(Move move:moves)
        {
            float h = calcAlphaBeta(new FisherNode(board, move), color.opposite(), depth+1, alpha, beta, thisH);
            if(color == _color)
                alpha = Math.max(alpha, h);
            else
                beta = Math.min(beta, h);

            if(beta <= alpha)
                break;
        }

        if(color == _color)
            return alpha;
        else
            return beta;
    }

    public float getH(FisherNode node, Color color)
    {
        CheckerBoard board = node.getBoard();
        Move move = node.getMove();

        int them = 0, me = 0;
        int theirLast = -1;
        for(int x = 1;x <= 32;x++)
        {
            Piece p = board.getPiece(x);
            if(p != null)
            {
                if(p.getColor() == _color)
                    me++;
                else
                {
                    them++;
                    theirLast = x;
                }
            }
        }

        if(color == _color)
        {
            float h = 0;

            Collection<Piece> captured = move.captures(board);
            if(captured != null)
            {
                for(Piece p:captured)
                    h += p.isKing() ? 2f : 1.5f;
            }

            if(color == Color.White && move.startPos() >= 28)
                h -= 0.5f;
            else if(move.startPos() <= 4)
                h -= 0.5f;

            if((move.endPos() + 3) % 8 == 0 || (move.endPos() + 4) % 8 == 0)
                h += 0.25f;

            if(move.kingsPiece()) h += 1.25f;

            if(move.getPiece().isKing())
            {
                if(move.isProductive())
                    h -= 0.5f;
                else
                    h += 0.5f;
            }
            else
            {
                if(move.isProductive())
                    h += 0.25f;
            }

            if(them == 1)
            {
                theirLast *= 2;
                theirLast--;
                int tx = theirLast/8;
                int ty = theirLast%8;
                int bx = ((move.startPos()*2)-1)/8;
                int by = ((move.startPos()*2)-1)%8;
                int ex = ((move.endPos()*2)-1)/8;
                int ey = ((move.endPos()*2)-1)%8;

                double distanceDelta =
                        Math.sqrt(Math.pow(tx-bx, 2) + Math.pow(ty-by, 2)) -
                        Math.sqrt(Math.pow(tx-ex, 2) + Math.pow(ty-ey, 2));

                if(distanceDelta > 0)
                    h += 1f;
                else
                    h -= 1f;
            }

            if(them == 0)
                h += 1000;

            return h;
        }
        else
        {
            float h = 0;

            if(move.kingsPiece()) h += 1.5f;
            if(move.isProductive()) h+= 0.25f;
            Collection<Piece> captured = move.captures(board);
            if(captured != null)
            {
                for(Piece p:captured)
                    h += p.isKing() ? 1.25f : 1f;
            }

            if(me == 0)
                h += 1000;

            return -2f*h;
        }
    }
}
