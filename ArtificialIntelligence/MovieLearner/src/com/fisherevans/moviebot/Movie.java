package com.fisherevans.moviebot;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/30/13
 * Time: 8:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Movie
{
    private String _title, _director, _writer;
    private ArrayList<String> _genres, _actors, _nominations;
    private float _userRating, _imdbRating;
    private int _year;

    public Movie(String input)
    {
        String[] col = input.split(",");
        for(int i = 0;i < col.length;i++)
            col[i] = col[i].replaceAll("\"", "");

        if(col.length != 11)
        {
            System.out.println("Invalid Movie Definition: " + input);
            System.exit(1);
        }

        _title = col[0];

        try { _year = Integer.parseInt(col[1]); }
        catch(Exception e) { _year = 0; }

        _genres = new ArrayList<String>();
        _genres.addAll(Arrays.asList(col[2].split(";")));

        try
        {
            _userRating = Float.parseFloat(col[3]);
        }
        catch(Exception e)
        {
            _userRating = -1f;
        }


        try { _imdbRating = Float.parseFloat(col[4]); }
        catch(Exception e) { _imdbRating = 0; }

        _actors = new ArrayList<String>();
        String actor;
        for(int colId = 5;colId <= 7;colId++)
        {
            if(!(actor = col[colId]).equals(""))
                _actors.add(actor);
        }

        _director = col[8];
        _writer = col[9];

        _nominations = new ArrayList<String>();
        _nominations.addAll(Arrays.asList(col[10].split(";")));
    }

    public float getDistance(Movie other)
    {
        float distance =  (float)Math.sqrt(
                Math.pow(MovieMath.getYearScore(this, other), 2) +
                Math.pow(MovieMath.getStingListCompareScore(this.getGenres(), other.getGenres()), 2) +
                Math.pow(MovieMath.getStingListCompareScore(this.getActors(), other.getActors()), 2) +
                Math.pow(MovieMath.getStringCompareScore(this.getDirector(), other.getDirector()), 2) +
                Math.pow(MovieMath.getStringCompareScore(this.getWriter(), other.getWriter()), 2) +
                Math.pow(MovieMath.getStingListCompareScore(this.getNominations(), other.getNominations()), 2) +
                Math.pow(MovieMath.getRatingComparison(_imdbRating, other.getUserRating()), 2)
        );
        return distance;
    }

    public void setUserRating(float rating)
    {
        _userRating = rating;
    }

    public String getTitle()
    {
        return _title;
    }

    public String getDirector()
    {
        return _director;
    }

    public String getWriter()
    {
        return _writer;
    }

    public ArrayList<String> getGenres()
    {
        return (ArrayList<String>)_genres.clone();
    }

    public ArrayList<String> getActors()
    {
        return (ArrayList<String>)_actors.clone();
    }

    public ArrayList<String> getNominations()
    {
        return (ArrayList<String>)_nominations.clone();
    }

    public float getUserRating()
    {
        return _userRating;
    }

    public float getImdbRating()
    {
        return _imdbRating;
    }

    public int getYear()
    {
        return _year;
    }

    public String toString()
    {
        return _title + " - Your Rating " + String.format("%.2f",_userRating) + "/10 - Year: " + _year + " - Genres: " + _genres + " - Actors: " + _actors + " - Director: " + _director + " - Writer: " + _writer + " - Nominations: " + _nominations;
    }
}
