import java.util.Scanner;

/**
 * This class contains the problems from lab 3.
 * name : Fisher Evans
 */

public class main
{
    public static void Num1()
    {
            // ASSIGN VARIABLES
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter an integer: ");
        double input1 = in.nextDouble();
        
            // PRINT SEPERATOR
        System.out.println("========================");
        
            // PRINT POWERS
        System.out.println(" - The Square of the two integers is: " + (input1*input1));
        System.out.println(" - The Cube of the two integers is: " + (input1*input1*input1));
        System.out.println(" - The Fourth of the two integers is: " + Math.pow(input1,4));
        
            // PRINT SEPERATOR
        System.out.println("========================\n");
        
    }
    
    public static void Num2()
    {
            // ASSIGN VARIABLES
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter side 1 (one) of the rectangle: ");
        double sideOne = in.nextDouble();
        System.out.print("Please enter side 2 (two) of the rectangle: ");
        double sideTwo = in.nextDouble();
        
            // PRINT OUT THE RESULTS
        System.out.println("------------------");
        System.out.println("     Area: " + (sideOne * sideTwo));
        System.out.println(" Diagonal: " + Math.pow( (Math.pow(sideOne,2) + Math.pow(sideTwo,2)), 0.5));
        System.out.println("------------------\n");
        
        
        
    }
    
    public static void Num3()
    {
            // ASSIGN VARIABLES
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the first number: ");
        double varOne = in.nextDouble();
        System.out.print("Please enter the second number: ");
        double varTwo = in.nextDouble();
        System.out.print("Please enter the third number: ");
        double varThree = in.nextDouble();
        
            // Highest 1 and 2
        double varHigh12 = Math.max(varOne,varTwo);
        
            // PRINT Highest Entered Number
        System.out.println("The highest number entered is:");
        System.out.println(Math.max(varHigh12,varThree));
    }
}