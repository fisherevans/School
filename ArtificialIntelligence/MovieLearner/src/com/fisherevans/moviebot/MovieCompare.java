package com.fisherevans.moviebot;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 5/1/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class MovieCompare implements Comparator<Movie>
{
    @Override
    public int compare(Movie o1, Movie o2) {
        return (int)Math.signum(o2.getUserRating()-o1.getUserRating());
    }
}
