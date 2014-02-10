package info.fisherevans.info.vtc.compgfx.raytracing;

public class Unknowns
{
	/*
	 * Math from http://www.cplusplus.happycodings.com/Mathematics/code5.html
	 * 
	 * ax+by+cz+d=0
	 * lx+my+nz+k=0
	 * px+qy+rz+s=0
	 *
	 * Enter the coefficients in the order a,b,c,d,l,m,n,k,p,q,r,s
	 */
	static float[] solveThreeUnknowns(float a, float b, float c, float d,
		float l, float m, float n, float k,
		float p, float q, float r, float s)
	{
		float D = (a*m*r+b*p*n+c*l*q)-(a*n*q+b*l*r+c*m*p);
		float x = ((b*r*k+c*m*s+d*n*q)-(b*n*s+c*q*k+d*m*r))/D;
		float y = ((a*n*s+c*p*k+d*l*r)-(a*r*k+c*l*s+d*n*p))/D;
		float z = ((a*q*k+b*l*s+d*m*p)-(a*m*s+b*p*k+d*l*q))/D;
		float results[] = {x, y, z};
		return results;
	}
}
