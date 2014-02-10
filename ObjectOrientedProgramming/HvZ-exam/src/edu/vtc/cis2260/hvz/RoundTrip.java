/**
 * RoundTrip.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

/**
 * RoundTrip - description
 * @author Craig A. Damon
 *
 */
public abstract class RoundTrip extends Mission
{

	/**
	 * 
	 */
	public RoundTrip(Player player,String destName,int waitTime)
		{
			super(player);
			_targetStructure = getGame().findStructure(destName);
			_returnStructure = player.currentlyInside();
		  _remainingTimeAtTarget = waitTime;
			_headingOut = true;
		}

	/**
	 * @return
	 * @see edu.vtc.cis2260.hvz.Mission#getDestination()
	 */
	@Override
	public Structure getDestination()
	{
		if (_headingOut)
			return _targetStructure;
		else if (_atTarget)
			return null;
		else if (_headingHome)
			return _returnStructure;
		else  // better be completed
		  return null;
	}

	/**
	 * @param structure
	 * @see edu.vtc.cis2260.hvz.Mission#arrivedAt(edu.vtc.cis2260.hvz.Structure)
	 */
	@Override
	public void arrivedAt(Structure structure)
	{
		if (_headingOut)
			{
				if (_targetStructure == structure)
					{
						_headingOut = false;
						_atTarget = true;
					}
			}
		else if (_headingHome)
			{
				if (_returnStructure == structure)
					{
						_headingHome = false;
						_completed = true;
					}
			}
	}

	/**
	 * 
	 * @see edu.vtc.cis2260.hvz.Mission#timePassed()
	 */
	@Override
	public void timePassed()
	{
		if (_atTarget)
			{
				if (--_remainingTimeAtTarget <= 0)
					{
						_atTarget = false;
						_headingHome = true;
						getPlayer().exitStructure(_targetStructure);
					}
			}

	}

	/**
	 * @return
	 * @see edu.vtc.cis2260.hvz.Mission#isMissionCompleted()
	 */
	@Override
	public boolean isMissionCompleted()
	{
		return _completed;
	}
	
	private Structure _targetStructure;
	private Structure _returnStructure;
	private boolean _headingOut;
	private boolean _atTarget;
	private int _remainingTimeAtTarget;
  private boolean _headingHome;
  private boolean _completed;
}
