package com.fisherevans.oop.deli;

import java.util.HashSet;
import java.util.Set;

import com.fisherevans.oop.deli.ItemComponents.*;

/**
 * @author Fisher
 *
 */
public class DeliSandwhich extends DeliItem
{
	private Bread _breadType; // type of bread, never null
	private Set<Meat> _meats; // meats to used int he sandwhich, never null
	private Set<Cheese> _cheeses; // cheeses to be used in the sandwhich, never null
	private Set<FreeSandwhichTopping> _toppings; // toppings to added, never nul
	
	private final int BASE_PRICE = 350; // base price of sandwhich
	private final int FIRST_MEAT = 50; // price for first meat
	private final int ADD_MEAT = 100; // price for additional meats
	private final int FIRST_CHEESE = 50; // price for first cheese
	private final int ADD_CHEESE = 100; // price for aditional cheese
	
	/**
	 * Creates the sandwhich object and hashes
	 */
	public DeliSandwhich()
	{
		_meats = new HashSet<Meat>();
		_cheeses = new HashSet<Cheese>();
		_toppings = new HashSet<FreeSandwhichTopping>();
	}
	
	/** Sets the bread type
	 * @param breadType the new bread type
	 */
	public void setBread(Bread breadType)
	{
		_breadType = breadType;
	}
	
	/** adds a meat to the sandwhich
	 * @param meat the new meat
	 */
	public void addMeat(Meat meat)
	{
		_meats.add(meat);
	}
	
	/** adds a cheese to the sandwhich
	 * @param cheese the new cheese
	 */
	public void addCheese(Cheese cheese)
	{
		_cheeses.add(cheese);
	}
	
	/** adds a topping to the sandwhich
	 * @param topping the new topping
	 */
	public void addTopping(FreeSandwhichTopping topping)
	{
		_toppings.add(topping);
	}

	@Override
	public int getTotalPrice()
	{
		int price = BASE_PRICE;
		
		if(_meats.size() > 0)
			price += FIRST_MEAT;
		if(_meats.size() > 1)
			price += ADD_MEAT*(_meats.size()-1);
		
		if(_cheeses.size() > 0)
			price += FIRST_CHEESE;
		if(_cheeses.size() > 1)
			price += ADD_CHEESE*(_cheeses.size()-1);
		
		return price;
	}
}
