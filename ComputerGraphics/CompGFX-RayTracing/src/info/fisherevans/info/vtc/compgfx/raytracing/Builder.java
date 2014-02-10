package info.fisherevans.info.vtc.compgfx.raytracing;

import java.awt.Color;
import java.util.ArrayList;

public class Builder
{
	private ArrayList<Triangle3D> _triangles;
	
	private final float PP_WIDTH = 2f;
	private final float PP_HEIGHT = 2f;
	private final int PIX_WIDTH = 400;
	private final int PIX_HEIGHT = 400;
	private final float FOCAL_LENGTH = 1f;
	
	public Builder()
	{
		_triangles = new ArrayList<Triangle3D>();

		System.out.println("Adding triangles");
		addTriangles();

		System.out.println("Creating Render Object");
		Render rayTracer = new Render(_triangles, PP_WIDTH, PP_HEIGHT, PIX_WIDTH, PIX_HEIGHT, FOCAL_LENGTH);
		rayTracer.displayRaster();
	}
	
	private void addTriangles()
	{
		Point3D tempA, tempB, tempC;
		Triangle3D tempTri;
		
		tempTri = new Triangle3D(
				new Point3D(-1f, -1f, 3f),
				new Vector3D(new Point3D(1f, 2f, 0f)),
				new Vector3D(new Point3D(2f, 0f, 0f)));
		tempTri.setColor(Color.RED);
		_triangles.add(tempTri);
	}
	
	public static void main(String[] args)
	{
		Builder main = new Builder();
	}
}
