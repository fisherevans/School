package com.fisherevans.vtc.oop.prime;

import java.util.Iterator;

public class PrimeNumbers implements Iterator<Integer>
{
	private Integer _cur = 1; // never null, never negative
	
	/** Checks if the nest prime is in the INTEGER range. Yeah, I know - REALLY slow.
	 * @return true if there is a next prime.
	 */
	@Override
	public boolean hasNext()
	{
		int test = _cur;
		while(!isPrime(++test))
		{
			if(test == Integer.MAX_VALUE)
				return false;
		}
		return true;
	}

	/** Returns the next prime number in the sequnce.
	 * @return The next prime number.
	 */
	@Override
	public Integer next()
	{
		while(!isPrime(++_cur));
		return _cur;
	}
	
	/** Not use din this iterator (it's optional). */
	@Override
	public void remove()
	{ }
	
	/**
	 * is the number a prime
	 * @param n the number 
	 * @return true if prime, false if not
	 */
	public static boolean isPrime(int n)
	{
	  if (n < 2)
	    return false;
	  if (n == 2)
	    return true;
	  if ((n & 1) == 0)
	    return false;
	  int max = approxSqrt(n);
	  for (int i = 3; i <= max; i+= 2)
	    {
	      if (n % i == 0)
	        return false;
	    }
	  return true;
	}
	
	/**
	 * find an int as close as convenient but >= sqrt of n
	 * @param n the number 
	 * @return a number that is >= sqrt(n)
	 */
	private static int approxSqrt(int n)
	{
	   if (n <= 0)
	     return 0;
	   int nBits = 0;
	   for (int i = n-1; i != 0; i >>= 1, nBits++)
	      ;
	   return (1 << (nBits+1)/2);
	}
}
