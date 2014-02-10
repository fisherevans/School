import java.util.Scanner;

public class Num1
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter a starting number: ");
        int start = in.nextInt();
        
        int tracker = 0;
        int sum = 0;
        while(tracker < start)
        {
            tracker++;
            sum = sum + tracker;
            System.out.print(tracker);
            if(tracker < start) { System.out.print("+"); }
            else { System.out.print(" = "); }
        }
        System.out.println(sum);
        
        tracker = 0;
        int fact = 1;
        while(tracker < start)
        {
            tracker++;
            fact = fact * tracker;
            System.out.print(tracker);
            if(tracker < start) { System.out.print("x"); }
            else { System.out.print(" = "); }
        }
        System.out.println(fact);
        
        
    }
}