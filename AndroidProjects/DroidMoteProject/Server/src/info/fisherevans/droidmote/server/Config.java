package info.fisherevans.droidmote.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
	private Main parent;
	public Properties configFile;
	
	public int port;
	
	public Config(Main parent)
	{
		this.parent = parent;
		
		parent.debug = false;
		
		try
		{
			configFile = new Properties(); // load config fi;e
			configFile.load(new FileInputStream("DroidMote.cfg"));
			parent.log("File stream successfully opened");
		}
		catch(Exception e)
		{
			parent.log("There was an error loading the config file"); // if it's not there, tell em
			e.printStackTrace();
			System.exit(1);
		}
		
		getConfigs(); // load port to var

		configFile.setProperty("port", port+"");
	}
	
	public void getConfigs()
	{
		port = Integer.parseInt(configFile.getProperty("port", "63930"));
	}
}