package info.fisherevans.sortie.main;

import info.fisherevans.sortie.entities.GameEntity;
import info.fisherevans.sortie.entities.Player;
import info.fisherevans.sortie.entities.PlayerLazer;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Sortie extends BasicGame
{
	public Player     player;
	public GameEntity testEntity;

	UnicodeFont       hudFont = null;
	
	public boolean    userShooting,
					  userTurbo,
					  userMoving;
	
	public int        userAimX,
					  userAimY;
	
	public float      userLR,
					  userUD;
	
	public float      userAngle = 90;
	
	public long       topLeftX,
					  topLeftY,
					  curX,
					  curY;
	
	public float      entSX,
					  entSY,
					  entSA,
					  rX,
					  rY;
	
	public static int screenWidth  = 800,
				      screenHeight = 600;
	
	public Image      gridBg;
	public float      gridBgSize;
	public int        gridBgTileX,
					  gridBgTileY;
	
	public ArrayList<GameEntity> entityList;
	
	/*
	 * Create the basic game with title
	 * @param title
	 */
	public Sortie(String title)
	{
		super(title);
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		player = new Player(0,0,100,100, this);
		
		entityList = new ArrayList<GameEntity>();
		
		for(int i = 0;i < 20;i++)
		{
			entityList.add(new GameEntity((int) (Math.random()*2000 - 1000), (int) (Math.random()*2000 - 1000), 0, 0, "", 1));
		}
		
		gc.setMouseCursor("res/images/gui/cursor.png",7,7);
		
		gridBg = new Image("res/images/backgrounds/bg_grid.png");
		gridBg.setAlpha(0.2f);
		gridBgSize = gridBg.getWidth();
		gridBgTileX = (int)(Math.ceil(screenWidth/gridBgSize) + 2);
		gridBgTileY = (int)(Math.ceil(screenHeight/gridBgSize) + 4);
		
		hudFont = new UnicodeFont("res/fonts/mainhud.ttf", 24, false, false);
		hudFont.addAsciiGlyphs();
		hudFont.addGlyphs(400, 600);
		hudFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		hudFont.setPaddingRight(1);
		hudFont.setPaddingLeft(1);
		hudFont.loadGlyphs();
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		updateUserInput(gc);

		player.update(userLR, userUD, delta);
		
		for(int i = 0;i < entityList.size();i++)
		{
			GameEntity tempEnt = entityList.get(i);
			if(tempEnt.getAlive())
			{
				tempEnt.update(delta);
			}
			else
			{
				entityList.remove(i);
				i--;
			}
		}
		
		System.out.println("TimeStep:" + delta);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{	
		int bgXPos = (int) -((player.getX()/4) % gridBg.getWidth());
		int bgYPos = (int) ((player.getY()/4) % gridBg.getHeight());
		for(int i = 0;i < gridBgTileY;i++) // y
		{
			for(int i2 = 0;i2 < gridBgTileX;i2++)
			{
				gridBg.draw(bgXPos - gridBgSize + (i*gridBgSize),  bgYPos - gridBgSize + (i2*gridBgSize));
			}
		}
		
		for(int i = 0;i < entityList.size();i++)
		{
			GameEntity tempEnt = entityList.get(i);
			if(tempEnt.getGroup().equals("playerWeapon"))
			{
				
			}
			drawEntity(tempEnt);
		}
		
		drawPlayer(player);
		
		hudFont.drawString(0f, screenHeight - 60, " X:" + (int)player.getX() + "\n Y:" + (int)(player.getY()));
	}
	
	public void drawEntity(GameEntity ent)
	{
		float entSX, entSY, entSA, entW, entH;
	
		entW = ent.getWidth();
		entH = ent.getHeight();
		
		entSA = (ent.getAngle() * -1) + 360;

		entSX = (float) (                ent.getX() - player.getX() + (screenWidth/2)  - (entW/2));
		entSY = (float) (screenHeight - (ent.getY() - player.getY() + (screenHeight/2) + (entH/2)));
		
		ent.getEntityImage().setRotation(entSA);
		ent.getEntityImage().draw(entSX, entSY);
		
	}
	
	public void drawPlayer(GameEntity ent)
	{
		rX = userAimX - (screenWidth/2);
		rY = userAimY - (screenHeight/2);
		
		entSA = rX == 0 && rY == 0 ? 0 : (float) Math.toDegrees( Math.atan(rY / rX) );
		
		entSA = (entSA*-1) + 360;
		
		if(rX < 0)
			entSA -= 180;
		
		entSX = (screenWidth/2)  - (ent.getWidth()/2);
		entSY = (screenHeight/2) - (ent.getHeight()/2);
		
		ent.getEntityImage().setRotation(entSA);
		ent.getEntityImage().draw(entSX, entSY);
		
	}
	
	public void playerShoot(int x, int y, float vel, float decay) throws SlickException
	{
		entityList.add(new PlayerLazer(x, y, -entSA, vel, decay));
	}
	
	public void updateUserInput(GameContainer gc)
	{
		Input ki = gc.getInput();
		boolean W      = ki.isKeyDown(Keyboard.KEY_W);
		boolean A      = ki.isKeyDown(Keyboard.KEY_A);
		boolean S      = ki.isKeyDown(Keyboard.KEY_S);
		boolean D      = ki.isKeyDown(Keyboard.KEY_D);
		boolean SPACE  = ki.isKeyDown(Keyboard.KEY_SPACE);
		boolean LSHIFT = ki.isKeyDown(Keyboard.KEY_LSHIFT);
		
		userLR = 0;
		userUD = 0;
		
		if(W)
			userUD += 1;
		if(S)
			userUD -= 1;
		if(D)
			userLR += 1;
		if(A)
			userLR -= 1;

		userShooting = SPACE  ? true : false;
		userTurbo    = LSHIFT ? true : false;

		userAimX = Mouse.getX();
		userAimY = Mouse.getY();
		
	}
	
	public void getLocations()
	{
		
	}
	
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer game = new AppGameContainer(new Sortie("Sortie"));
		game.setDisplayMode(screenWidth, screenHeight, false);
		game.start();
	}
}
