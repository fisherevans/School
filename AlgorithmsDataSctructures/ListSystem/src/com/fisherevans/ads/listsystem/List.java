package com.fisherevans.ads.listsystem;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/27/13
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class List<T>
{
    private T t;

    public abstract int add(T object);

    public abstract void remove(T object);
    public abstract T remove(int index);
    public abstract void clear();

    public abstract T set(int index, T object);
    public abstract void insert(int index, T object);

    public abstract T get(T object);

    public abstract int size();
    public abstract boolean isEmpty();
    public abstract boolean isFull();

    public abstract boolean contains(T object);
    public abstract int indexOf(T object);
    public abstract int lastIndexOf(T object);

    public abstract boolean equals(List otherList);

    public abstract Object[] copyToArray();

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}
