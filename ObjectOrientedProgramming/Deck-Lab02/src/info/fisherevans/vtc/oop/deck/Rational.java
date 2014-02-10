package info.fisherevans.vtc.oop.deck;

import info.fisherevans.vtc.oop.deck.es.BottomZeroException;

public class Rational
{
	private long _top,   // any number, not null
				  _bottom; // any number that's not 0, not null
	
	/** Creates a new rational number
	 * @param top Top of the fraction, never null
	 * @param bottom bottom of fraction, never 0, never null
	 * @throws BottomZeroException thrown if bottom is 0.
	 */
	public Rational(long top, long bottom) throws BottomZeroException
	{
		if(bottom == 0)
		{
			throw new BottomZeroException();
		}
		
		_top = top;
		_bottom = bottom;
	}

	/** Creates a new rational number. implies bottom is 1
	 * @param top Top of the fraction, never null
	 */
	public Rational(long top)
	{
		_top = top;
		_bottom = 1;
	}

	/** Creates a new rational number. sets top and bottom of fraction to 1 */
	public Rational()
	{
		_top = 1;
		_bottom = 1;
	}
	
	/** sets the top value of the fraction
	 * @param top any number, never null
	 */
	public void setTop(long top)
	{
		_top = top;
	}
	
	/** sets the bottom value of the fraction.
	 * @param bottom never null, never 0
	 * @throws BottomZeroException thrown if bottom == 0
	 */
	public void setBottom(long bottom) throws BottomZeroException
	{
		if(bottom == 0)
		{
			throw new BottomZeroException();
		}
		_bottom = bottom;
	}
	
	/** sets both top and bottom of fraction.
	 * @param top never null
	 * @param bottom nevr null, never 0
	 * @throws BottomZeroException thrown if bottom == 0
	 */
	public void set(long top, long bottom) throws BottomZeroException
	{
		if(bottom == 0)
		{
			throw new BottomZeroException();
		}
		_top = top;
		_bottom = bottom;
	}
	
	/** @return top value of fraction. */
	public long getTop()
	{
		return _top;
	}

	/** @return bottom value of fraction. */
	public long getBottom()
	{
		return _bottom;
	}

	/** @return Gets the double object of the top deviced by the bottom values. */
	public double getDouble()
	{
		return (double)_top/((double)_bottom);
	}
}
