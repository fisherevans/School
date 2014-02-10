import java.util.Scanner;

/**
 * This class contains the problems from lab 3.
 * name : Fisher Evans
 */

public class Grades
{
    public static void Ex1()
    {
            // ASSIGN VARIABLES
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a grade: (Integer)");
        int grade = in.nextInt();
        
        if(grade <= 100)
        {
            System.out.println(grade + " gives you a: A");
        }
        if(grade >= 90 && grade <= 100)
        {
            System.out.println(grade + " gives you a: A");
        } 
        else if(grade >= 80 && grade <= 100)
        {
            System.out.println(grade + " gives you a: B");
        }
        else if(grade >= 70 && grade <= 100)
        {
            System.out.println(grade + " gives you a: C");
        }
        else if(grade >= 60 && grade <= 100)
        {
            System.out.println(grade + " gives you a: D");
        }
        else if(grade > 0 && grade <= 100)
        {
            System.out.println(grade + " gives you a: F");
        }
        else
        {
            System.out.println(grade + " is not a valid grade. Try again...");
            Ex1();
        }
        
    }
}