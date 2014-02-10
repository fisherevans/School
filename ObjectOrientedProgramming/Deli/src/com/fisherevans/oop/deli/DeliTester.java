package com.fisherevans.oop.deli;

import com.fisherevans.oop.deli.ItemComponents.*;
import com.fisherevans.oop.deli.exeps.EmptyOrderException;
import com.fisherevans.oop.deli.exeps.InvalidOrderExecption;

public class DeliTester
{
	public static void main(String[] args)
	{
		DeliDriver deli = new DeliDriver();
		
		DeliSalad salad1 = new DeliSalad(); // 300
			salad1.addCheese(Cheese.Cheddar); // 50
			salad1.addMeat(Meat.Turkey); // 100
			salad1.addMeat(Meat.Bacon); // 100
			salad1.addTopping(FreeSaladTopping.Pepper);
			salad1.addTopping(FreeSaladTopping.Tomato);
		DeliSoda soda1 = new DeliSoda();
			soda1.setType(Soda.Sprite);
		DeliOrder order1 = new DeliOrder();
			order1.addDeliItem(salad1); // 300 + 50 + 100 + 100 = 700
			order1.addDeliItem(soda1); // 150
		
		DeliSandwhich sandwhich1 = new DeliSandwhich(); // 350
			sandwhich1.addCheese(Cheese.Cheddar); // 50
			sandwhich1.addMeat(Meat.Ham); // 50
			sandwhich1.addMeat(Meat.Turkey); // 100
			sandwhich1.addTopping(FreeSandwhichTopping.Lettuce);
			sandwhich1.addTopping(FreeSandwhichTopping.Pickle);
			sandwhich1.addTopping(FreeSandwhichTopping.Mayo);
		DeliOrder order2 = new DeliOrder();
			order2.addDeliItem(sandwhich1); // 350 + 50 + 50 + 100 = 550
			
		try { deli.addOrder(order1); } // 700
		catch (EmptyOrderException e) { System.out.println("The order was empty!"); } 
		
		try { deli.addOrder(order2); } // 550
		catch (EmptyOrderException e) { System.out.println("The order was empty!"); } 
									   // TOTAL 1250

		try { deli.finishOrder(order1); }
		catch (InvalidOrderExecption e) { System.out.println("The order doesn't exist"); }
		try { deli.finishOrder(order2); }
		catch (InvalidOrderExecption e) { System.out.println("The order doesn't exist"); }

		System.out.println("Total Money Made Thus Far (in cents): " + deli.getTotalMoneyMade() + " - Expected 0");
		
		try { deli.pickupOrder(order1); }
		catch (InvalidOrderExecption e) { System.out.println("The order isn't ready to be picked up!"); }
		
		System.out.println("Total Money Made Thus Far (in cents): " + deli.getTotalMoneyMade() + " - Expected 700");
		
		try { deli.pickupOrder(order2); }
		catch (InvalidOrderExecption e) { System.out.println("The order isn't ready to be picked up!"); }
		
		System.out.println("Total Money Made Thus Far (in cents): " + deli.getTotalMoneyMade() + " - Expected 700 + 550 = 1250");
		
	}
}
