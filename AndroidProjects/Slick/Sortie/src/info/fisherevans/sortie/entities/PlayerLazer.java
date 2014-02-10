package info.fisherevans.sortie.entities;

import org.newdawn.slick.SlickException;

public class PlayerLazer extends GameEntity
{
	float alpha, decay;
	public PlayerLazer(int startX, int startY, float startAngle, float startVelocity, float decay) throws SlickException
	{
		super(startX, startY, startAngle, startVelocity, "res/images/entities/weapons/lazerDefault.png", startVelocity);
		//super(startX, startY, startAngle, startVelocity, "res/images/Untitled.png", startVelocity);
		
		alpha = 1;
		this.decay = decay;
		
		super.getEntityImage().setColor(0,1,0,0,1);
		super.getEntityImage().setColor(1,1,0,0,1);
		super.getEntityImage().setColor(2,1,0,0,1);
		super.getEntityImage().setColor(3,1,0,0,1);
		
		super.setGroup("playerWeapon");
	}
	
	public void update(int delta)
	{
		alpha -= decay;
		
		if(alpha <= 0)
		{
			alpha = 0;
			super.invertAliveState();
		}
		else
		{
			super.setAlpha(alpha);
			super.update(delta);
		}
	}
}
