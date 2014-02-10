package com.fisherevans.ai.checkers;

import edu.vtc.cis3310.checkers.CheckersGame;
import edu.vtc.cis3310.checkers.CheckersPlayer;
import edu.vtc.cis3310.checkers.Color;
import edu.vtc.cis3310.checkers.StupidPlayer;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 2/13/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameProctor
{
    public GameProctor()
    {
        boolean first = Math.random() > 0.5;
        String whos = "";

        //first = false;

        CheckersPlayer red, white;
        if(first)
        {
            red = new StupidPlayer(Color.Red);
            white = new FisherPlayer(Color.White);
        }
        else
        {
            red = new FisherPlayer(Color.Red);
            white = new StupidPlayer(Color.White);
        }

        CheckersGame game = new CheckersGame(red, white);
        Color winner = game.play();

        String player = "";
        if(first && winner == Color.Red) player = "Stupid";
        else if(first && winner == Color.White) player = "Fisher";
        else if(!first && winner == Color.Red) player = "Fisher";
        else player = "Stupid";

        System.out.println("The winner is: " + winner + " (" + player + ")");
        System.out.println("Red's Average Time: " + game.getAverageRedTime());
        System.out.println("White's Average Time: " + game.getAverageWhiteTime());
    }

    public static void main(String[] args)
    {
        new GameProctor();
    }
}
