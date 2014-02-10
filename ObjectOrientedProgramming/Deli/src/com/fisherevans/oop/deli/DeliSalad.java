package com.fisherevans.oop.deli;

import java.util.HashSet;
import java.util.Set;

import com.fisherevans.oop.deli.ItemComponents.*;

/** A deli sald you can buy
 * @author Fisher
 *
 */
public class DeliSalad extends DeliItem
{
	private Set<Meat> _meats; // meats to be on the salad, never null
	private Set<Cheese> _cheeses; // // cheeses to be on the salad
	private Set<FreeSaladTopping> _toppings; // toppings to be on the salad, never null
	
	private final int BASE_PRICE = 300; // base price of sald
	private final int MEAT_PRICE = 100; // price for each meat
	private final int CHEESE_PRICE = 50; // price for each sheese
	
	/**
	 * Creates the deli salad
	 */
	public DeliSalad()
	{
		_meats = new HashSet<Meat>();
		_cheeses = new HashSet<Cheese>();
		_toppings = new HashSet<FreeSaladTopping>();
	}
	
	/** adds a meat tot he salad
	 * @param meat the new meat
	 */
	public void addMeat(Meat meat)
	{
		_meats.add(meat);
	}
	
	/** adds a cheese to the sald
	 * @param cheese the new cheese
	 */
	public void addCheese(Cheese cheese)
	{
		_cheeses.add(cheese);
	}
	
	/** adds a topping to the salad
	 * @param topping the new topping
	 */
	public void addTopping(FreeSaladTopping topping)
	{
		_toppings.add(topping);
	}

	@Override
	public int getTotalPrice()
	{
		int price = BASE_PRICE;
		
		if(_meats.size() > 0)
			price += MEAT_PRICE*_meats.size();
		
		if(_cheeses.size() > 0)
			price += CHEESE_PRICE*_cheeses.size();
		
		return price;
	}
}
