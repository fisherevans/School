/**
 * BuyWeapon.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

/**
 * BuyWeapon - description
 * @author Craig A. Damon
 *
 */
public class BuyWeapon extends GoToStore
{

	/**
	 * 
	 */
	public BuyWeapon(HumanController human)
		{
			super(human.getPlayer());
			_human = human;
		}


	/**
	 * @param structure
	 * @see edu.vtc.cis2260.hvz.Mission#arrivedAt(edu.vtc.cis2260.hvz.Structure)
	 */
	@Override
	public void arrivedAt(Structure structure)
	{
	    super.arrivedAt(structure);
		if (structure.getName().equals("Store"))
		{
			double rand = Math.random();
			if(rand < 0.5) // 50%
				_human.setCurrentWeapon(new SixShooter(_human.getPlayer()));// choose a weapon for the player/human
			else if(rand < 0.75) // 25%
				_human.setCurrentWeapon(new RPG(_human.getPlayer()));// choose a weapon for the player/human
		}
	}

	private HumanController _human;
}
