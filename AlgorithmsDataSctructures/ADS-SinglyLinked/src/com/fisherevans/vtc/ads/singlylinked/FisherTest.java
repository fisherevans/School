package com.fisherevans.vtc.ads.singlylinked;

import static org.junit.Assert.*;

import org.junit.Test;

/*
    Author: Fisher Evans
    Date: 09/24/13
 */

public class FisherTest
{
    @Test
    public void testGenerics()
    {
        SList<String> strList = new SList<String>();
        strList.insertFirst("3");
        strList.insertFirst("2");
        strList.insertFirst("1");

        SList<Integer> intList = new SList<Integer>();
        intList.insertFirst(3);
        intList.insertFirst(2);
        intList.insertFirst(1);

        assertTrue(strList.getFirst().equals(intList.getFirst()+""));
        assertTrue(strList.getLast().equals(intList.getLast()+""));
    }

    @Test
    public void testSize()
    {
        SList<String> list = new SList<String>();
        assertTrue(list.size() == 0);

        list.insertFirst("test");
        assertTrue(list.size() == 1);

        list.insertFirst("test");
        assertTrue(list.size() == 2);

        list.insertFirst("test");
        assertTrue(list.size() == 3);

        list.insertFirst("test");
        assertTrue(list.size() == 4);

        list.removeFirst();
        assertTrue(list.size() == 3);

        list.removeFirst();
        assertTrue(list.size() == 2);
    }

    @Test
    public void testInsertNth()
    {
        SList<String> list = new SList<String>();

        try
        {
            list.insertNth(0, "first");
            list.insertNth(0, "second");
            assertTrue(list.getNth(0).equals("second"));
            list.insertNth(1, "third");
            assertTrue(list.getNth(2).equals("first"));
            list.insertNth(list.size(), "last");
            assertTrue(list.getNth(list.size()-1).equals("last"));
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertNthLowRange()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        list.insertNth(-1, "4");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertNthHighRange()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        list.insertNth(4, "4");
    }

    @Test
    public void testGetNth()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        assertTrue(list.getNth(0).equals("3"));
        assertTrue(list.getNth(1).equals("2"));
        assertTrue(list.getNth(2).equals("1"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNthLowRange()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        list.getNth(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNthHighRange()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        list.getNth(4);
    }

    @Test
    public void testRemoveNth()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        assertTrue(list.size() == 3);

        list.removeNth(0);
        assertTrue(list.getNth(0).equals("2"));
        assertTrue(list.size() == 2);

        list.removeNth(1);
        assertTrue(list.getNth(0).equals("2"));
        assertTrue(list.size() == 1);

        list.removeNth(0);
        assertTrue(list.size() == 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveNthLowRange()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        list.removeNth(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveNthHighRange()
    {
        SList<String> list = new SList<String>();

        list.insertFirst("1");
        list.insertFirst("2");
        list.insertFirst("3");

        list.removeNth(4);
    }
}
