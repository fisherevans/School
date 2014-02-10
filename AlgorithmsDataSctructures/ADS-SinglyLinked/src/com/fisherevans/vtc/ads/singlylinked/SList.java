package com.fisherevans.vtc.ads.singlylinked;

/*
    Author: Fisher Evans
    Date: 09/24/13
 */

/**
 * A singly linked list
 * @param <T> The type of element this list will hold
 */
public class SList<T>
{
    /** The first element of the list **/
    private Node<T> _head;
    private int _size;

    /** creates an empty singly linked list **/
    public SList()
    {
        _head = null;
        _size = 0;
    }

    /**
     * Big-Oh (1)
     * @return the number of elements within the array
     */
    public int size()
    {
        return _size;
    }

    /**
     * Calls "insertNth(0, info)"
     * DIFFERENT BIG-OH(1)
     * @param info The element to add to the array
     */
    public void insertFirst(T info)
    {
        try
        {
            insertNth(0, info);
        }
        catch (Exception e)
        {
            // Should never happen as 0 should always be valid
        }
    }

    /**
     * Calls "insertNth(_size, info)"
     * @param info The element to add to the array
     */
    public void insertLast(T info)
    {
        try
        {
            insertNth(_size, info);
        }
        catch (Exception e)
        {
            // should never be thrown as size should always be a valid insert index
        }
    }

    /**
     * Big-Oh(n)
     * Adds a given element to a specified index
     * @param n the index to insert the element at
     * @param info the new element
     * @throws IndexOutOfBoundsException if there given index is out of range
     * @throws NullPointerException if there is an internal error, should not be thrown
     */
    public void insertNth(int n, T info) throws IndexOutOfBoundsException, NullPointerException
    {
        if(n > _size || n < 0)
            throw new IndexOutOfBoundsException();

        // loop through the array until you reach the desired index. Keep track of both pieces of bread
        Node before = null, current = _head;
        for(int index = 0;index < n;index++)
        {
            if(current == null)
                throw new NullPointerException();

            before = current;
            current = current.getLink();
        }

        // once you have the two correct pieces, insert the meat
        Node newNode = new Node<T>(info);
        if(before != null)
            before.setLink(newNode);
        newNode.setLink(current);

        // special case where the head should change
        if(n == 0)
            _head = newNode;

        _size++;
    }

    /**
     * calls "getNth(0)"
     * DIFFERENT BIG-OH(1)
     * @return the first element of the array
     */
    public T getFirst()
    {
        return getNth(0);
    }

    /**
     * calls "getNth(_size-1)"
     * @return the last element of the array
     */
    public T getLast()
    {
        return getNth(_size-1);
    }

    /**
     * Big-Oh(n)
     * returns the element in the given index of this array
     * @param n the index of the element you're looking for
     * @return the element
     * @throws IndexOutOfBoundsException if the given index is out of range
     * @throws NullPointerException if there is an internal error, shouldn't be thrown
     */
    public T getNth(int n) throws IndexOutOfBoundsException, NullPointerException
    {
        if(n > _size || n < 0)
            throw new IndexOutOfBoundsException();

        // loop through the list until the desired index is reached
        Node current = _head;
        for(int index = 0;index < n;index++)
        {
            if(current == null)
                throw new NullPointerException();
            current = current.getLink();
        }

        // give them that node's data
        return (T)current.getInfo();
    }

    /**
     * calls "removeNth(0)"
     * DIFFERENT BIG-OH(1)
     */
    public void removeFirst()
    {
        removeNth(0);
    }

    /**
     * calls "removeNth(_size-1)"
     */
    public void removeLast()
    {
        removeNth(_size-1);
    }

    /**
     * Big-Oh(n)
     * removes an element at the given index from the array
     * @param n the index of the element to remove
     * @throws IndexOutOfBoundsException if the given index is out of range
     * @throws NullPointerException if there is an internal error, shouldn't be thrown
     */
    public void removeNth(int n) throws IndexOutOfBoundsException, NullPointerException
    {
        if(n >= _size || n < 0)
            throw new IndexOutOfBoundsException();

        // loop through the array until you reach the desired index. Keep track of the bottom piece of bread the meat
        Node before = null, current = _head;
        for(int index = 0;index < n;index++)
        {
            if(current == null)
                throw new NullPointerException();

            before = current;
            current = current.getLink();
        }
        // get the top piece of bread if it exists
        Node after = current == null ? null : current.getLink();

        if(n == 0) // special case where you  change head
            _head = after;
        else if(before != null) // remove meat from sandwich
            before.setLink(after);

        _size--;
    }

    /** prints all elements in this array. Each element is printed with it's index on a new line **/
    public void print()
    {
        Node current = _head;
        Object info;
        int index = 0;
        String format = String.format("[%s] %s\n", "%"+String.valueOf(_size).length()+"d", "%s"), itemString;
        while(current != null)
        {
            info = current.getInfo();
            itemString = info == null ? "null" : info.toString();
            System.out.printf(format, index, itemString);

            index++;
            current = current.getLink();
        }
    }

    /**
     * A Node to hold the place of an element in a linked list
     * @param <T> The type of element this node holds
     */
    private class Node<T>
    {
        /** the actual object this node holds **/
        private T _info;

        /** the next node in the array, null if last element **/
        private Node<T> _link;

        /**
         * creates a node with no links
         * @param info the object this node holds
         */
        public Node(T info)
        {
            _info = info;
            _link = null;
        }

        /**
         * @return the object this node holds
         */
        private T getInfo()
        {
            return _info;
        }

        /**
         * @return the next node in the array
         */
        private Node<T> getLink()
        {
            return _link;
        }

        /**
         * sets the next node in the array after this node
         * @param link the new "next node"
         */
        private void setLink(Node<T> link)
        {
            _link = link;
        }
    }
}
