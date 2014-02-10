package info.fisherevans.school.compgfx.matrixtranslation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Logger
{
	private Controler _parent;
	private String _logFileName;
	private Writer _logBuffer;
	
	private boolean _logging;
	
	public Logger(Controler parent)
	{
		_parent = parent;
		_logging = true;
		
		Calendar calendar = new GregorianCalendar();
		_logFileName = String.format("MatrixTranslator_%04d.%02d.%02d_%02d.%02d.%02d.log",
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		
		try
		{
			File logFile = new File(_logFileName);
			if(!logFile.exists())
				logFile.createNewFile();
			
			_logBuffer = new BufferedWriter(new FileWriter(_logFileName, true));
		}
		catch(Exception e) { logError(e); }
	}
	
	public void printToLog(String line)
	{
		if(_logging)
		{
			try
			{
				_logBuffer.append(line + "\n");
				_logBuffer.flush();
			}
			catch(Exception e) { logError(e); }
		}
	}
	
	public void closeLog()
	{
		if(_logging)
		{
			Calendar calendar = new GregorianCalendar();
			String exitLine = String.format("\nThis log has ended at: %04d.%02d.%02d %02d:%02d:%02d",
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
			printToLog(exitLine);
			
			System.out.println("\nThis session was logged in the file:\n" + _logFileName);
			
			try { _logBuffer.close(); }
			catch(Exception e) { logError(e); }
		}
	}
	
	private void logError(Exception e)
	{
		_logging = false;
		e.printStackTrace();
		System.out.println();
		_parent.log("There was an error opening/writing the log file. This session will not be logged, this error does not effect the rest of the program.");
	}
}
