/**
 * PlayerController.java
 * Copyright 2011, Craig A. Damon
 * all rights reserved
 */
package edu.vtc.cis2260.hvz;

import java.util.SortedSet;

/**
 * PlayerController - controls a players actions
 * @author Craig A. Damon
 *
 */
public abstract class PlayerController
{
   protected PlayerController(HvZGame game,Player p)
   {
  	   _player = p;
  	   _game = game;
   }
   
   public Player getPlayer()
   {
  	   return _player;
   }
   
   public HvZGame getGame()
   {
  	  return _game;
   }
   
 	/** description
 	 * @param structure
 	 */
 	public abstract void enteredStructure(Structure structure);
  
 	/** description
 	 * @param structure
 	 */
 	public abstract void leftStructure(Structure structure);
 	
  
 	/** description
 	 * @param structure
 	 */
 	public abstract void act();
 	
	/** return 'H' pr 'Z'
	 * @return
	 */
	abstract public char getType();
	
	/** description
	 * @param random
	 */
	public void createMission(float random)
	{
		// do nothing
	}


	/**
	 * find the opponents in view
	 * @return the opponents, sorted from closest to farthest
	 */
	abstract SortedSet<Player> findOpponents();


   
   private Player _player;
   private HvZGame _game;
}
