package com.fisherevans.oop.deli;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** An order holding one or more deli items
 * @author Fisher
 *
 */
public class DeliOrder
{
	private Set<DeliItem> _orderItems; // Items int he order, never null
	private int _orderNumber = -1; // order number, never < 0
	
	/** Initiates the variables needed for the order
	 * 
	 */
	public DeliOrder()
	{
		_orderItems = new HashSet<DeliItem>();
	}
	
	/** Add an item to the order
	 * @param item the item to add
	 */
	public void addDeliItem(DeliItem item)
	{
		_orderItems.add(item);
	}
	
	/** Sums up the cost of all deli items
	 * @return the sum of all item costs
	 */
	public int getTotalOrderPrice()
	{
		Iterator<DeliItem> itemIter = _orderItems.iterator();
		int totalPrice = 0;
		while(itemIter.hasNext())
		{
			totalPrice += itemIter.next().getTotalPrice();
		}
		return totalPrice;
	}
	
	/**
	 * @return if the order contains no items
	 */
	public boolean isEmpty()
	{
		return _orderItems.isEmpty();
	}

	/**
	 * @return the order number
	 */
	public int getOrderNumber()
	{
		return _orderNumber;
	}

	/** sets the order number for this order
	 * @param orderNumber the new order numebr
	 */
	public void setOrderNumber(int orderNumber)
	{
		_orderNumber = orderNumber;
	}
}
