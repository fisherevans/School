package com.fisherevans.oop.deli;

/** Holds multiple options for all the different deli items
 * @author Fisher
 *
 */
public class ItemComponents
{
	/** The available breads for sandwhiches
	 * @author Fisher
	 *
	 */
	public enum Bread { White, Wheat, Rye };
	
	/** The availabele meets for salads, and sandwhichs
	 * @author Fisher
	 *
	 */
	public enum Meat { Ham, Turkey, Tuna, Bacon };
	
	/** The available cheeses for sandwhiches and salads
	 * @author Fisher
	 *
	 */
	public enum Cheese { Swiss, Cheddar, Provolone };
	
	/** The available free toppings for sandwhiches
	 * @author Fisher
	 *
	 */
	public enum FreeSandwhichTopping { Lettuce, Tomato, Cucumber, Pepper, Pickles, Mayo, Mustard, Pickle };
	
	/** the available free toppings for salds
	 * @author Fisher
	 *
	 */
	public enum FreeSaladTopping { Tomato, Cucumber, Pickle, Pepper };
	
	/** The avail soda optins
	 * @author Fisher
	 *
	 */
	public enum Soda { Coke, Sprite, OrangeSoda, RootBeer };
}
