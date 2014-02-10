package info.fisherevans.school.compgfx.matrixtranslation.comps;

public class MatrixMath
{
	/**
	 * 
	 * @param fourFour [x][y]
	 * @param oneFour [y]
	 * @return result oneFour
	 */
	public static float[] FourFourTimesOneFour(float[][] fourFour, float[] oneFour)
	{
		float[] result = new float[4];
		result[0] = fourFour[0][0]*oneFour[0] + fourFour[0][1]*oneFour[1] + fourFour[0][2]*oneFour[2] + fourFour[0][3]*oneFour[3];
		result[1] = fourFour[1][0]*oneFour[0] + fourFour[1][1]*oneFour[1] + fourFour[1][2]*oneFour[2] + fourFour[1][3]*oneFour[3];
		result[2] = fourFour[2][0]*oneFour[0] + fourFour[2][1]*oneFour[1] + fourFour[2][2]*oneFour[2] + fourFour[2][3]*oneFour[3];
		result[3] = fourFour[3][0]*oneFour[0] + fourFour[3][1]*oneFour[1] + fourFour[3][2]*oneFour[2] + fourFour[3][3]*oneFour[3];
		return result;
	}
}
