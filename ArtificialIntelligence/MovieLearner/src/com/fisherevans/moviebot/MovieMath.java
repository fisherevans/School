package com.fisherevans.moviebot;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/30/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovieMath
{
    public static float getYearScore(Movie m1, Movie m2)
    {
        float range = 2013-1930;
        float score = (range-Math.abs(m1.getYear() - m2.getYear()))/range;
        return score;
    }

    public static float getStingListCompareScore(ArrayList<String> l1, ArrayList<String> l2)
    {
        l1.retainAll(l2);
        float score = l1.size()*3.5f;
        score = clamp(0, score, 10);
        return score;
    }

    public static float getStringCompareScore(String s1, String s2)
    {
       float score = s1.equals(s2) ? 10 : 0;
       return score;
    }

    public static float getRatingComparison(float imdb, float user)
    {
        float score = (user/10f)*imdb;
        //System.out.println(score);
        return score;
    }

    private static float clamp(float min, float x, float max)
    {
        if(x < min) x = min;
        else if(x > max) x = max;
        return x;
    }
}
