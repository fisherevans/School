package com.fisherevans.ads.listsystem;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/27/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayList<T> extends List<T>
{
    private static int DEFAULT_ALLOCATION_HEAP_SIZE = 10;

    private Object[] _array;
    private int _allocationHeapSize, _size;

    public ArrayList()
    {
        this(DEFAULT_ALLOCATION_HEAP_SIZE);
    }

    public ArrayList(int allocationHeapSize)
    {
        _allocationHeapSize = allocationHeapSize;
        _size = 0;
        _array = new Object[_allocationHeapSize];
    }

    @Override
    public int add(T object)
    {

        return 0;
    }

    @Override
    public void remove(T object)
    {

    }

    @Override
    public T remove(int index)
    {
        return null;
    }

    @Override
    public void clear()
    {

    }

    @Override
    public T set(int index, T object)
    {
        return null;
    }

    @Override
    public void insert(int index, T object)
    {

    }

    @Override
    public T get(T object)
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public boolean isFull()
    {
        return false;
    }

    @Override
    public boolean contains(T object)
    {
        return false;
    }

    @Override
    public int indexOf(T object)
    {
        return 0;
    }

    @Override
    public int lastIndexOf(T object)
    {
        return 0;
    }

    @Override
    public boolean equals(List otherList)
    {
        return false;
    }

    @Override
    public Object[] copyToArray()
    {
        return new Object[0];
    }
}
