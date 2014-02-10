package com.fisherevans.oop.deli;

import java.util.HashMap;

import com.fisherevans.oop.deli.exeps.EmptyOrderException;
import com.fisherevans.oop.deli.exeps.InvalidOrderExecption;
import com.fisherevans.oop.deli.exeps.InvalidOrderNumberExecption;

/**
 * @author Fisher
 *	Contains the system for using the deli (no GUI)
 */
public class DeliDriver
{
	private HashMap<Integer, DeliOrder> _ordersToMake; // Order queue to make, never null
	private HashMap<Integer, DeliOrder> _ordersToPickup; // Order queue to pickup, never null
	
	private int _nextOrderNumber; // never negative
	
	private int _totalMoneyMade = 0; // never negative
	
	/** Creates the deli driver system to be used in keeping track of orders and money made/
	 * 
	 */
	public DeliDriver()
	{
		_ordersToMake = new HashMap<Integer, DeliOrder>();
		_ordersToPickup = new HashMap<Integer, DeliOrder>();
		_nextOrderNumber = 1;
	}
	
	/** Adds an order to the "To Make" queue
	 * @param order The order to make
	 * @return the order number
	 * @throws EmptyOrderException if the orde ris empty or null
	 */
	public int addOrder(DeliOrder order) throws EmptyOrderException
	{
		if(order == null || order.isEmpty())
			throw new EmptyOrderException();

		_ordersToMake.put(_nextOrderNumber, order);
		order.setOrderNumber(_nextOrderNumber);
		System.out.println("Adding Order #" + order.getOrderNumber() + " -> " + order.toString());
		int orderNumber = _nextOrderNumber;
		_nextOrderNumber++;
		return orderNumber;
	}
	
	/** Moves an order from the to make queue to the to pickup queue
	 * @param order order to swap
	 * @throws InvalidOrderExecption if the order doesn't exist int he to make queue
	 */
	public void finishOrder(DeliOrder order) throws InvalidOrderExecption
	{
		if(!_ordersToMake.containsValue(order))
			throw new InvalidOrderExecption();
		
		_ordersToMake.remove(order.getOrderNumber());
		_ordersToPickup.put(order.getOrderNumber(), order);
		System.out.println("Finished Making Order #" + order.getOrderNumber() + " -> " + order.toString());
	}
	
	/** calls finishOrder with the order corresponding to the given order number
	 * @param orderNumber number to get orer from
	 * @throws InvalidOrderNumberExecption if the order number does not exist.
	 * @throws InvalidOrderExecption if the order does not exist in the to make queue
	 */
	public void finishOrder(int orderNumber) throws InvalidOrderNumberExecption, InvalidOrderExecption
	{
		if(!_ordersToMake.containsKey(orderNumber))
			throw new InvalidOrderNumberExecption();
		
		finishOrder((DeliOrder) _ordersToMake.get(orderNumber));
	}
	
	/** Removes orde from to pick up queue and adds the price of the order to the total money made.
	 * @param order order to pickup
	 * @throws InvalidOrderExecption if the order is not ready to be picked up
	 */
	public void pickupOrder(DeliOrder order) throws InvalidOrderExecption
	{
		if(!_ordersToPickup.containsValue(order))
			throw new InvalidOrderExecption();
		
		_totalMoneyMade += order.getTotalOrderPrice();
		_ordersToPickup.remove(order.getOrderNumber());
		System.out.println("Someone Picked Up Order #" + order.getOrderNumber() + " -> " + order.toString());
	}
	
	/** call pickupOrder with order corresponding to given order number
	 * @param orderNumber ordernumber to get order from
	 * @throws InvalidOrderNumberExecption if the order number doesnt match an order
	 * @throws InvalidOrderExecption if the order isn't ready to be picked up
	 */
	public void pickupOrder(int orderNumber) throws InvalidOrderNumberExecption, InvalidOrderExecption
	{
		if(!_ordersToPickup.containsKey(orderNumber))
			throw new InvalidOrderNumberExecption();
		
		pickupOrder((DeliOrder) _ordersToPickup.get(orderNumber));
	}
	
	/** Gets the total money made so far
	 * @return the total money made
	 */
	public int getTotalMoneyMade()
	{
		return _totalMoneyMade;
	}
}
