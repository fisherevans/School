package info.fisherevans.vtc.compgfx.threedtranslator;

import info.fisherevans.vtc.compgfx.threedtranslator.components.Point;
import info.fisherevans.vtc.compgfx.threedtranslator.components.Shape;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Translator
{
	private Point _cameraBase;
	private float _cameraPitch, _cameraYaw, _cameraRoll;
	private ArrayList<Shape> _shapes;
	
	public Translator(String inputFileName)
	{
		_shapes = new ArrayList<Shape>();
		try
			{ parseInputFile(inputFileName); }
		catch(Exception e)
			{ System.out.println("There was an error reading the input file."); e.printStackTrace(); }
		
		
	}
	
	/** Parses an input file. And gets variables from it.
	 * @param inputFileName location of file to input
	 */
	private void parseInputFile(String inputFileName) throws Exception
	{
		Scanner config = new Scanner(new FileReader(new File(inputFileName)));
		String lineBuffer;
		String[] lineArray;
		
		while(config.hasNextLine())
		{
			lineBuffer = config.nextLine();
			lineBuffer = lineBuffer.replaceAll("#.*", "").replaceAll(",", ":").replaceAll(" ", "");
			lineArray = lineBuffer.split(":");
			
			if(lineArray[0].equals("cameraBase"))
			{ _cameraBase = new Point(Float.parseFloat(lineArray[1]), Float.parseFloat(lineArray[2]), Float.parseFloat(lineArray[3])); }
			else if(lineArray[0].equals("cameraAngles"))
			{
				_cameraRoll = Float.parseFloat(lineArray[1]);
				_cameraPitch = Float.parseFloat(lineArray[2]);
				_cameraYaw = Float.parseFloat(lineArray[3]);
			}
			else if(lineArray[0].equals("shape"))
			{
				if(lineArray.length-2 % 3 != 0) { System.out.println("Invalid shape! -> " + lineBuffer); }
				else
				{
					ArrayList<Point> tempPoints = new ArrayList<Point>();
					for(int index = 2;index < lineArray.length;index += 3)
					{
						tempPoints.add(new Point(Float.parseFloat(lineArray[index]), Float.parseFloat(lineArray[index+1]), Float.parseFloat(lineArray[index+2])));
					}
					_shapes.add(new Shape(tempPoints, lineArray[1]));
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		String inputFileName;
		if(args.length == 0)
			inputFileName = "res/defaultConfig.txt";
		else
			inputFileName = args[0];
		
		new Translator(inputFileName);
	}
}
