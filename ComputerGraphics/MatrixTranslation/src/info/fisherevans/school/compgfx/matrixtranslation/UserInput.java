package info.fisherevans.school.compgfx.matrixtranslation;

import info.fisherevans.school.compgfx.matrixtranslation.comps.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;
/*
			1     0     0    0
rotate x    0     Cos  -Sin  0
			0     Sin   Cos  0
			0     0     0    1

			Cos   0     Sin  0
rotate y    0     1     0    0
			-Sin   0     Cos  0
			0     0     0    1

			 Cos  -Sin   0    0
rotate  z    Sin   Cos   0    0
			 0     0     1    0
			 0     0     0    1

*/
public class UserInput
{
	private Controler _parent;
	private BufferedReader _in;
	
	private UserInputThread _userInputRunnable;
	private Thread _userInput;
	
	public UserInput(Controler parent)
	{
		_parent = parent;
		
		_userInputRunnable = new UserInputThread();
		_userInput = new Thread(_userInputRunnable);
	}
	
	public void startThread()
	{
		try { _userInput.start(); }
		catch(Exception e) { _parent.log("[FATAL ERROR] Could not read STDIN."); e.printStackTrace(); }
	}
	
	public void runCommand(String command)
	{
		command = command.replaceAll("#.*", "");
		_parent.logger.printToLog("USER COMMAND >> " + command);
		String[] commandArray = command.split(" ");
		if(command.startsWith("help"))
		{
			_parent.log("TODO -> Help Command.");
		}
		else if(command.startsWith("run"))
		{
			try
			{
				Scanner read = new Scanner(new FileReader(new File(command.replaceAll("run ", ""))));
				_parent.log("START RUNNING AUTOMATED ANSWER FILE: " + command.replaceAll("run ",""));
				while(read.hasNextLine()) { String temp = read.nextLine(); System.out.println("> " + temp); runCommand(temp); }
				_parent.log("DONE RUNNING AUTOMATED ANSWER FILE: " + command);
			}
			catch(Exception e) { _parent.log("There was an error loading the file: " + command); e.printStackTrace(); }
		}
		else if(command.startsWith("selected"))
		{
			_parent._world.selected();
		}
		else if(command.startsWith("add shape"))
		{
			_parent._world.addShape();
		}
		else if(command.startsWith("add point"))
		{
			try { _parent._world.addPoint(new Point(Float.parseFloat(commandArray[2]), Float.parseFloat(commandArray[3]), Float.parseFloat(commandArray[4]), 1f)); }
			catch(Exception e) { _parent.log("[ERROR] Please enter 3 parsable floating point numbers."); }
		}
		else if(command.startsWith("select shape"))
		{
			try { _parent._world.selectShape(Integer.parseInt(commandArray[2])); }
			catch(Exception e) { _parent.log("[ERROR] Please enter a parsable integer."); }
		}
		else if(command.startsWith("select point"))
		{
			try { _parent._world.selectPoint(Integer.parseInt(commandArray[2])); }
			catch(Exception e) { _parent.log("[ERROR] Please enter a parsable integer."); }
		}
		else if(command.startsWith("scale "))
		{
			try
			{
				float[][] scaler = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 1 } };
				scaler[0][0] = Float.parseFloat(commandArray[1]);
				scaler[1][1] = Float.parseFloat(commandArray[2]);
				scaler[2][2] = Float.parseFloat(commandArray[3]);

				if(commandArray.length == 5 && commandArray[4].equals("shape"))
					_parent._world.matrixMultShape(scaler);
				else
					_parent._world.matixMult(scaler);
			}
			catch(Exception e) { _parent.log("[ERROR] Please enter one or three parsable floats."); }
		}
		else if(command.startsWith("translate "))
		{
			try
			{
				float[][] scaler = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
				scaler[0][3] = Float.parseFloat(commandArray[1]);
				scaler[1][3] = Float.parseFloat(commandArray[2]);
				scaler[2][3] = Float.parseFloat(commandArray[3]);

				if(commandArray.length == 5 && commandArray[4].equals("shape"))
					_parent._world.matrixMultShape(scaler);
				else
					_parent._world.matixMult(scaler);
			}
			catch(Exception e) { _parent.log("[ERROR] Please enter one or three parsable floats."); }
		}
		else if(command.startsWith("rotate "))
		{
			try
			{
				float angle = Float.parseFloat(commandArray[2]);
				float cos = (float) Math.cos(Math.toRadians(angle));
				float sin = (float) Math.sin(Math.toRadians(angle));

				float[][] rotate = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
				float[][] x = { { 1, 0, 0, 0 }, { 0, cos, -sin, 0 }, { 0, sin, cos, 0 }, { 0, 0, 0, 1 } };
				float[][] y = { { cos, 0, sin, 0 }, { 0, 1, 0, 0 }, { -sin, 0, cos, 0 }, { 0, 0, 0, 1 } };
				float[][] z = { { cos, -sin, 0, 0 }, { sin, cos, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
				
				boolean badIn = true;
				if(commandArray[1].equals("x")) { rotate = x; badIn = false; }
				else if(commandArray[1].equals("y")) { rotate = y; badIn = false; }
				else if(commandArray[1].equals("z")) { rotate = z; badIn = false; }
				if(badIn) { _parent.log("[ERROR] Bad input. Use: rotate <x/y/z> <angle> [shape]"); }
				
				else
				{
					if(commandArray.length == 4 && commandArray[3].equals("shape"))
						_parent._world.matrixMultShape(rotate);
					else
						_parent._world.matixMult(rotate);
				}
			}
			catch(Exception e) { _parent.log("[ERROR] Please enter a float rotationa angle in degrees."); e.printStackTrace(); }
		}
		else if(command.startsWith("shapes"))
		{
			_parent._world.printShapes();
		}
		else if(command.startsWith("points"))
		{
			if(commandArray.length > 1)
			{
				try { _parent._world.printPoints(Integer.parseInt(commandArray[1])); }
				catch(Exception e) { _parent.log("[ERROR] Please enter a parsable integer."); }
			}
			else
				_parent._world.printPoints();
		}
		else if(command.startsWith("quit"))
		{
			_parent.log("Quitting now...");
			_parent.logger.closeLog();
			System.exit(0);
		}
		else
		{
			_parent.log("[ERROR] " + command + " is not a command.");
		}
	}
	
	public class UserInputThread implements Runnable
	{
		public void run()
		{
			_in = new BufferedReader(new InputStreamReader(System.in));
			try
			{
				while(true)
				{
					runCommand(_in.readLine());
				}
			} catch(Exception e) { }
		}
	}
}
