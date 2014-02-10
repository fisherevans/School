import java.util.Scanner;

public class Num3
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter a temperature in Farenheit: ");
        int tempF = in.nextInt();
        
        int tempT = (tempF - 32) * 5;
        
        double tempC = tempT/9;
        
        System.out.println(tempF + " Farenheit is equal to " + tempC + " Celcius.");
    }
}