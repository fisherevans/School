package com.fisherevans.ads.assignment2;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Fisher Evans
 *
 */
public class UnorderedArrayListTest {

    /**
     *
     */
    @Test
    public void testComparableDates()
    {
        PrimDate date1 = new PrimDate(12,31,1994);
        PrimDate date2 = new PrimDate(12,31,1994);
        boolean result = date1.equals(date2);

        assertTrue("Expect true, got " + result, result);
    }
    /**
     *
     */
    @Test
    public void testDatesList()
    {
        UnorderedArrayList<PrimDate> list = new UnorderedArrayList<PrimDate>();

        PrimDate date1 = new PrimDate(12,31,1994);

        list.insertEnd(date1);

        PrimDate date2 = new PrimDate(12,31,1994);

        assertTrue(list.indexOf(date2) >= 0);

        list.clear();

        list.insertEnd(new PrimDate(12,31,1994));
        PrimDate result = list.get(0);
        assertTrue("expected new PrimDate(12,31,1994), received " + result, result.equals(new PrimDate(12,31,1994)));
        result = list.get(list.size() - 1);
        assertTrue(result.equals("1"));
        int size = list.size();
        assertTrue(size == 1);
    }

    /**
     *
     */
    @Test
    public void addTwoStrings()
    {
        UnorderedArrayList<String> list = new UnorderedArrayList<String>();
        list.insertEnd("1");
        String result = list.get(0);
        assertTrue("expected 1, received " + result, result.equals("1"));
        result = list.get(list.size() - 1);
        assertTrue(result.equals("1"));
        int size = list.size();
        assertTrue(size == 1);

        list.insertEnd("2");
        result = list.get(0);
        assertTrue(result.equals("1"));
        result = list.get(list.size() - 1);
        assertTrue(result.equals("2"));
        size = list.size();
        assertTrue("expected 2, received "+size,size == 2);
        System.out.println("Printing list: expect list with two elements '1  2'");
        System.out.println(list);

        list.remove("2");
        size = list.size();
        assertTrue(size == 1);
        result = list.get(0);
        assertTrue(result.equals("1"));
        result = list.get(list.size()-1);
        assertTrue(result.equals("1"));

        System.out.println("Printing list: expect list with 1 element '1'");
        System.out.println(list);

        list.remove("1");
        size = list.size();
        assertTrue(size == 0);

        System.out.println("Printing list: expect list with 0 elements");
        System.out.println(list);

    }
    /**
     *
     */
    @Test
    public void addTwoIntegers()
    {
        UnorderedArrayList<Integer> list = new UnorderedArrayList<Integer>();
        list.insertEnd(1);
        int result = list.get(0);
        assertTrue("expected 1, received " + result, result == 1);
        result = list.get(list.size() - 1);
        assertTrue(result == 1);
        int size = list.size();
        assertTrue(size == 1);

        list.insertEnd(2);
        result = list.get(0);
        assertTrue(result == 1);
        result = list.get(list.size() - 1);
        assertTrue(result == 2);
        size = list.size();
        assertTrue("expected 2, received "+size,size == 2);
        System.out.println("Printing list: expect list with two elements '1  2'");
        System.out.println(list);

        list.remove(2);
        size = list.size();
        assertTrue(size == 1);
        result = list.get(0);
        assertTrue(result == 1);
        result = list.get(list.size()-1);
        assertTrue(result == 1);

        System.out.println("Printing list: expect list with 1 element '1'");
        System.out.println(list);

        list.remove(1);
        size = list.size();
        assertTrue(size == 0);

        System.out.println("Printing list: expect list with 0 elements");
        System.out.println(list);

    }
	
	/**
	 * 
	 */
	@Test
	public void addThreeIntegers()
	{
		UnorderedArrayList<Integer> list = new UnorderedArrayList<Integer>();
		list.insertEnd(1);
		int result = list.get(0);
		assertTrue(result == 1);
		result = list.get(list.size()-1);
		assertTrue(result == 1);
		int size = list.size();
		assertTrue(size == 1);
		
		list.insertEnd(2);
		result = list.get(0);
		assertTrue(result == 1);
		result = list.get(list.size()-1);
		assertTrue(result == 2);
		size = list.size();
		assertTrue("expected 2, received "+size,size == 2);
		System.out.println("Printing list: expect list with two elements '1  2'");
		System.out.println(list);
		
		list.insertEnd(3);
		result = list.get(0);
		assertTrue(result == 1);
		result = list.get(list.size()-1);
		assertTrue(result == 3);
		size = list.size();
		assertTrue("expected 3, received "+size,size == 3);
		System.out.println("Printing list: expect list with three elements '1  2  3'");
		System.out.println(list);

		
		list.remove(1);
		size = list.size();
		assertTrue(size == 2);
		result = list.get(0);
		assertTrue(result == 2);
		result = list.get(list.size()-1);
		assertTrue(result == 3);

		System.out.println("Printing list: expect list with 2 elements '2  3'");
		System.out.println(list);
	
		list.remove(2);
		size = list.size();
		assertTrue(size == 1);
		result = list.get(0);
		assertTrue("expected 3, received "+result, result == 3);
		result = list.get(list.size()-1);
		assertTrue(result == 3);

		System.out.println("Printing list: expect list with 1 element '3'");
		System.out.println(list);
		
		list.remove(3);
		size = list.size();
		assertTrue(size == 0);
		
		System.out.println("Printing list: expect list with 0 elements");
		System.out.println(list);
		
	}
	/**
	 * 
	 */
	@Test 
	public void testEmptyList()
	{
		UnorderedArrayList<Integer> emptyList = new UnorderedArrayList<Integer>();
		
		boolean result = emptyList.remove(1);
		assertTrue(result == false);
	}
	
	/**
	 * 
	 */
	@Test (expected=IndexOutOfBoundsException.class)
	public void testEmptyListGet()
	{
		UnorderedArrayList<Integer> emptyList = new UnorderedArrayList<Integer>();
		
		emptyList.get(emptyList.size()-1);
	}
}

