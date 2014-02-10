package info.fisherevans.droidmote.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserInput
{
	private Main parent;
	private BufferedReader in;
	
	private UserInputThread userInputRunnable;
	private Thread userInput;
	
	public UserInput(Main main)
	{
		parent = main;
		
		userInputRunnable = new UserInputThread();
		userInput = new Thread(userInputRunnable);
	}
	
	public void startThread()
	{
		userInput.start();
	}
	
	public class UserInputThread implements Runnable
	{
		public void run()
		{
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String command;
			
			try
			{
				while(true)
				{
					command = input.readLine();
					if(command.equals("quit"))
					{
						parent.quit();
					}
					else if(command.equals("restart"))
					{
						parent.restart();
					}
					else if (command.startsWith("set "))
					{
						String commands[] = command.split(" ");
						parent.setConfig(commands[1], commands[2]);
					}
					else
					{
						parent.log("[ERROR] " + command + " is not a recognised command.");
					}
				}
			} catch(Exception e) { }
		}
	}
}
