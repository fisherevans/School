package info.fisherevans.school.compgfx.matrixtranslation;

import info.fisherevans.school.compgfx.matrixtranslation.comps.World;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Controler
{
	private UserInput _userInput;
	public Logger logger;
	public World _world;
	
	public Controler()
	{
		logger = new Logger(this);
		_world = new World(this);
		
		log("Starting the 3D Matrix Translator.");
		log("Created by David \"Fisher\" Evans.");
		
		log("Enter \"help\" to see a list of available commands.");
		
		_userInput = new UserInput(this);
		_userInput.startThread();
	}

	public void log(String message)
	{
		Calendar calendar = new GregorianCalendar();
		String line = String.format("[%02d:%02d:%02d] %s",
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
				message);
		System.out.println(line);
		logger.printToLog(line);
	}
	
	public static void main(String[] args)
	{ new Controler(); }
}
