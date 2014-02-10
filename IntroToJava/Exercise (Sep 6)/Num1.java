import java.util.Scanner;

public class Num1
{
    public static void main(String[] args)
    {
        String firstName;
        String lastName;
        String fullName;
        
        Scanner in = new Scanner(System.in);
        
        System.out.print("Please enter your first name: ");
        firstName = in.next();
        System.out.print("Please enter your last name: ");
        lastName = in.next();
        
        fullName = firstName + " " + lastName;
        
        System.out.println("Your full name is : " + fullName);
    }
}