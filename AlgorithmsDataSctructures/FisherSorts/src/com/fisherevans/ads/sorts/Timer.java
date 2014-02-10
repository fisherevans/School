package com.fisherevans.ads.sorts;

/**
 * Class to collect timings of other code.  Usage:
 * <ul>
 * <li>Timer stopwatch = new Timer();      // creates a timer </li>
 * 
 * <li>stopwatch.start();     // start the timer </li>
 * <li>&nbsp&nbsp&nbsp&nbsp// do the stuff you want to time in here</li>
 * <li> stopwatch.stop();    // stop the timer </li>
 * 
 * <li>int elapsedMillis = stopwatch.time();   // read the timer </li>
 * <li> stopwatch.reset();   // clear the timer before timing anything else </li>
 * <br>
 * @author ldamon
 * @version 7 Nov 2012
 *
 */
public class Timer {
	
	private long _startTime;
	private boolean _hasRun;
	private long _endTime;
	
	public Timer()
	{
		_startTime = 0;
		_hasRun = false;
	}
	
	/**
	 * starts the timing
	 */
	public void start()
	{
		_hasRun = true;
		_startTime = System.currentTimeMillis();
	}
	
	/**
	 * ends the current timing
	 */
	public void stop()
	{
		_endTime = System.currentTimeMillis();
	}
	
	/**
	 * calculates the amount of time the timer ran
	 * @return the time in milliseconds that the time was active between the start() and stop(). Remember to divide
	 *  by 1000 to convert to seconds.
	 */
	public long time()
	{
		if (_hasRun)
			return _endTime - _startTime;
		else
			return -1;
	}
	
	/**
	 * reset the Timer so it is ready to start timing again.
	 */
	public void reset()
	{
		_hasRun = false;
		_startTime = 0;
		_endTime = 0;
	}

}
