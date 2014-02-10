/**
 * Solver.java
 * Copyright 2010, Craig A. Damon
 * all rights reserved
 */

// WAS - package edu.vtc.cis4210.raytracer;
package info.fisherevans.info.vtc.compgfx.raytracing;

/**
 * Solver - a generalized simultaneous equation solver
 * @author Craig A. Damon
 *
 */
public class Solver
{
	/**
	 * create a solver for a specific problem of simultaneous linear equations with numVars variables in each
	 * @param numVars
	 */
  public Solver(int numVars)
  {
  	_numVars = numVars;
  	_equations = new float[numVars][];
  }
  
  /**
   * add an equation to the problem to be solved
   * @param eq the values describing this equation. The first n values should be the coefficients for the n variables.
   * The last value is the constant. The equation implicitly equates the sum of the n+1 terms to zero.
   */
  public void addEquation(float[] eq)
  {
  	if (eq.length != _numVars+1)
  		throw new IllegalArgumentException("Invalid sized equation provided");
  	for (int i = 0; i < _numVars; i++)
  		{
  			if (_equations[i] == null)
  				{
  					_equations[i] = new float[_numVars+1];
  					for (int j = 0; j <= _numVars; j++)
  						_equations[i][j] = eq[j];
  					return;
  				}
  		}
  	throw new IllegalArgumentException("Too many equations given for solver");
  }
  
  /**
   * solve the parallel equations
   *
   */
  public void solve()
  {
  	float[][] working = new float[_numVars][];
  	for (int i = 0; i < _numVars; i++)
  		{
  			if (_equations[i] == null)
  				{
  					throw new IllegalArgumentException("Missing equation "+i+" in solver");
  				}
  			working[i] = new float[_numVars+1];
  			for (int j = 0; j <= _numVars; j++)
  				working[i][j] = _equations[i][j];
  		}
  	
  	// zero the ith variable in all equations below i for all variables up to but not including the last
  	for (int i = 0; i < _numVars-1; i++)
  		{
  			// if the ith equation has a zero there already, swap it with the next non-zero one if any
  			if (working[i][i] == 0.0f)
  				{
  					for (int j = i+1; j < _numVars; j++)
  						{
  							if (working[j][i] != 0.0f)
  								{
  									float[] t = working[i];
  									working[i] = working[j];
  									working[j] = t;
  									break;
  								}
  						}
  					if (working[i][i] == 0.0f)
  						continue;  // already zero everywhere
  				}
  			
  			// now eliminate variable i from all equations below i
  			for (int j = i+1; j < _numVars; j++)
  				{
  					if (working[j][i] == 0.0f)
  						continue;  // already eliminated
  					float scalar = working[j][i]/working[i][i];
  					working[j][i] = 0.0f;
  					for (int k = i+1; k <= _numVars; k++)
  						working[j][k] -= working[i][k]*scalar;
  				}
  		}
  	
  	// work backwards to solve 
  	_results = new float[_numVars];
  	for (int i = _numVars-1; i >= 0; i--)
  		{
  			float x = working[i][_numVars];
  			for (int j = i+1; j < _numVars; j++)
  				{
  					x += working[i][j]*_results[j];
  				}
  			_results[i] = -x/working[i][i];
  		}

  	// now should have solution
  	// lets double check that
  	for (int i = 0; i < _numVars; i++)
  		{
  			float x = _equations[i][_numVars];
  			for (int j = 0; j < _numVars; j++)
  				x += _equations[i][j]*_results[j];
  			if (Math.abs(x) >= 0.01)
  				throw new IllegalArgumentException("solver failed somehow on equation "+i+" results = "+_results+" for equations "+_equations);
  		}
  }
  
  /**
   * get the result for the nth variable
   * @param n a zero-based count of the unknowns in the equations
   * @return the value for the nth variable that satisfies the given equations
   */
  public float getResult(int n)
  {
  	if (_results == null)
  		solve();
  	return _results[n];
  }
  
  public String toString()
  {
  	StringBuffer buffer = new StringBuffer(_numVars*50);
  	for (int i = 0; i < _numVars; i++)
  		{
  	    for (int j = 0; j < _numVars; j++)
  	    	{
  	    		buffer.append(_equations[i][j]);
  	    		buffer.append(" x");
  	    		buffer.append(j+1);
  	    		buffer.append(" + ");
  	    	}
  	    buffer.append(_equations[i][_numVars]);
  	    buffer.append(" = 0\n");
  		}
  	
  	return new String(buffer);
  }
  
  
  
  private int _numVars;  // the number of variables
  private float[] _results;  // the numvars results
  private float[][] _equations; // the equation data
}