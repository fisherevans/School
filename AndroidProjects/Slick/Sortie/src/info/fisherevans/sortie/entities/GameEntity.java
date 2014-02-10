package info.fisherevans.sortie.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameEntity
{
	float angle, velocity, width, height, maxVelocity;
	
	double posX, posY;
	
	boolean isAlive, def;
	
	Image entityImage;
	
	String group;

	/*
	 * Create the basic entity
	 * 
	 * @param startX
	 * @param startY
	 * @param startAngle
	 * @param startVelocity
	 * @param imageLocation
	 */
	public GameEntity(int startX, int startY, float startAngle, float startVelocity, String imageLocation, float maxVelocity) throws SlickException
	{
		posX = startX;
		posY = startY;
		angle = startAngle;
		velocity = startVelocity;
		this.maxVelocity = maxVelocity;
		
		isAlive = true;
		
		group = "";
		
		initResources(imageLocation);
	}
	
	/*
	 * Set image for entity and grab height/width from it
	 * @param imageLocation
	 */
	public void initResources(String imageLocation) throws SlickException
	{
		if(imageLocation.equals(""))
		{
			entityImage = new Image("res/images/entities/defaultEntity.png");
			def = true;
		}
		else
		{
			entityImage = new Image(imageLocation);
			def = false;
		}
		
		width = entityImage.getWidth();
		height = entityImage.getHeight();
	}
	
	/*
	 * Update X and Y positions;
	 * @param delta
	 */
	public void update(int delta)
	{
		if(def)
		{
			rotate(0.1f, delta);
		}
		posX += Math.cos(Math.toRadians(angle))*velocity*delta;
		posY += Math.sin(Math.toRadians(angle))*velocity*delta;
		
		//System.out.println("X:" + Math.cos(Math.toRadians(angle))*velocity*delta + " - Y:" + Math.sin(Math.toRadians(angle))*velocity*delta + " - Angle:" + angle); // Debug
	}
	
	/*
	 * Increment angle
	 * @param amount
	 * @param delta
	 */
	public void rotate(float amount, int delta)
	{
		angle += amount*delta;
		angle %= 360;
		angle = angle < 0 ? angle + 360 : angle;
	}
	
	public void setAngle(float newAngle)
	{
		angle = newAngle;
		angle %= 360;
		angle = angle < 0 ? angle + 360 : angle;
	}
	
	/*
	 * adjust velocity (multiply by)
	 * @param amount
	 * @param delta
	 */
	public void multVelocity(float amount, int delta)
	{
		velocity *= amount*delta;
		velocity = velocity > maxVelocity ? maxVelocity : velocity;
		
		//System.out.println("Amount:" + amount + " - Delta:" + delta); // Debug
	}

	public void incrVelocity(float amount, int delta)
	{
		velocity += amount*delta;
		velocity = velocity > maxVelocity ? maxVelocity : velocity;
		
		//System.out.println("Amount:" + amount + " - Delta:" + delta + " - Velocity:" + velocity); // Debug
	}
	
	public void lssnVelocity(float amount, int delta)
	{
		if(velocity > 0)
			velocity -= amount*delta;
		else if(velocity < 0)
			velocity += amount*delta;
		
		if(Math.abs(velocity) <= amount*delta)
			velocity = 0;
		else
			velocity = velocity > maxVelocity ? maxVelocity : velocity;
	}

	/*
	 * set velocity
	 * @param newVelocity
	 */
	public void setVelocity(float newVelocity)
	{
		velocity = newVelocity > maxVelocity ? maxVelocity : newVelocity;
		
	}
	
	/*
	 * set max velocity
	 * @param newMaxVelocity
	 */
	public void setMaxVelocity(float newMaxVelocity)
	{
		maxVelocity = newMaxVelocity;
	}
	
	/*
	 * Return the entity image
	 */
	public Image getEntityImage()
	{
		return entityImage;
	}
	
	/*
	 * invert entities living state
	 */
	public void invertAliveState()
	{
		isAlive = !isAlive;
	}

	public void setX(double newX)
	{
		posX = newX;
	}
	
	public void setY(double newY)
	{
		posY = newY;
	}
	
	public void setAlpha(float newAlpha)
	{
		entityImage.setAlpha(newAlpha);
	}
	
	public void setGroup(String newGroup)
	{
		group = newGroup;
	}
	
	/*
	 * get If alive
	 */
	public boolean getAlive()
	{
		return isAlive;
	}
	
	public double getX()
	{
		return posX;
	}
	
	public double getY()
	{
		return posY;
	}
	
	public float getAngle()
	{
		return angle;
	}

	public float getVelocity()
	{
		return velocity;
	}
	
	public float getMaxVelocity()
	{
		return maxVelocity;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public String getGroup()
	{
		return group;
	}
}
