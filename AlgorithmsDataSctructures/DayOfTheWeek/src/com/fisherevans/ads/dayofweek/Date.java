package com.fisherevans.ads.dayofweek;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher Evans
 * Date: 8/22/13
 * Time: 1:07 PM
 */
public class Date
{
    /** Monthly adjustments of of the month value - 1 **/
    private static final int[] MONTHLY_ADJUSTMENTS =
            { 1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6 };

    /** String value of the day of week - 1 **/
    private static final String[] DAY_OF_WEEK_NAMES =
            { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };

    /** The number of days in each month on a non leap year **/
    private static final int[] DAYS_IN_MONTHS =
            { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /** The number of days in each month on a leap year **/
    private static final int[] DAYS_IN_MONTHS_IN_LEAP_YEAR =
            { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /** Between 0 and 199 **/
    private int _year;

    /** Between 1 and 12 **/
    private int _month;

    /** Between 1 and 31 **/
    private int _day;

    /**
     * Create the date object
     * @param day The day of the month. (1-31)
     * @param month The Month of the year. (1-12)
     * @param year Number of years after 1900, must be before 2100. (0-199)
     */
    public Date(int year, int month, int day)
    {
        _year = year;
        _month = month;
        _day = day;
    }

    public int getYear()
    {
        return _year;
    }

    public int getMonth()
    {
        return _month;
    }

    public int getDay()
    {
        return _day;
    }

    /** Calculate the day of week from 0-6 where 0 is Sat and 6 is Fri using Professor John Conway's algorithm
     *
     * @return The int day of week
     */
    public int getDayOfWeek()
    {
        int monthAdjustment = MONTHLY_ADJUSTMENTS[getMonth()-1];
        if(getYear() % 4 == 0 && (getMonth() == 1 || getMonth() == 2))
            monthAdjustment -= 1;

        int yearDivided = getYear()/4;
        int dayOfWeek = (getDay() + monthAdjustment + getYear() + yearDivided) % 7;

        return dayOfWeek;
    }

    /** Get the String value of the int value of the day of the week
     *
     * @param dayOfWeek The day of week to translate
     * @return The string value - "Undefined" if an invalid day
     */
    public static String getDayOfWeekName(int dayOfWeek)
    {
        if(isValidDayOfWeek(dayOfWeek))
            return DAY_OF_WEEK_NAMES[dayOfWeek];
        else
            return "Undefined";
    }

    /** get the day of the week based on user input
     *
     * @param input the user's input
     * @return the day of the week in int format (0-6) - returns -1 if there is an invalid String input
     */
    public static int parseDayOfWeek(String input)
    {
        try // try to parse it as an integer
        {
            return new Integer(input);
        }
        catch(Exception e) // If not, try to read it as a string (day)
        {
            String lowercaseInput = input.toLowerCase();
            for(int id = 0;id < DAY_OF_WEEK_NAMES.length;id++)
            {
                if(DAY_OF_WEEK_NAMES[id].toLowerCase().startsWith(lowercaseInput))
                    return id;
            }
            return -1; // if all else fails, return an invalid day
        }
    }

    /** checks input for a valid day of week (in range)
     *
     * @param dayOfWeek day of week to check
     * @return true if it's a valid day, else false
     */
    public static boolean isValidDayOfWeek(int dayOfWeek)
    {
        return dayOfWeek >= 0 && dayOfWeek < DAY_OF_WEEK_NAMES.length;
    }

    /** gets a random date between 1900/1/1 and 2099/12/31
     *
     * @return new Random date object
     */
    public static Date createRandomDate()
    {
        int year = (int) (Math.random()*200);
        int month = (int) ((Math.random()*12) + 1);

        int day;
        if(year % 4 == 0) // if it's a leap year
            day = DAYS_IN_MONTHS_IN_LEAP_YEAR[month-1];
        else
            day = DAYS_IN_MONTHS[month-1];
        day = (int) (Math.random()*day) + 1;

        return new Date(year, month, day);
    }

    @Override
    public String toString()
    {
        return String.format("%04d/%02d/%02d", getYear()+1900, getMonth(), getDay());
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Date) // Only matches if it's a Date
        {
            Date objDate = (Date) obj;
            // Return true if the day, month and year match
            return objDate.getDay() == getDay()
                    && objDate.getMonth() == getMonth()
                    && objDate.getYear() == getYear();
        }
        return false;
    }
}
