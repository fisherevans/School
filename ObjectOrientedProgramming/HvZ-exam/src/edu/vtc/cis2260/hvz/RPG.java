package edu.vtc.cis2260.hvz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

public class RPG extends Weapon
{
	private int _ammo;
	private final int MAX_AMMO = 1;
	private final int BLAST_RANGE = 30;
	private final int RANGE = 200;
	
	private SortedSet<Player> _opps;
	private Player _curTarg;

	protected RPG(Player owner)
	{
		super(owner);
		_ammo = MAX_AMMO;
	}

	@Override
	public void chooseTargets()
	{
		_opps = getPlayer().findOpponents();
	}

	@Override
	public void fire()
	{
		if(!isEmpty())
		{
			Iterator<Player> zombs = _opps.iterator();
			Player zomb = zombs.next();
			if(getPlayer().distanceTo(zomb) <= RANGE)
			{
				System.out.println("H" + getPlayer().getNumber() + " shot RPG - " + _ammo + "/" + MAX_AMMO + " ammo");
				_ammo--;
				while(zombs.hasNext())
				{
					Player tzomb = zombs.next();
					if(tzomb.distanceTo(zomb) <= BLAST_RANGE)
					{
						getPlayer().getGame().killZombie(tzomb);
					}
				}
				getPlayer().getGame().killZombie(zomb);
				
			}
		}
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return _ammo <= 0;
	}

	@Override
	public void reload()
	{
		_ammo = MAX_AMMO;
	}

	@Override
	public int getRemainingShots()
	{
		// TODO Auto-generated method stub
		return MAX_AMMO - _ammo;
	}

	@Override
	public Iterator<Player> getCurrentTargets()
	{
		return _opps.iterator();
	}

}
