package info.fisherevans.droidmote.server;

import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main
{
	private UserInput userInput;
	
	private Robot keyRobot;
	
	public Config config;
	
	public CharInterp charInterp;
	
	public Server server;
	
	public boolean debug;
	
	
	public Main()
	{
		System.out.print("\n\n");
		log("Starting the DroidMote Server v0.1");
		log("Created by Fisher Evans");
		log("=========================");
		
		log("Loading the user command interface");
		userInput = new UserInput(this);
		userInput.startThread();
		
		log("Loading the character interperator");
		charInterp = new CharInterp(this);
		
		log("Loading the user interface controler");
		try { keyRobot = new Robot(); }
		catch(Exception e) { }
		
		log("Loading server configuration from 'DroidMote.cfg'");
		config = new Config(this);
		
		log("Preparing the network thread");
		server = new Server(this);
		
		start();
	}
	
	public void mouseMove(float dx, float dy)
	{
		PointerInfo mouse = MouseInfo.getPointerInfo();
		float x = (float) mouse.getLocation().getX();
		float y = (float) mouse.getLocation().getY();
		keyRobot.mouseMove((int)(x+dx), (int)(y+dy));
	}
	
	public void mousePress(int id)
	{
		keyRobot.mousePress(id);
	}
	
	public void mouseRelease(int id)
	{
		keyRobot.mouseRelease(id);
	}
	
	public void mouseScroll(int d)
	{
		keyRobot.mouseWheel(d);
	}
	
	public void keyPress(int press)
	{
		keyRobot.keyPress(press);
	}
	
	public void keyRelease(int release)
	{
		keyRobot.keyRelease(release);
	}
	
	public void setConfig(String key, String value)
	{
		if(key.equals("port"))
		{
			config.configFile.setProperty(key, value);
			log("[CONFIG] Your server " + key + " has been changed to: " + value);
			log("[CONFIG] The server must restart for changes to take effect.");
		}
		if(key.equals("debug"))
		{
			if(value.equals("true"))
				debug = true;
			else
				debug = false;
			
			log("[NOTICE] Toggling debug mode to " + value);
		}
		else
		{
			log("[ERROR] There is no configuration item called: " + key);
		}
		config.getConfigs();
	}
	
	public void restart()
	{
		server.run = false;
		
		log("Restarting the server");
		log("Closing all network connections");
		server.endConnection();
		
		start();
	}
	
	public void start()
	{
		log("=========================");
		log("Starting the server");
		server.initiateThread();
		server.startConnection();
	}
	
	public void quit()
	{
		log("Quitting...");
		log("Closing all network connections");
		server.endConnection();
		
		log("Saving the current configuration");
		try { config.configFile.store(new FileOutputStream("DroidMote.cfg"), null); }
		catch (Exception e) {  }
		
		log("=========================");
		log("The server has been shut down");
		System.exit(0);
	}
	
	public void log(String message)
	{
		Calendar calendar = new GregorianCalendar();
		String time = String.format("[%02d:%02d:%02d DroidMote] ",
				calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		System.out.println(time + message);
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
	}
}
