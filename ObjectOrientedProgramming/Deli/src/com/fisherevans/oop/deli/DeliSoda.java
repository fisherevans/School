package com.fisherevans.oop.deli;

import com.fisherevans.oop.deli.ItemComponents.Soda;

/** a soda you can buy
 * @author Fisher
 *
 */
public class DeliSoda extends DeliItem
{
	private Soda _type; // type of soda, never null
	private final int BASE_PRICE = 150; // price of soda
	
	/** sets the type of soda
	 * @param type the new type
	 */
	public void setType(Soda type)
	{
		_type = type;
	}
	
	@Override
	public int getTotalPrice()
	{
		return BASE_PRICE;
	}
}
