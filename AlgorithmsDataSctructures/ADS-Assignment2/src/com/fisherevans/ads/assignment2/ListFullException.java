package com.fisherevans.ads.assignment2;

/**
 * Exception thrown when a list is at its capacity
 * @author Fisher Evans
 *
 */
public class ListFullException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2247861726773273845L;
	
	/**
	 * constructor for ListFullException class
	 */
	public ListFullException()
	{
		super("List is full");
	}

}
