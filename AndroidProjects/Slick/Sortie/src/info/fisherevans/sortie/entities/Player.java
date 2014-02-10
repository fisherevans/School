package info.fisherevans.sortie.entities;

import info.fisherevans.sortie.main.Sortie;

import org.newdawn.slick.SlickException;

public class Player extends GameEntity
{
	float curHp, maxHp, deAcc, rotScale, accRate, localAngle, lr, ud, lazerCool, lazerDecay;
	
	Sortie source;
	
	/*
	 * Create the player
	 * @param startX
	 * @param startY
	 * @param startHp
	 * @param maxHp
	 */
	public Player(int startX, int startY, float startHp, float maxHp, Sortie source) throws SlickException
	{
		super(startX, startY, 270f, 0f, "res/images/entities/playerShip.png", 0.45f);
		curHp = startHp;
		this.maxHp = maxHp;
		this.source = source;

		lazerCool = 0;
		lazerDecay = 0.0075f;
		deAcc = 0.995f;
		rotScale = 0.7f;
		accRate = 0.002f;
		localAngle = 0;
	}
	
	public void update(float userLR, float userUD, int delta) throws SlickException
	{
		float lrD, udD;
		
		if(userLR != 0)
		{
			lrD = userLR * accRate * delta;
			lr += lrD;
			lr = lr > super.getMaxVelocity() ? super.getMaxVelocity() : lr;
			lr = lr < -super.getMaxVelocity() ? -super.getMaxVelocity() : lr;
		} else {
			lr *= deAcc;
		}
		
		if(userUD != 0)
		{
			udD = userUD * accRate * delta;
			ud += udD;
			ud = ud > super.getMaxVelocity() ? super.getMaxVelocity() : ud;
			ud = ud < -super.getMaxVelocity() ? -super.getMaxVelocity() : ud;
		} else {
			ud *= deAcc;
		}

		super.setX(super.getX() + lr * delta);
		super.setY(super.getY() + ud * delta);
		
		lazerCool = lazerCool > 0 ? lazerCool - lazerDecay * delta : 0;
		
		if(source.userShooting && lazerCool <= 0)
		{
			lazerCool = 1;
			source.playerShoot((int)super.getX(), (int)super.getY(), 0.85f, 0.001f);
		}
	}
	
	public void rotate(float amount, int delta)
	{
		super.rotate(amount*rotScale, delta);
	}
	
	public void incrVelocity(float amount, int delta)
	{
		super.incrVelocity(amount*accRate, delta);
	}

}
