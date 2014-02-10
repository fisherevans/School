
package com.fisherevans.ads.assignment2;

/**
 * Interface for an unordered list containing int values.
 * @author Fisher Evans
 * @version 29 Aug 2013
 *
 */
public interface UnorderedList<T> {

	/**
	 * InsertEnd adds the value at the end of the list. 
	 * @param value the value to insert
	 * @throws ListFullException if the list is full
	 */
	public void insertEnd(T value) throws ListFullException;
	
	/**
	 * InsertEnd adds the value at the end of the list.
	 * @param location the place to insert the element.  Legal locations range from 0 to size-1
	 * @param value the value to insert
	 * @throws IndexOutOfBoundsException if the location is not legal
	 * @throws ListFullException if the list is full
	 */
	public void insertAt(int location, T value) throws IndexOutOfBoundsException, ListFullException;
	
	/**
	 * get the value at the specified location
	 * @param location
	 * @return the value
	 * @throws IndexOutOfBoundsException if the location is not a legal location
	 */
	public T get(int location) throws IndexOutOfBoundsException;


	/** 
	 * Find returns the index of the value matching the given value
	 * @param item the item to locate
	 * @return the index of the item or -1 if it doesn't exist
	 */
	public int indexOf(T item);

	
	/**
	 * Remove the specified item.
	 * @param item to remove
	 * @return true if the item is found and removed, false if the item isn't found
	 */
	public boolean remove(T item);


	/**
	 * The size method returns the number of elements found in the list.
	 * @return the size of the list at this time
	 */
	public int size();
	
	/**
	 * The isEmpty method checks if the list is empty. 
	 * @return true if the list is empty (has no elements), false otherwise
	 */
	public boolean isEmpty();
	

	/**
	 * The isFull method checks the list is full.
	 * @return true if the list has no room left, false if more elements can be inserted
	 */
	public boolean isFull();
	
	/**
	 * Empty the list.
	 */
	public void clear();
}
