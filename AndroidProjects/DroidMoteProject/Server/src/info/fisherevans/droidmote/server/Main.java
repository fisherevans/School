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
		
		log("Loading the user command interface"); // load command thread
		userInput = new UserInput(this);
		userInput.startThread();
		
		log("Loading the character interperator"); // load char interp class
		charInterp = new CharInterp(this);
		
		log("Loading the user interface controler"); // key typing mouse movement agent
		try { keyRobot = new Robot(); }
		catch(Exception e) { }
		
		log("Loading server configuration from 'DroidMote.cfg'"); // get config
		config = new Config(this);
		
		log("Preparing the network thread"); // prepare the network thread
		server = new Server(this);
		
		start(); // start the listening server
	}
	
	public void mouseMove(float dx, float dy) // mouse mouse dx and dy
	{
		PointerInfo mouse = MouseInfo.getPointerInfo(); // based on current pos
		float x = (float) mouse.getLocation().getX();
		float y = (float) mouse.getLocation().getY();
		keyRobot.mouseMove((int)(x+dx), (int)(y+dy));
	}
	
	public void mousePress(int id) // press mouse id
	{
		keyRobot.mousePress(id);
	}
	
	public void mouseRelease(int id) // release moue id
	{
		keyRobot.mouseRelease(id);
	}
	
	public void mouseScroll(int d) // scroll mouse in direction
	{
		keyRobot.mouseWheel(d);
	}
	
	public void keyPress(int press) // press key
	{
		keyRobot.keyPress(press);
	}
	
	public void keyRelease(int release) /// release key
	{
		keyRobot.keyRelease(release);
	}
	
	public void setConfig(String key, String value) // change cinfig
	{
		if(key.equals("port")) // if it's port, do so
		{
			config.configFile.setProperty(key, value);
			log("[CONFIG] Your server " + key + " has been changed to: " + value);
			log("[CONFIG] The server must restart for changes to take effect.");
		}
		if(key.equals("debug")) // if it's debug, turn it on or off
		{
			if(value.equals("true"))
				debug = true;
			else
				debug = false;
			
			log("[NOTICE] Toggling debug mode to " + value);
		}
		else // no unknown config
		{
			log("[ERROR] There is no configuration item called: " + key);
		}
		config.getConfigs(); // reload configs after changes
	}
	
	public void restart() // restart the ;istening server
	{
		server.run = false;
		
		log("Restarting the server");
		log("Closing all network connections");
		server.endConnection();
		
		start(); // start once shutdown 
	}
	
	public void start()
	{
		log("=========================");
		log("Starting the server");
		server.initiateThread(); // start thres
		server.startConnection(); // start listee=ning 
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
