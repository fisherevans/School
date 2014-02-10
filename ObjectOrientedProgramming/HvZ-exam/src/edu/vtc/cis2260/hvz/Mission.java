/**
 * Mission.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

/**
 * Mission - an activity that a human must do
 * @author Craig A. Damon
 *
 */
public abstract class Mission
{
	protected Mission(Player player)
	{
		_player = player;
	}
	
	/**
	 * get the player whose mission this is
	 * @return the player
	 */
	public Player getPlayer()
	{
		return _player;
	}
	
	/**
	 * get the active game
	 * @return the game
	 */
	public HvZGame getGame()
	{
		return _player.getGame();
	}
	
	/**
	 * get the destination where the player is heading now
	 * @return the structure being headed to
	 */
	public abstract Structure getDestination();
	
	/**
	 * let the mission know that we have arrived at a structure 
	 * @param structure
	 */
	public abstract void arrivedAt(Structure structure);
	
	/**
	 * let the mission know that another unit of time has passed
	 *
	 */
	public abstract void timePassed();
	
	/**
	 * has the mission completed
	 * @return true if this mission is completed
	 */
	public abstract boolean isMissionCompleted();
	
	private Player _player;
}
