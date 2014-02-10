package info.fisherevans.far;
import info.fisherevans.far.input.KeyboardInput;
import info.fisherevans.far.particals.PlayerLazer;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;

public class Main extends BasicGame
{
	int screenWidth = 1000;
	int screenHeight = 750;
	
	float posAcc = 0.0005f;
	float posDec = 0.999f;
	float posDX = 0;
	float posDY = 0;
	double posX = 0;
	double posY = 0;
	float maxSpeed = 0.3f;
	
	float aimAngle;
	float aimXOS;
	float aimYOS;
	
	int screenX = 0;
	int screenY = 0;
	
	boolean[] keys = {false, false, false, false, false, false};
	
	KeyboardInput keyboardInput;

	int bgSize = 500;
	Image bgGrid = null;
	int maxBGTileX, maxBGTileY;
	
	Image playerShip = null;
	Image mouseCursor = null;
	Image playerLazer = null;
	
	Rectangle healthBar = null;
	
	UnicodeFont hudFont = null;
	
	ArrayList<PlayerLazer> playerLazers;

	public Main()
	{
		super("Far - A Game by Fisher Evans");
		
		maxBGTileX = (int)(Math.ceil(screenWidth/bgSize) + 2);
		maxBGTileY = (int)(Math.ceil(screenHeight/bgSize) + 4);
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		keyboardInput = new KeyboardInput();
		
		bgGrid = new Image("res/images/backgrounds/bg_grid.png");
		bgGrid.setAlpha(0.2f);

		playerShip = new Image("res/images/entities/ship.png");
		playerLazer = new Image("res/images/entities/playerLazer.png");
		
		gc.setMouseCursor("res/images/cursor.png",7,7);

		hudFont = new UnicodeFont("res/fonts/mainhud.ttf", 24, false, false);
		hudFont.addAsciiGlyphs();
		hudFont.addGlyphs(400, 600);
		hudFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		hudFont.setPaddingRight(1);
		hudFont.setPaddingLeft(1);
		hudFont.loadGlyphs();
		
		playerLazers = new ArrayList<PlayerLazer>();
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		keys = keyboardInput.getKeys(gc);

		if(keys[1] && !keys[3]) { posDX += posAcc; }
		else if(keys[3] && !keys[1]) { posDX -= posAcc; }
		else { posDX *= posDec; }
		posDX = posDX <= maxSpeed ? posDX : maxSpeed;
		posDX = posDX >= -maxSpeed ? posDX : -maxSpeed;
		
		if(keys[0] && !keys[2]) { posDY += posAcc; }
		else if(keys[2] && !keys[0]) { posDY -= posAcc; }
		else { posDY *= posDec; }
		posDY = posDY <= maxSpeed ? posDY : maxSpeed;
		posDY = posDY >= -maxSpeed ? posDY : -maxSpeed;

		posX += posDX*delta;
		posY += posDY*delta;
		
		screenX = gc.getInput().getMouseX();
		screenY = gc.getInput().getMouseY();

		aimXOS = screenX - (screenWidth/2);
		aimYOS = screenHeight-screenY - (screenHeight/2);
		
		if(aimXOS != 0)
			aimAngle = (float)(-Math.toDegrees(Math.atan(aimYOS / aimXOS))) + 90;
		else if(aimXOS == 0 && aimYOS < 0)
			aimAngle = 180;
		else
			aimAngle = 0;

		aimAngle = aimXOS < 0 ? aimAngle + 180 : aimAngle;
		
		playerShip.setRotation(aimAngle);
		
		if(Mouse.isButtonDown(0))
		{
				playerLazers.add(new PlayerLazer(aimAngle - 90, 1, (float)(screenWidth/2)-1, (float)(screenHeight/2)-3.5f, 1.0f, 0, 0));
		}
		for(int i = 0;i < playerLazers.size();i++)
		{
			playerLazers.get(i).update(delta);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		float localX = (float) (posX % bgSize);
		float localY = (float) (posY % bgSize);

		for(int i = 0;i < maxBGTileY;i++) // y
		{
			for(int i2 = 0;i2 < maxBGTileX;i2++)
			{
				bgGrid.draw(localX - bgSize + (i*bgSize),  localY - bgSize + (i2*bgSize));
			}
		}

		
		for(int i = 0;i < playerLazers.size();i++)
		{
			PlayerLazer tempLazer = playerLazers.get(i);
			playerLazer.setColor(0, tempLazer.getR(), tempLazer.getG(), tempLazer.getB(), 1.0f);
			playerLazer.setColor(1, tempLazer.getR(), tempLazer.getG(), tempLazer.getB(), 1.0f);
			playerLazer.setColor(2, tempLazer.getR(), tempLazer.getG(), tempLazer.getB(), 1.0f);
			playerLazer.setColor(3, tempLazer.getR(), tempLazer.getG(), tempLazer.getB(), 1.0f);
			playerLazer.setRotation(tempLazer.getAngle() + 90);
			playerLazer.draw(tempLazer.getX(), tempLazer.getY());
		}
		
		playerShip.draw((screenWidth/2)-35,(screenHeight/2)-44);
		
		
		hudFont.drawString(10f, 710f, " X:" + (int)posX + " - Y:" + (int)posY);
		

	}


	
	public void getKeys()
	{
		
	}
	
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer game = new AppGameContainer(new Main());
		game.setDisplayMode(1000, 750, false);
		game.start();
	}

}
