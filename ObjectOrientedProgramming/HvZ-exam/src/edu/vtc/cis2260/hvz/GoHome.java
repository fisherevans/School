/**
 * GoHome.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

/**
 * GoHome - description
 * @author Craig A. Damon
 *
 */
public class GoHome extends Mission
{
	public GoHome(Player player)
	{
		super(player);
		_target = player.getGame().findStructure("Home");
		_completed = false;
	}

	/**
	 * @return
	 * @see edu.vtc.cis2260.hvz.Mission#getDestination()
	 */
	@Override
	public Structure getDestination()
	{
		if (_completed)
			return null;
		return _target;
	}

	/**
	 * @param structure
	 * @see edu.vtc.cis2260.hvz.Mission#arrivedAt(edu.vtc.cis2260.hvz.Structure)
	 */
	@Override
	public void arrivedAt(Structure structure)
	{
    if (_target == structure)
    	{
    		_completed = true;
    	}
    else
    	{
    		getPlayer().exitStructure(structure);
    	}
	}

	/**
	 * 
	 * @see edu.vtc.cis2260.hvz.Mission#timePassed()
	 */
	@Override
	public void timePassed()
	{
    // nothing to do here
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
	
	private Structure _target;
	private boolean _completed;

}
