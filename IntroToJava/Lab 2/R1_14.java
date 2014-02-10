import java.util.Scanner;

public class R1_14
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Distance (In miles): ");
        double distance = in.nextDouble();
        System.out.print("Fuel Efficiancy (Mpg): ");
        double fuelEff = in.nextDouble();
        System.out.print("Train Ticket (In dollars): ");
        double ticket = in.nextDouble();
        
        double carMait = 0.05*distance;
        double carMPG = (distance*4/fuelEff);
        double carP = carMait + carMPG;
        
        System.out.println("It costs $" + carP + " if you use your car.");
        System.out.println("It costs $" + ticket + " if you use the train.");
        if(carP <= ticket)
        {
            System.out.println("The car is a better option.");
        }
        else
        {
            System.out.println("The train is a better option."); 
        }
    }
}