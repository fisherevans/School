package edu.vtc.cis2260.hvz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

public class SixShooter extends Weapon
{
	private int _ammo;
	private final int MAX_AMMO = 6;
	private final int RANGE = 100;
	
	private SortedSet<Player> _opps;
	private Player _curTarg;

	protected SixShooter(Player owner)
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
		chooseTargets();
		if(!isEmpty())
		{
			Player _zomb = _opps.iterator().next();
			if(getPlayer().distanceTo(_zomb) <= RANGE)
			{
				System.out.println("H" + getPlayer().getNumber() + " shot 6 Shooter- " + _ammo + "/" + MAX_AMMO + " ammo");
				_zomb.getGame().killZombie(_zomb);
				_ammo--;
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
		chooseTargets();
		return _opps.iterator();
	}

}
