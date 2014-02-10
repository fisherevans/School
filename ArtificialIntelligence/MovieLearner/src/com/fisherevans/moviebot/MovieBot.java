package com.fisherevans.moviebot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 4/24/13
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovieBot
{
    private ArrayList<Movie> _movies;
    private ArrayList<Movie> _training;

    public MovieBot(String inputFile)
    {
        _movies = new ArrayList<Movie>();
        _training = new ArrayList<Movie>();

        System.out.println("\n\n>>> READING FILE: " + inputFile);
        Scanner reader;
        try
        {
            File file = new File(inputFile);
            reader = new Scanner(file);
            reader.nextLine();

            String line;
            while(reader.hasNextLine())
            {
                line = reader.nextLine();
                System.out.println(line);
                Movie movie = new Movie(line);
                if(movie.getUserRating() >= 0)
                    _training.add(movie);
                else
                    _movies.add(movie);
            }

            System.out.println("\n\n>>> MOVIES:");
            for(Movie movie:_movies)
                System.out.println(movie);

            System.out.println("\n\n>>> TRAINING SET:");
            for(Movie movie:_training)
                System.out.println(movie);
        }
        catch(Exception e)
        {
            System.out.println("There was an error reading the file: " + inputFile);
        }

        for(Movie prospect:_movies)
        {
            float totalScore = 0;
            for(Movie trained:_training)
            {
                float compareScore = prospect.getDistance(trained)*(trained.getUserRating()/10f);
                totalScore += compareScore;
            }
            prospect.setUserRating(totalScore/((float)_training.size()));
        }

        Collections.sort(_movies, new MovieCompare());

        System.out.println("\n\n>>> Movies You May Like:");
        for(int movieId = 0;movieId < Math.min(_movies.size(), 5);movieId++)
            System.out.println(_movies.get(movieId));
    }

    public static void main(String[] args)
    {
        new MovieBot("bin/movies.csv");
    }
}
