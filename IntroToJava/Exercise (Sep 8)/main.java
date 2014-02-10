import java.util.Scanner;

/**
 * This class contains the problems from Exercise 9-8-11.
 * name : Fisher Evans
 */

public class main
{
    public static void Num1()
    {
            // ASSIGN VARIABLES
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter an integer between 0 and 100: ");
        int input = in.nextInt();
        
        if(input < 0 || input > 100) 
        {
            System.out.println(input + " is less 0, or greater than 100!\n Try again...");
            Num1();
        }
        else if(input >= 60)
        {
            System.out.println("You pass!");
        }
        else
        {
            System.out.println("You fail.");
        }
    }
}
