import java.util.Scanner;

public class Num2
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter a starting balance: ");
        double start = in.nextDouble();
        System.out.print("Please enter an interest (in percentage): ");
        double interest = in.nextDouble();
        System.out.print("Please enter the number of years: ");
        double years = in.nextDouble();
        
        System.out.println("Your balance after...");
        double curYear = 1;
        double temp = 0;
        double curBalance = 0;
        
        while(curYear <= years)
        {
            temp = java.lang.Math.pow((1+(interest/100)),curYear);
            curBalance = start*temp;
            System.out.println("  " + curYear + " year(s): $" + curBalance);
            curYear++;
        }
        
        
    }
}