package com.fisherevans.vtc.oop.prime;

public class PrimeTester
{
	public static void main(String[] args)
	{
		PrimeNumbers pn = new PrimeNumbers();
		while(pn.hasNext())
			System.out.println(pn.next());
	}
}
