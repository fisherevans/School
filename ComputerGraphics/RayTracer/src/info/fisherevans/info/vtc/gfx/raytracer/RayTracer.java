package info.fisherevans.info.vtc.gfx.raytracer;

import info.fisherevans.info.vtc.gfx.raytracer.components.Light;
import info.fisherevans.info.vtc.gfx.raytracer.components.Pixel;
import info.fisherevans.info.vtc.gfx.raytracer.components.Point;
import info.fisherevans.info.vtc.gfx.raytracer.components.Triangle;
import info.fisherevans.info.vtc.gfx.raytracer.components.Vector;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class RayTracer
{
	private ArrayList<Triangle> _triangles;
	private ArrayList<Pixel> _pixels;
	private ArrayList<Light> _lights;
	private Color _ambientLight;
	private Point _ppA, _ppB;
	private int _pixWide, _pixHigh;
	private int _aaDepth;
	private int _displayScale;
	private String _inputFile;
	
	/** the main controler of the ray tracer. Reads in a configuration file, computes the rays, traces them and passes the results to a display object.
	 * @param input The location of the config file to use.
	 */
	public RayTracer(String input)
	{
		_triangles = new ArrayList<Triangle>();
		_pixels = new ArrayList<Pixel>();
		_lights = new ArrayList<Light>();
		_inputFile = input;
		
		parseInputFile();
		
		System.out.println("Using " + (Runtime.getRuntime().totalMemory()/(1024*1024)) + "MB of memory.");
		
		generatePixels();

		System.out.println("Using " + (Runtime.getRuntime().totalMemory()/(1024*1024)) + "MB of memory.");
		
		traceRays();

		System.out.println("Using " + (Runtime.getRuntime().totalMemory()/(1024*1024)) + "MB of memory.");

		System.out.println("Displaying the generated raster image based on the previous calculations.");
		System.out.println();
		new Display(_pixels, _pixWide, _pixHigh, _displayScale);
	}
	
	/** parses a configuration file that defines camera settings and triangles. */
	private void parseInputFile()
	{
		try
		{
			Scanner read = new Scanner(new FileReader(new File(_inputFile)));
			try
			{
				_pixWide = -1;
				_pixHigh = -1;
				_aaDepth = -1;
				_displayScale = 1;
				while(read.hasNextLine())
					parseInputLine(read.nextLine());
				
				checkInputErrors();
			}
			catch(Exception e)
			{
				System.out.println("There was a fatal syntax error in the file '" + _inputFile + "'. Please make sure to use the correct file syntax.");
				e.printStackTrace();
				System.exit(2);
			}
			
			printVariables();
		}
		catch(Exception e)
		{
			System.out.println("There was an eerror opening the file '" + _inputFile + "'. Please ensure the file is there, and is not read protected.");
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	/** given a string, set a camera/world variable, or ignore */
	private void parseInputLine(String line)
	{
		line = line.replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(",", ":").replaceAll("#.*", "");
		String[] lineArray = line.split(":");
		if(lineArray[0].equals("pp_a"))
			_ppA = new Point(Float.parseFloat(lineArray[1]), Float.parseFloat(lineArray[2]), Float.parseFloat(lineArray[3]));
		else if(lineArray[0].equals("pp_b"))
			_ppB = new Point(Float.parseFloat(lineArray[1]), Float.parseFloat(lineArray[2]), Float.parseFloat(lineArray[3]));
		else if(lineArray[0].equals("pix_wide"))
			_pixWide = Integer.parseInt(lineArray[1]);
		else if(lineArray[0].equals("pix_high"))
			_pixHigh = Integer.parseInt(lineArray[1]);
		else if(lineArray[0].equals("aa_depth"))
			_aaDepth = Integer.parseInt(lineArray[1]);
		else if(lineArray[0].equals("display_scale"))
			_displayScale = Integer.parseInt(lineArray[1]);
		else if(lineArray[0].equals("ambient_light"))
			_ambientLight = new Color(Float.parseFloat(lineArray[1])/((float)255), Float.parseFloat(lineArray[2])/((float)255), Float.parseFloat(lineArray[3])/((float)255));
		else if(lineArray[0].equals("triangle"))
		{
			Point tempBase = new Point(Float.parseFloat(lineArray[1]), Float.parseFloat(lineArray[2]), Float.parseFloat(lineArray[3]));
			Vector tempBA = new Vector(Float.parseFloat(lineArray[4]), Float.parseFloat(lineArray[5]), Float.parseFloat(lineArray[6]));
			Vector tempBC = new Vector(Float.parseFloat(lineArray[7]), Float.parseFloat(lineArray[8]), Float.parseFloat(lineArray[9]));
			Color tempColor = new Color(Float.parseFloat(lineArray[10])/((float)255), Float.parseFloat(lineArray[11])/((float)255), Float.parseFloat(lineArray[12])/((float)255));
			_triangles.add(new Triangle(tempBase, tempBA, tempBC, tempColor, Integer.parseInt(lineArray[13])));
		}
		else if(lineArray[0].equals("light"))
		{
			Point tempPosition = new Point(Float.parseFloat(lineArray[1]), Float.parseFloat(lineArray[2]), Float.parseFloat(lineArray[3]));
			Color tempColor = new Color(Float.parseFloat(lineArray[4])/((float)255), Float.parseFloat(lineArray[5])/((float)255), Float.parseFloat(lineArray[6])/((float)255));
			_lights.add(new Light(tempPosition, tempColor, Float.parseFloat(lineArray[7])));
		}
	}
	
	/** check for missing inputs, tell them and exit. */
	private void checkInputErrors()
	{
		boolean syntaxError = false;
		if(_ppA == null) { System.out.println("Please enter a valid value for 'pp_a' in the file '"+_inputFile+"'."); syntaxError = true; }
		if(_ppB == null) { System.out.println("Please enter a valid value for 'pp_b' in the file '"+_inputFile+"'."); syntaxError = true; }
		if(_ambientLight == null) { System.out.println("Please enter a valid value for 'ambient_light' in the file '"+_inputFile+"'."); syntaxError = true; }
		if(_pixWide <= 0) { System.out.println("Please enter a valid value for 'pix_wide' in the file '"+_inputFile+"'."); syntaxError = true; }
		if(_pixHigh <= 0) { System.out.println("Please enter a valid value for 'pix_high' in the file '"+_inputFile+"'."); syntaxError = true; }
		if(_aaDepth <= 0) { System.out.println("Please enter a valid value for 'aa_depth' in the file '"+_inputFile+"'."); syntaxError = true; }
		if(_triangles.size() == 0) { System.out.println("There are no triangles defined in '"+_inputFile+"'."); syntaxError = true; }
		if(_lights.size() == 0) { System.out.println("There are is no light defined in '"+_inputFile+"'."); syntaxError = true; }
			
		if(syntaxError)
		{
			System.out.println();
			System.out.println("Please fix the errors in your config file.");
			System.exit(1);
		}
	}
	
	/** prints out all variables that effect the render */
	private void printVariables()
	{
		System.out.println("These configurations were loading from the following file:");
		System.out.println("  " + _inputFile);
		System.out.println();
		System.out.println("Generating rendor based on the following info:");
		System.out.println("  pp_a = " + _ppA);
		System.out.println("  pp_b = " + _ppB);
		System.out.println("  pix_width = " + _pixWide);
		System.out.println("  pix_high = " + _pixHigh);
		System.out.println("  aa_depth = " + _aaDepth);
		System.out.println("  display_scale = " + _displayScale);
		System.out.println("  ambient_light = " + _ambientLight);
		System.out.println();
		System.out.println("Using the following Lights:");
		for(Light light:_lights)
			System.out.println("  " + light);
		System.out.println();
		System.out.println("Using the following Triangles:");
		for(Triangle triangle:_triangles)
			System.out.println("  " + triangle);
		
		System.out.println();
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
	}
	
	/** Generates a list of pixels that define the picture plane. Stored in _pixels. */
	private void generatePixels()
	{
		System.out.println("Generating the pixels and their ray(s).");
		
		float unitWide = _ppB.getX() - _ppA.getX();
		float unitHigh = _ppA.getY() - _ppB.getY();
		
		float pixWidth = unitWide/_pixWide;
		float pixHeight = unitHigh/_pixHigh;
		
		for(int x = 0;x < _pixWide;x++)
		{
			for(int y = 0;y < _pixHigh;y++)
			{
				Point pixTL = new Point((float)(_ppA.getX() + ((float)x)*pixWidth), (float)(_ppA.getY() - ((float)y)*pixHeight), _ppA.getZ());
				Point pixBR = new Point(pixTL.getX() + pixWidth, pixTL.getY() - pixHeight, pixTL.getZ());
				_pixels.add(new Pixel(x, y, pixTL, pixBR, _aaDepth));
			}
		}
	}
	
	/** traces the rays defined in the pixels listed in _pixels and detirmns the color to display int he raster. */
	private void traceRays()
	{
		System.out.println("Tracing each pixel's ray(s) to intersections.");
		float ambientR = (float)(_ambientLight.getRed()/255.0);
		float ambientG = (float)(_ambientLight.getGreen()/255.0);
		float ambientB = (float)(_ambientLight.getBlue()/255.0);
		
		for(Pixel pixel:_pixels) // for each pixel
		{
			ArrayList<Vector> pixVectors = pixel.getVectors();
			ArrayList<Color> pixColors = new ArrayList<Color>();
			for(Vector pixVector:pixVectors) // go through each defining ray
			{
				float minumunDistance = 1000;
				Triangle closestTriangle = null;
				float[] closestResult = new float[3];
				for(Triangle triangle:_triangles) // And check for intersections with triangles to draw
				{
					float UVT[] = RTMath.solveThreeUnknowns(triangle.getBA().getX(), triangle.getBC().getX(), -pixVector.getX(), triangle.getBase().getX(),
							triangle.getBA().getY(), triangle.getBC().getY(), -pixVector.getY(), triangle.getBase().getY(),
							triangle.getBA().getZ(), triangle.getBC().getZ(), -pixVector.getZ(), triangle.getBase().getZ());
					float u = UVT[0];
					float v = UVT[1];
					float t = UVT[2];

					if(RTMath.numberIsWithin(u, 0, 1) && RTMath.numberIsWithin(v, 0, 1) && RTMath.numberIsWithin(u+v, 0, 1) && t < minumunDistance) // keep track of the closest triangle
					{
						minumunDistance = t;
						closestTriangle = triangle;
						closestResult = UVT;
					}
				}
				
				if(closestTriangle == null) // If the ray doesn't hit anything
					pixColors.add(Color.BLACK);
				else // otherwise, what's the lighting like?
				{
					float[] lightColors = {0, 0, 0};
					for(Light light:_lights)
					{
						Color ci = light.getColor();
						float ciR = (float)(ci.getRed()/255.0);
						float ciG = (float)(ci.getGreen()/255.0);
						float ciB = (float)(ci.getBlue()/255.0);
						Vector intersection = new Vector(pixVector.getX(), pixVector.getY(), pixVector.getZ());
							intersection.multiplyVector(closestResult[2]);
						Vector l = new Vector(light.getPosition().getX()-intersection.getX(), light.getPosition().getY()-intersection.getY(), light.getPosition().getZ()-intersection.getZ());
						Color cr = closestTriangle.getColor();
						float crR = (float)(cr.getRed()/255.0); float crG = (float)(cr.getGreen()/255.0); float crB = (float)(cr.getBlue()/255.0);
						int shinnyness = closestTriangle.getShinny();
						boolean lit = true;
						for(Triangle triangle:_triangles) // go through each triangle and see if they intersect with the intersection the the light
						{
							if(!triangle.equals(closestTriangle) && triangle.doesIntersect(intersection, light.getPosition()))
							{
								lit = false;
							}
						}
						
						if(lit) // if there is nothing in the way, calcualte the light
						{
							//System.out.println("LIGHT");
							// scale based on y = -((x-1)^2) + 1
							float scale = RTMath.limitFloat((float)(-1*Math.pow(((light.getDistance()-l.getLength())/light.getDistance())-1, 2)+1), 0, 1);
							Vector n = closestTriangle.getNormal();
							Vector h = (new Vector(l.getX()-intersection.getX(), l.getY()-intersection.getY(), l.getZ()-intersection.getZ()));
							float dotNL = (float)Math.abs((double)RTMath.dotProduct(n.getNormalaized(), l.getNormalaized()));
							float dotNH = (float)Math.abs((double)RTMath.dotProduct(n.getNormalaized(), h.getNormalaized()));
							
							//System.out.printf("%f*(%f+(%f*%f)), %f*(%f+(%f*%f)), %f*(%f+(%f*%f))\n", crR, ambientR, ciR, dotNL, crG, ambientG, ciG, dotNL, crB, ambientB, ciB, dotNL);
							
							lightColors[0] += (float) Math.min(1,crR*(ambientR + (ciR*dotNL)*scale) + Math.pow(dotNH, shinnyness)*ciR);
							lightColors[1] += (float) Math.min(1,crG*(ambientG + (ciG*dotNL)*scale) + Math.pow(dotNH, shinnyness)*ciG);
							lightColors[2] += (float) Math.min(1,crB*(ambientB + (ciB*dotNL)*scale) + Math.pow(dotNH, shinnyness)*ciB);
						}
						else // other wise, just use ambient lighting.
						{
							//System.out.println("NO LIGHT");
							lightColors[0] += crR*ambientR;
							lightColors[1] += crG*ambientG;
							lightColors[2] += crB*ambientB;
						}
					} // end of light loop
					pixColors.add(new Color(RTMath.limitFloat(lightColors[0], 0, 1), RTMath.limitFloat(lightColors[1], 0, 1), RTMath.limitFloat(lightColors[2], 0, 1)));
				}
				pixel.setColor(pixColors); // average the colors of the rays per this pixel
			}
		}
	}
	
	/** The method called when you run the program. Displays an menu to render a default image, school image or your own.
	 * @param args Not used
	 */
	public static void main(String[] args) throws IOException
	{
		System.out.println("Please select one of the following to render:");
		System.out.println("1) Default Rendor");
		System.out.println("2) School Assignment");
		System.out.println("3) Custom Location");
		System.out.print("> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String selection = br.readLine();
		
		if(selection.startsWith("1"))
			new RayTracer("res/default.txt");
		else if(selection.startsWith("2"))
			new RayTracer("res/school.txt");
		else if(selection.startsWith("3"))
		{
			System.out.print("Please enter a valid file name:\n> ");
			new RayTracer(br.readLine());
		}
		else
		{
			System.out.println("Please enter a valid selection!");
			main(args);
		}
		
		br = null;
	}
}
