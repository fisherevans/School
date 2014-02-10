/**
 * HumanController.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

import java.util.SortedSet;

/**
 * HumanController - controls a human actions. Driven primarily by missions, otherwise stays in same structure.
 * @author Craig A. Damon
 *
 */
public class HumanController extends PlayerController
{

	/**
	 * @param p
	 */
	public HumanController(HvZGame game,Player p)
		{
			super(game,p);
			_seenZombies = false;
		}

	/**
	 * @return
	 * @see edu.vtc.cis2260.hvz.PlayerController#getType()
	 */
	@Override
	public char getType()
	{
		return 'H';
	}
	
	/**
	 * @return the currentWeapon
	 */
	public Weapon getCurrentWeapon()
	{
		return _currentWeapon;
	}

	/**
	 * @param currentWeapon the currentWeapon to set
	 */
	public void setCurrentWeapon(Weapon currentWeapon)
	{
		_currentWeapon = currentWeapon;
	}


	/**
	 * @return the currentMission
	 */
	public Mission getCurrentMission()
	{
		return _currentMission;
	}

	/**
	 * @param newMission the currentMission to set
	 */
	public void setCurrentMission(Mission newMission)
	{
		System.out.println("Gave "+getPlayer()+" mission "+newMission);
		_currentMission = newMission;
		if (getPlayer().currentlyInside() != null)
			getPlayer().exitStructure(getPlayer().currentlyInside());
	}

	/**
	 * @param structure
	 * @see edu.vtc.cis2260.hvz.PlayerController#enteredStructure(edu.vtc.cis2260.hvz.Structure)
	 */
	@Override
	public void enteredStructure(Structure structure)
	{
		if (_currentMission != null)
			_currentMission.arrivedAt(structure);
		if (_currentWeapon != null)
			_currentWeapon.reload();
	}

	/**
	 * @param structure
	 * @see edu.vtc.cis2260.hvz.PlayerController#leftStructure(edu.vtc.cis2260.hvz.Structure)
	 */
	@Override
	public void leftStructure(Structure structure)
	{
		//
	}

	/**
	 * 
	 * @see edu.vtc.cis2260.hvz.PlayerController#act()
	 */
	@Override
	public void act()
	{
		SortedSet<Player> zombies = findOpponents();
		if (!zombies.isEmpty())
			{
				_seenZombies = true;
				if (_currentWeapon != null && !_currentWeapon.isEmpty())
					{
						_currentWeapon.chooseTargets();
						if (_currentWeapon.getCurrentTargets().hasNext())
							{
								_currentWeapon.fire();
								return;
							}
					}
			}
		if (_currentMission != null)
			{
				Structure target = _currentMission.getDestination();
				if (target != null)
					{
						if (getPlayer().getX() == target.getDoorX() && getPlayer().getY() == target.getDoorY())
							{
								getPlayer().enterStructure(target);
								if (_currentMission.isMissionCompleted())
									{
										_currentMission = null;
									}
								return;
							}
						getPlayer().setDirection(getPlayer().angleTo(target));
						if (getPlayer().distanceTo(target) < 2)
							getPlayer().move(1);
						else
					    getPlayer().move(2);
					}
			}
	}

	/**
	 * @return
	 * @see edu.vtc.cis2260.hvz.PlayerController#findOpponents()
	 */
	@Override
	public SortedSet<Player> findOpponents()
	{
		return getGame().findZombies(getPlayer(),120,300,240,50);
	}
	
	
	/** description
	 * @param random
	 */
	public void createMission(float random)
	{
		if (_seenZombies && getCurrentWeapon() == null)
			{  // need protection more than anything
				setCurrentMission(new BuyWeapon(this));
				return;
			}
		if (_currentMission != null)
			{
				_currentMission.timePassed();
				return;
			}
		if (random > 0.05f)
			return;
		Mission mission;
		if (random < 0.02f && !getPlayer().currentlyInside().getName().equals("Store"))
			{
				mission = new GoToStore(getPlayer());
			}
		else if (getPlayer().currentlyInside().getName().equals("Home"))
			{
				mission = new GoToWork(getPlayer());
			}
		else
			{
				mission = new GoHome(getPlayer());
			}
		setCurrentMission(mission);
	}

	
	private Weapon _currentWeapon;    // may be null
	private Mission _currentMission;   // may be null
	private boolean _seenZombies;
}
