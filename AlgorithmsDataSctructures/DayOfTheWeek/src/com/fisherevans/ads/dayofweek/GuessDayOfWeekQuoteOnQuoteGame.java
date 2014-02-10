package com.fisherevans.ads.dayofweek;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher Evans
 * Date: 8/22/13
 * Time: 1:07 PM
 */
public class GuessDayOfWeekQuoteOnQuoteGame
{
    /** Flag for unit testing **/
    public static final boolean DEBUG = true;

    /** Default number of correct dates required **/
    private final static int DEFAULT_NEED_CORRECT = 3;

    /** Starts a simple Date Guessing game where the user has to correctly guess a specified number of week days based
     * on random dates between 1900/1/1 and 2099/12/31
     *
     * @param needCorrect The number of correct dates the user needs to enter
     */
    public GuessDayOfWeekQuoteOnQuoteGame(int needCorrect)
    {
        // Tell the user how to play.
        System.out.println("Hi, my name is Computer. I'm going to give you random dates and you have to tell me what "
                + "day of the week each date lies on.");
        System.out.println(needCorrect + " correct dates need to be entered.");
        System.out.println("Dates can be entered as an:\n"
                + "    Integer (0-6) where 0 is Saturday, 1 is Sunday, etc.;\n"
                + "    or as a String: 'Mon', 'th', 'Friday', etc.");

        Scanner input = null; // Capture the user input
        try
        {
            input = new Scanner(System.in);
        }
        catch(Exception e) // If the universe is broken and there is no user STDIN
        {
            System.err.println("Failed to capture user input. Quitting.");
            System.exit(1);
        }

        long startTime = System.currentTimeMillis(); // Start the duration count

        Date randomDate; // Variables used in the loop
        String inputLine;
        int guess = 0;
        int tries = 0;
        int numberCorrect = 0; // Game tally
        while(numberCorrect < needCorrect) // While the user still has to correctly answer dates
        {
            randomDate = Date.createRandomDate(); // generate a random date

            System.out.println("\nEnter the day of week this date lands on: " + randomDate);

            guess = -1; // generate an invalid guess for the input loop
            while(!Date.isValidDayOfWeek(guess)) // While the guess is invalid
            {
                System.out.print("> "); // prompt

                inputLine = input.nextLine(); // and ask for their answer
                if(inputLine.toLowerCase().startsWith("!q")) // In case the user gives up
                {
                    System.out.println("You decided to quit. Good Bye :)"); // Give em' a nice message and kick em out
                    System.exit(0);
                }

                guess = Date.parseDayOfWeek(inputLine); // Parse their input as a day of the week

                if(!Date.isValidDayOfWeek(guess)) // If the date passed was an invalid format, tell them they're bad.
                    System.out.println("I'm sorry, '" + inputLine + "' is not a valid input. Try again.");
            }

            if(guess == randomDate.getDayOfWeek()) // If the guess is correct
            {
                numberCorrect++; // Increment their correct number
                if(numberCorrect < needCorrect) // Check if they're not done. If they aren't, tell them their progress
                    System.out.println("Good job! " + numberCorrect + " down, "
                            + (needCorrect-numberCorrect) + " to go!");
            }
            else // If their guess was wrong, tell them that and then their progress
            {
                System.out.println("Wrong, the correct answer was "
                        + Date.getDayOfWeekName(randomDate.getDayOfWeek()) + " (" + randomDate.getDayOfWeek() + ")."
                        + " You need " + (needCorrect-numberCorrect) + " more correct answers.");
            }

            tries++; // Increase the dates they've answered
        }

        // Once they've completed the task tell them how wonderful they are and give them some nice statistics.
        System.out.println("Congratulations! You got " + needCorrect + " correct days in " + tries + " tries.");
        System.out.printf("It took %.1f seconds to complete this task.", (System.currentTimeMillis()-startTime)/1000.0);

        // Quit
    }

    public static void main(String[] args)
    {
        if(DEBUG) // Sloppy unit testing
        {
            System.out.println("Testing this program. To turn on the interactive mode, switch the DEBUG flag.");

            Date date;

            date = new Date(75, 12, 30);
            System.out.printf("%s - %s (Tuesday)\n", date.toString(), Date.getDayOfWeekName(date.getDayOfWeek()));

            date = new Date(93, 6,  3);
            System.out.printf("%s - %s (Thursday)\n", date.toString(), Date.getDayOfWeekName(date.getDayOfWeek()));

            date = new Date(113, 8,  26);
            System.out.printf("%s - %s (Monday)\n", date.toString(), Date.getDayOfWeekName(date.getDayOfWeek()));

            date = new Date(189, 10, 26);
            System.out.printf("%s - %s (Sunday)\n", date.toString(), Date.getDayOfWeekName(date.getDayOfWeek()));
        }
        else // If we're not unit testing
        {
            int needCorrect = DEFAULT_NEED_CORRECT; // Default the required correct

            if(args.length > 0) // if they passed an argument
            {
                try // try to parse it as a number
                {
                    needCorrect = Integer.parseInt(args[0]);
                }
                catch(Exception e) // if they fail at typing an integer, tell them and stick with the default
                {
                    System.out.println("Failed to parse the argument: '" + args[0] + "' as a valid number of correct "
                            + "dates to be entered. Defaulting to: " + needCorrect + ".");
                }
            }

            // Start the "game"...
            new GuessDayOfWeekQuoteOnQuoteGame(needCorrect);
        }
    }
}
